/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTextArea;

/**
 *
 * @author Fernando
 */
public class Log {
    ArrayList<String> info;
    JTextArea area;
    String notas;
    
    public Log (JTextArea area) {
        this.info = new ArrayList<>();
        this.area = area;
        this.notas = "";
    }
    
    public void resetLog(String log) {
        this.info = new ArrayList<>();
        this.info.add(log);
        this.area.setText(log);
        this.notas = "";
    }
    
    public String getNotas () {
        return this.notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public String addLog(String tirada) {
        Calendar calendario = new GregorianCalendar();
        String text = "[" + calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE)+"] " + tirada;
        info.add(text);
        area.append(text+"\n");
        return text+"\n";
    }
    
    public ArrayList<String> getLog() {
        return this.info;
    }
    
    public String queBoton(String tipo, int i) {
        String res = "";
        
        if (null != tipo) switch (tipo) {
            case "Atributos":
                res = queAtributo(i);
                break;
            case "Secundarias":
                res = queSecundaria(i);
                break;
            case "Resistencias":
                res = queResistencia(i);
                break;
            case "Arma":
                res = queArma(i);
                break;
            case "Combate Fisico":
                res = queCombateFisico(i);
                break;
            case "Combate Sobrenatural":
                res = queCombateSobrenatural(i);
                break;
            case "Convocatoria":
                res = queConvocatoria(i);
                break;
            default:
                break;
        }
        
        return res;
    }
    
    private String queAtributo(int i) {
        String res;
        
        switch (i) {
            case 0:
                res = "AGI";
                break;
            case 1:
                res = "CON";
                break;
            case 2:
                res = "DES";
                break;
            case 3:
                res = "FUE";
                break;
            case 4:
                res = "INT";
                break;
            case 5:
                res = "PER";
                break;
            case 6:
                res = "POD";
                break;
            default:
                res = "VOL";
                break;
        }
        
        
        return res;
    }
    
    private String queSecundaria(int i) {
        String res;
        
        switch (i) {
            case 0:
                res = "Acrobacias";
                break;
            case 1:
                res = "Atletismo";
                break;
            case 2:
                res = "Montar";
                break;
            case 3:
                res = "Nadar";
                break;
            case 4:
                res = "Trepar";
                break;
            case 5:
                res = "Saltar";
                break;
            case 6:
                res = "Frialdad";
                break;
            case 7:
                res = "P.Fuerza";
                break;
            case 8:
                res = "Res.Dolor";
                break;
            case 9:
                res = "Advertir";
                break;
            case 10:
                res = "Buscar";
                break;
            case 11:
                res = "Rastrear";
                break;
            case 12:
                res = "Animales";
                break;
            case 13:
                res = "Ciencia";
                break;
            case 14:
                res = "Herbolaria";
                break;
            case 15:
                res = "Historia";
                break;
            case 16:
                res = "Medicina";
                break;
            case 17:
                res = "Memorizar";
                break;
            case 18:
                res = "Navegación";
                break;
            case 19:
                res = "Ocultismo";
                break;
            case 20:
                res = "Tasación";
                break;
            case 21:
                res = "V.Mágica";
                break;
            case 22:
                res = "Estilo";
                break;
            case 23:
                res = "Intimidar";
                break;
            case 24:
                res = "Liderazgo";
                break;
            case 25:
                res = "Persuasión";
                break;
            case 26:
                res = "Cerrajería";
                break;
            case 27:
                res = "Disfraz";
                break;
            case 28:
                res = "Ocultarse";
                break;
            case 29:
                res = "Robo";
                break;
            case 30:
                res = "Sigilo";
                break;
            case 31:
                res = "Trampería";
                break;
            case 32:
                res = "Venenos";
                break;
            case 33:
                res = "Arte";
                break;
            case 34:
                res = "Baile";
                break;
            case 35:
                res = "Forja";
                break;
            case 36:
                res = "Música";
                break;
            case 37:
                res = "T.Manos";
                break;
            default:
                res = "ERROR";
                break;
        }
        
        return res;
    }
    
    private String queResistencia (int i) {
        String res;
        
        switch (i) {
            case 0:
                res = "RF";
                break;
            case 1:
                res = "RE";
                break;
            case 2:
                res = "RV";
                break;
            case 3:
                res = "RM";
                break;
            case 4:
                res = "RP";
                break;
            default:
                res = "ERROR";
                break;
        }
        
        return res;
    }
    
    private String queArma (int i) {
        String res;
        
        switch (i) {
            case 0:
                res = "Entereza Arma 1";
                break;
            case 1:
                res = "Rotura Arma 1";
                break;
            case 2:
                res = "Presencia Arma 1";
                break;
            case 3:
                res = "Entereza Arma 2";
                break;
            case 4:
                res = "Rotura Arma 2";
                break;
            case 5:
                res = "Presencia Arma 2";
                break;
            case 6:
                res = "Entereza Arma 3";
                break;
            case 7:
                res = "Rotura Arma 3";
                break;
            case 8:
                res = "Presencia Arma 3";
                break;
            case 9:
                res = "Entereza Arma 4";
                break;
            case 10:
                res = "Rotura Arma 4";
                break;
            case 11:
                res = "Presencia Arma 4";
                break;
            default:
                res = "ERROR";
                break;
        }
        
        return res;
    }
    
    private String queCombateFisico(int i) {
        String res;
        
        switch (i) {
            case 0:
                res = "Ataque";
                break;
            case 1:
                res = "Defensa";
                break;
            case 2:
                res = "Esquiva";
                break;
            default:
                res = "ERROR";
                break;
        }
        
        return res;
    }
    
    private String queCombateSobrenatural (int i) {
        String res;
        
        switch (i) {
            case 0:
                res = "Proy. Mágica";
                break;
            case 1:
                res = "Potencial Psíquico";
                break;
            case 2:
                res = "Proy. Psíquica";
                break;
            default:
                res = "ERROR";
                break;
        }
        
        return res;
    }
    
    private String queConvocatoria (int i) {
        String res;
        
        switch (i) {
            case 0:
                res = "Convocar";
                break;
            case 1:
                res = "Dominar";
                break;
            case 2:
                res = "Atar";
                break;
            case 3:
                res = "Desconvocar";
                break;
            default:
                res = "ERROR";
                break;
        }
        
        return res;
    }
}
