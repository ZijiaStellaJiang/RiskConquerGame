package edu.duke.ece651.group4.risc.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import edu.duke.ece651.group4.risc.shared.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServerTest {

  @Test
  public void test_send_map() throws IOException, InterruptedException, ClassNotFoundException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    int port_num = 6067;
    Server s = new Server(port_num);
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          s.initializeGame();
          s.close_all_connection();
        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(100);
    bytes.reset();
    // setting up socket client
    Socket client_skd = new Socket("localhost", 6067);
    ObjectOutputStream player_out = new ObjectOutputStream(client_skd.getOutputStream());
    ObjectInputStream player_in = new ObjectInputStream(new BufferedInputStream(client_skd.getInputStream()));
    Map<Character> recv1 = (Map<Character>) player_in.readObject();
    assertEquals("Green", recv1.getPlayerName(0));
    Socket client_skd1 = new Socket("localhost", 6067);
    ObjectOutputStream player_out1 = new ObjectOutputStream(client_skd1.getOutputStream());
    ObjectInputStream player_in1 = new ObjectInputStream(new BufferedInputStream(client_skd1.getInputStream()));

  }

  // TODO: test connection
  @Test
  // @Timeout(2500)
  public void test_server() throws IOException, InterruptedException, ClassNotFoundException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    int port_num = 6066;
    Server s = new Server(port_num);
    assertEquals(6066, s.getServerSocket().getLocalPort());
    // assertThrows(IndexOutOfBoundsException.class, ()->s.close_all_connection());

    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          s.accept_connection();
          // FactorServer.main(new String[0]);
          // assertEquals("Waiting for client on port 6066.
          // ..\n", bytes.toString());
          s.send_to_client("test", 0);
          // test non exist player
          String received = (String) s.recv_from_client(0);
          assertEquals("hello from client", received);
          s.send_original_map(0);
          s.close_all_connection();
        } catch (Exception e) {
        }
      }
    };

    // create client manually
    th.start();
    Thread.sleep(200);
    // Client client = new Client("localhost", 6066, new BufferedReader(new
    // InputStreamReader(System.in)), System.out);
    Socket client_skd = new Socket("localhost", 6066);
    // compare system out prinln
    ObjectOutputStream player_out = new ObjectOutputStream(client_skd.getOutputStream());
    ObjectInputStream player_in = new ObjectInputStream(new BufferedInputStream(client_skd.getInputStream()));

    // receive test string from server
    Object obj = player_in.readObject();
    assertEquals("test", (String) obj);
    player_out.writeObject("hello from client");
    player_out.flush();

    Map<Character> newMap = (Map<Character>) player_in.readObject();
    Integer myId = (Integer) player_in.readObject();
    assertEquals(0, myId);
    // th.interrupt();
    // th.join();
  }

  @Mock
  private ArrayList<ObjectOutputStream> out;

  @Test
  public void test_exception_init() throws IOException, InterruptedException, ClassNotFoundException {
    Socket socketMock = mock(Socket.class);
    ObjectInputStream mock = mock(ObjectInputStream.class);
    // ArrayList<ObjectOutputStream> out = mock(ArrayList<ObjectOutputStream>.cla)
    // //when(out.writeObject(any())).thenThrow(new IOException());
    // doThrow(new IOException()).when(mock).readObject();
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    int port_num = 7000;
    Server s = new Server(port_num);
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          s.accept_connection();
          s.accept_connection();
          assertThrows(RuntimeException.class, () -> s.send_to_client("test", 2));
          s.send_to_client("test", 0);
          s.close_all_connection();
          s.send_to_client("test", 1);
          // s.close_all_connection();
        } catch (Exception e) {
        }
        assertThrows(IOException.class, () -> s.recv_from_client(1));
      }
    };
    th.start();
    Thread.sleep(100);
    bytes.reset();
    // setting up socket client
    Socket client_skd = new Socket("localhost", 7000);
    ObjectOutputStream player_out = new ObjectOutputStream(client_skd.getOutputStream());
    ObjectInputStream player_in = new ObjectInputStream(new BufferedInputStream(client_skd.getInputStream()));
    Socket client_skd1 = new Socket("localhost", 7000);
    ObjectOutputStream player_out1 = new ObjectOutputStream(client_skd1.getOutputStream());
    ObjectInputStream player_in1 = new ObjectInputStream(new BufferedInputStream(client_skd1.getInputStream()));
    player_out1.writeObject("hello from client");
    player_out1.flush();
    // -----finish client socket connection
  }
}
