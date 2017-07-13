package animadicer;

public class Settings {
    boolean abiertas; // Si esta a true las abiertas son cada vez m�s dif�ciles
    boolean capicua;
    boolean tiradas;
    boolean fisicos;

    public Settings() {
        abiertas = true;
        capicua = false;
        tiradas = true;
        fisicos = false;
    }

    public boolean getAbiertas () {
        return abiertas;
    }

    public boolean getCapicua() {
        return capicua;
    }
    
    public boolean getTiradas () {
        return tiradas;
    }
    
    public boolean getFisicos(){
        return fisicos;
    }

    public void setAbiertas(boolean abiertas) {
        this.abiertas = abiertas;
    }

    public void setCapicua(boolean capicua) {
        this.capicua = capicua;
    }
    
    public void setTiradas(boolean tiradas) {
        this.tiradas = tiradas;
    }
    
    public void setFisicos(boolean fisicos) {
        this.fisicos = fisicos;
    }
}
