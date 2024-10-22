package us.mudkip989.mods.nbs_extensions.client.nbs;

import net.minecraft.item.*;
import net.minecraft.text.*;
import us.mudkip989.mods.nbs_extensions.client.nbs.alt.*;
import us.mudkip989.mods.nbs_extensions.client.nbs.exceptions.*;
import us.mudkip989.mods.nbs_extensions.client.util.*;

import java.io.*;

public class NBSLoad {

    public static void loadNbs(File file, String fileName) {

            try {
                SongData d = NBSDecoder.parse(file);
                String code = new NBSToTemplate(d).convert();
                ItemStack stack = new ItemStack(Items.NOTE_BLOCK);
                TemplateUtil.compressTemplateNBT(stack, d.getName(), d.getAuthor(), code);

                if (d.getName().length() == 0) {
                    String name;
                    if (d.getFileName().indexOf(".") > 0) {
                        name = d.getFileName().substring(0, d.getFileName().lastIndexOf("."));
                    } else {
                        name = d.getFileName();
                    }
                    stack.setCustomName(Text.literal("§5SONG§7 -§f " + name));
                } else {
                    stack.setCustomName(Text.literal("§5SONG§7 -§f " + d.getName()));
                }

//                ToasterUtil.sendToaster("NBS Loaded!", fileName, SystemToast.SystemToastId.NARRATOR_TOGGLE);
                ItemUtil.giveCreativeItem(stack, true);
            } catch (OutdatedNBSException e) {
//                ToasterUtil.sendToaster("§cLoading Error!", "Unsupported file version", SystemToast.SystemToastId.NARRATOR_TOGGLE);
            } catch (IOException e) {
//                ToasterUtil.sendToaster("§cLoading Error!", "Invalid file", SystemToast.SystemToastId.NARRATOR_TOGGLE);
            }

    }

    public static void loadNbsU(File file, String fileName) {

            try {
                SongData d = NBSDecoderU.parse(file);
                String code = new NBSToTemplate(d).convert();
                ItemStack stack = new ItemStack(Items.NOTE_BLOCK);
                TemplateUtil.compressTemplateNBT(stack, d.getName(), d.getAuthor(), code);

                if (d.getName().length() == 0) {
                    String name;
                    if (d.getFileName().indexOf(".") > 0) {
                        name = d.getFileName().substring(0, d.getFileName().lastIndexOf("."));
                    } else {
                        name = d.getFileName();
                    }
                    stack.setCustomName(Text.literal("§5SONG§7 -§f " + name));
                } else {
                    stack.setCustomName(Text.literal("§5SONG§7 -§f " + d.getName()));
                }

//                ToasterUtil.sendToaster("NBS Loaded!", fileName, SystemToast.SystemToastId.NARRATOR_TOGGLE);
                ItemUtil.giveCreativeItem(stack, true);
            } catch (OutdatedNBSException e) {
//                ToasterUtil.sendToaster("§cLoading Error!", "Unsupported file version", SystemToast.SystemToastId.NARRATOR_TOGGLE);
            } catch (IOException e) {
//                ToasterUtil.sendToaster("§cLoading Error!", "Invalid file", SystemToast.SystemToastId.NARRATOR_TOGGLE);
            }

    }
}
