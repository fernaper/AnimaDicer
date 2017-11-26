package animadicer;

public class Ficha {
    String nombre;
    String categoria;
    String notas;
    int nivel;
    int vida;
    int vidaActual;
    int zeon;
    int zeonActual;
    int cansancio;
    int cansancioActual;
    int[] ki; // FUE, AGI, DES, CON, VOL, POD
    int[] kiActual;
    // Todos los tipos de armaduras (para saber cu�l es aplicable en cada caso)
    int[]atributos; // AGI, CON, DES, FUE, INT, PER, POD, VOL

    int[]combate; // ATA, DEF, ESQ, PROY.MAGICA, PROY.PSIQUICA
    int[]res; //PRESENCIA, RF, RE, RV, RM, RP
    int[]secundarias; // 38 secundarias

    int[]turno; // 5 turnos, dependiendo del arma que use
    Arma[]arma; // 4 armas puede usar
    Armadura[] armadura;

    int[] convocatoria; // CONVOCAR, DOMINAR, ATAR, DESCONVOCAR

    int potencialPsiquico;

    public Ficha() {
        ki = new int [6];
        kiActual = new int [6];
        atributos = new int [8];
        combate = new int [5];
        res = new int [6];
        secundarias = new int [38];
        turno = new int [5];
        convocatoria = new int [4];
        arma = new Arma[4];
        armadura = new Armadura[3];
    }

    public void setNotas(String notas) {
       this.notas = notas; 
    }
    
    public String getNotas () {
        return this.notas;
    }
    
    public void setArma(Arma[] arma) {
        this.arma = arma;
    }
    
    public Arma getArma(int i){
        return this.arma[i];
    }
    
    public void setArmadura(Armadura[] armadura) {
        this.armadura = armadura;
    }
    
    public Armadura getArmadura(int i) {
        return this.armadura[i];
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria () {
        return this.categoria;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVida() {
        return this.vida;
    }

    public void setVidaActual(int vida) {
        this.vidaActual = vida;
    }

    public int getVidaActual() {
        return this.vidaActual;
    }

    public void setZeon(int zeon) {
        this.zeon = zeon;
    }

    public int getZeon() {
        return this.zeon;
    }

    public void setZeonActual(int zeon) {
        this.zeonActual = zeon;
    }

    public int getZeonActual() {
        return this.zeonActual;
    }
    
    public int getCansancio() {
        return this.cansancio;
    }
    
    public void setCansancio (int cansancio) {
        this.cansancio = cansancio;
    }
    
    public int getCansancioActual() {
        return this.cansancioActual;
    }
    
    public void setCansancioActual (int cansancio) {
        this.cansancioActual = cansancio;
    }

    public void setKi(int[] ki) {
        for (int i = 0; i < 6; i++) {
            this.ki[i] = ki[i];
        }
    }

    public int getKi(int type) {
        return this.ki[type];
    }
    
    public void setKiActual(int[] ki) {
        for (int i = 0; i < 6; i++) {
            this.kiActual[i] = ki[i];
        }
    }
    
    public void setKiActual(int cual, int ki) {
        this.kiActual[cual] = ki;
    }

    public int getKiActual(int type) {
        return this.kiActual[type];
    }

    public void setAtributos(int[] atributos) {
        for (int i = 0; i < 8; i++) {
            this.atributos[i] = atributos[i];
        }
    }

    public int getAtributo(int i) {
        return atributos[i];
    }

    public void setCombate(int[] combate) {
        for (int i = 0; i < 5; i++) {
            this.combate[i] = combate[i];
        }
    }

    public int getCombate(int type) {
        return this.combate[type];
    }

    public void setRes(int[] res) {
        for (int i = 0; i < 6; i++) {
            this.res[i] = res[i];
        }
    }

    public int getRes(int type) {
        return this.res[type];
    }

    public void setSecundarias(int[] secundarias) {
        for (int i = 0; i < 38; i++) {
            this.secundarias[i] = secundarias[i];
        }
    }

    public int getSecundarias(int type) {
        return this.secundarias[type];
    }

    public void setTurno(int[] turno) {
        for (int i = 0; i < 5; i++) {
            this.turno[i] = turno[i];
        }
    }

    public int getTurno(int type) {
        return this.turno[type];
    }

    public void setConvocatoria(int[] convocatoria) {
        for (int i = 0; i < 4; i++) {
            this.convocatoria[i] = convocatoria[i];
        }
    }

    public int getConvocatoria(int type) {
        return this.convocatoria[type];
    }

    public void setPotencialPsiquico(int potencialPsiquico) {
        this.potencialPsiquico = potencialPsiquico;
    }

    public int getPotencialPsiquico () {
        return this.potencialPsiquico;
    }

}
