package animadicer;

import java.util.Arrays;
import java.util.List;

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
    int[] ki; // FUE, AGI, DES, CON, VOL, POD
    int[] kiActual;
    int kiGenerico;
    int kiGenericoActual;
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

    public Ficha(String path) {
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
        log = "";
        kiGenerico = 5;
        kiGenericoActual = 5;

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
    

    private int maxKi()
    {
        int index = 0;
        int max = -1;
        
        for(int i = 0; i < 6; i++){
            if(kiActual[i] > max && kiActual[i] > 0){
                max = kiActual[i];
                index = i;
            }
        }
        
        return index;
    }
    
    private int minKi()
    {
        int index = 0;
        int min = Integer.MAX_VALUE;
        
        for(int i = 0; i < 6; i++){
            if(kiActual[i] < min && kiActual[i] < ki[i]){
                min = kiActual[i];
                index = i;
            }
        }
        
        if(kiActual[index] == ki[index])
            index = -1;
        
        return index;
    }
    
    private int repartirKi(int a_repartir, boolean sumar){
        if(sumar){
            while(a_repartir > 0){
                int min = minKi();
                
                if(min == -1)
                    return a_repartir;
                
                kiActual[min]++;
                a_repartir--;
            }
        }
        else{
            while(a_repartir > 0){
                kiActual[maxKi()]--;
                a_repartir--;
            } 
        }
        return a_repartir;
    }
    
    public void rstKi()
    {
        for(int i = 0; i < 6; i++)
            kiActual[i] = 0;
        
        kiGenericoActual = 0;
    }
    
    public void setKiTotalActual(int a_repartir)
    {
        //Sumar
        if(a_repartir > 0){
            int queda = repartirKi(a_repartir, true);
            if(queda > 0)
                kiGenericoActual = Math.min(queda + kiGenericoActual, kiGenerico);
        }
        //Restar
        else if(a_repartir < 0){
            a_repartir = Math.abs(a_repartir);
            if(a_repartir > kiGenericoActual){
                a_repartir -= kiGenericoActual;
                kiGenericoActual = 0;
            }
            else{
                kiGenericoActual -= a_repartir;
                return;
            }
            repartirKi(a_repartir, false);
        } 
    }
    
    
    public void setKi(int[] ki) {
        System.arraycopy(ki, 0, this.ki, 0, 6);
    }

    public int getKi(int type) {
        return this.ki[type];
    }
    
    public int getKiTotalActual()
    {
        int suma = 0;
        
        for(int i = 0; i < 6; i++)
            suma += kiActual[i];
        
        return suma + kiGenericoActual;
    }
    
    public void setKiActual(int[] ki) {
        System.arraycopy(ki, 0, this.kiActual, 0, 6);
    }
    
    public void setKiActual(int cual, int ki) {
        this.kiActual[cual] = ki;
    }

    public int getKiActual(int type) {
        return this.kiActual[type];
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

    public int getKiTotal() {
        int suma = 0;
        
        for(int i = 0; i < 6; i++)
            suma += ki[i];
        
        return suma + kiGenerico;
    }

    public void setKiGenericoActual(int value) {
        this.kiGenericoActual = value;
    }

    public int getKiGenerico() {
        return this.kiGenerico;
    }
    
    public int getKiGenericoActual() {
        return this.kiGenericoActual;
    }
}