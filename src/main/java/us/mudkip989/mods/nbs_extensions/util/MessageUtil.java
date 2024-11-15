package us.mudkip989.mods.nbs_extensions.util;

import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import us.mudkip989.mods.nbs_extensions.NBSExtensions;

public class MessageUtil {

    public static void send(String message) {
        send(message, MessageType.INFO);
    }

    public static void send(String message, MessageType messageType) {
        if (NBSExtensions.MC.player == null) return;

        NBSExtensions.MC.player.sendMessage(
                Text.literal("» ").styled(s -> s.withColor(messageType.formatting).withBold(true))
                        .append(Text.literal(message).styled(s -> s.withColor(Formatting.WHITE).withBold(false)))
        );

        if (messageType == MessageType.ERROR)
            NBSExtensions.MC.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO.value(), 1.0F, 0.5F);
    }
}
