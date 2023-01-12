package com.svalero.gesdescarga.task;

import com.svalero.gesdescarga.controller.DownloadController;
import javafx.concurrent.Task;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Extendemos de la clase Task que es la que nos permite realizar procesos de manera concurrente
 */
public class DownloadTask extends Task<Integer> {

    private URL url;
    private File file;

    private static final Logger logger = LogManager.getLogger(DownloadController.class); //Para disponer de un log de las Descargas

    public DownloadTask(String urlText, File file) throws MalformedURLException {
        this.url = new URL(urlText);
        this.file = file;
    }

    @Override
    protected Integer call() throws Exception {
        logger.trace("Descarga" + url.toString() + "iniciada");
        updateMessage("Conectando con el servidor ..");

        /**
         * Comienzo código para descargar ficheros de internet
         * Código para conectarnos a una URL de Internet
         */
        URLConnection urlConnection = url.openConnection();
        double fileSize = urlConnection.getContentLength(); // Podemos leer el tamaño del fichero alojado en la URL
        //Para poder poner una limitación máxima de descarga por ejemplo si no es cuenta premiun
        double megaSize = fileSize / 1048576; // Tamaño del fichero que queremos descargar pasado a MB
//        if (megaSize < 10) { //Si el tamaño de la descarga es superior a 10 Mb
//            logger.trace("Maximo tamaño de fichero alcanzado"); //Lo escribimos en el log
//            throw new Exception("Max. size"); //Creamos una excepción para que no deje proceder a la descarga se la mandamos al controlador DownloadCrontroller
//        }

        BufferedInputStream in = new BufferedInputStream(url.openStream()); // Leemos y copiamos al disco
        FileOutputStream fileOutputStream = new FileOutputStream(file);// Objeto java que permite escribir en un disco
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        int totalRead = 0;
        double downloadProgress = 0;
        double downloadSize =0;
        //Dos formas de medir el tiempo de descarga Libreria de JAva estandar o Clase de apache commos
        //Instant es de la libreia básica de JAVA la comentamos para usar las de apache commons
//        Instant start = Instant.now(); //Libreria que permite calcular el tiempo que lleva la descarga recogemos el método now
//        Instant current; //Tiempo que lleva descargando
//        float elapsedTime; //Tiempo calculado

        //Clase StopWatch de apache commons para medir el tiempo de la descarga
        StopWatch watch = new StopWatch(); //Creamos el objeto
        watch.start(); //Inicializamos el cronometro ya no guardamos el tiempo, el objeto se encarga de hacerlo a diferencia de si usamos la libreria de java

        /**
         * Con un buble ir leyendo trocito a trocito de la descarga
         * Calcular la velocidad, el avanza, el progreso de la descarga.
         * Código listo para usar para realizar descargas de Internet
         */
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            downloadProgress = ((double) totalRead / fileSize);
            updateProgress(downloadProgress, 1);

            //updateMessage(downloadProgress * 100 + " % "); Escribir en la barra de progreso lo comento porque usare o la opcion JAva o Apache commons

            //PAra la libreria java
//            current = Instant.now(); //Calcular el tiempo actual por cada kilobytes que vamos descargando en cada interaccion
//            elapsedTime = Duration.between(start, current).toSeconds();
//            updateMessage(Math.round(downloadProgress *100) + "%\t\t\t\t" +Math.round(elapsedTime) + "sec."); //Para redondear el porcentaje de descarga completado. \t -> para separar, son tabuladores

            //Para usar la libreria de Apache Commons gracias a watch.getTime se encarga de ir realizando el calculo de tiempo
            updateMessage(Math.round(downloadProgress * 100) + " %\t\t" + Math.round(watch.getTime()/1000) + " sec.\t\t" + Math.round(totalRead/ (1024 * 1024)) + " Mb /" + Math.round(fileSize / (1024*1024)) + " Mb. " + Math.round((downloadProgress*megaSize)/watch.getTime()/1000) + " Mbs"); //Para redondear el porcentaje de descarga completado. \t -> para separar, son tabuladores
            //updateMessage(Math.round(downloadProgress * 100) + " %\t\t\t\t" + Math.round(downloadProgress*megaSize) + " de " + Math.round(megaSize) + "MB");//Otra Opción Para ver porcentaje descarga, tamaño total y descarga en mb. Borja

            //Modificado para hacer la descarga más lenta
            //Thread.sleep(1); //Cada bloque de descarga de 1024 Mb para el Thread un milisegundo

            fileOutputStream.write(dataBuffer, 0, bytesRead); //Escribir el disco HD
            totalRead += bytesRead;
            /**
             * Fin Código para realizar descargas de internet
             */

            if (isCancelled()) {
                logger.trace("Descarga " + url.toString() + " cancelada");
                return null;
            }
        }

        updateProgress(1, 1); //Para actualizar la barra de proceso
        updateMessage("100 %"); //Mensaje cuando termina

        logger.trace("Descarga " + url.toString() + " finalizada");

        return null;
    }
}
