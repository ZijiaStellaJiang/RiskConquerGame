package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class ClientTest {
  @Test
  public void test_() {
    int port = 6066;
    // Server t = new Server(port);
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ServerSocket server = new ServerSocket(port);
          Socket server_socket = server.accept();
          // FactorServer.main(new String[0]);
          ObjectOutputStream os = new ObjectOutputStream(server_socket.getOutputStream());
          ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(server_socket.getInputStream()));
          String received = (String) is.readObject();
          assertEquals("hello from client\n", received);
          os.writeObject("hello from server\n");
          os.flush();
        } catch (Exception e) {
        }
      }
    };
    th.start();
    // client part
    Client client = new Client("localhost", 6066, new BufferedReader(new InputStreamReader(System.in)), System.out);
    assertEquals("localhost/127.0.0.1:6066", client.getSocket().getRemoteSocketAddress().toString());
    //finish test successfully and start test transimitting data
    client.send_to_server("hello from client\n");
    String received  = (String) client.recv_from_server();
    assertEquals("hello from server\n", received);
    //close connection
    client.close_connection();
  }

}
