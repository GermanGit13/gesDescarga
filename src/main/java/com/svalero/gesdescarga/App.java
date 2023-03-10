package com.svalero.gesdescarga;

import com.svalero.gesdescarga.controller.AppController;
import com.svalero.gesdescarga.controller.SplashController;
import com.svalero.gesdescarga.util.R;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Creamos la clase App como principal para interactuar con el usuario y los controller
 * extiende de Application para decirle que es una aplicación e implementamos el método start
 */
public class App extends Application {

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {

         /**
         * Clase FXMLLoader: Es un formato basado en XML que permite a los desarrolladores definir interfaces de usuario de manera declarativa,
         * en lugar de definir interfaces de manera programática, es decir, mediante el uso directo de las API de JavaFX
         * Creamos un splashScreen que se verá al inicio y mandará al controller del Splash dondese carga la ventana inicial del programa
         */

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(R.getUI("gesScreen.fxml"));
         loader.setController(new SplashController());
         anchorPane = loader.load();

        Scene scene = new Scene(anchorPane); //Usamos la clase Scene para inicialiar la ventana (escena)
        stage.setScene(scene); // Le indicamos que tiene que inicialiazar
        stage.setTitle("Splash Screen"); //Ponerle nombre a la ventana principal
        stage.show(); // Le indicamos que pinte la escena
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}

/**
 * Sin SplashSreen
 */
//        /**
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
