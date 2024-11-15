package us.mudkip989.mods.nbs_extensions.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import us.mudkip989.mods.nbs_extensions.NBSExtensions;

public class ItemUtil {
    public static void giveCreativeItem(ItemStack item, boolean preferHand) {
        MinecraftClient mc = NBSExtensions.MC;
        if (mc.player == null || mc.interactionManager == null) return;
        DefaultedList<ItemStack> inv = mc.player.getInventory().main;


        if (preferHand) {
            if (mc.player.getMainHandStack().isEmpty()) {
                mc.interactionManager.clickCreativeStack(item, mc.player.getInventory().selectedSlot + 36);
                return;
            }
        }

        for (int index = 0; index < inv.size(); index++) {
            ItemStack i = inv.get(index);
            ItemStack compareItem = i.copy();
            compareItem.setCount(item.getCount());
            if (item == compareItem) {
                while (i.getCount() < i.getMaxCount() && item.getCount() > 0) {
                    i.setCount(i.getCount() + 1);
                    item.setCount(item.getCount() - 1);
                }
            } else {
                if (i.getItem() == Items.AIR) {
                    if (index < 9)
                        mc.interactionManager.clickCreativeStack(item, index + 36);
                    inv.set(index, item);
                    return;
                }
            }
        }
    }

}