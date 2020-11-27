package com.rosemite;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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

        dropItem(block.getBlockData().getMaterial(), block.getLocation());

        event.getBlock().setType(Material.AIR);
        event.setDropItems(false);
        event.setCancelled(true);
    }

    private void dropItem(Material m, Location location) {
        Material material = blockMap.get(m);
        Log.d(material);

        try {
            Objects.requireNonNull(location.getWorld()).dropItemNaturally(location, new ItemStack(material));
        } catch (Exception ignored) {
            dropItem(material, location);
        }
    }
}
