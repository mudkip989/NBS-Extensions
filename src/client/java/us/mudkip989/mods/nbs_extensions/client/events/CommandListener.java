package us.mudkip989.mods.nbs_extensions.client.events;

import com.mojang.brigadier.*;
import com.mojang.brigadier.arguments.*;
import net.fabricmc.fabric.api.client.command.v2.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.command.*;
import us.mudkip989.mods.nbs_extensions.client.*;
import us.mudkip989.mods.nbs_extensions.client.gui.*;
import us.mudkip989.mods.nbs_extensions.client.sys.*;
import us.mudkip989.mods.nbs_extensions.client.util.*;

import java.io.*;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static us.mudkip989.mods.nbs_extensions.client.nbs.NBSLoad.loadNbs;
import static us.mudkip989.mods.nbs_extensions.client.nbs.NBSLoad.loadNbsU;

public class CommandListener implements ClientCommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("nbs").then(literal("load").then(argument("filename", PathArgumentType.folder(ExternalFile.NBS_FILES.getPath(), true)).executes(ctx -> {
            if(Nbs_extensionsClient.MC.player == null) return 0;

            if (Nbs_extensionsClient.MC.player.isCreative()) {
                File file = PathArgumentType.getPath(ctx, "filename").toFile();

                loadNbs(file, file.getName());
            }


            return 1;
        }))).then(literal("load2").then(argument("filename", PathArgumentType.folder(ExternalFile.NBS_FILES.getPath(), true)).executes(ctx -> {
            if(Nbs_extensionsClient.MC.player == null) return 0;

            if (Nbs_extensionsClient.MC.player.isCreative()) {
                File file = PathArgumentType.getPath(ctx, "filename").toFile();

                loadNbsU(file, file.getName());
            }


            return 1;
        }))).then(literal("gui")).executes(ctx -> {
            if (Nbs_extensionsClient.MC.player.isCreative()) {
                Nbs_extensionsClient.MC.setScreen(new NBSScreen());
            }
            return 1;
        }));
    }




//    .then(ArgBuilder.literal("load")
//                        .then(ArgBuilder.argument("filename", PathArgumentType.folder(ExternalFile.NBS_FILES.getPath(), true))
//            .executes(ctx -> {
//        if (this.isCreative(mc)) {
//            File file = PathArgumentType.getPath(ctx, "filename").toFile();
//
//            loadNbs(file, file.getName());
//        }
//        return 1;
//    })
//            )
//            ).then(ArgBuilder.literal("load2")
//                        .then(ArgBuilder.argument("filename", PathArgumentType.folder(ExternalFile.NBS_FILES.getPath(), true))
//            .executes(ctx -> {
//        if (this.isCreative(mc)) {
//            File file = PathArgumentType.getPath(ctx, "filename").toFile();
//
//            loadNbsU(file, file.getName());
//        }
//        return 1;
//    })
//            )
//            )


}
