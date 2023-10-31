package com.archercalder.farmeconomy.organicproduce;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.archercalder.farmeconomy.CropData;

import org.bukkit.block.data.Ageable;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CropBreakListener implements Listener {
  private HashMap<Material, CropData> cropDataMap;

  public CropBreakListener(HashMap<Material, CropData> cropDataMap) {
    this.cropDataMap = cropDataMap;
  }

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
      final CropData cropData = cropDataMap.get(type);

      boolean isFullyGrown = false;
      if (cropData.isAgeable()) {
        Ageable ageable = (Ageable) block.getBlockData();
        isFullyGrown = ageable.getAge() == ageable.getMaximumAge();
      }

      if (!cropData.isAgeable() || isFullyGrown) {
        // Prevent the default drop
        event.setDropItems(false);

        // Create itemstack for organic produce drops
        ItemStack droppedItem = new ItemStack(cropData.getCropMaterial(), getDropAmount(type));

        // Set the lore
        ItemMeta meta = droppedItem.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§r§aOrganic Produce");
        meta.setLore(lore);
        droppedItem.setItemMeta(meta);

        // Drop the item stack for the crops
        block.getWorld().dropItemNaturally(block.getLocation(), droppedItem);

        if (cropData.getSeedMaterial() != null) {
          // Create and drop the item stack for the seeds
          ItemStack seedStack = new ItemStack(cropData.getSeedMaterial(), 1);
          block.getWorld().dropItemNaturally(block.getLocation(), seedStack);
        }
      }
    }
  }

  private boolean isCrop(Material type) {
    return cropDataMap.containsKey(type);
  }

  private int getDropAmount(Material type) {
    CropData cropData = cropDataMap.get(type);
    return (int) (Math.random() * (cropData.getMaxDrop() - cropData.getMinDrop() + 1) + cropData.getMinDrop());
  }
}
