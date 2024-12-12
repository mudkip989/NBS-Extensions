package us.mudkip989.mods.nbs_extensions.gui.widget;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.*;
import org.w3c.dom.*;

import java.util.*;

public class SongListWidget extends ElementListWidget<SongListWidget.Entry> {
    public static final int SPACING = 3;

    public final int rowWidth;
    public final int rowHeight;

    public SongListWidget(MinecraftClient mc, int w, int h, int y, int rowWidth, int rowHeight) {
        super(mc, w, h, y, rowHeight + SPACING);
        this.rowWidth = rowWidth;
        this.rowHeight = rowHeight;
    }

    public void add(ClickableWidget... widgets) {
        if (widgets.length == 0) return;

        var grid = new GridWidget();
        grid.setColumnSpacing(SPACING);
        var adder = grid.createAdder(widgets.length);

        int width = (this.rowWidth - ((widgets.length - 1) * SPACING));
        int count = 0;

        for (var widget: widgets){
            if(widget.getWidth() != 0){
                width -= widget.getWidth();
            } else {
                count++;
            }
        }

        for (var widget : widgets) {
            if (widget.getWidth() == 0){
                widget.setDimensions(width / count, this.rowHeight);
            }
            adder.add(widget);
        }


        grid.refreshPositions();

        this.addEntry(new Entry(grid));
    }


    @Override
    public int getRowWidth() {
        return rowWidth;
    }

    @Override
    protected int getScrollbarX() {
        return this.width - 6;
    }

    @Override
    protected void drawMenuListBackground(DrawContext context) {
    }


    public static class Entry extends ElementListWidget.Entry<Entry> {
        private final GridWidget widget;
        private final List<ClickableWidget> children = new ArrayList<>();

        public Entry(GridWidget widget) {
            this.widget = widget;
            widget.forEachChild(children::add);
        }

        @Override
        public List<? extends Element> children() {
            return this.children;
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return this.children;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.widget.setPosition(x - 3, y);
            this.widget.refreshPositions();

            this.widget.forEachChild(c -> c.render(context, mouseX, mouseY, tickDelta));
        }
    }
}