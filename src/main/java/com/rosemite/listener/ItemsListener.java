package com.rosemite.listener;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.rosemite.RandomItems;
import com.rosemite.common.Common;
import com.rosemite.helper.Log;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ItemsListener implements Listener {

    private final Map<Material, Material> blockMap;

    public ItemsListener(Map<Material, Material> blockMap) {
        this.blockMap = blockMap;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        boolean hasSilkTouch = event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH);

        if (hasSilkTouch || block.getBlockData().getMaterial().name().toLowerCase().contains("shulker")) {
            return;
        }

        dropItem(block.getBlockData().getMaterial(), block.getLocation());

        event.getBlock().setType(Material.AIR);
        event.setDropItems(false);
        event.setCancelled(true);
    }

    private void dropItem(Material m, Location location) {
        Material material = blockMap.get(m);

        try {
            Objects.requireNonNull(location.getWorld()).dropItemNaturally(location, new ItemStack(material));
            for (int i = 0; i < RandomItems.foundItems.size(); i++) {
                Map.Entry<Material, Material> val = RandomItems.foundItems.get(i);
                if (val.getKey() == m && val.getValue() == material) {
                    return;
                }
            }

            addItem(m, material);
        } catch (Exception ignored) {
            dropItem(material, location, m);
        }
    }

    private void dropItem(Material m, Location location, Material origin) {
        Material material = blockMap.get(m);

        try {
            Objects.requireNonNull(location.getWorld()).dropItemNaturally(location, new ItemStack(material));
            for (int i = 0; i < RandomItems.foundItems.size(); i++) {
                Map.Entry<Material, Material> val = RandomItems.foundItems.get(i);
                if (val.getKey() == m && val.getValue() == material) {
                    return;
                }
            }

            addItem(origin, material);
        } catch (Exception ignored) {
            dropItem(material, location, m);
        }
    }

    private void addItem(Material m, Material material) {
        Map.Entry<Material, Material> entry = Maps.immutableEntry(m, material);
        RandomItems.foundItems.add(entry);

        Common.saveRandomItemsConfigFoundItems(RandomItems.foundItems);
    }
}
