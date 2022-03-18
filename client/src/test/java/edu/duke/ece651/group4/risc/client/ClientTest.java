package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import edu.duke.ece651.group4.risc.shared.*;
import jdk.jfr.Timestamp;

public class ClientTest {
  
  @Test
  public void test_socket_connection() throws InterruptedException, IOException {
    int port = 7010;
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
    Thread.sleep(200);
    // client part
    Client client = new Client("localhost", 7010, new BufferedReader(new InputStreamReader(System.in)), System.out);
    assertEquals("localhost/127.0.0.1:7010", client.getSocket().getRemoteSocketAddress().toString());
    // finish test successfully and start test transimitting data
    client.send_to_server("hello from client\n");
    String received = (String) client.recv_from_server();
    assertEquals("hello from server\n", received);
    assertThrows(IllegalArgumentException.class,()->client.initializeGame());
    // close connection
    client.close_connection();

    //test recv and send failure
    assertThrows(IOException.class, ()->client.send_to_server("fail"));
    assertThrows(IOException.class, ()->client.recv_from_server());
  }

  @Test
  public void test_connect_server_failure() {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    //assertThrows(RuntimeException.class, () -> Client.connectServer("noExistServer", 1234));
    //assertThrows(RuntimeException.class, () -> new Client("noExistServer", 1234, in, System.out));
  }

//  @Test
//  public void test_play_one_round() throws InterruptedException, IOException, ClassNotFoundException {
//    int port = 7003;
//    // Server t = new Server(port);
//    Thread th = new Thread() {
//      @Override()
//      public void run() {
//        try {
//          Map<Character> map = generateMap();
//
//
//          ServerSocket server = new ServerSocket(port);
//          Socket server_socket = server.accept();
//          // FactorServer.main(new String[0]);
//          ObjectOutputStream os = new ObjectOutputStream(server_socket.getOutputStream());
//          ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(server_socket.getInputStream()));
//          os.writeObject(map);
//          os.flush();
//          os.writeObject(0);
//          os.flush();
//
//          Socket server1 = server.accept();
//          // get input and output
//          ObjectOutputStream os1 = new ObjectOutputStream(server1.getOutputStream());
//          ObjectInputStream is1 = new ObjectInputStream(new BufferedInputStream(server1.getInputStream()));
//
//          os1.writeObject(map);
//          os1.flush();
//          os1.writeObject(0);
//          os1.flush();
//          System.out.println("finish write");
//          ArrayList<ActionParser> order_list = (ArrayList<ActionParser>) is.readObject();
//          ArrayList<ActionParser> order_list1 = (ArrayList<ActionParser>) is1.readObject();
//
//          //resend map
//          os.writeObject(map);
//          os.flush();
//          os1.writeObject(map);
//          os1.flush();
//        } catch (Exception e) {
//        }
//      }
//    };
//    th.start();
//    Thread.sleep(200);
//    Thread client_th = run() -> {
//
//    };
//    BufferedReader input = new BufferedReader(new StringReader("move hogwarts oz 1\ndone\n"));
//    BufferedReader input1 = new BufferedReader(new StringReader("move hogwarts oz 1\ndone\n"));
//
//    // client part
//    Client client = new Client("localhost", 7003, input, System.out);
//    client.initializeGame();
//    Client client2 = new Client("localhost", 7003, input1, System.out);
//    client2.initializeGame();
//    System.out.println("finish init");
//    client.playOneRound();
//    client2.playOneRound();
//    // close connection
//    client.close_connection();
//
//
//  }
}
