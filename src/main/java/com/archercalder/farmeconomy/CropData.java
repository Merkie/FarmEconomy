package com.archercalder.farmeconomy;

import org.bukkit.Material;

public class CropData {
  private int minDrop;
  private int maxDrop;
  private int salePrice;
  private boolean ageable;
  private Material cropMaterial;
  private Material seedMaterial;

  public CropData(int minDrop, int maxDrop, int salePrice, boolean ageable, Material cropMaterial,
      Material seedMaterial) {
    this.minDrop = minDrop;
    this.maxDrop = maxDrop;
    this.salePrice = salePrice;
    this.ageable = ageable;
    this.cropMaterial = cropMaterial;
    this.seedMaterial = seedMaterial;
  }

  public int getMinDrop() {
    return minDrop;
  }

  public int getMaxDrop() {
    return maxDrop;
  }

  public int getSalePrice() {
    return salePrice;
  }

  public boolean isAgeable() {
    return ageable;
  }

  public Material getCropMaterial() {
    return cropMaterial;
  }

  public Material getSeedMaterial() {
    return seedMaterial;
  }
}