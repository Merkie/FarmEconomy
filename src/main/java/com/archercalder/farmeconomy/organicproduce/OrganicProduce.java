package com.archercalder.farmeconomy.organicproduce;

import com.archercalder.farmeconomy.FarmEconomy;

public class OrganicProduce {
  // private final FarmEconomy plugin;

  public OrganicProduce(FarmEconomy plugin) {
    // this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(new CropBreakListener(), plugin);
  }
}
