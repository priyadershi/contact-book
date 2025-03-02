package com.contacts.service;

import java.sql.SQLException;
import java.util.List;

import com.contacts.model.Contact;
import com.contacts.repository.ContactRepository;

public class ContactService {

  private final ContactRepository repository;

  public ContactService(ContactRepository repository) {
    this.repository = repository;
  }

  public String addContact(Contact c) {
    try {
      int count = repository.addContact(c);
      if (count > 0) {
        System.out.println("contact added!");
        return "success";
      } else {
        System.err.println("contact not added!");
        return "unsuccessfull!";
      }
    } catch (SQLException e) {
      if (e.getMessage().contains("Duplicate entry")) {
        return "name already exist";
      }
      return e.getMessage();
    }
  }

  public String deleteContact(int id) {
    try {
      int count = repository.deleteContact(id);
      if (count > 0) {
        System.out.println("deleted!");
        return "success";
      } else {
        return "no contact found!";
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return e.getMessage();
    }
  }

  public Contact getContact(int id) {
    try {
      return repository.getContact(id);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public List<Contact> getAllContact() {
    try {
      return repository.getAllContacts();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public String updateContact(Contact c) {
    try {
      int flag = repository.updateContact(c);
      if (flag > 0) {
        System.out.println("contact updated.");
        return "success";
      } else
        return "no updates have been done!";
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return e.getMessage();
    }
  }

}
