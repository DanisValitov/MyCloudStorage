package com.danis.valitov.cloud.client.controller;

import com.danis.valitov.cloud.client.Network;
import com.danis.valitov.cloud.common.FilePacket;
import com.danis.valitov.cloud.common.MyPacket;
import com.danis.valitov.cloud.common.PacketMsg;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    // For Test
    public static void main(String[] args) {
        Network.start();
        String file = "C:\\Users\\12\\Desktop\\проекты\\netty-file-transfer\\client_storage\\PuTTY-0.66-RU-16.zip";
        Network.fileSend(file);

        try {
            while (true) {
                MyPacket myPacket = Network.readObject();
                if (myPacket instanceof FilePacket) {
                    FilePacket filePacket = (FilePacket) myPacket;
                    //  Files.write(Paths.get("client_storage/" + fm.getFilename()), fm.getData(), StandardOpenOption.CREATE);

                }

                if (myPacket instanceof PacketMsg) {
                    PacketMsg packetMsg = (PacketMsg) myPacket;
                    System.out.println(packetMsg.getMsg());
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            Network.stop();
        }



    }



    @FXML
    TextField tfFileName;

    @FXML
    ListView<String> filesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.start();

String file = "C:\\Users\\12\\Desktop\\проекты\\netty-file-transfer\\client_storage\\2.txt";

        Network.fileSend(file);



        Thread t = new Thread(() -> {
            try {
                while (true) {
                    MyPacket myPacket = Network.readObject();
                    if (myPacket instanceof FilePacket) {
                        FilePacket filePacket = (FilePacket) myPacket;
                      //  Files.write(Paths.get("client_storage/" + fm.getFilename()), fm.getData(), StandardOpenOption.CREATE);
                        refreshLocalFilesList();
                    }

                    if (myPacket instanceof PacketMsg) {
                        PacketMsg packetMsg = (PacketMsg) myPacket;
                        System.out.println(packetMsg.getMsg());
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            } finally {
                Network.stop();
            }
        });
        t.setDaemon(true);
        t.start();
        filesList.setItems(FXCollections.observableArrayList());
        refreshLocalFilesList();
    }

//    public void pressOnDownloadBtn(ActionEvent actionEvent) {
//        if (tfFileName.getLength() > 0) {
//            Network.sendMsg(new FileRequest(tfFileName.getText()));
//            tfFileName.clear();
//        }
//    }

    public void refreshLocalFilesList() {
        if (Platform.isFxApplicationThread()) {
            try {
                filesList.getItems().clear();
                Files.list(Paths.get("client_storage")).map(p -> p.getFileName().toString()).forEach(o -> filesList.getItems().add(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Platform.runLater(() -> {
                try {
                    filesList.getItems().clear();
                    Files.list(Paths.get("client_storage")).map(p -> p.getFileName().toString()).forEach(o -> filesList.getItems().add(o));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void openAuthWindow(ActionEvent actionEvent) {

    }
}
