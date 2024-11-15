package us.mudkip989.mods.nbs_extensions;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.mudkip989.mods.nbs_extensions.event.CommandListener;

public class NBSExtensions implements ClientModInitializer {
    public static MinecraftClient MC = MinecraftClient.getInstance();
    public static Logger LOGGER = LoggerFactory.getLogger("NBSExtensions");

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(new CommandListener());

    }
}
