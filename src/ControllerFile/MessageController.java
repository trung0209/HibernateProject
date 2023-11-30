package ControllerFile;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MessageController implements Initializable {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    private String userID;
    private String targetID;
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                    Scanner serverInput = new Scanner(socket.getInputStream());
                    BufferedWriter severOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    severOutput.write(userID + "\n");
                    severOutput.flush();
                    severOutput.write(targetID + "\n");
                    severOutput.flush();
                    Thread thread = new Thread(() -> receiveMessages(socket));
                    thread.start();

                    sendButton.setOnAction(event -> {
                        try {
                            String message = messageField.getText();
                            chatArea.appendText(userID + ":" + message + "\n");
                            severOutput.write(message + "\n");
                            severOutput.flush();
                            messageField.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    chatArea.setText("Server error");
                }
            }
        });

    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    private void receiveMessages(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String message = reader.readLine();
                chatArea.appendText(message + "\n");
            }
        } catch (IOException e) {

        }
    }
}
