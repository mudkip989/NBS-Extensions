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

    public ButtonWidget button1;
    public ButtonWidget button2;
    public SongListWidget thing2;

    public NBSScreen() {
        super(Text.literal("NBS Songs"));
    }

    Random random = new Random();

    @Override
    protected void init() {
        Stream<Path> files;
        try {
            files = Files.list(ExternalFile.NBS_FILES.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        thing2 = new SongListWidget(this.client, this.width, this.height - 80, 40, this.width - 160, 20);

        files.forEach(p -> {
            thing2.add(new TextWidget(0, 20, Text.of(p.toFile().getName()), NBSExtensions.MC.textRenderer), ButtonWidget.builder(Text.literal("Import"), button -> {
                if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                    loadNbs(p.toFile(), true);
                }

            }).dimensions(20, 20, 40, 20).build());

        });




//        for (int i = 0; i < 40; i++) {
//            thing2.add(new TextWidget(Text.literal("Test"), NBSExtensions.MC.textRenderer), ButtonWidget.builder(Text.literal((String.valueOf(random.nextInt(100)))), button -> {
//                MessageUtil.send("Funny" + String.valueOf(random.nextInt(100)));
//            }).dimensions(20, 20, 100, 20).build());
//        }

            addDrawableChild(thing2);
    }
}


