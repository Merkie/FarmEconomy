package com.archercalder.farmeconomy.commands;

import com.archercalder.farmeconomy.FarmEconomy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
// import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SellAllCommand implements TabExecutor {
    private FarmEconomy plugin;

    public SellAllCommand(FarmEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label,
            @NonNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack[] items = player.getInventory().getContents();
            int wheatCount = 0;

            for (ItemStack item : items) {
                if (item != null && item.getType() == Material.WHEAT) {
                    wheatCount += item.getAmount();
                    // This will remove all wheat from the inventory
                    player.getInventory().removeItem(new ItemStack(Material.WHEAT, item.getAmount()));
                }
            }

            if (wheatCount > 0) {
                // Assume 1 wheat is worth $1, update player balance accordingly
                plugin.balanceManager.updatePlayerBalanceByUUID(player.getUniqueId(), wheatCount);

                // Send a confirmation message to the player
                player.sendMessage(ChatColor.GREEN + "Sold " + wheatCount + " wheat for $" + wheatCount);
            } else {
                // Send a message if no wheat was found in the inventory
                player.sendMessage(ChatColor.RED + "You have no wheat to sell!");
            }
        } else {
            // If the sender is not a player (e.g., console), send an error message
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
        }
        return true;
    }

    @Override
    public java.util.List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
            @NonNull String alias, @NonNull String[] args) {
        return java.util.Collections.emptyList();
    }
}
