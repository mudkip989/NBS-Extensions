package us.mudkip989.mods.nbs_extensions.client.gui;

import net.minecraft.client.gui.navigation.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.*;
import org.jetbrains.annotations.*;
import us.mudkip989.mods.nbs_extensions.client.*;

public class NBSScreen extends Screen {
    public NBSScreen() {
        super(Text.literal("NBS Songs"));
    }

    public ButtonWidget button1;
    public ElementListWidget thing2;

    @Override
    protected void init() {
        button1 = ButtonWidget.builder(Text.literal("I am Button"), button -> {
            Nbs_extensionsClient.MC.player.sendMessage(Text.literal("Funny"));
        }).dimensions(20, 20, 100, 20).build();
        
        addDrawableChild(button1);
    }


}

