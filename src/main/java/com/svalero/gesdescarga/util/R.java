package com.svalero.gesdescarga.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    public static InputStream getImage(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("images" + File.separator + name);
    }

    /**
     * Lo usamos para no tener que poner la ruta entera de donde se ubican las pantallas creadas con SceneBuilder
     * @param name
     * @return
     */
    public static URL getUI(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }
}

