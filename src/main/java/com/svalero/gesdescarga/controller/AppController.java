package com.svalero.gesdescarga.controller;

import com.svalero.gesdescarga.util.R;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.jfr.FlightRecorder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppController {
    @FXML
    public TextField tfUrl; //Caja de Texto que usamos en JavaFx
    @FXML
    public TextField tfRoute; //Caja con la ruta de Descarga
    @FXML
    public TextField tfTimeOut; // Para recibir los minutos a programar la descarga
    @FXML
    public Button btDownload; // Botón que usamos para la descarga en JavaFx
    @FXML
    public Button btRoute; //Boton para buscar la ruta de guardar
    @FXML
    public Button btShowRoute; // Para mostrar ruta de descarga
    @FXML
    public Button btLog; //Botón para abrir el log
    @FXML
    public TabPane tpDownloads; //Panel creado en JavaFx para que las descargas se añadan en pestañas
    @FXML
    public VBox vbLog; //lo uso para el log
    public String route = System.getProperty("user.dir"); //Ruta con la descarga

    private Map<String, DownloadController> allDownloads; // Creamos un mapa para guardar todas las descargas

    /**
     * Método para inicializar el Mapa de las descargas de DownloadController
     */
    public AppController() {
        allDownloads = new HashMap();
    }

    /**
     * Método que se ejecuta cuando pulsamos el botón descarga, lo ponemos en onAction Scene Builder para que JavaFx lo encuentre
     * Recoge la url que hay en la caja de texto "tfUrl" y ejecuta la descarga o lo implantado dentro del método
     * @param event Recibimos el evento una vez pulsado sobre el botón que creamos en Scene Builder
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
            loader.setLocation(R.getUI("gesDownload.fxml")); // Le pasamos la localización de la ventana diseñada con JavaFx
            String timeSchedule = tfTimeOut.getText(); //recogemos el valor introducido
            // Valor por defecto en el timer en caso de no introducir la entrada
            if (timeSchedule.length() == 0)
                timeSchedule = "0";
            int timeDownload = Integer.parseInt(timeSchedule); // lo pasamos a int

            DownloadController downloadController = new DownloadController(url, route, timeDownload); //Creamos su propio controler desde su clase DownloadController para gestionar los botones y demás cosas
            loader.setController(downloadController); //cargamos el controller
            //Todo revisar si crearé un VBox o el padre será de otro tipo
            VBox downloadBox = loader.load(); //En este caso el padre de la ventana es un Vbox en JavaFx

            String filename = url.substring(url.lastIndexOf("/") + 1); //para añadirle a cada pestaña el final de la URL que le pasamos, gracias al filename.
            //tpDownloads.setTabClosingPolicy(TabClosingPolicy.ALL_TABS); Otra forma de poder cerrar las pestañas con la política de JavaFX -Borja-
            tpDownloads.getTabs().add(new Tab(filename, downloadBox)); //Lo añadimos al panel de ventana de "gesDescargaHome.fxml"y añadimos una PESTAÑA por cada descarga que le damos.
            //Se puede hacer desde Scence en las propiedades del TabPane ->TabClosingPolicy poner ALL_TABS
            downloadBox.getScene().getWindow(); //Parar cerrar cada pestaña creada en el Tab

            allDownloads.put(url, downloadController); //Cada vez que lancemos una descarga lo añadimos al mapa para poder cancelarlas todas, tenemos cada url de descarga asociado al objeto que se crea por cada descarga

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Para descargar desde Fichero
     * @param event
     */
    @FXML
    public void launchFileDownload(ActionEvent event) {
        String urlText = tfUrl.getText(); //Le pasamos la ruta de donde esta el ficehro
        tfUrl.clear();
        tfUrl.requestFocus();
        readDLC(); //Llamamos al método que leerá el fichero
    }

    /**
     *Para leer un fichero con url de descargas
     */
    @FXML
    public void readDLC() {
        // Pedir el fichero que queremos procesar su contenido para añadir multiples descargas (FileChooser)
        //Para preguntarnos el ficehro que queremos abrir
        //FileChooser fileChooser = new FileChooser(); // FileChooser: Tipica ventana para buscar un fichero por windows
        //File file = fileChooser.showSaveDialog(tfUrl.getScene().getWindow()); // Aparece con un botón que pone Save
        //File file = fileChooser.showOpenDialog(tfUrl.getScene().getWindow()); // Aparece con un botón que pone Open
        //if (file == null)
        //    return;
        // FIN Para preguntarnos donde queremos guardar el fichero

        // Quitar comentario para descarga desde fichero otra opcion.

        try {
            //File dlcFile = new File("dlc.txt"); //recogia el fichero desde la raiz del proyecto, no le damos la opcion de elegir donde esta el fichero al usuario
            //Para preguntarnos donde esta el fichero que queremosusar para leerlo y descargar su contenido
            FileChooser fileChooser = new FileChooser(); // FileChooser: Tipica ventana para buscar un fichero por windows
            File dlcFile = fileChooser.showOpenDialog(tfUrl.getScene().getWindow()); // Aparece una ventana para buscar el archivo que queremos leer
            if (dlcFile == null)
                return;
            // FIN Para preguntarnos donde esta el fichero que queremosusar para leerlo y descargar su contenido

            Scanner reader = new Scanner(dlcFile);
            while (reader.hasNextLine()) { //leemos las lineas del fichero
                String data = reader.nextLine();
                System.out.println(data);
                launchDownload(data);
            }
            reader.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Se ha producido un error");
            fnfe.printStackTrace();
        }

        //Leo el fichero y cargo cada linea un List (Clase Files)

        //Files.read //Clase gana que lee todas las lineas de uin fichero y nos devuelve un lista de String con las lineas del fichero

        // Para cada linea, llamar al método launchDownload que por cada linea de fichero que le pasamos creo una pestaña de descarga
    }

    /**
     * Método que llamamos desde el boton btLog
     * @param event
     */
    @FXML
    public void readLog(ActionEvent event) {
        readLog();
    }

    /**
     * Prueba
     */
    public void readLog() {
        try {

            FXMLLoader loader = new FXMLLoader(); //Creamos un objeto FMXLloader que se encargará de Montarnos la interfaz de lo otra ventana
            loader.setLocation(R.getUI("gesLog.fxml")); // Le pasamos la localización de la ventana diseñada con JavaFx
            LogController logController = new LogController(); //Creamos su propio controler desde su clase LogController para gestionar los botones y demás cosas
            loader.setController(logController);
            //Todo revisar si crearé un VBox o el padre será de otro tipo
            VBox logBox = loader.load(); //En este caso el padre de la ventana es un Vbox en JavaFx

        } catch (IOException ioe) {
            ioe.printStackTrace(); //Pintamos la traza
        }
    }

    /**
     * Recorremos el ArrayList y cancelamos todas las descargas
     */
    @FXML
    public void stopAllDownloads() {
        for ( DownloadController downloadController : allDownloads.values()) // devuelve una coleccion de los valores
            downloadController.stop();
    }

    /**
     * Método que llamamos desde le boton btRoute para seleccionar el directorio de descarga
     */
    @FXML
    public void routeSelect(Event event) {
        DirectoryChooser directoryChooser = new DirectoryChooser(); //Clase para buscar y selecionar un directorio
        File file = directoryChooser.showDialog(tfRoute.getScene().getWindow()); //Creamos un fichero con la ruta seleccionado en el explorador de Windows
        route = file.getPath(); //Ingresamos la ruta dentro del String
        tfRoute.setText(route); //devolvemos el string al label
    }

    /**
     * Método que llamamos desde le boton btRouteShow para mostrar en el TExt Field la ruta de descarga
     */
    @FXML
    public void routeShow(Event event) {
        route = System.getProperty("user.dir"); //Ingresamos la ruta dentro del String
        tfRoute.setText(route); //devolvemos el string al label
    }
}

