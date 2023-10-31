package com.archercalder.farmeconomy.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import com.archercalder.farmeconomy.FarmEconomy;

public class SQLite {
  private final FarmEconomy plugin;
  private Connection connection;

  public SQLite(FarmEconomy plugin) {
    this.plugin = plugin;
    establishSqliteConnection();
  }

  private void establishSqliteConnection() {
    try {
      // Ensure that we have the sqlite driver, this will throw an exception if we
      // don't
      Class.forName("org.sqlite.JDBC");

      if (!plugin.getDataFolder().exists()) {
        plugin.getDataFolder().mkdirs();
      }

      String dbPath = plugin.getDataFolder().getAbsolutePath() + "/farmeconomy.db";

      connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

      plugin.getLogger().log(Level.INFO, "Successfully established a database connection.");
    } catch (ClassNotFoundException | SQLException e) {
      plugin.getLogger().log(Level.SEVERE, "Could not establish a database connection.");
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      plugin.getLogger().log(Level.SEVERE, "Could not close database connection.");
    }
  }
}