package us.mudkip989.mods.nbs_extensions.gui;

import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import us.mudkip989.mods.nbs_extensions.*;
import us.mudkip989.mods.nbs_extensions.gui.widget.*;
import us.mudkip989.mods.nbs_extensions.sys.*;

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

import static us.mudkip989.mods.nbs_extensions.nbs.NBSLoad.loadNbs;

public class NBSScreen extends Screen {

    public TextFieldWidget searchBox;
    public SongListWidget songList;
    public CheckboxWidget customFormat;

    private Stream<Path> files;
    private String search;
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
                search = this.getText();
                refresh();
                return test;
            }

            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                boolean test = super.keyPressed(keyCode, scanCode, modifiers);
                search = this.getText();
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
}


