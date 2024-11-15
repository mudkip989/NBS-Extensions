package us.mudkip989.mods.nbs_extensions.util;

import net.minecraft.util.Formatting;

public enum MessageType {
    SUCCESS(Formatting.GREEN),
    INFO(Formatting.AQUA),
    ERROR(Formatting.RED);

    public final Formatting formatting;

    MessageType(Formatting formatting) {
        this.formatting = formatting;
    }
}
