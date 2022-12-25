package com.svalero.gesdescarga.controller;

import com.svalero.gesdescarga.util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppController {

    public TextField tfUrl; //Caja de Texto que usamos en JavaFx
    public Button btDownload; // Botón que usamos para la descarga en JavaFx
    public TabPane tpDownloads; //Panel creado en JavaFx para que las descargas se añadan en pestañas

//    private Map<String, DownloadController> allDownloads; // Creamos un mapa para guardar todas las descargas


}