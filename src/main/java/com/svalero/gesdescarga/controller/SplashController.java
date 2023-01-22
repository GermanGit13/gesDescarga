package com.svalero.gesdescarga.controller;

import com.svalero.gesdescarga.util.R;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           new splashScreen().start();
    }

    class  splashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000); ///Para que esté 5seg

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(R.getUI("gesDescargaHome.fxml")); //Usamos la clase util creada para solo indicarle el nombre el layout creado desde Scence Builder
                        loader.setController(new AppController()); //Le pasamos el controller creado con los métodos que vamos querer usar
                        ScrollPane scrollPane = null; //En este caso el padre de la ventana es un ScrolPane en JavaFx es un Vbox siempre sera del tipo padre que creemos en Scene Builder
                        try {
                            scrollPane = loader.load();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }

                        Stage stage = new Stage();
                        Scene scene = new Scene(scrollPane); //Usamos la clase Scene para inicialiar la ventana (escena)
                        stage.setScene(scene); // Le indicamos que tiene que inicialiazar
                        stage.setTitle("Gestor de Descargadas"); //Ponerle nombre a la ventana principal
                        stage.show(); // Le indicamos que pinte la escena

                        anchorPane.getScene().getWindow().hide(); //Para ocultar la ventana
                    }
                });
            } catch (InterruptedException iex) {
                iex.printStackTrace();
            }
        }
    }
}





//         * Clase FXMLLoader: Es un formato basado en XML que permite a los desarrolladores definir interfaces de usuario de manera declarativa,
//         * en lugar de definir interfaces de manera programática, es decir, mediante el uso directo de las API de JavaFX
//         */
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(R.getUI("gesDescargaHome.fxml")); //Usamos la clase util creada para solo indicarle el nombre el layout creado desde Scence Builder
//        loader.setController(new AppController()); //Le pasamos el controller creado con los métodos que vamos querer usar
//        ScrollPane scrollPane = loader.load(); //En este caso el padre de la ventana es un ScrolPane en JavaFx es un Vbox siempre sera del tipo padre que creemos en Scene Builder
//
//        Scene scene = new Scene(scrollPane); //Usamos la clase Scene para inicialiar la ventana (escena)
//        stage.setScene(scene); // Le indicamos que tiene que inicialiazar
//        stage.setTitle("Gestor de Descargadas"); //Ponerle nombre a la ventana principal
//        stage.show(); // Le indicamos que pinte la escena


