package com.contacts.repository;

import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.SQLException;

public class DatabaseConfig {
  public static final String URL = "jdbc:mysql://localhost:3306/contact_db";
  public static final String USER = "root";
  public static final String PASSWORD = "Pankaj";

  public static Connection getConnection() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      return DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
