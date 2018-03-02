package animadicer;

import animadicer.ventana.Dicer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Fernando Pérez Gutiérrez, Alfredo Pérez Gutiérrez y Juan Mas Aguilar
 */
public class AnimaDicer {
    private static final String VERSION = "v1.1.0";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        new Thread () {
            @Override
            public void run() {
                String dir = direccionCargaGuardado();
                Dicer d = new Dicer(VERSION, CargarSettings.importJason(new File(dir+"\\settings.json"), dir),new Anima(dir).start(), dir);
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
