package com.contacts;

import com.contacts.controller.ContactController;
import com.contacts.repository.ContactRepository;
import com.contacts.repository.DatabaseConfig;
import com.contacts.server.MainServer;
import com.contacts.service.ContactService;

import java.io.IOException;
import java.sql.Connection;

public class App {
  public static void main(String[] args) throws IOException {
    Connection connection = DatabaseConfig.getConnection();
    ContactRepository repository = new ContactRepository(connection);
    ContactService service = new ContactService(repository);
    ContactController controller = new ContactController(service);
    MainServer server = new MainServer(controller, "/contacts", 3000);
    server.start();
  }
}