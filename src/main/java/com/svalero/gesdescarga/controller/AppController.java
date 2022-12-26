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

    private Map<String, DownloadController> allDownloads; // Creamos un mapa para guardar todas las descargas

    /**
     * Método para inicializar el Mapa de las descargas de DownloadController
     */
    public AppController() {
        allDownloads = new HashMap();
    }

    /**
     * Método que se ejecuta cuando pulsamos el botón descarga, lo ponemos en onAction  Scene Builder para que JavaFx lo encuentre
     * Recoge la url que hay en la caja de texto "tfUrl" y ejecuta la descarga o lo implantado dentro del  método
     * @param event Recibimos el evento una vez pulsado sobre el boton que creamos en Scene Builder
     * @FXML : Para hacer la conexión, que JavaFx haga la conexión y la integración de manera directa. Siempre poner está anotación de JavaFX
     */
    @FXML
    public void launchDownload(ActionEvent event) {
        String urlText = tfUrl.getText(); //Metemos el contenido de la caja de texto en un String
        tfUrl.clear(); //Para vaciar la caja de texto una vez recogido el texto y metido en un String
        tfUrl.requestFocus(); //Volvemos a pillar el foco para poder meter otra Url rápidamente

        launchDownload(urlText); //Llama a otro método que se llama igual pero con un parámetro String para la Url. Sobrecargar el método.
    }

    /**
     * Sobrecargamos el método anterior y le pasamos un String
     * @param url
     */
    private void launchDownload(String url) {

        try {
            FXMLLoader loader = new FXMLLoader(); //Creamos un objeto FMXLloader que se encargará de Montarnos la interfaz de lo otra ventana
            //Todo cambiar por la ventana que necesitamos presentar al realizar las descargas
            loader.setLocation(R.getUI("gesDescargaHome.fxml")); // Le pasamos la localización de la ventana diseñada con JavaFx

            DownloadController downloadController = new DownloadController(url); //Creamos su propio controler desde su clase DownloadController para gestionar los botones y demás cosas
            loader.setController(downloadController);
            //Todo revisar si crearé un VBox o el padre será de otro tipo
            VBox downloadBox = loader.load(); //En este caso el padre de la ventana es un Vbox en JavaFx

            String filename = url.substring(url.lastIndexOf("/") + 1); //para añadirle a cada pestaña el final de la URL que le pasamos, gracias al filename.
            tpDownloads.getTabs().add(new Tab(filename, downloadBox)); //Lo añadimos al panel de ventana de "multidownload.fxml"y añadimos una PESTAÑA por cada descarga que le damos.

            allDownloads.put(url, downloadController); //Cada vez que lancemos una descarga lo añadimos al mapa para poder cancelarlas todas, tenemos cada url de descarga asociado al objeto que se crea por cada descarga

        } catch (IOException ioe) {
            ioe.printStackTrace(); //Pintamos la traza
        }
    }

}