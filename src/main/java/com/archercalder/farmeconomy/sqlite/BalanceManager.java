package com.archercalder.farmeconomy.sqlite;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.ResultSet;

import com.archercalder.farmeconomy.FarmEconomy;

public class BalanceManager {
  private final FarmEconomy plugin;
  private final Connection sqlite;

  public BalanceManager(FarmEconomy plugin) {
    this.plugin = plugin;
    this.sqlite = plugin.sqlite.getConnection();
    createBalancesTable();
  }

  private void createBalancesTable() {
    try {
      sqlite.createStatement().executeUpdate(
          "CREATE TABLE IF NOT EXISTS balances ("
              + "uuid TEXT PRIMARY KEY,"
              + "balance INTEGER NOT NULL"
              + ");");
    } catch (SQLException e) {
      plugin.getLogger().log(Level.SEVERE, "Could not create balances table.");
    }
  }

  public int getPlayerBalanceByUUID(UUID uuid) {
    try {
      // Check if player exists in the database
      ResultSet rs = sqlite.createStatement()
          .executeQuery("SELECT balance FROM balances WHERE uuid = '" + uuid.toString() + "';");

      // If player exists, return their balance
      if (rs.next()) {
        return rs.getInt("balance");
      } else {
        // If player doesn't exist, create a new record with balance 0
        sqlite.createStatement()
            .executeUpdate("INSERT INTO balances (uuid, balance) VALUES ('" + uuid.toString() + "', 0);");
        return 0;
      }
    } catch (SQLException e) {
      plugin.getLogger().log(Level.SEVERE, "Could not get player balance.");
      return 0; // Return default balance in case of an error
    }
  }

  public void updatePlayerBalanceByUUID(UUID uuid, int amount) {
    try {
      int currentBalance = getPlayerBalanceByUUID(uuid);
      sqlite.createStatement().executeUpdate(
          "UPDATE balances SET balance = " + (currentBalance + amount) + " WHERE uuid = '" + uuid.toString() + "';");
    } catch (SQLException e) {
      plugin.getLogger().log(Level.SEVERE, "Could not update player balance.");
    }
  }
}
