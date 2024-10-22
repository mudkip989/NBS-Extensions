package us.mudkip989.mods.nbs_extensions.client.util;

import com.google.gson.JsonParser;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.util.collection.*;
//import net.minecraft.client.Minecraft;
//import net.minecraft.core.NonNullList;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.nbt.*;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.inventory.ClickType;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ItemUtil {
    public static void giveCreativeItem(ItemStack item, boolean preferHand) {
        MinecraftClient mc = MinecraftClient.getInstance();
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