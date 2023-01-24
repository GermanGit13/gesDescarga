package com.svalero.gesdescarga.controller;

import com.svalero.gesdescarga.task.DownloadTask;
import javafx.concurrent.Worker;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            File logFile = new File("multidescargas.log");

            if (!Desktop.isDesktopSupported()) {
                System.out.println(" no soportado");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(logFile.exists())
                desktop.open(logFile);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

