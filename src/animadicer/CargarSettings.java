/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Fernando
 */
public class CargarSettings {
    private final String direccion;
    public CargarSettings (String direccion) {
        this.direccion = direccion;
    }
    
    public Settings cargar() {
        Settings s = new Settings();
        //File archivo;
        FileReader fr = null;
        BufferedReader br;

        try {
           // Apertura del fichero y creacion de BufferedReader para poder
           // hacer una lectura comoda (disponer del metodo readLine()).
           //archivo = new File (getClass().getResource("/opciones/settings.txt").toURI());
           fr = new FileReader (direccion+"\\settings.txt");
           br = new BufferedReader(fr);

           // Lectura del fichero
           String linea;
           //while((linea=br.readLine())!=null) {
           //    System.out.println(linea);
           //}
           for (int i = 0; i < 4; i++) {
               linea = br.readLine();
               switch (i) {
                   case 0:
                       s.setAbiertas("true".equals(linea));
                       break;
                   case 1:
                       s.setCapicua("true".equals(linea));
                       break;
                   case 2:
                       s.setTiradas("true".equals(linea));
                       break;
                   case 3:
                       s.setFisicos("true".equals(linea));
                       break;
                   default:
                       break;
               }
           }
        }
        catch(IOException e){
        }finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (IOException e){ 
           }
        }
        
        return s;
    }
}
