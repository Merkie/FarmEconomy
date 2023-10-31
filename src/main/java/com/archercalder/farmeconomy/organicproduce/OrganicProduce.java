package com.archercalder.farmeconomy.organicproduce;

import com.archercalder.farmeconomy.FarmEconomy;

public class OrganicProduce {
  public OrganicProduce(FarmEconomy plugin) {
    plugin.getServer().getPluginManager().registerEvents(new CropBreakListener(plugin.cropDataMap), plugin);
  }
}
