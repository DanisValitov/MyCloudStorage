package com.danis.valitov.cloud.client.controller;

import com.danis.valitov.cloud.client.MainClient;
import com.danis.valitov.cloud.client.Network;
import com.danis.valitov.cloud.client.NewAccount;
import com.danis.valitov.cloud.common.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
  private   ObservableList<String> observableList;
  private List<String> empty = new ArrayList<>();
public  static String user;
BufferedOutputStream bos = null;
private long countOfPackets;
private String name;
private File file;
private  long counter;




private List<String> list;

    @FXML
    TextField tfFileName;

    @FXML
    ListView<String> filesList;
    @FXML
    Label label;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.start();

        filesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                label.setText(newValue);
            }
        });




        Thread t = new Thread(() -> {

            try {
                while (true) {
                    MyPacket myPacket = Network.readObject();
                    if (myPacket instanceof FilePacket) {

                    }

                    if (myPacket instanceof PacketMsg) {
                        PacketMsg packetMsg = (PacketMsg) myPacket;
                        System.out.println(packetMsg.getMsg());
                    }

                    if(myPacket instanceof PacketDescription){

                        PacketDescription packetDescription = (PacketDescription)myPacket;
                        countOfPackets  = packetDescription.getCount();
                        name = packetDescription.getFileName();
                        file = new File("C:\\Users\\12\\Desktop\\проекты\\" +
                                "netty-file-transfer\\client_storage" +"\\" +name );
                        System.out.println(file);
                        counter = countOfPackets;

                        bos = new BufferedOutputStream(new FileOutputStream(file));
                        System.out.println("awaiting for " + countOfPackets + " packets");
                    }



                    if (myPacket instanceof FilePacket){
//    System.out.println("Получение файла...");
                        FilePacket filePacket = (FilePacket)myPacket;
                        bos.write(filePacket.getData());
                        counter--;
                        System.out.println(counter);
                        if(counter == 0){
                            bos.close();
                            System.out.println("all done");
                            counter = 0;

                        }
                    }

                    if (myPacket instanceof FilesListPacket){
                        System.out.println("получен лист с файлами");
                        FilesListPacket filesListPacket = (FilesListPacket)myPacket;
                        refreshLocalFilesList(filesListPacket.getList());


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

    }


// НАДО ИСПРАВИТЬ!!!!!!!!!!!!


    public void refreshLocalFilesList(List<String> files) {


        observableList = FXCollections.observableList(files);

        filesList.setItems(observableList);
    }

    public void openAuthWindow(ActionEvent actionEvent) {
        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/auth.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewAccount(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/newAccount.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pressOnDownloadBtn(ActionEvent actionEvent) {
        if(tfFileName.getLength() > 0){
            Network.sendNewFile(tfFileName.getText());
            tfFileName.clear();
        }
    }

    public void toServer(ActionEvent actionEvent) {
        String file = "\\" +user + "\\" + label.getText();
        System.out.println(file);

Network.request(Request.FILE,file);
    }

}
