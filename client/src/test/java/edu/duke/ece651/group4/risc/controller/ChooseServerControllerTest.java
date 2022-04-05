package edu.duke.ece651.group4.risc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.group4.risc.shared.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ChooseServerControllerTest {
  private Text text;
  private ChooseServerController cont;
  private TextField server;

  @Start
  private void start(Stage stage) {
    text = new Text();
    server = new TextField("localhost");
    cont = new ChooseServerController();
    cont.alert = text;
    cont.select = server;
  }

  @Test
  public void test_server_connect_failure(FxRobot robot) throws IOException {
    Platform.runLater(() -> {
      Button b = new Button("CONNECT");

      server.setText("localhost");
      ;
      try {
        cont.connect_server(new ActionEvent(b, null));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_server_connect_success() throws InterruptedException, IOException {
    int port = 6066;
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ServerSocket server = new ServerSocket(port);
          Socket server_socket = server.accept();
          // FactorServer.main(new String[0]);
          ObjectOutputStream os = new ObjectOutputStream(server_socket.getOutputStream());
          ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(server_socket.getInputStream()));
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

    // client side
    Platform.runLater(() -> {
      Button b = new Button("CONNECT");
      cont.select.setText("localhost");
      try {
        cont.connect_server(new ActionEvent(b, null));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
