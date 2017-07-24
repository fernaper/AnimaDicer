/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer;

/**
 *
 * @author Fernando
 */
public class Arma {
    String nombre;
    int damage;
    String[]critico; // critico 1 y critico 2
    int entereza;
    int rotura;
    int presencia;
    
    public Arma () {
        this.nombre = "";
        this.damage = 0;
        this.entereza = 0;
        this.rotura = 0;
        this.presencia = 0;
        this.critico = new String[2];
        this.critico[0] = "FIL";
        this.critico[1] = "CON";
    }
    
    public int getPresencia() {
        return this.presencia;
    }
    
    public void setPresencia(int presencia){
        this.presencia = presencia;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public String getCritico(int i){
        return this.critico[i];
    }
    
    public int getRotura (){
        return this.rotura;
    }
    
    public int getEntereza() {
        return this.entereza;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public void setCritico(String[] critico) {
        this.critico = critico;
    }
    
    public void setEntereza(int entereza) {
        this.entereza = entereza;
    }
    
    public void setRotura(int rotura) {
        this.rotura = rotura;
    }
    
}
