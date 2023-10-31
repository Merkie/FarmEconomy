package com.archercalder.farmeconomy;

import com.archercalder.farmeconomy.commands.BalanceCommand;
import com.archercalder.farmeconomy.commands.SellAllCommand;
import com.archercalder.farmeconomy.organicproduce.OrganicProduce;
import com.archercalder.farmeconomy.sqlite.BalanceManager;
import com.archercalder.farmeconomy.sqlite.SQLite;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class FarmEconomy extends JavaPlugin {

    public SQLite sqlite;
    public BalanceManager balanceManager;
    public HashMap<Material, CropData> cropDataMap;

    @Override
    public void onEnable() {
        // Initialize everything
        initializeCropData();
        new OrganicProduce(this);
        sqlite = new SQLite(this);
        balanceManager = new BalanceManager(this);

        this.getCommand("bal").setExecutor(new BalanceCommand(this));
        this.getCommand("sellall").setExecutor(new SellAllCommand(this));
    }

    public void initializeCropData() {
        cropDataMap = new HashMap<>();
        cropDataMap.put(Material.WHEAT, new CropData(1, 3, 2, true, Material.WHEAT, Material.WHEAT_SEEDS));
        cropDataMap.put(Material.CARROTS, new CropData(1, 4, 3, true, Material.CARROT, null));
        cropDataMap.put(Material.POTATOES, new CropData(1, 4, 3, true, Material.POTATO, null));
        cropDataMap.put(Material.BEETROOTS, new CropData(1, 4, 3, true, Material.BEETROOT, Material.BEETROOT_SEEDS));
        cropDataMap.put(Material.NETHER_WART, new CropData(1, 4, 4, true, Material.NETHER_WART, null));
        cropDataMap.put(Material.SUGAR_CANE, new CropData(1, 1, 2, false, Material.SUGAR_CANE, null));
        cropDataMap.put(Material.CACTUS, new CropData(1, 1, 1, false, Material.CACTUS, null));
        cropDataMap.put(Material.PUMPKIN, new CropData(1, 1, 3, false, Material.PUMPKIN, null));
    }

    @Override
    public void onDisable() {
        sqlite.closeConnection();
    }
}
