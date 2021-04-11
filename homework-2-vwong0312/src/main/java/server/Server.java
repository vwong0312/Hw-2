package server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import pointofsale.EcommerceService;

public class Server {

  public static void main(String[] args) {
    Server instance = new Server();
    instance.runServer();
  }

  public String processRequest(String request){
    // Fill this in!
    String[] lines = request.split("\n");
    String inputBody = lines[lines.length - 1]; // Extract this after the first blank space
    String host = lines[5].substring(6); // extract from request headers
    System.out.println("----------REQUEST START---------");
    for (int i = 0; i < lines.length; i++) {
      System.out.println(lines[i]);
    }
    System.out.println("----------REQUEST END---------\n\n");
    String fullPath = "https://" + host + lines[0].substring(lines[0].indexOf(" ") + 1, lines[0].lastIndexOf(" ")); // should be like http://localhost:1234/api?abc=123
    return EcommerceService.getInstance().restApi(fullPath, inputBody.trim());
  }

  public void runServer() {
    ServerSocket ding;
    Socket socket = null;
    try {
      ding = new ServerSocket(1299);
      System.out.println("Opened socket " + 1299);
      while (true) {
        // keeps listening for new clients, one at a time
        try {
          socket = ding.accept(); // waits for client here
        } catch (IOException e) {
          System.out.println("Error opening socket");
          System.exit(1);
        }

        InputStream stream = socket.getInputStream();

        int c;
        String raw = "";
        do {
          c = stream.read();
          raw+=(char)c;
        } while(stream.available()>0);
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);  // char output to the client
        // every response will always have the status-line, date, and server name
        writer.println("HTTP/1.1 200 OK");
        writer.println("Server: TEST");
        writer.println("Connection: close");
        writer.println("Content-type: application/json");
        writer.println("");
        // response body
        writer.println(processRequest(raw));
        socket.close();
      }
    } catch (IOException e) {
      System.out.println("Error opening socket");
      System.exit(1);
    }
  }
}
