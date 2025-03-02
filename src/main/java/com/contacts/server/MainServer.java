package com.contacts.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.contacts.controller.ContactController;
import com.sun.net.httpserver.HttpServer;

public class MainServer {
  private final ContactController controller;
  private final int port;
  private final String contextPath;

  public MainServer(ContactController controller, String contextPath, int port) {
    this.controller = controller;
    this.contextPath = contextPath;
    this.port = port;
  }

  public void start() throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext(contextPath, controller);
    server.setExecutor(null);
    server.start();
    System.out.println("server started at port " + port);
    System.out.println("\u001B]8;;http://localhost:3000/contacts\u001B\\Click here to open\u001B]8;;\u001B\\");

  }

}
