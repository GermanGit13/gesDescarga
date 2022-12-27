package com.svalero.gesdescarga.controller;

import com.svalero.gesdescarga.task.DownloadTask;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadController implements Initializable {

    public TextField tfUrl;
    public Label lbStatus;
    public ProgressBar pbProgress;
    private String urlText;
    private DownloadTask downloadTask;
    private Stage stage;
    private String dir = "H:\\Grapo DAM\\10 Programación de Servicios y Procesos\\AP_German_Rodriguez\\downloads";

    private static final Logger logger = LogManager.getLogger(DownloadController.class);

    public DownloadController(String urlText) {
        logger.info("Descarga " + urlText + " creada");
        this.urlText = urlText;
    }

    /**
     * initialize: Este método se ejecuta siempre no depende del usuario para nada, podemos usarlo para hacerlo automático la descarga
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUrl.setText(urlText); //Recogemos el texto de la caja

        // Quitar comentario para descarga desde fichero desde este método sería automático el guardar los ficheros
        try {
            String fileName = this.urlText.substring(this.urlText.lastIndexOf("/") + 1); //Extraemos la parte a continuación del / para ponerla como nombre de fichero
            File file = new File(dir, fileName);
            file.createNewFile();

            downloadTask = new DownloadTask(urlText, file); //Le pasamos a Task la url y el archivo donde se guarda

            pbProgress.progressProperty().unbind();
            pbProgress.progressProperty().bind(downloadTask.progressProperty());

            downloadTask.stateProperty().addListener((observableValue, oldState, newState) -> {
                System.out.println(observableValue.toString());
                if (newState == Worker.State.SUCCEEDED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La descarga ha terminado");
                    alert.show();
                }
            });

            downloadTask.messageProperty()
                    .addListener((observableValue, oldValue, newValue) -> lbStatus.setText(newValue));

            new Thread(downloadTask).start(); //Se crea el Thread con la tarea de descarga
        } catch (MalformedURLException murle) {
            murle.printStackTrace();
            logger.error("URL mal formada", murle.fillInStackTrace());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Método start se debe realizar una acción sobre el, para poder usarlo se debe pulsar un boton etc.. no es como initialize que se ejecuta si o si
     * Método para iniciar la descarga
     * @param event
     * @FXML para que JavaFx sepa que es un método suyo
     */
    @FXML
    public void start(ActionEvent event) {
        try {
            //Para preguntarnos donde queremos guardar el fichero
            FileChooser fileChooser = new FileChooser(); // FileChooser: Tipica ventana para buscar un fichero por windows
            File file = fileChooser.showSaveDialog(tfUrl.getScene().getWindow()); // Aparece con un botón que pone Save
            File fileOpen = fileChooser.showOpenDialog(tfUrl.getScene().getWindow()); // Aparece con un botón que pone Open
            if (file == null)
                return;
            // FIN Para preguntarnos donde queremos guardar el fichero


            downloadTask = new DownloadTask(urlText, file); //creamos la tarea que tira del DownloadTask

            pbProgress.progressProperty().unbind();
            pbProgress.progressProperty().bind(downloadTask.progressProperty());

            downloadTask.stateProperty().addListener((observableValue, oldState, newState) -> {
                System.out.println(observableValue.toString());
                if (newState == Worker.State.SUCCEEDED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La descarga ha terminado");
                    alert.show();
                    //Para recoger la excepcion del limite de descargar y mandar el mensaje al usuario
//                } else if (newState == Worker.State.FAILED) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setContentText("El tamaño de la descarga es superior al limite de 10Mb");
//                    alert.show();
                }
            });

            downloadTask.messageProperty().addListener((observableValue, oldValue, newValue) -> lbStatus.setText(newValue));

            new Thread(downloadTask).start(); //Aqui es donde se crea el Thread para hacer la concurrencia
        } catch (MalformedURLException murle) {
            murle.printStackTrace();
            logger.error("URL mal formada", murle.fillInStackTrace());
        }
    }

    /**
     * Método para parar las descargas
     * @param event
     */
    @FXML
    public void stop(ActionEvent event) {
        stop();

        logger.trace("Descarga " + urlText + " detenida");
        downloadTask.cancel();
    }

    /**
     * Método para parar las descargas
     */
    public void stop() {
        if (downloadTask != null)
            downloadTask.cancel();
    }

    /**
     * Recogemos el valor de la caja texto
     * @return retomamos la Url que hemos recogido
     */
    public String getUrlText() {
        return urlText;
    }

}

