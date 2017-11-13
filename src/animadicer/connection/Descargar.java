/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer.connection;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class Descargar {
    private static final String URL = "https://raw.githubusercontent.com/TheCorPlay/AnimaDicer/master/Downloader";

    public Descargar() {}
    
    public static void descargar(String ficheroDestino) throws Exception {
        try {
            String urlD = getURL();

            URL ficheroUrl = new URL(urlD);
            OutputStream outputStream;
            try (InputStream inputStream = ficheroUrl.openStream()) {
                outputStream = new FileOutputStream(ficheroDestino); // path y nombre del nuevo fichero creado
                byte[] b = new byte[400000];
                int longitud;
                while ((longitud = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, longitud);
                }
                // Cerramos la conexión entrada
            } // path y nombre del nuevo fichero creado
            outputStream.close(); // Cerramos la conexión salida
        } catch (java.net.MalformedURLException ex) {
            System.err.println("Error. URL mal formado.");
        }
    }
    
    public static void actualiza(URL ficheroUrl, String ficheroDestino) throws IOException {
        OutputStream outputStream;
        try (InputStream inputStream = ficheroUrl.openStream()) {
            outputStream = new FileOutputStream(ficheroDestino); // path y nombre del nuevo fichero creado
            byte[] b = new byte[50000000]; // Reservo unos 50 MB
            int longitud;
            while ((longitud = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, longitud);
            }
            // Cerramos la conexión entrada
        } // path y nombre del nuevo fichero creado
        outputStream.close(); // Cerramos la conexión salida
    }
    
    public static URL actualizar(String version){
        try{
            String urlD = getUpdateURL(version);
            if (urlD == null)
                return null;
            URL ficheroUrl = new URL(urlD);
            return ficheroUrl;
        } catch (java.net.MalformedURLException ex) {
            System.err.println("Error. URL mal formado.");
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Descargar.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static String getURL() throws IOException {
        URL url2 = new URL(URL);
        URLConnection uc = url2.openConnection();
        uc.connect();
        String contenido;
        try ( //Creamos el objeto con el que vamos a leer
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()))) {
            contenido = in.readLine();
        }

        return contenido;
    }
    
    private static String getUpdateURL(String version) throws IOException {
        URL url2 = new URL(URL);
        URLConnection uc = url2.openConnection();
        uc.connect();
        String contenido;
        try ( //Creamos el objeto con el que vamos a leer
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()))) {
            // Ahora compruebo si la versión es mejor
            in.readLine();
            contenido = in.readLine();
            String[] vector = contenido.split(" ");
            if (!newVersion(vector[0],version)) {
                // No necesita actualizar
                return null;
            }
            contenido = vector[1];
        }
        
        return contenido;
    }
    
    private static boolean newVersion(String urlVersion, String myVersion) {
        urlVersion = urlVersion.substring(1);
        myVersion = myVersion.substring(1);
        
        String [] url = urlVersion.split("\\.");
        String [] my = myVersion.split("\\.");
        
        for (int i = 0; i < Math.min(url.length, my.length); i++) {
            try {
                int u = Integer.parseInt(url[i]);
                int m = Integer.parseInt(my[i]);

                if (u > m) {
                    return true;
                } else if (u < m) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }
}
