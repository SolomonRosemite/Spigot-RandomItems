package com.rosemite.helper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Log {
    public static void d(Object message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Log]: " + ChatColor.GRAY + message);
    }
}
