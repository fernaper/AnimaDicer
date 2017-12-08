/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer;

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
public class FileJSON {
    public FileJSON () {
        
    }
    
    public static Ficha importJason(File file, String path) {
        Ficha ficha = new Ficha(path);
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
                JSONArray ki = (JSONArray) jsonObj.get("kiActual");
                Iterator<String> it = ki.iterator();
                int i = 0;
                int arrayKi[] = new int[6];
                while (it.hasNext()) {
                    arrayKi[i] = Integer.parseInt(it.next());
                    i++;
                }
                ficha.setKiActual(arrayKi);
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
                    arrayArma[i] = new Arma();
                    JSONObject jsonobject = (JSONObject)arma.get(i);

                    arrayArma[i].setNombre(jsonobject.get("nombre").toString());
                    arrayArma[i].setDamage(Integer.parseInt(jsonobject.get("damage").toString()));
                    arrayArma[i].setEntereza(Integer.parseInt(jsonobject.get("entereza").toString()));
                    arrayArma[i].setRotura(Integer.parseInt(jsonobject.get("rotura").toString()));
                    arrayArma[i].setPresencia(Integer.parseInt(jsonobject.get("presencia").toString()));
                    String arrayC[] = new String[2];
                    arrayC[0] = jsonobject.get("critico-1").toString();
                    arrayC[1] = jsonobject.get("critico-2").toString();
                    arrayArma[i].setCritico(arrayC);
                }
                
                ficha.setArma(arrayArma);
            }
            {
                JSONArray armadura = (JSONArray) jsonObj.get("armadura");
                Armadura arrayArmadura[] = new Armadura[3];
                for (int i = 0; i < armadura.size(); i++) {
                    arrayArmadura[i] = new Armadura();
                    JSONObject jsonobject = (JSONObject)armadura.get(i);
                    arrayArmadura[i].setNombre(jsonobject.get("nombre").toString());
                    arrayArmadura[i].setPosicion(jsonobject.get("posicion").toString());
                    int arrayD[] = new int[7];
                    arrayD[0]=(Integer.parseInt(jsonobject.get("defensa-1").toString()));
                    arrayD[1]=(Integer.parseInt(jsonobject.get("defensa-2").toString()));
                    arrayD[2]=(Integer.parseInt(jsonobject.get("defensa-3").toString()));
                    arrayD[3]=(Integer.parseInt(jsonobject.get("defensa-4").toString()));
                    arrayD[4]=(Integer.parseInt(jsonobject.get("defensa-5").toString()));
                    arrayD[5]=(Integer.parseInt(jsonobject.get("defensa-6").toString()));
                    arrayD[6]=(Integer.parseInt(jsonobject.get("defensa-7").toString()));
                    
                    arrayArmadura[i].setDefensa(arrayD);
                }
                ficha.setArmadura(arrayArmadura);
                
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
            ficha.setLog((String)jsonObj.get("log"));
            ficha.setNotas((String)jsonObj.get("notas"));
        } catch(IOException | ParseException | NumberFormatException e) {
        }
        
        return ficha;
    }
    
    public static void exportJason(String path, Ficha ficha){
        JSONObject obj = new JSONObject();
        obj.put("nombre",ficha.getNombre());
        obj.put("categoria",ficha.getCategoria());
        obj.put("notas",ficha.getNotas());
        obj.put("nivel",String.valueOf(ficha.getNivel()));
        obj.put("vida",String.valueOf(ficha.getVida()));
        obj.put("vidaActual",String.valueOf(ficha.getVidaActual()));
        obj.put("zeon",String.valueOf(ficha.getZeon()));
        obj.put("zeonActual",String.valueOf(ficha.getZeonActual()));
        obj.put("cansancio",String.valueOf(ficha.getCansancio()));
        obj.put("cansancioActual",String.valueOf(ficha.getCansancioActual()));
        obj.put("log", ficha.getLog());
        obj.put("notas", ficha.getNotas());
        
        JSONArray ki = new JSONArray();
        JSONArray kiActual = new JSONArray();
        for (int i = 0; i < 6; i++) {
            ki.add(String.valueOf(ficha.getKi(i)));
            kiActual.add(String.valueOf(ficha.getKiActual(i)));
        }
        obj.put("ki", ki);
        obj.put("kiActual", kiActual);
        
        JSONArray atributos = new JSONArray();
        for (int i = 0; i < 8; i++) {
            atributos.add(String.valueOf(ficha.getAtributo(i)));
        }
        obj.put("atributos",atributos);
        
        JSONArray combate = new JSONArray();
        for (int i = 0; i < 5; i++) {
            combate.add(String.valueOf(ficha.getCombate(i)));
        }
        obj.put("combate",combate);
        
        JSONArray res = new JSONArray();
        for (int i = 0; i < 6; i++) {
            res.add(String.valueOf(ficha.getRes(i)));
        }
        obj.put("res",res);
        
        JSONArray secundarias = new JSONArray();
        for (int i = 0; i < 38; i++) {
            secundarias.add(String.valueOf(ficha.getSecundarias(i)));
        }
        obj.put("secundarias",secundarias);
        
        JSONArray turno = new JSONArray();
        for (int i = 0; i < 5; i++) {
            turno.add(String.valueOf(ficha.getTurno(i)));
        }
        obj.put("turno",turno);
        
        JSONArray armas = new JSONArray();
        for (int i = 0; i < 4; i++) {
            JSONObject arma = new JSONObject();
            arma.put("nombre",ficha.getArma(i).getNombre());
            arma.put("damage",String.valueOf(ficha.getArma(i).getDamage()));
            arma.put("critico-1",ficha.getArma(i).getCritico(0));
            arma.put("critico-2",ficha.getArma(i).getCritico(1));
            arma.put("entereza",String.valueOf(ficha.getArma(i).getEntereza()));
            arma.put("rotura",String.valueOf(ficha.getArma(i).getRotura()));
            arma.put("presencia",String.valueOf(ficha.getArma(i).getPresencia()));
            armas.add(arma);
        }
        obj.put("arma",armas);
        
        JSONArray armaduras = new JSONArray();
        for (int i = 0; i < 3; i++) {
            JSONObject armadura = new JSONObject();
            armadura.put("nombre",ficha.getArmadura(i).getNombre());
            armadura.put("posicion",ficha.getArmadura(i).getPosicion());
            armadura.put("defensa-1",String.valueOf(ficha.getArmadura(i).getDefensa(0)));
            armadura.put("defensa-2",String.valueOf(ficha.getArmadura(i).getDefensa(1)));
            armadura.put("defensa-3",String.valueOf(ficha.getArmadura(i).getDefensa(2)));
            armadura.put("defensa-4",String.valueOf(ficha.getArmadura(i).getDefensa(3)));
            armadura.put("defensa-5",String.valueOf(ficha.getArmadura(i).getDefensa(4)));
            armadura.put("defensa-6",String.valueOf(ficha.getArmadura(i).getDefensa(5)));
            armadura.put("defensa-7",String.valueOf(ficha.getArmadura(i).getDefensa(6)));
            armaduras.add(armadura);
        }
        obj.put("armadura",armaduras);
        
        JSONArray convocatoria = new JSONArray();
        for (int i = 0; i < 4; i++) {
            convocatoria.add(String.valueOf(ficha.getConvocatoria(i)));
        }
        obj.put("convocatoria",convocatoria);
        
        obj.put("potencialPsiquico",String.valueOf(ficha.getPotencialPsiquico()));
        try (FileWriter file = new FileWriter(path)) {
            file.write(obj.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(FileJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
