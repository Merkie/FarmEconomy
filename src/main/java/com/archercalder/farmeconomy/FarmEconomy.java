package com.archercalder.farmeconomy;

import com.archercalder.farmeconomy.commands.BalanceCommand;
import com.archercalder.farmeconomy.commands.SellAllCommand;
import com.archercalder.farmeconomy.organicproduce.OrganicProduce;
import com.archercalder.farmeconomy.sqlite.BalanceManager;
import com.archercalder.farmeconomy.sqlite.SQLite;

import org.bukkit.plugin.java.JavaPlugin;

public final class FarmEconomy extends JavaPlugin {

    public SQLite sqlite;
    public BalanceManager balanceManager;

    @Override
    public void onEnable() {
        new OrganicProduce(this);

        sqlite = new SQLite(this);
        balanceManager = new BalanceManager(this);
        this.getCommand("bal").setExecutor(new BalanceCommand(this));
        this.getCommand("sellall").setExecutor(new SellAllCommand(this));
    }

    @Override
    public void onDisable() {
        sqlite.closeConnection();
    }
}
