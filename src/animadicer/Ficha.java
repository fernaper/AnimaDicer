package animadicer;

public class Ficha {
    String nombre;
    String categoria;
    String notas;
    String log;
    String path;
    int nivel;
    int vida;
    int vidaActual;
    int zeon;
    int zeonActual;
    int cansancio;
    int cansancioActual;
    int ki;
    int kiActual;
    
    // Todos los tipos de armaduras (para saber cu�l es aplicable en cada caso)
    int[]atributos; // AGI, CON, DES, FUE, INT, PER, POD, VOL

    int[]combate; // ATA, DEF, ESQ, PROY.MAGICA, PROY.PSIQUICA
    int[]res; //PRESENCIA, RF, RE, RV, RM, RP
    int[]secundarias; // 38 secundarias

    int[]turno; // 5 turnos, dependiendo del arma que use
    Arma[]arma; // 4 armas puede usar
    Armadura[] armadura; // 3 armaduras y 1 casco

    int[] convocatoria; // CONVOCAR, DOMINAR, ATAR, DESCONVOCAR

    int potencialPsiquico;

    public Ficha(String path) {
        atributos = new int [8];
        combate = new int [5];
        res = new int [6];
        secundarias = new int [38];
        turno = new int [5];
        convocatoria = new int [4];
        arma = new Arma[4];
        armadura = new Armadura[4];
        log = "";
        this.path = path;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public void setNotas(String notas) {
       this.notas = notas; 
    }
    
    public String getNotas () {
        return this.notas;
    }
    
    public void setArma(Arma[] arma) {
        System.arraycopy(arma, 0, this.arma, 0, 4);
    }
    
    public Arma getArma(int i){
        return this.arma[i];
    }
    
    public void setArmadura(Armadura[] armadura) {
        System.arraycopy(armadura, 0, this.armadura, 0, 4);
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
    
    public void setKi(int ki) {
        this.ki = ki;
    }

    public int getKi() {
        return this.ki;
    }
    
    public void setKiActual(int ki) {
        this.kiActual = ki;
    }

    public int getKiActual() {
        return this.kiActual;
    }

    public void setAtributos(int[] atributos) {
        System.arraycopy(atributos, 0, this.atributos, 0, 8);
    }

    public int getAtributo(int i) {
        return atributos[i];
    }

    public void setCombate(int[] combate) {
        System.arraycopy(combate, 0, this.combate, 0, 5);
    }

    public int getCombate(int type) {
        return this.combate[type];
    }

    public void setRes(int[] res) {
        System.arraycopy(res, 0, this.res, 0, 6);
    }

    public int getRes(int type) {
        return this.res[type];
    }

    public void setSecundarias(int[] secundarias) {
        System.arraycopy(secundarias, 0, this.secundarias, 0, 38);
    }

    public int getSecundarias(int type) {
        return this.secundarias[type];
    }

    public void setTurno(int[] turno) {
        System.arraycopy(turno, 0, this.turno, 0, 5);
    }

    public int getTurno(int type) {
        return this.turno[type];
    }

    public void setConvocatoria(int[] convocatoria) {
        System.arraycopy(convocatoria, 0, this.convocatoria, 0, 4);
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

    public String getLog() {
        return this.log;
    }
    
    public void setLog(String text) {
        this.log = text;
    }
    
    public void addLog(String text) {
        this.log += text;
    }
}