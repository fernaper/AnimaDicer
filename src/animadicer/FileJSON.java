/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Fernando
 */
public class FileJSON {
    public FileJSON () {
        
    }
    
    public Ficha importJason(File file) {
        Ficha ficha = new Ficha();
        JSONParser parser = new JSONParser();
        
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(file));
            
            ficha.setNombre((String)jsonObj.get("nombre"));
            ficha.setCategoria((String)jsonObj.get("categoria"));
            ficha.setNotas((String)jsonObj.get("notas"));
            ficha.setNivel(Integer.parseInt((String)jsonObj.get("nivel")));
            ficha.setVida(Integer.parseInt((String)jsonObj.get("vida")));
            ficha.setVidaActual(Integer.parseInt((String)jsonObj.get("vidaActual")));
            ficha.setZeon(Integer.parseInt((String)jsonObj.get("zeon")));
            ficha.setZeonActual(Integer.parseInt((String)jsonObj.get("zeonActual")));
            ficha.setCansancio(Integer.parseInt((String)jsonObj.get("cansancio")));
            ficha.setCansancioActual(Integer.parseInt((String)jsonObj.get("cansancioActual")));
            ficha.setPotencialPsiquico(Integer.parseInt((String)jsonObj.get("potencialPsiquico")));
            {
                JSONArray ki = (JSONArray) jsonObj.get("ki");
                Iterator<String> it = ki.iterator();
                int i = 0;
                int arrayKi[] = new int[6];
                while (it.hasNext()) {
                    arrayKi[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setKi(arrayKi);
            }
            {
                JSONArray atributos = (JSONArray) jsonObj.get("atributos");
                Iterator<String> it = atributos.iterator();
                int i = 0;
                int arrayAtributos[] = new int[8];
                while (it.hasNext()) {
                    arrayAtributos[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setAtributos(arrayAtributos);
            }
            {
                JSONArray combate = (JSONArray) jsonObj.get("combate");
                Iterator<String> it = combate.iterator();
                int i = 0;
                int arrayCombate[] = new int[5];
                while (it.hasNext()) {
                    arrayCombate[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setCombate(arrayCombate);
            }
            {
                JSONArray res = (JSONArray) jsonObj.get("res");
                Iterator<String> it = res.iterator();
                int i = 0;
                int arrayRes[] = new int[6];
                while (it.hasNext()) {
                    arrayRes[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setRes(arrayRes);
            }
            {
                JSONArray secundarias = (JSONArray) jsonObj.get("secundarias");
                Iterator<String> it = secundarias.iterator();
                int i = 0;
                int arraySecundarias[] = new int[38];
                while (it.hasNext()) {
                    arraySecundarias[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setSecundarias(arraySecundarias);
            }
            {
                JSONArray turno = (JSONArray) jsonObj.get("turno");
                Iterator<String> it = turno.iterator();
                int i = 0;
                int arrayTurno[] = new int[5];
                while (it.hasNext()) {
                    arrayTurno[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setTurno(arrayTurno);
            }
            {
                JSONArray arma = (JSONArray) jsonObj.get("arma");
                Arma arrayArma[] = new Arma[4];

                for (int i = 0; i < arma.size(); i++) {
                    JSONObject jsonobject = (JSONObject)arma.get(i);
                    arrayArma[i].setNombre((String)jsonobject.get("nombre"));
                    arrayArma[i].setDamage(Integer.parseInt((String)jsonobject.get("damage")));
                    arrayArma[i].setEntereza(Integer.parseInt((String)jsonobject.get("entereza")));
                    arrayArma[i].setRotura(Integer.parseInt((String)jsonobject.get("rotura")));
                    arrayArma[i].setPresencia(Integer.parseInt((String)jsonobject.get("presencia")));
                    
                    JSONArray critico = (JSONArray) jsonobject.get("critico");
                    int j = 0;
                    String arrayC[] = new String[2];
                    Iterator<String> it = critico.iterator();
                    while (it.hasNext()) {
                        arrayC[i] = it.next();
                        j++;
                    }
                    arrayArma[i].setCritico(arrayC);
                }
                ficha.setArma(arrayArma);
            }
            {
                JSONArray armadura = (JSONArray) jsonObj.get("armadura");
                Armadura arrayArmadura[] = new Armadura[3];

                for (int i = 0; i < armadura.size(); i++) {
                    JSONObject jsonobject = (JSONObject)armadura.get(i);
                    arrayArmadura[i].setNombre((String)jsonobject.get("nombre"));
                    arrayArmadura[i].setPosicion((String)jsonobject.get("posicion"));
                    
                    JSONArray defensa = (JSONArray) jsonobject.get("defensa");
                    int j = 0;
                    int arrayD[] = new int[7];
                    Iterator<String> it = defensa.iterator();
                    while (it.hasNext()) {
                        arrayD[i] = Integer.parseInt(it.next());
                        j++;
                    }
                    arrayArmadura[i].setDefensa(arrayD);
                }
                ficha.setArmadura(arrayArmadura);
            }
            {
                JSONArray convocatoria = (JSONArray) jsonObj.get("convocatoria");
                Iterator<String> it = convocatoria.iterator();
                int i = 0;
                int arrayConvocatoria[] = new int[4];
                while (it.hasNext()) {
                    arrayConvocatoria[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setConvocatoria(arrayConvocatoria);
            }
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            
        }
        
        return ficha;
    }
    
    // Lo he puesto para testear
    public static void main (String args[]) {
        FileJSON json = new FileJSON();
        File file = new File("C:\\Users\\Usuario\\Desktop\\test.json");
        json.importJason(file);
    }
}
