package us.mudkip989.mods.nbs_extensions.nbs;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import us.mudkip989.mods.nbs_extensions.nbs.exception.OutdatedNBSException;
import us.mudkip989.mods.nbs_extensions.util.ItemUtil;
import us.mudkip989.mods.nbs_extensions.util.MessageType;
import us.mudkip989.mods.nbs_extensions.util.MessageUtil;
import us.mudkip989.mods.nbs_extensions.util.TemplateUtil;

import java.io.File;
import java.io.IOException;

public class NBSLoad {

    public static void loadNbs(File file, boolean useCustom) {

        try {
            SongData d = NBSDecoder.parse(file, useCustom);
            if (d == null) {
                MessageUtil.send("Loading error!", MessageType.ERROR);
                return;
            }
            String code = new NBSToTemplate(d).convert();
            ItemStack stack = new ItemStack(Items.NOTE_BLOCK);
            TemplateUtil.compressTemplateNBT(stack, d.name(), d.author(), code);


            String name = d.name();
            if (name.isEmpty()) {
                if (d.fileName().indexOf(".") > 0) {
                    name = d.fileName().substring(0, d.fileName().lastIndexOf("."));
                } else {
                    name = d.fileName();
                }
            }
            stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("§5SONG§7 -§f " + name));

            MessageUtil.send("NBS Loaded!", MessageType.SUCCESS);
            ItemUtil.giveCreativeItem(stack, true);
        } catch (OutdatedNBSException e) {
            MessageUtil.send("Loading error! Unsupported file version", MessageType.ERROR);
        } catch (IOException e) {
            MessageUtil.send("Loading error! Invalid file", MessageType.ERROR);
        }

    }
}
