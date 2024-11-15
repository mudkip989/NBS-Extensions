package us.mudkip989.mods.nbs_extensions.util;

import com.mojang.brigadier.StringReader;

public class StringReaders {
    public static String readRemaining(StringReader reader) {
        final String text = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());
        return text;
    }
}
