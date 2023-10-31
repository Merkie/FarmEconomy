package com.archercalder.farmeconomy.commands;

import com.archercalder.farmeconomy.CropData;
import com.archercalder.farmeconomy.FarmEconomy;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
            int soldItemCount = 0;
            int totalSaleValue = 0;

            for (ItemStack item : items) {
                if (item != null) {
                    CropData cropData = getCropData(item.getType());
                    if (cropData != null) {
                        totalSaleValue += getCropSalePrice(item);
                        soldItemCount += item.getAmount();
                        player.getInventory().removeItem(item);
                    }
                }
            }

            if (soldItemCount > 0) {
                plugin.balanceManager.updatePlayerBalanceByUUID(player.getUniqueId(), totalSaleValue);
                player.sendMessage(ChatColor.GREEN + "Sold " + soldItemCount + " items for $" + totalSaleValue);
            } else {
                player.sendMessage(ChatColor.RED + "You have no items to sell!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
        }
        return true;
    }

    @Override
    public java.util.List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
            @NonNull String alias, @NonNull String[] args) {
        return java.util.Collections.emptyList();
    }

    private boolean isOrganicProduced(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null && meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (String line : lore) {
                if (line.toLowerCase().contains("organic")) {
                    return true;
                }
            }
        }

        return false;
    }

    private CropData getCropData(Material type) {
        for (Material key : plugin.cropDataMap.keySet()) {
            if (key == type) {
                return plugin.cropDataMap.get(key);
            } else if (plugin.cropDataMap.get(key).getCropMaterial() == type) {
                return plugin.cropDataMap.get(key);
            }
        }

        return null;
    }

    private int getCropSalePrice(ItemStack item) {
        Material type = item.getType();
        int amount = item.getAmount();

        if (isOrganicProduced(item)) {
            return (amount * getCropData(type).getSalePrice()) * 2;
        } else {
            return (amount * getCropData(type).getSalePrice());
        }
    }
}
