package com.rosemite.commands;

import com.google.gson.Gson;
import com.rosemite.RandomItems;
import com.rosemite.common.Common;
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
        Common.saveRandomItemsConfigFoundItems(RandomItems.foundItems);
        return true;
    }
}
