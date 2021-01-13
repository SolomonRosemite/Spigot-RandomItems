package com.rosemite;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rosemite.commands.ClearChatCommand;
import com.rosemite.commands.ItemsCommand;
import com.rosemite.commands.SaveConfigCommand;
import com.rosemite.helper.Log;
import com.rosemite.listener.ItemsListener;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.*;

public final class RandomItems extends JavaPlugin {
    public static List<Map.Entry<Material, Material>> foundItems;
    public static Map<Material, Material> randomsBlocks;
    public static RandomItems randomItems;
    private static Random random;

    @Override
    public void onEnable() {
        random = new Random();
        randomItems = this;

        foundItems = new ArrayList<>();

        Map.Entry<Map<Material, Material>, Boolean> res = getRandomBlocks(Arrays.stream(Material.values()).collect(Collectors.toList()));
        if (res.getValue()) {
            List<Map.Entry<Material, Material>> foundItemsConfig = getFoundItems();
            if (foundItemsConfig != null) {
                foundItems = foundItemsConfig;
            }
        }

        randomsBlocks = res.getKey();

        getServer().getPluginManager().registerEvents(new ItemsListener(randomsBlocks), this);
        this.getCommand("items").setExecutor(new ItemsCommand());
        this.getCommand("save").setExecutor(new SaveConfigCommand());
        this.getCommand("c").setExecutor(new ClearChatCommand());
    }

    private Map.Entry<Map<Material, Material>, Boolean> getRandomBlocks(List<Material> availableMaterials) {
        Map<Material, Material> values = getRandomsBlocksFromConfig();

        if (values != null)
            return new AbstractMap.SimpleEntry(values, true);

        Material[] materials = Material.values();
        Map<Material, Material> map = new HashMap<>();

        for (Material value : materials) {
            int index = random.nextInt(availableMaterials.size());

            Material material = availableMaterials.get(index);
            map.put(value, material);

            availableMaterials.remove(index);
        }

        return new AbstractMap.SimpleEntry(map, false);
    }

    private Map<Material, Material> getRandomsBlocksFromConfig() {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get("RandomItems Config.json")));
            Log.d("Found RandomItems Config.json. Loading Config...");
        } catch (IOException ignored) {
            Log.d("RandomItems Config.json not found. Loading a newly generated one.");
        }

        if (content.length() == 0) {
            return null;
        }

        Map<String, String> map = new Gson().fromJson(content, Map.class);
        Map<Material, Material> materialMap = new HashMap<>();

        map.forEach((k, v) -> {
            materialMap.put(Material.valueOf(k), Material.valueOf(v));
        });

        return materialMap;
    }

    private List<Map.Entry<Material, Material>> getFoundItems() {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get("RandomItems Config FoundItems.json")));
        } catch (IOException ignored) {
        }

        if (content.length() == 0) {
            return null;
        }

        Type listType = new TypeToken<ArrayList<AbstractMap.SimpleEntry<String, String>>>(){}.getType();
        List<AbstractMap.SimpleEntry<String, String>> list = new Gson().fromJson(content, listType);
        List<Map.Entry<Material, Material>> materialList = new ArrayList<>();

        list.forEach((entry) -> {
            materialList.add(new AbstractMap.SimpleEntry<>(Material.valueOf(entry.getKey()), Material.valueOf(entry.getValue())));
        });

        return materialList;
    }

    public static Random getRandom() {
        return random;
    }
}
