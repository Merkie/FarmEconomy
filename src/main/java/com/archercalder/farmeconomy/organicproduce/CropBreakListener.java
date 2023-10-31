package com.archercalder.farmeconomy.organicproduce;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.block.data.Ageable;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CropBreakListener implements Listener {
  @EventHandler
  public void onCropBreak(BlockBreakEvent event) {
    // Check if the player is in survival mode
    if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
      return;
    }

    Block block = event.getBlock();
    Material type = block.getType();

    // Check if the block is a crop
    if (isCrop(type)) {
      final boolean isAgeableCrop = isAgeableCrop(type);
      boolean isFullyGrown = false;
      if (isAgeableCrop) {
        Ageable ageable = (Ageable) block.getBlockData();
        isFullyGrown = ageable.getAge() == ageable.getMaximumAge();
      }

      if (!isAgeableCrop || isFullyGrown) {
        // The crop is fully grown, modify the item drop
        event.setDropItems(false); // Prevent the default drop
        ItemStack droppedItem = new ItemStack(type, 1); // Modify this if different crops drop different items
        ItemMeta meta = droppedItem.getItemMeta();

        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§r§aOrganic Produce");
        meta.setLore(lore);
        droppedItem.setItemMeta(meta);

        // Drop the modified item
        block.getWorld().dropItemNaturally(block.getLocation(), droppedItem);
      }
    }
  }

  private boolean isCrop(Material type) {
    // Add other crops as needed
    return type == Material.WHEAT || type == Material.CARROTS || type == Material.POTATOES ||
        type == Material.BEETROOTS || type == Material.SUGAR_CANE;
  }

  private boolean isAgeableCrop(Material type) {
    return type == Material.WHEAT || type == Material.CARROTS || type == Material.POTATOES ||
        type == Material.BEETROOTS;
  }
}
