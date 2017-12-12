package animadicer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Fernando
 */
public class CargarSettings {
    
    public static Settings importJason(File file, String path) {
        JSONParser parser = new JSONParser();
        Settings settings = new Settings();
        
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(file));
            settings.setAbiertas(((String)jsonObj.get("abiertas")).equals("true"));
            settings.setCapicua(((String)jsonObj.get("capicua")).equals("true"));
            settings.setFisicos(((String)jsonObj.get("fisicos")).equals("true"));
        } catch(IOException | ParseException | NumberFormatException e) {
        }
        
        return settings;
    }
    
    public static void exportJason(String path, Settings settings){
        JSONObject obj = new JSONObject(); //(condicion)?valor1:valor2;
        obj.put("abiertas",(settings.getAbiertas())?"true":"false");
        obj.put("capicua",(settings.getCapicua())?"true":"false");
        obj.put("fisicos",(settings.getFisicos())?"true":"false");
        
        try (FileWriter file = new FileWriter(path)) {
            file.write(obj.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(FileJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
