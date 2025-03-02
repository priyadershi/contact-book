package com.contacts.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import com.contacts.model.Contact;
import com.contacts.service.ContactService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ContactController implements HttpHandler {
  private final ContactService service;

  public ContactController(ContactService service) {
    this.service = service;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String method = exchange.getRequestMethod();

    try {
      if ("GET".equals(method)) {
        handleGet(exchange);
      } else if ("POST".equals(method)) {
        handlePost(exchange);
      } else if ("PUT".equals(method) || "PATCH".equals(method)) {
        handleUpdate(exchange);
      } else if ("DELETE".equals(method)) {
        handleDelete(exchange);
      } else {
        sendResponse(exchange, "Method Not Allowed!", 405);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  };

  public void handleGet(HttpExchange exchange) throws SQLException, IOException {
    Gson gson = new Gson();
    String path = exchange.getRequestURI().getPath();
    String pathParts[] = path.split("/");
    String response;
    Contact contact;
    if (pathParts.length == 3) {
      if (pathParts[2].matches("\\d+")) {
        int id = Integer.parseInt(pathParts[2]);
        contact = service.getContact(id);
        if (contact == null) {
          sendResponse(exchange, "some error occured!", 500);
        } else if (contact.getId() == -1)
          sendResponse(exchange, "No Contact Found!", 404);
        else {
          response = gson.toJson(service.getContact(id));
          sendResponse(exchange, response, 200);
        }
      } else {
        sendResponse(exchange, "No Contact Found!", 404);
      }
    } else {
      List<Contact> contacts = service.getAllContact();
      if (contacts == null) {
        sendResponse(exchange, "some error occured!", 500);
      }
      response = gson.toJson(contacts);
      sendResponse(exchange, response, 200);
    }

  }

  public void handlePost(HttpExchange exchange) throws SQLException, IOException {
    Gson gson = new Gson();
    String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    Contact contact = gson.fromJson(json, Contact.class);
    String response = service.addContact(contact);
    sendResponse(exchange, response, 200);
  }

  public void handleUpdate(HttpExchange exchange) throws SQLException, IOException {
    Gson gson = new Gson();
    String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    Contact contact = gson.fromJson(json, Contact.class);
    String response = service.updateContact(contact);
    sendResponse(exchange, response, 200);
  }

  public void handleDelete(HttpExchange exchange) throws SQLException, IOException {
    String path = exchange.getRequestURI().getPath();
    String pathParts[] = path.split("/");
    if (pathParts.length == 3 && pathParts[2].matches("\\d+")) {
      int id = Integer.parseInt(pathParts[2]);
      String response = service.deleteContact(id);
      if (response == "success")
        sendResponse(exchange, response, 200);
    } else {
      sendResponse(exchange, "delete unsuccessfull!", 404);
    }
  }

  public void sendResponse(HttpExchange exchange, String response, int status) throws IOException {
    exchange.sendResponseHeaders(status, response.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }

}
