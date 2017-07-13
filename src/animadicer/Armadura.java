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
public class Armadura {
    String nombre;
    int[]defensa; // FIL, CON, PEN, CAL, ELE, FRI, ENER
    String posicion;
    
    public void Armadura() {
        this.defensa = new int[7];
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setDefensa(int[] defensa) {
        this.defensa = defensa;
    }
    
    public int getDefensa(int i) {
        return this.defensa[i];
    }
    
    public void setPosicion(String posicion){
        this.posicion = posicion;
    }
    
    public String getPosicion(){
        return this.posicion;
    }
    
}
