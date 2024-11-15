package us.mudkip989.mods.nbs_extensions.event;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import us.mudkip989.mods.nbs_extensions.NBSExtensions;
import us.mudkip989.mods.nbs_extensions.gui.NBSScreen;
import us.mudkip989.mods.nbs_extensions.sys.ExternalFile;
import us.mudkip989.mods.nbs_extensions.util.PathArgumentType;

import java.io.File;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static us.mudkip989.mods.nbs_extensions.nbs.NBSLoad.loadNbs;

public class CommandListener implements ClientCommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("nbs")
                .then(literal("load")
                        .then(argument("filename", PathArgumentType.folder(ExternalFile.NBS_FILES.getPath(), true))
                                .executes(ctx -> {
                                    if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                                        File file = PathArgumentType.getPath(ctx, "filename").toFile();

                                        loadNbs(file, false);
                                    }
                                    return 1;
                                })))
                .then(literal("load_custom")
                        .then(argument("filename", PathArgumentType.folder(ExternalFile.NBS_FILES.getPath(), true))
                                .executes(ctx -> {
                                    if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                                        File file = PathArgumentType.getPath(ctx, "filename").toFile();

                                        loadNbs(file, true);
                                    }
                                    return 1;
                                })))
                .then(literal("gui")
                        .executes(ctx -> {
                            if (NBSExtensions.MC.player != null && NBSExtensions.MC.player.isCreative()) {
                                NBSExtensions.MC.send(() -> NBSExtensions.MC.setScreen(new NBSScreen()));
                            }
                            return 1;
                        })));
    }
}
