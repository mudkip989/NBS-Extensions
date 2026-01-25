package us.mudkip989.mods.nbs_extensions.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.collection.DefaultedList;
import us.mudkip989.mods.nbs_extensions.NBSExtensions;

public class ItemUtil {
    public static void giveCreativeItem(ItemStack item, boolean preferHand) {
        MinecraftClient mc = NBSExtensions.MC;
        if (mc.player == null || mc.interactionManager == null) return;
        DefaultedList<ItemStack> inv = mc.player.getInventory().getMainStacks();


        if (preferHand) {
            if (mc.player.getMainHandStack().isEmpty()) {
//                mc.interactionManager.clickCreativeStack(item, mc.player.getInventory().getSelectedSlot());
                mc.player.getInventory().setStack(mc.player.getInventory().getSelectedSlot(), item);
                mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().getSelectedSlot()));
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
                    if (index < 9) {
//                        mc.interactionManager.clickCreativeStack(item, index);
                        mc.player.getInventory().setStack(index, item);
                        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(index));
                    }
                    inv.set(index, item);

                    return;
                }
            }
        }
    }

}