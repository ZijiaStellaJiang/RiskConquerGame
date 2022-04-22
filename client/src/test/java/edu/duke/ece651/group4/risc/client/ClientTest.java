package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Disabled;
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
    //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    //assertThrows(Exception.class, () -> new Client("localhost", 1003, null, null));
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
  @Disabled
  @Test
  public void test_OneRoundBegin() throws InterruptedException, IOException {
    int port = 6070;
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
          
          // os.flush();
          // generate a map
          Map<Character> mymap = generateTestMap();
          os.writeObject(mymap);
          os.flush();
          os.writeObject(1);
          os.flush();
          // receive order lists
          ArrayList<ActionParser> order_list = (ArrayList<ActionParser>) is.readObject();
          assertEquals(0, order_list.size());
          // send map
          os.writeObject(mymap);
          os.flush();


        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(200);
    // client part
    Client client = new Client("localhost", 6070, new BufferedReader(new InputStreamReader(System.in)), System.out);
    assertEquals("localhost/127.0.0.1:6070", client.getSocket().getRemoteSocketAddress().toString());
    // finish test successfully and start test transimitting data
    client.initializeGame(); 
    
    client.oneRoundBegin();
    client.addOrder(new ActionParser("move", "a", null, 0, 0, 0));
    client.addOrder(new ActionParser("attack", "b", null, 0, 0, 0));
    client.addOrder(new ActionParser("update", "c", null, 0, 0, 0));
    client.oneRoundEnd();
    client.oneRoundUpdate();
    //client.send_to_server("hello from client\n");
    //String received = (String) client.recv_from_server();
    //assertEquals("hello from server\n", received);
    //assertThrows(IllegalArgumentException.class,()->client.initializeGame());
    // close connection
    client.close_connection();

    //test recv and send failure
    //assertThrows(IOException.class, ()->client.send_to_server("fail"));
    //assertThrows(IOException.class, ()->client.recv_from_server());
    
  }

  public Map<Character> generateTestMap() {
    Map<Character> testMap = new Map<Character>();
    Territory<Character> terriN = new Territory<Character>("Narnia", 5, 15, 10);
    Territory<Character> terriO = new Territory<Character>("Oz", 10, 20, 15);
    terriN.addNeigh(terriO);
    ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>(0)));
    terriN.addGroupUnit(nUnits);
    ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>(0)));
    terriO.addGroupUnit(oUnits);
    testMap.addTerritory(terriN);
    testMap.addTerritory(terriO);
    TextPlayer p1 = new TextPlayer("Green", 200, 200);
    TextPlayer p2 = new TextPlayer("Blue", 200, 200);
    p1.addToTerritory(terriN);
    p2.addToTerritory(terriO);
    testMap.addPlayer(p1);
    testMap.addPlayer(p2);
    return testMap;
  }

  @Test
  public void test_utility() throws InterruptedException, IOException{
    int port = 6070;
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ServerSocket server = new ServerSocket(port);
          Socket server_socket = server.accept();
          ObjectOutputStream os = new ObjectOutputStream(server_socket.getOutputStream());
          ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(server_socket.getInputStream()));

          Map<Character> mymap = generateTestMap();
          os.writeObject(mymap);
          os.flush();
          os.writeObject(1);
          os.flush();
          // receive order lists
          ArrayList<ActionParser> order_list = (ArrayList<ActionParser>) is.readObject();
          assertEquals(0, order_list.size());
          // send map
          os.writeObject(mymap);
          os.flush();

        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(200);
    // client part
    Client client = new Client("localhost", 6070, new BufferedReader(new InputStreamReader(System.in)), System.out);
    assertEquals("localhost/127.0.0.1:6070", client.getSocket().getRemoteSocketAddress().toString());
    // finish test successfully and start test transimitting data
    client.initializeGame();

    client.oneRoundBegin();
    assertEquals(200,client.getPlayerFood());
    assertEquals(200,client.getPlayerWood());
    assertEquals(1,client.getPlayerId());
    assertEquals(new ArrayList<String>(Collections.singleton("Oz")),client.getClientTerritories());
    assertEquals(new ArrayList<String>(),client.getClientCanReach("Oz",true));
    assertEquals(new ArrayList<String>(Collections.singleton("Narnia")),client.getClientCanReach("Oz",false));
    assertTrue(client.territoryIsMine("oz"));
    assertFalse(client.territoryIsMine("narnia"));
    assertFalse(client.cloakIsResearch());
    assertEquals(0,client.cloakRemain("oz"));
    assertEquals(0,client.getMySpyNum("oz"));
    assertEquals(0,client.getEnemySpyNum("oz"));
    assertEquals(generateTestMap().getMyPlayers(),client.getMap().getMyPlayers());
  }
}
