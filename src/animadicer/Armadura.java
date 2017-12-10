package animadicer;

/**
 *
 * @author Fernando
 */
public class Armadura {
    String nombre;
    int[]defensa; // FIL, CON, PEN, CAL, ELE, FRI, ENER
    String posicion;
    
    public Armadura() {
        this.nombre = "";
        this.posicion = "";
        this.defensa = new int[7];
        
        for (int i = 0; i < 7; i++) {
            this.defensa[i] = 0;
        }
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
