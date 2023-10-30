package com.archercalder.farmeconomy;

import com.archercalder.farmeconomy.commands.BalanceCommand;
import com.archercalder.farmeconomy.commands.SellAllCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Hashtable;
import java.util.Objects;
import java.util.UUID;

public final class FarmEconomy extends JavaPlugin {

    private Hashtable<UUID, Integer> balances;

    @Override
    public void onEnable() {
        balances = new Hashtable<>();
        Objects.requireNonNull(this.getCommand("bal")).setExecutor(new BalanceCommand(this));
        Objects.requireNonNull(this.getCommand("sellall")).setExecutor(new SellAllCommand(this));
    }

    public Hashtable<UUID, Integer> getBalances() {
        return balances;
    }

    public int getPlayerBalanceById(UUID uuid) {
        if (!balances.containsKey(uuid)) {
            balances.put(uuid, 0);
        }
        return balances.get(uuid);
    }

    public void addPlayerBalanceById(UUID uuid, int amount) {
        if (!balances.containsKey(uuid)) {
            balances.put(uuid, 0);
        }
        int newBalance = balances.get(uuid) + amount;
        balances.put(uuid, newBalance);
        return newBalance;
    }
}
