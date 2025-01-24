package us.mudkip989.mods.nbs_extensions.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.commons.lang3.mutable.MutableBoolean;
import us.mudkip989.mods.nbs_extensions.NBSExtensions;
import us.mudkip989.mods.nbs_extensions.gui.widget.SongListWidget;
import us.mudkip989.mods.nbs_extensions.sys.ExternalFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static us.mudkip989.mods.nbs_extensions.nbs.NBSLoad.loadNbs;

public class NBSScreen extends Screen {

    public TextFieldWidget searchBox;
    public SongListWidget songList;
    public CheckboxWidget customFormat;

    private Stream<Path> files;
    private String search = "";
    private boolean custom;

    public NBSScreen() {
        super(Text.literal("NBS Songs"));
    }

    @Override
    protected void init() {
        custom = false;
        try {
            files = Files.list(ExternalFile.NBS_FILES.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        songList = new SongListWidget(this.client, this.width, this.height - 80, 40, this.width - 160, 20);
        customFormat = CheckboxWidget.builder(Text.literal("Use Custom Format"), NBSExtensions.MC.textRenderer).pos(this.width/2 + 5, this.height - 28).callback(new CheckboxWidget.Callback() {
            @Override
            public void onValueChange(CheckboxWidget checkbox, boolean checked) {
                custom = checked;
            }
        }).build();
        searchBox = new TextFieldWidget(NBSExtensions.MC.textRenderer, width / 2 - 100, 10, 200, 20, Text.literal("Search")) {
            @Override
            public boolean charTyped(char chr, int modifiers) {
                boolean test = super.charTyped(chr, modifiers);
                search = this.getText() == null ? "" : this.getText();
                refresh();
                return test;
            }

            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                boolean test = super.keyPressed(keyCode, scanCode, modifiers);
                search = this.getText() == null ? "" : this.getText();
                refresh();
                return test;
            }
        };
        files.filter(p -> p.toFile().getName().endsWith(".nbs")).forEach(p -> {
            songList.add(
                    new TextWidget(0, 20, Text.of(p.toFile().getName()), NBSExtensions.MC.textRenderer),
                    ButtonWidget.builder(Text.literal("Import"), button -> {
                                if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                                    loadNbs(p.toFile(), custom);
                                }
                            }
                    ).dimensions(20, 20, 40, 20).build()
            );
        });


        addDrawableChild(songList);
        addDrawableChild(searchBox);
        addDrawableChild(customFormat);
        addDrawableChild(ButtonWidget.builder(Text.literal("Open NBS Folder"), button -> Util.getOperatingSystem().open(ExternalFile.NBS_FILES.getPath())).dimensions(width / 2 - 100 - 5, this.height - 29, 100, 20).build());
    }

    private void refresh() {
        remove(songList);
        try {
            files = Files.list(ExternalFile.NBS_FILES.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        songList = new SongListWidget(this.client, this.width, this.height - 80, 40, this.width - 160, 20);
        files.filter(p -> p.toFile().getName().endsWith(".nbs") && p.toFile().getName().toLowerCase().contains(search.toLowerCase())).forEach(p -> {
            songList.add(new TextWidget(0, 20, Text.of(p.toFile().getName()), NBSExtensions.MC.textRenderer), ButtonWidget.builder(Text.literal("Import"), button -> {
                if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                    loadNbs(p.toFile(), custom);
                }

            }).dimensions(20, 20, 40, 20).build());

        });
        addDrawableChild(songList);
    }

    private static Stream<String> streamFileNames(Collection<Path> paths) {
        return paths.stream().map(Path::getFileName).map(Path::toString);
    }

    protected static void copySongs(MinecraftClient client, List<Path> srcPaths, Path destPath) {
        MutableBoolean mutableBoolean = new MutableBoolean();
        srcPaths.forEach((src) -> {
            try (Stream<Path> stream = Files.walk(src)) {
                stream.forEach((toCopy) -> {
                    try {
                        Util.relativeCopy(src.getParent(), destPath, toCopy);
                    } catch (IOException iOException) {
                        NBSExtensions.LOGGER.warn("Failed to copy nbs file from {} to {}", toCopy, destPath, iOException);
                        mutableBoolean.setTrue();
                    }
                });
            } catch (IOException var8) {
                NBSExtensions.LOGGER.warn("Failed to copy nbs file from {} to {}", src, destPath);
                mutableBoolean.setTrue();
            }
        });
        if (mutableBoolean.isTrue()) {
            SystemToast.add(client.getToastManager(), SystemToast.Type.PACK_COPY_FAILURE, Text.literal("Failed to copy songs"), Text.literal(destPath.toString()));
        }

    }

    @Override
    public void filesDragged(List<Path> paths) {
        if (this.client == null) return;
        String string = streamFileNames(paths).collect(Collectors.joining(", "));
        this.client.setScreen(new ConfirmScreen((confirmed) -> {
            if (confirmed) {
                ArrayList<Path> nbsFiles = new ArrayList<>();
                for (Path path : paths) {
                    if (path.toString().endsWith(".nbs")) {
                        nbsFiles.add(path);
                    }
                }

                if (!nbsFiles.isEmpty()) {
                    copySongs(this.client, nbsFiles, ExternalFile.NBS_FILES.getPath());
                    this.refresh();
                }
            }

            this.client.setScreen(this);
        }, Text.literal("Do you want to add the following song(s)?"), Text.literal(string)));
    }
}


