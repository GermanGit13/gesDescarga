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
    private String cadena;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            File logFile = new File("H:\\Grapo DAM\\10 Programación de Servicios y Procesos\\AP_German_Rodriguez\\multidescargas.log");

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

/**
 * Pruebas
 */
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        try {
//
//            File logFile = new File("H:\\Grapo DAM\\10 Programación de Servicios y Procesos\\AP_German_Rodriguez\\multidescargas.log");
//            FileReader leer = new FileReader(logFile);
////            BufferedReader bufferedReader = new BufferedReader(leer);
////            cadena = "";
////            while (cadena != null){
////                cadena = bufferedReader.readLine();
////                if (cadena != null) {
////                    return cadena;
////                }
////
////            }
////
////            bufferedReader.close();
////            leer.close();
//            if (!Desktop.isDesktopSupported()) {
//                System.out.println(" no soportado");
//                return;
//            }
//            Desktop desktop = Desktop.getDesktop();
//            if(logFile.exists())
//                desktop.open(logFile);
//
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }