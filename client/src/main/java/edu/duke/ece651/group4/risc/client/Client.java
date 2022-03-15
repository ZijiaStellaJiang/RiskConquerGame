/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.group4.risc.client;

import edu.duke.ece651.group4.risc.shared.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Client {
  private Map<Character> map;
  private Socket player_skd;
  private ObjectOutputStream player_out;
  private ObjectInputStream player_in;
  private int player_id;
  private BufferedReader inputReader;
  private PrintStream output;

  public Client(String serverName, int port, BufferedReader input, PrintStream outputStream) {
    inputReader = input;
    output = outputStream;
    // connection to Server
    player_skd = connectServer(serverName, port);
    player_id = -1;
    try {
      player_out = new ObjectOutputStream(player_skd.getOutputStream());
      player_in = new ObjectInputStream(new BufferedInputStream(player_skd.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Socket getSocket(){
    return player_skd;
  }
  public static Socket connectServer(String serverName, int port) {
    try {
      System.out.println("Connecting to " + serverName + " on port " + port);
      Socket client_skd = new Socket(serverName, port);
      System.out.println("Just connected to " + client_skd.getRemoteSocketAddress());
      return client_skd;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  public void initializeGame() {
    try {
      // receive an object from server
      Object obj_map = player_in.readObject();
      Object obj_id = player_in.readObject();
      // display the initial map and player_id
      if (obj_map != null && obj_id != null) {
        // update the map and player_id
        map = (Map<Character>) obj_map;
        player_id = (Integer) obj_id;
        MapTextView displayInfo = new MapTextView(map, System.out);
        // display the map
        displayInfo.displayCurrentMap();
        // display player id
        displayInfo.displayPlayerMsg(player_id);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  public void send_to_server(Object obj) {
    try {
      player_out.writeObject(obj);
      player_out.flush();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  public Object recv_from_server() {
    Object obj = null;
    try{
      obj = player_in.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return obj;
  }
  public String playOneRound() throws IOException {
    ArrayList<ActionParser> order_list = new ArrayList<ActionParser>();
    while (true) {
      // read an input from client
      String str = inputReader.readLine();
      if (str == null) throw new EOFException("END");
      // check done
      str = str.toUpperCase();
      if (str.equals("DONE")) break;
      // parse the input to order
      ActionParser order = null;
      try {
        order = new ActionParser(str);
      } catch (IllegalArgumentException e) {
        output.println(e.getMessage());
        continue;
      }
      // validate the order (fake action) -> invalid printout msg
      //if (order.getType() == "MOVE") 
      //ActionRuleChecker<Character> ruleChecker = new UnitNumberRuleChecker<>(new MoveOwnershipChecker<>(null));
      //Action<Character> move = new MoveAction<>(order, map, map.getPlayer(player_id), ruleChecker);
      Player<Character> player = map.getPlayer(player_id);
      Action<Character> move = new MoveAction<>(order, true);
      String result = move.doAction(map, player);
      if (result != null) {
        output.println(result);
        continue;
      } else {
        output.println("Valid Action!\n");
      }
      // TODO assume valid order first
      // add to order list
      order_list.add(order);
    }
    output.println("-----------Sending message to server--------");
    // send order list to server
    send_to_server(order_list);
    // receive new update map
    output.println("-----------Receving message from server--------");
    map = (Map<Character>)recv_from_server();
    // display new update map
    output.println("-----------showing the map--------");
    MapTextView displayInfo = new MapTextView(map, output);
    displayInfo.displayCurrentMap();
    displayInfo.displayPlayerMsg(player_id);
    return null;
  }
  public void close_connection() {
    try {
      player_out.close();
      player_in.close();
      player_skd.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) throws IOException {
    Client client = new Client("localhost", 6066, new BufferedReader(new InputStreamReader(System.in)), System.out);
    // receive initial map and id
    client.initializeGame();
    // play one round
    client.playOneRound();
  }

}
