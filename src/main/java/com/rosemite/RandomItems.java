package com.rosemite;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public final class RandomItems extends JavaPlugin {
    private Random random;

    @Override
    public void onEnable() {
        random = new Random();

        Map<Material, Material> randomsBlocks = getRandomBlocks(Arrays.stream(Material.values()).collect(Collectors.toList()));

        getServer().getPluginManager().registerEvents(new ItemsListener(randomsBlocks), this);
    }

    private Map<Material, Material> getRandomBlocks(List<Material> availableMaterials) {
        Material[] materials = Material.values();
        Map<Material, Material> map = new HashMap<>();

        for (Material value : materials) {
            int index = random.nextInt(availableMaterials.size());

            Material material = availableMaterials.get(index);
            map.put(value, material);

            availableMaterials.remove(index);
        }

        return map;
    }
}
