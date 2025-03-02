package com.contacts.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.contacts.model.Contact;

public class ContactRepository {
  private final Connection conn;

  public ContactRepository(Connection conn) {
    this.conn = conn;
  }

  public int addContact(Contact c) throws SQLException {
    String sql = "insert into contacts (phone, name, email, address, dob) values(?, ?, ?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, c.getPhone());
    ps.setString(2, c.getName());
    ps.setString(3, c.getEmail());
    ps.setString(4, c.getAddress());
    ps.setString(5, c.getDob());
    int count = ps.executeUpdate();
    return count;
  }

  public int deleteContact(int id) throws SQLException {
    String sql = "delete from contacts where id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      int count = ps.executeUpdate();
      return count;
    }
  }

  public Contact getContact(int id) throws SQLException {
    String sql = "select * from contacts where id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      String name, phone, email, address, dob;
      if (rs.next()) {
        name = rs.getString("name");
        phone = rs.getString("phone");
        email = rs.getString("email");
        address = rs.getString("address");
        dob = rs.getString("dob");
        return new Contact(id, name, phone, email, address, dob);
      }
    }
    return new Contact();
  }

  public List<Contact> getAllContacts() throws SQLException {
    String sql = "select * from Contacts";
    List<Contact> list = new ArrayList<Contact>();
    int id;
    String phone, name, email, address, dob;
    try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        id = rs.getInt("id");
        name = rs.getString("name");
        phone = rs.getString("phone");
        email = rs.getString("email");
        address = rs.getString("address");
        dob = rs.getString("dob");
        list.add(new Contact(id, name, phone, email, address, dob));
      }
    }
    return list;
  }

  public int updateContact(Contact c) throws SQLException {
    String sql = "update Contacts set name = ?, phone = ?, email = ?, address = ?, dob = ? where id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, c.getName());
      ps.setString(2, c.getPhone());
      ps.setString(3, c.getEmail());
      ps.setString(4, c.getAddress());
      ps.setString(5, c.getDob());
      ps.setInt(6, c.getId());
      int count = ps.executeUpdate();
      return count;
    }
  }

}
