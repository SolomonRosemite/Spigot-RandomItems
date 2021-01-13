package com.rosemite.commands;

import com.rosemite.RandomItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

public class ItemsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        
        Player p = (Player) sender;

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("get")) {
               String matStr = args[1];
               List<Material> res = Arrays.stream(Material.values()).filter(material -> filter(material, matStr)).collect(Collectors.toList());

               res.forEach(material -> {
                   boolean found = false;
                   for (int i = 0; i < RandomItems.foundItems.size(); i++) {
                       if (RandomItems.foundItems.get(i).getValue() == material) {
                           found = true;
                           p.sendMessage(logMaterial(RandomItems.foundItems.get(i).getKey(), material));
                           break;
                       }
                   }

                   if (!found) {
                       p.sendMessage(undefined(material));
                   }
               });
            } else if (args[0].equalsIgnoreCase("with")) {
                String matStr = args[1];
                List<Material> res = Arrays.stream(Material.values()).filter(material -> filter(material, matStr)).collect(Collectors.toList());

                res.forEach(material -> {
                    boolean found = false;
                    for (int i = 0; i < RandomItems.foundItems.size(); i++) {
                        if (RandomItems.foundItems.get(i).getKey() == material) {
                            found = true;
                            p.sendMessage(logMaterial(material, RandomItems.foundItems.get(i).getValue()));
                            break;
                        }
                    }

                    if (!found) {
                        p.sendMessage(undefined(material));
                    }
                });
            } else {
                sender.sendMessage("/items get [Material] or /items with [Material]");
            }

            return true;
        }


        if (RandomItems.foundItems.size() == 0) {
            p.sendMessage("No Items (ᵔᴥᵔ)");
            return true;
        }

        p.sendMessage( getRandomChatColor(RandomItems.getRandom()) + "§m---------------------------------------");
        RandomItems.foundItems.forEach(item -> p.sendMessage(logMaterial(item.getKey(), item.getValue())));
        return true;
    }

    private boolean filter(Material mat, String reqname) {
        return mat.name().toLowerCase().contains(reqname.toLowerCase()) || reqname.toLowerCase().contains(mat.name().toLowerCase());
    }

    private String logMaterial(Material m1, Material m2) {
        return ChatColor.AQUA + m1.name() + ChatColor.GRAY + " -> " + ChatColor.GREEN + m2.name();
    }

    private String undefined(Material m) {
        return ChatColor.RED + "unknown".toUpperCase() + ChatColor.GRAY + " -> " + ChatColor.GREEN + m;
    }

    private ChatColor getRandomChatColor(Random r) {
        int l = ChatColor.values().length;
        return ChatColor.values()[r.nextInt(l)];
    }
}
