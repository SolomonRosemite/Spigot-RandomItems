package com.rosemite.commands;

import com.google.gson.Gson;
import com.rosemite.RandomItems;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;

public class SaveConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String filename = "RandomItems Config.json";

        try {
            FileWriter myWriter = new FileWriter(filename);

            myWriter.write(new Gson().toJson(RandomItems.randomsBlocks));
            myWriter.close();
            if (sender instanceof Player) {
                sender.sendMessage("Successfully saved RandomItems Config as " + ChatColor.GREEN + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
