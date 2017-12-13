package animadicer;

public class Settings {
    boolean abiertas; // Si esta a true las abiertas son cada vez m�s dif�ciles
    boolean capicua;
    boolean fisicos;
    boolean autoGuardado;

    public Settings() {
        abiertas = true;
        capicua = false;
        fisicos = false;
        autoGuardado = true;
    }

    public boolean getAbiertas () {
        return abiertas;
    }

    public boolean getCapicua() {
        return capicua;
    }
    
    public boolean getFisicos(){
        return fisicos;
    }
    
    public boolean getAutoguardado(){
        return autoGuardado;
    }

    public void setAbiertas(boolean abiertas) {
        this.abiertas = abiertas;
    }

    public void setCapicua(boolean capicua) {
        this.capicua = capicua;
    }
    
    public void setFisicos(boolean fisicos) {
        this.fisicos = fisicos;
    }
    
    public void setAutoguardado(boolean autoGuardado) {
        this.autoGuardado = autoGuardado;
    }
}
