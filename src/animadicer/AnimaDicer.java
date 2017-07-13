/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer;

import animadicer.ventana.Dicer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Fernando
 */
public class AnimaDicer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        new Thread () {
            @Override
            public void run() {
                String dir = direccionCargaGuardado();
                Dicer d = new Dicer(new CargarSettings(dir).cargar(),new Anima(dir).start(), dir);
            }
        }.start();
    }
    
    public static String direccionCargaGuardado() {
        String myDocuments = null;

        try {
            Process p = Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
            p.waitFor();

            byte[] b;
            try (InputStream in = p.getInputStream()) {
                b = new byte[in.available()];
                in.read(b);
            }

            myDocuments = new String(b);
            myDocuments = myDocuments.split("\\s\\s+")[4];

        } catch(IOException | InterruptedException t) {
        }
        
        File folder = new File(myDocuments + "\\AnimaDicer");
        if (!folder.exists())
            folder.mkdir();
        
        return myDocuments + "\\AnimaDicer";
    }
    
}
