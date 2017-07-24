/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer.connection;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 *
 * @author Fernando
 */
public class Descargar {
    private static final String url = "https://github.com/TheCorPlay/AnimaDicer/raw/master/Animat%C3%A9%20a%20hacer%20fichas%20en%20xls%20v12.06.2017.xlsx";

    public Descargar() {}
    
    public static void descargar(String ficheroDestino) throws Exception {
        URL ficheroUrl = new URL(url);
        InputStream inputStream = ficheroUrl.openStream();			
        OutputStream outputStream = new FileOutputStream(ficheroDestino); // path y nombre del nuevo fichero creado

        byte[] b = new byte[400000];
        int longitud;

        while ((longitud = inputStream.read(b)) != -1) {
           outputStream.write(b, 0, longitud);
        }

        inputStream.close();  // Cerramos la conexión entrada
        outputStream.close(); // Cerramos la conexión salida
     }
}
