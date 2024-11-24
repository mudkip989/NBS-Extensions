package us.mudkip989.mods.nbs_extensions.gui;

import net.minecraft.client.font.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import us.mudkip989.mods.nbs_extensions.*;
import us.mudkip989.mods.nbs_extensions.gui.widget.*;
import us.mudkip989.mods.nbs_extensions.sys.*;
import us.mudkip989.mods.nbs_extensions.util.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import static us.mudkip989.mods.nbs_extensions.nbs.NBSLoad.loadNbs;

public class NBSScreen extends Screen {

    public TextFieldWidget searchBox;
    public SongListWidget thing2;
    public CheckboxWidget check;
    private Stream<Path> files;
    private String search;
    private boolean custom;


    public NBSScreen() {
        super(Text.literal("NBS Songs"));
    }

    Random random = new Random();

    @Override
    protected void init() {
        custom = false;
        try {
            files = Files.list(ExternalFile.NBS_FILES.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        thing2 = new SongListWidget(this.client, this.width, this.height - 80, 40, this.width - 160, 20);
        check = CheckboxWidget.builder(Text.literal("Use Custom Format"), NBSExtensions.MC.textRenderer).pos(this.width/2, this.height - 30).callback(new CheckboxWidget.Callback() {
            @Override
            public void onValueChange(CheckboxWidget checkbox, boolean checked) {
                custom = checked;
            }
        }).build();
        searchBox = new TextFieldWidget(NBSExtensions.MC.textRenderer, 40, this.height - 30, 100, 20, Text.literal("Search")) {
            @Override
            public boolean charTyped(char chr, int modifiers) {
                search = this.getText();
                refresh();
                return super.charTyped(chr, modifiers);
            }
        };
        files.filter(p -> p.toFile().getName().endsWith(".nbs")).forEach(p -> {
            thing2.add(new TextWidget(0, 20, Text.of(p.toFile().getName()), NBSExtensions.MC.textRenderer), ButtonWidget.builder(Text.literal("Import"), button -> {

                if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                    loadNbs(p.toFile(), custom);

                }

            }).dimensions(20, 20, 40, 20).build());

        });


            addDrawableChild(thing2);
            addDrawableChild(searchBox);
            addDrawableChild(check);
    }

    private void refresh() {
        remove(thing2);
        try {
            files = Files.list(ExternalFile.NBS_FILES.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thing2 = new SongListWidget(this.client, this.width, this.height - 80, 40, this.width - 160, 20);
        files.filter(p -> p.toFile().getName().endsWith(".nbs") && p.toFile().getName().contains(search)).forEach(p -> {
            thing2.add(new TextWidget(0, 20, Text.of(p.toFile().getName()), NBSExtensions.MC.textRenderer), ButtonWidget.builder(Text.literal("Import"), button -> {
                if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                    loadNbs(p.toFile(), custom);
                }

            }).dimensions(20, 20, 40, 20).build());

        });
        addDrawableChild(thing2);
    }
}


