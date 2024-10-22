package us.mudkip989.mods.nbs_extensions.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.*;
import net.minecraft.client.*;
import us.mudkip989.mods.nbs_extensions.client.events.*;

public class Nbs_extensionsClient implements ClientModInitializer {
    public static MinecraftClient MC = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(new CommandListener());

    }
}
