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
}
