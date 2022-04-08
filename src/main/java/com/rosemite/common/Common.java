package com.rosemite.common;

import com.google.gson.Gson;
import com.rosemite.models.ImportMapEntry;
import org.bukkit.Material;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Common {
    public static void saveRandomItemsConfigFoundItems(List<Map.Entry<Material, Material>> entries) {
        String filename = "RandomItems Config FoundItems.json";

        try {
            FileWriter myWriter = new FileWriter(filename);

            myWriter.write(new Gson().toJson(convertToImportMapEntries(entries)));
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<ImportMapEntry<Material, Material>> convertToImportMapEntries(List<Map.Entry<Material, Material>> entries) {
        List<ImportMapEntry<Material, Material>> convertedEntries = new ArrayList<>();
        entries.forEach(entry -> convertedEntries.add(new ImportMapEntry<>(entry.getKey(), entry.getValue())));

        return convertedEntries;
    }
}
