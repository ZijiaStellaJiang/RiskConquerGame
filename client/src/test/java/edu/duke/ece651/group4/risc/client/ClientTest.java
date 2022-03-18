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
import static org.mockito.Mockito.*;
import edu.duke.ece651.group4.risc.shared.*;
import jdk.jfr.Timestamp;

public class ClientTest {
  @Test
  public void test_socket_connection() throws InterruptedException{
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
          // generate a map
          Map<Character> mymap = new Map<Character>();
          os.writeObject(mymap);
          os.flush();
          os.writeObject(1);
          os.flush();
        } catch (Exception e) {
        }
      }
    };
    th.start();
    th.sleep(200);
    // client part
    Client client = new Client("localhost", 6066, new BufferedReader(new InputStreamReader(System.in)), System.out);
    assertEquals("localhost/127.0.0.1:6066", client.getSocket().getRemoteSocketAddress().toString());
    // finish test successfully and start test transimitting data
    client.send_to_server("hello from client\n");
    String received = (String) client.recv_from_server();
    assertEquals("hello from server\n", received);
    assertThrows(IllegalArgumentException.class,()->client.initializeGame());
    // close connection
    client.close_connection();

    //test recv and send failure
    client.send_to_server("fail");
    client.recv_from_server();
  }

  @Test
  public void test_connect_server_failure() {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    //assertThrows(RuntimeException.class, () -> Client.connectServer("noExistServer", 1234));
    assertThrows(RuntimeException.class, () -> new Client("noExistServer", 1234, in, System.out));
  }
}
