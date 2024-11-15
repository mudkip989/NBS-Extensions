package us.mudkip989.mods.nbs_extensions.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import us.mudkip989.mods.nbs_extensions.util.MessageUtil;

public class NBSScreen extends Screen {

    public ButtonWidget button1;
    public ElementListWidget thing2;

    public NBSScreen() {
        super(Text.literal("NBS Songs"));
    }

    @Override
    protected void init() {
        button1 = ButtonWidget.builder(Text.literal("I am Button"), button -> {
            MessageUtil.send("Funny");
        }).dimensions(20, 20, 100, 20).build();

        addDrawableChild(button1);
    }
}