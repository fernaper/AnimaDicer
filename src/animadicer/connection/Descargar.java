/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer.connection;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
    private static final String url = "https://github.com/TheCorPlay/AnimaDicer/blob/master/Downloader";

    public Descargar() {}
    
    public static void descargar(String ficheroDestino) throws Exception {
        try {
            String urlD = getURL();

            URL ficheroUrl = new URL(urlD);
            InputStream inputStream = ficheroUrl.openStream();			
            OutputStream outputStream = new FileOutputStream(ficheroDestino); // path y nombre del nuevo fichero creado

            byte[] b = new byte[400000];
            int longitud;

            while ((longitud = inputStream.read(b)) != -1) {
               outputStream.write(b, 0, longitud);
            }

            inputStream.close();  // Cerramos la conexión entrada
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
        URL url2 = new URL(url);
        URLConnection uc = url2.openConnection();
        uc.connect();
        //Creamos el objeto con el que vamos a leer
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String inputLine;
        String contenido = "";
        int numLine = 0;

        while ((inputLine = in.readLine()) != null) {
            if (numLine == 482) {
                contenido = inputLine;
                contenido = contenido.substring("        <td id=\"LC1\" class=\"blob-code blob-code-inner js-file-line\">".length(),contenido.length()-5);
                break;
            }
            numLine++;
        }
        in.close();

        return contenido;
    }
    
    private static String getUpdateURL(String version) throws IOException {
        URL url2 = new URL(url);
        URLConnection uc = url2.openConnection();
        uc.connect();
        //Creamos el objeto con el que vamos a leer
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String inputLine;
        String contenido = "";
        int numLine = 0;

        while ((inputLine = in.readLine()) != null) {            
            // Ahora compruebo si la versión es mejor
            if (numLine == 488) {
                contenido = inputLine;
                contenido = contenido.substring("<td id=\"LC1\" class=\"blob-code blob-code-inner js-file-line\">".length(),contenido.length()-5);
                String[] vector = contenido.split(" ");
                if (vector[0].equals(version)) {
                    // No necesita actualizar
                    return null;
                }
                contenido = vector[1];
                break;
            }
            numLine++;
        }
        in.close();
        
        return contenido;
    }
    
    private static boolean newVersion(String urlVersion, String myVersion) {
        urlVersion = urlVersion.substring(1);
        myVersion = myVersion.substring(1);
        
        String [] url = urlVersion.split(".");
        String [] my = myVersion.split(".");
        
        for (int i = 0; i < Math.min(url.length, my.length); i++) {
            try {
                int u = Integer.parseInt(url[i]);
                int m = Integer.parseInt(my[i]);
                if (u > m) {
                    return true;
                } else if (i < m) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }
}
