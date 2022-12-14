/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.group4.risc.server;

//import edu.duke.ece651.group4.risc.server.AbstractMapFactory;
import edu.duke.ece651.group4.risc.shared.*;

import java.net.*;
import java.time.Clock;
import java.io.*;
import java.util.ArrayList;

public class Server {
  private ServerSocket serverSocket;
  private AbstractMapFactory<Character> mapFactory;
  private Map<Character> map;
  private int player_num;
  private ArrayList<Socket> player_skd;
  private ArrayList<ObjectOutputStream> player_out;
  private ArrayList<ObjectInputStream> player_in;
  private Action<Character> move_myself;
  private Action<Character> move_enemy;
  private Action<Character> attack;
  private Action<Character> update;
  private Action<Character> supdate;
  private Action<Character> rcloak;
  private Action<Character> cloak;
  private Action<Character> smove;
  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    mapFactory = new V2MapFactory();
    map = mapFactory.generateMap();
    player_num = 2;
    player_skd = new ArrayList<Socket>();
    player_out = new ArrayList<ObjectOutputStream>();
    player_in = new ArrayList<ObjectInputStream>();
    move_myself = new MoveAction<>(true);
    move_enemy = new MoveAction<>(false);
    attack = new AttackAction<>();
    update = new UpdateAction<>();
    supdate = new SUpdateAction<>();
    rcloak = new ResearchCloakAction<>();
    cloak = new CloakAction<>();
    smove = new SMoveAction<>();
  }

  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  public void accept_connection() throws IOException {
    System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
    // connect to player
    Socket server = serverSocket.accept();
    // get input and output
    ObjectOutputStream os = new ObjectOutputStream(server.getOutputStream());
    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
    // add socket, output, input to Arraylist
    player_skd.add(server);
    player_out.add(os);
    player_in.add(is);
    System.out.println("Just connected to " + server.getRemoteSocketAddress());
  }

  public void send_original_map(int player_id) throws IOException {
    player_out.get(player_id).reset();
    player_out.get(player_id).writeObject(map);
    player_out.get(player_id).flush();
    Integer id = player_id;
    player_out.get(player_id).reset();
    player_out.get(player_id).writeObject(id);
    player_out.get(player_id).flush();
  }

  public void close_all_connection() throws IOException {
    for (int i = 0; i < player_num; i++) {
      player_skd.get(i).close();
      player_out.get(i).close();
      player_in.get(i).close();
    }
  }

  public void initializeGame() {
    for (int i = 0; i < player_num; i++) {
      try {
        // accept connection
        accept_connection();
        // send the map and player_id to client
        send_original_map(i);
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
  }

  /**
   * Sends message to client
   */
  public void send_to_client(Object obj, int id) throws RuntimeException {
    if (id >= player_num) {
      throw new RuntimeException("cannot find player to send message");
    }
    try {
      player_out.get(id).reset();
      player_out.get(id).writeObject(obj);
      player_out.get(id).flush();
    } catch (IOException e) {
      throw new RuntimeException("cannot send to client-player id:" + id);
    }
  }

  public Object recv_from_client(int id) throws ClassNotFoundException {
    Object obj = null;
    try {
      obj = player_in.get(id).readObject();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return obj;
  }

  public String playOneRound() throws IOException, ClassNotFoundException {
    for (int i = 0; i < player_num; i++) {
      // accept the order and execute MOVING
      ArrayList<ActionParser> order_list = (ArrayList<ActionParser>) recv_from_client(i);
      if(order_list==null){
        System.out.println("no action received from player: " + i);
      }else {
        for (ActionParser action : order_list) {
          System.out.println("player: " + i +" " +action.getType() + " " + action.getSource() + " " + action.getDest() + " " + action.getUnit());
        }
      }
      Player<Character> cur_player = map.getPlayer(i);
      for (int j = 0; j < order_list.size(); j++) {
        ActionParser order = order_list.get(j);
        if (order.getType().equals("MOVE")) {
          move_myself.doAction(order, map, cur_player);
        } else if (order.getType().equals("ATTACK")) {
          move_enemy.doAction(order, map, cur_player);
        } else if (order.getType().equals("UPDATE")) {
          update.doAction(order, map, cur_player);
        } else if (order.getType().equals("SUPDATE")) {
          supdate.doAction(order, map, cur_player);
        } else if (order.getType().equals("SMOVE")) {
          // TODO
          smove.doAction(order, map, cur_player);
        } else if (order.getType().equals("CLOAK")) {
          cloak.doAction(order, map, cur_player);
        } else if (order.getType().equals("RCLOAK")) {
          rcloak.doAction(order, map, cur_player);
        } else {
          System.out.println("WRONG TYPE ERROR!");
        }
      }
    }

    // execute attacking
    for (int i = 0; i < player_num; i++) {
      Player<Character> cur_player = map.getPlayer(i);
      attack.doAction(null, map, cur_player);
    }

    // add unit to each territory and Update Resource
    map.receive_new_units();
    for (int i = 0; i < player_num; i++) {
      map.getPlayer(i).updateResource();
    }
    //update cloak info
    map.updateOneRound();
    // sending updating map
    for (int i = 0; i < player_num; i++) {
      send_to_client(map, i);
    }
    // resetLastRound last round change
    map.resetLastRound();
    if (map.getLoserId() != null)
      return "over";
    return null;
  }
  public Map<Character> getMap(){
    return this.map;
  }
  /*
   * <<<<<<< HEAD public void run() { // initialize game: receive connection and
   * send the map initializeGame();
   * 
   * // play one round playOneRound();
   * 
   * // close all connection close_all_connection(); }
   */

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // int port = Integer.parseInt(args[0]);
    Server server = null;
    try {
      int port_num = 6066;
      server = new Server(port_num);
      // t.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // initialize game: receive connection and send the map
    server.initializeGame();
    server.getMap().updateOneRound();
    // play one round
    while (true) {

      if (server.playOneRound() != null)
        break;
    }

    // close all connection
    server.close_all_connection();
  }
}
