package com.archercalder.farmeconomy.commands;

import com.archercalder.farmeconomy.FarmEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BalanceCommand implements CommandExecutor, TabExecutor {

    private FarmEconomy plugin;

    public BalanceCommand(FarmEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            int balance = plugin.getPlayerBalanceById(player.getUniqueId());

            player.sendMessage(ChatColor.GREEN + "Your balance is: " + balance);
        }
        return true;
    }

    @Override
    public java.util.List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, @NonNull String[] args) {
        return java.util.Collections.emptyList();
    }
}
