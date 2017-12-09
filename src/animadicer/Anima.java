package animadicer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;


public class Anima {
    FileInputStream file;
    Workbook workbook;
    Settings settings;
    Ficha ficha;
    String direccion;
    File seleccionado;

    public Anima (String dir) {
        this.direccion = dir;
        try {
            file = new FileInputStream(new File("test.xlsx"));
            workbook = WorkbookFactory.create(file);
            ficha = new Ficha("");
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (InvalidFormatException | EncryptedDocumentException ex) {
            Logger.getLogger(Anima.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Anima(File seleccionado, String dir) {
        this.direccion = dir;
        this.seleccionado = seleccionado;
        try {
            file = new FileInputStream(seleccionado);
            workbook = WorkbookFactory.create(file);
            ficha = new Ficha(seleccionado.getAbsolutePath());
        } catch (FileNotFoundException e) {
        } catch (IOException | InvalidFormatException | EncryptedDocumentException e) {
        }
    }
	
    public Ficha start () {
        this.ficha = crearGenerico();
        return this.ficha;
    }
    
    public Ficha cargar () {
        if (this.seleccionado.getName().endsWith("json")) {
            this.ficha = FileJSON.importJason(this.seleccionado, this.seleccionado.getAbsolutePath());
        } else {
            try {
                Sheet sheet = workbook.getSheetAt(0);
                String vExcel = "v1.0.0";
                
                try {
                    vExcel = sheet.getRow(0).getCell(0).getStringCellValue();
                } catch(NullPointerException ex) {}
                
                if ("v1.0.6".equals(vExcel)) {
                    //v1.0.6
                    this.ficha = cargarGenericoV106(sheet, this.seleccionado.getAbsolutePath());
                } else if ("Capacidades Físicas".equals(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("G")).getStringCellValue())) {
                    // v1.0.0
                    this.ficha = cargarGenericoViejo(sheet, this.seleccionado.getAbsolutePath());
                } else {
                    // v1.0.5
                    this.ficha = cargarGenericoV105(sheet, this.seleccionado.getAbsolutePath());
                }

            } catch (NullPointerException | CargaException ex) {}
            // cerramos el libro excel
            try {
                if (workbook != null)
                    workbook.close();
            } catch (IOException e) {
            }
        }
        
        return this.ficha;
    }
    
    public static void guardar (Ficha ficha) throws GuardaException {
        FileInputStream inputStream;
        Workbook workbook;
        try {
            inputStream = new FileInputStream(new File(ficha.getPath()));
            workbook = WorkbookFactory.create(inputStream);
        } catch (FileNotFoundException ex) {
            throw new GuardaException("Documento Excel no encontrado");
        } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
            throw new GuardaException("Documento Excel no encontrado");
        }
        
        try {
            Sheet sheet = workbook.getSheetAt(0);
            String vExcel = "v1.0.0";

            try {
                vExcel = sheet.getRow(0).getCell(0).getStringCellValue();
            } catch(NullPointerException ex) {}

            if ("v1.0.6".equals(vExcel)) {
                //v1.0.6
                
            } else if ("Capacidades Físicas".equals(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("G")).getStringCellValue())) {
                // v1.0.0
                try {
                    sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).setCellValue(ficha.getVidaActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar la vida actual");}
                try {
                    sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BB")).setCellValue(ficha.getZeonActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
            } else {
                // v1.0.5
                try {
                    sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).setCellValue(ficha.getVidaActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar la vida actual");}
                try {
                    sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BC")).setCellValue(ficha.getZeonActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
            }
            
            inputStream.close();
            try (FileOutputStream outputStream = new FileOutputStream(ficha.getPath())) {
                workbook.write(outputStream);
                workbook.close();
            }
            
        } catch (NullPointerException | CargaException | IOException ex) {
            throw new GuardaException("Error al guardar en el documento excel.");
        }
    }
    
    public Ficha getFicha() {
        return this.ficha;
    }
    
    public Ficha crear () {
        this.ficha = crearGenerico();
        return this.ficha;
    }
    
    private Ficha cargarGenericoViejo(Sheet sheet, String path) throws CargaException {
        // Testeo de que está leyendo una ficha y no un documento cualquiera
        if (!"Turno".equals(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("L")).getStringCellValue()) ||
            !"Creativas".equals(sheet.getRow(51).getCell(CellReference.convertColStringToIndex("P")).getStringCellValue())) {
            throw new CargaException("Este documento no es una ficha preparada");
        }
        ficha = new Ficha(path);
        
        ficha.setNombre(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setCategoria(sheet.getRow(1).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setNivel((int)(sheet.getRow(2).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
        ficha.setVida((int)(sheet.getRow(6).getCell(CellReference.convertColStringToIndex("V")).getNumericCellValue()));
        ficha.setZeon((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("BD")).getNumericCellValue()));
        ficha.setCansancio((int)(sheet.getRow(14).getCell(CellReference.convertColStringToIndex("H")).getNumericCellValue()));
        
        try {
            ficha.setVidaActual((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setVidaActual(ficha.getVida());
        }
        try {
            ficha.setZeonActual((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BB")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setZeonActual(this.ficha.getZeon());
        }
        try {
            ficha.setCansancioActual((int)(sheet.getRow(15).getCell(CellReference.convertColStringToIndex("H")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setCansancioActual(ficha.getCansancio());
        }
        
        {
            int ki = 0;
            for (int i = 0; i < 7; i++) {
                ki += (int)(sheet.getRow(2+i).getCell(CellReference.convertColStringToIndex("AN")).getNumericCellValue());
            }
            ficha.setKi(ki);
            ficha.setKiActual(ki);
        }
        {
            int[] atributos = new int [8];
            for (int i = 0; i < 8; i++) {
                atributos[i] = (int)(sheet.getRow(9+i).getCell(CellReference.convertColStringToIndex("D")).getNumericCellValue());
            }
            ficha.setAtributos(atributos);
        }
        {
            int[] combate = new int [5];
            combate[0] = (int)(sheet.getRow(33).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue());
            combate[1] = (int)(sheet.getRow(33).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue());
            combate[2] = (int)(sheet.getRow(33).getCell(CellReference.convertColStringToIndex("G")).getNumericCellValue());
            combate[3] = (int)(sheet.getRow(11).getCell(CellReference.convertColStringToIndex("AR")).getNumericCellValue());
            boolean error = false;
            try {
                combate[4] = (int)(sheet.getRow(21).getCell(CellReference.convertColStringToIndex("BQ")).getNumericCellValue());
            } catch (NullPointerException ex) {
                error = true;
            }
            
            if (error) {
                try {
                    combate[4] = (int)(sheet.getRow(20).getCell(CellReference.convertColStringToIndex("BP")).getNumericCellValue());
                } catch (NullPointerException ex) {
                    combate[4] = 0;
                }
            }

            ficha.setCombate(combate);
        }
        {
            int[] res = new int [6];
            int add = 0;
            if ("Resistencias".equals(sheet.getRow(57).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue()))
                add = 1;
            for (int i = 0; i < 6; i++) {
                res[i] = (int)(sheet.getRow(57+add+i).getCell(CellReference.convertColStringToIndex("H")).getNumericCellValue());
            }
            ficha.setRes(res);
        }
        {
            int[] secundarias = new int [38];
            int j = 0;
            for (int i = 0; i < 38+6; i++) {
                if (13+i != 19 && 13+i != 23 && 13+i != 27 && 13+i != 38 && 13+i != 43 && 13+i != 51) {
                    secundarias[j] = (int)(sheet.getRow(13+i).getCell(CellReference.convertColStringToIndex("V")).getNumericCellValue());
                    j++;
                }
            }

            ficha.setSecundarias(secundarias);
        }
        {
            int[] turno = new int [5];
            turno[0] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());
            turno[1] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue());
            turno[2] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
            turno[3] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("R")).getNumericCellValue());
            turno[4] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("S")).getNumericCellValue());

            ficha.setTurno(turno);
        }
        {
            Arma[] arma;
            arma = new Arma[4];
            
            int add = 0;
            if ("Arma 1".equals(sheet.getRow(36).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue()))
                add = 1;
            
            for (int i = 0; i < 4; i++) {
                String [] criticos = new String [2];
                String nombre = sheet.getRow(35+(5*i)+add).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue();
                arma[i] = new Arma();
                if (!"Nada".equals(nombre)) {
                    arma[i].setNombre(nombre);
                    criticos[0] = sheet.getRow(39+(5*i)+add).getCell(CellReference.convertColStringToIndex("F")).getStringCellValue();
                    criticos[1] = sheet.getRow(39+(5*i)+add).getCell(CellReference.convertColStringToIndex("G")).getStringCellValue();
                    arma[i].setCritico(criticos);
                    arma[i].setDamage((int)(sheet.getRow(37+(5*i)+add).getCell(CellReference.convertColStringToIndex("F")).getNumericCellValue()));
                    arma[i].setEntereza((int)(sheet.getRow(39+(5*i)+add).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
                    arma[i].setRotura((int)(sheet.getRow(39+(5*i)+add).getCell(CellReference.convertColStringToIndex("D")).getNumericCellValue()));
                    arma[i].setPresencia((int)(sheet.getRow(39+(5*i)+add).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue()));
                } else {
                    criticos[0] = "FIL";
                    criticos[1] = "CON";
                    arma[i].setCritico(criticos);
                    arma[i].setDamage(0);
                    arma[i].setEntereza(0);
                    arma[i].setNombre("Nada");
                    arma[i].setRotura(0);
                    arma[i].setPresencia(0);
                } 
            }
            
            ficha.setArma(arma);
        }
        {
            Armadura []armadura;
            armadura = new Armadura[3];
            for (int i = 0; i < 3; i++) {
                armadura[i] = new Armadura();
                armadura[i].setNombre(sheet.getRow(20+i).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue());
                int []defensa = new int[7];
                for (int j = 0; j < 7; j++) {
                    defensa[j] = (int)(sheet.getRow(24+i).getCell(2+j).getNumericCellValue());
                }
                
                armadura[i].setDefensa(defensa);
                armadura[i].setPosicion(sheet.getRow(24+i).getCell(CellReference.convertColStringToIndex("J")).getStringCellValue());
            }
            ficha.setArmadura(armadura);
        }
        {
            int[] convocatoria;
            convocatoria = new int [4];
            for (int i = 0; i < 4; i++) {
                    convocatoria[i] = (int)(sheet.getRow(76+i).getCell(CellReference.convertColStringToIndex("V")).getNumericCellValue());
            }
            ficha.setConvocatoria(convocatoria);
        }

        ficha.setPotencialPsiquico((int)(sheet.getRow(5).getCell(CellReference.convertColStringToIndex("BH")).getNumericCellValue()));
        
        ficha.setNotas(cargarNotas());
        return ficha;
    }
    
    private Ficha cargarGenericoV105(Sheet sheet, String path) throws CargaException {
        // Testeo de que está leyendo una ficha y no un documento cualquiera
        if (!"Turno".equals(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("L")).getStringCellValue()) ||
            !"Creativas".equals(sheet.getRow(51).getCell(CellReference.convertColStringToIndex("P")).getStringCellValue())) {
            throw new CargaException("Este documento no es una ficha preparada");
        }
        ficha = new Ficha(path);
        
        ficha.setNombre(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setCategoria(sheet.getRow(1).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setNivel((int)(sheet.getRow(2).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
        ficha.setVida((int)(sheet.getRow(6).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue()));
        ficha.setZeon((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("BE")).getNumericCellValue()));
        ficha.setCansancio((int)(sheet.getRow(87).getCell(CellReference.convertColStringToIndex("O")).getNumericCellValue()));
        
        try {
            ficha.setVidaActual((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setVidaActual(ficha.getVida());
        }
        try {
            ficha.setZeonActual((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BC")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setZeonActual(this.ficha.getZeon());
        }
        try {
            ficha.setCansancioActual((int)(sheet.getRow(87).getCell(CellReference.convertColStringToIndex("O")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setCansancioActual(ficha.getCansancio());
        }
        
        {
            int ki = 0;
            for (int i = 0; i < 7; i++) {
                ki += (int)(sheet.getRow(2+i).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue());
            }
            ficha.setKi(ki);
            ficha.setKiActual(ki);
        }
        {
            int[] atributos = new int [8];
            for (int i = 0; i < 8; i++) {
                atributos[i] = (int)(sheet.getRow(9+i).getCell(CellReference.convertColStringToIndex("D")).getNumericCellValue());
            }
            ficha.setAtributos(atributos);
        }
        {
            int[] combate = new int [5];
            combate[0] = (int)(sheet.getRow(40).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue());
            combate[1] = (int)(sheet.getRow(40).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue());
            combate[2] = (int)(sheet.getRow(40).getCell(CellReference.convertColStringToIndex("G")).getNumericCellValue());
            combate[3] = (int)(sheet.getRow(11).getCell(CellReference.convertColStringToIndex("AS")).getNumericCellValue());
            combate[4] = (int)(sheet.getRow(21).getCell(CellReference.convertColStringToIndex("BR")).getNumericCellValue());

            ficha.setCombate(combate);
        }
        {
            int[] res = new int [6];
            for (int i = 0; i < 6; i++) {
                res[i] = (int)(sheet.getRow(70+i).getCell(CellReference.convertColStringToIndex("H")).getNumericCellValue());
            }
            ficha.setRes(res);
        }
        {
            int[] secundarias = new int [38];
            int j = 0;
            for (int i = 0; i < 38+6; i++) {
                if (13+i != 19 && 13+i != 23 && 13+i != 27 && 13+i != 38 && 13+i != 43 && 13+i != 51) {
                    secundarias[j] = (int)(sheet.getRow(13+i).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue());
                    j++;
                }
            }

            ficha.setSecundarias(secundarias);
        }
        {
            int[] turno = new int [5];
            turno[0] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());
            turno[1] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue());
            turno[2] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
            turno[3] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("R")).getNumericCellValue());
            turno[4] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("S")).getNumericCellValue());

            ficha.setTurno(turno);
        }
        {
            Arma[] arma;
            arma = new Arma[4];
            
            for (int i = 0; i < 4; i++) {
                String [] criticos = new String [2];
                String nombre = sheet.getRow(44+(6*i)).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue();
                arma[i] = new Arma();
                if (!"".equals(nombre)) {
                    arma[i].setNombre(nombre);
                    criticos[0] = sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("F")).getStringCellValue();
                    criticos[1] = sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("G")).getStringCellValue();
                    arma[i].setCritico(criticos);
                    arma[i].setDamage((int)(sheet.getRow(46+(6*i)).getCell(CellReference.convertColStringToIndex("F")).getNumericCellValue()));
                    arma[i].setEntereza((int)(sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
                    arma[i].setRotura((int)(sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("D")).getNumericCellValue()));
                    arma[i].setPresencia((int)(sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue()));
                } else {
                    criticos[0] = "FIL";
                    criticos[1] = "CON";
                    arma[i].setCritico(criticos);
                    arma[i].setDamage(0);
                    arma[i].setEntereza(0);
                    arma[i].setNombre("Nada");
                    arma[i].setRotura(0);
                    arma[i].setPresencia(0);
                } 
            }
            
            ficha.setArma(arma);
        }
        {
            Armadura []armadura;
            armadura = new Armadura[3];
            for (int i = 0; i < 3; i++) {
                armadura[i] = new Armadura();
                armadura[i].setNombre(sheet.getRow(23+i).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue());
                int []defensa = new int[7];
                for (int j = 0; j < 7; j++) {
                    defensa[j] = (int)(sheet.getRow(28+i).getCell(2+j).getNumericCellValue());
                }
                
                armadura[i].setDefensa(defensa);
                armadura[i].setPosicion(sheet.getRow(28+i).getCell(CellReference.convertColStringToIndex("J")).getStringCellValue());
            }
            ficha.setArmadura(armadura);
        }
        {
            int[] convocatoria;
            convocatoria = new int [4];
            for (int i = 0; i < 4; i++) {
                    convocatoria[i] = (int)(sheet.getRow(76+i).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue());
            }
            ficha.setConvocatoria(convocatoria);
        }

        ficha.setPotencialPsiquico((int)(sheet.getRow(5).getCell(CellReference.convertColStringToIndex("BI")).getNumericCellValue()));
        
        ficha.setNotas(cargarNotas());
        return ficha;
    }
    
    private Ficha cargarGenericoV106(Sheet sheet, String path) throws CargaException {
        // Testeo de que está leyendo una ficha y no un documento cualquiera
        if (!"Turno".equals(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("L")).getStringCellValue()) ||
            !"Creativas".equals(sheet.getRow(51).getCell(CellReference.convertColStringToIndex("P")).getStringCellValue())) {
            throw new CargaException("Este documento no es una ficha preparada");
        }
        ficha = new Ficha(path);
        
        ficha.setNombre(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setCategoria(sheet.getRow(1).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setNivel((int)(sheet.getRow(2).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
        ficha.setVida((int)(sheet.getRow(6).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue()));
        ficha.setZeon((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("BE")).getNumericCellValue()));
        ficha.setCansancio((int)(sheet.getRow(87).getCell(CellReference.convertColStringToIndex("O")).getNumericCellValue()));
        
        ficha.setVidaActual(ficha.getVida());
        ficha.setZeonActual(ficha.getZeon());
        ficha.setCansancioActual(ficha.getCansancio());
        
        {
            int ki = 0;
            for (int i = 0; i < 7; i++) {
                ki += (int)(sheet.getRow(2+i).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue());
            }
            ficha.setKi(ki);
            ficha.setKiActual(ki);
        }
        {
            int[] atributos = new int [8];
            for (int i = 0; i < 8; i++) {
                atributos[i] = (int)(sheet.getRow(9+i).getCell(CellReference.convertColStringToIndex("D")).getNumericCellValue());
            }
            ficha.setAtributos(atributos);
        }
        {
            int[] combate = new int [5];
            combate[0] = (int)(sheet.getRow(40).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue());
            combate[1] = (int)(sheet.getRow(40).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue());
            combate[2] = (int)(sheet.getRow(40).getCell(CellReference.convertColStringToIndex("G")).getNumericCellValue());
            combate[3] = (int)(sheet.getRow(11).getCell(CellReference.convertColStringToIndex("AS")).getNumericCellValue());
            combate[4] = (int)(sheet.getRow(21).getCell(CellReference.convertColStringToIndex("BR")).getNumericCellValue());

            ficha.setCombate(combate);
        }
        {
            int[] res = new int [6];
            for (int i = 0; i < 6; i++) {
                res[i] = (int)(sheet.getRow(70+i).getCell(CellReference.convertColStringToIndex("H")).getNumericCellValue());
            }
            ficha.setRes(res);
        }
        {
            int[] secundarias = new int [38];
            int j = 0;
            for (int i = 0; i < 38+6; i++) {
                if (13+i != 19 && 13+i != 23 && 13+i != 27 && 13+i != 38 && 13+i != 43 && 13+i != 51) {
                    secundarias[j] = (int)(sheet.getRow(13+i).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue());
                    j++;
                }
            }

            ficha.setSecundarias(secundarias);
        }
        {
            int[] turno = new int [5];
            turno[0] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());
            turno[1] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue());
            turno[2] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
            turno[3] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("R")).getNumericCellValue());
            turno[4] = (int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("S")).getNumericCellValue());

            ficha.setTurno(turno);
        }
        {
            Arma[] arma;
            arma = new Arma[4];
            
            for (int i = 0; i < 4; i++) {
                String [] criticos = new String [2];
                String nombre = sheet.getRow(44+(6*i)).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue();
                arma[i] = new Arma();
                if (!"".equals(nombre)) {
                    arma[i].setNombre(nombre);
                    criticos[0] = sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("F")).getStringCellValue();
                    criticos[1] = sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("G")).getStringCellValue();
                    arma[i].setCritico(criticos);
                    arma[i].setDamage((int)(sheet.getRow(46+(6*i)).getCell(CellReference.convertColStringToIndex("F")).getNumericCellValue()));
                    arma[i].setEntereza((int)(sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
                    arma[i].setRotura((int)(sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("D")).getNumericCellValue()));
                    arma[i].setPresencia((int)(sheet.getRow(48+(6*i)).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue()));
                } else {
                    criticos[0] = "FIL";
                    criticos[1] = "CON";
                    arma[i].setCritico(criticos);
                    arma[i].setDamage(0);
                    arma[i].setEntereza(0);
                    arma[i].setNombre("Nada");
                    arma[i].setRotura(0);
                    arma[i].setPresencia(0);
                } 
            }
            
            ficha.setArma(arma);
        }
        {
            Armadura []armadura;
            armadura = new Armadura[3];
            for (int i = 0; i < 3; i++) {
                armadura[i] = new Armadura();
                armadura[i].setNombre(sheet.getRow(23+i).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue());
                int []defensa = new int[7];
                for (int j = 0; j < 7; j++) {
                    defensa[j] = (int)(sheet.getRow(28+i).getCell(2+j).getNumericCellValue());
                }
                
                armadura[i].setDefensa(defensa);
                armadura[i].setPosicion(sheet.getRow(28+i).getCell(CellReference.convertColStringToIndex("J")).getStringCellValue());
            }
            ficha.setArmadura(armadura);
        }
        {
            int[] convocatoria;
            convocatoria = new int [4];
            for (int i = 0; i < 4; i++) {
                    convocatoria[i] = (int)(sheet.getRow(76+i).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue());
            }
            ficha.setConvocatoria(convocatoria);
        }

        ficha.setPotencialPsiquico((int)(sheet.getRow(5).getCell(CellReference.convertColStringToIndex("BI")).getNumericCellValue()));
        
        ficha.setNotas(cargarNotas());
        return ficha;
    }
    
    
    private String cargarNotas() {
        String s = "";
        FileReader fr = null;
        BufferedReader br;

        try {
           fr = new FileReader (direccion+"\\notas\\"+ficha.getNombre()+".txt");
           br = new BufferedReader(fr);
           
           // Lectura del fichero
           String linea;
           while((linea=br.readLine())!=null) {
                if (!"".equals(s))
                    s += "\n" + linea;
                else
                    s = linea;
           }
        }
        catch(IOException e){
        }finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (IOException e2){ 
           }
        }
        
        return s;
    }
    
    private Ficha crearGenerico () {
        ficha = new Ficha("");
        ficha.setNombre("Nombre");
        ficha.setCategoria("Novel");
        ficha.setNivel(1);
        ficha.setVida(75);
        ficha.setZeon(80);
        ficha.setCansancio(5);
        ficha.setCansancioActual(5);
        ficha.setVidaActual(75);
        ficha.setZeonActual(80);
        
        {
            ficha.setKi(30);
            ficha.setKiActual(30);
        }
        {
            int[] atributos = new int [8];
            for (int i = 0; i < 8; i++) {
                atributos[i] = 5;
            }
            ficha.setAtributos(atributos);
        }
        {
            int[] combate = new int [5];
            combate[0] = 0;
            combate[1] = 0;
            combate[2] = 0;
            combate[3] = 0;
            combate[4] = 0;
            ficha.setCombate(combate);
        }
        {
            int[] res = new int [6];
            for (int i = 0; i < 6; i++) {
                res[i] = 30;
            }
            ficha.setRes(res);
        }
        {
            int[] secundarias = new int [38];
            for (int i = 0; i < 38; i++) {
                secundarias[i] = -30;
            }

            ficha.setSecundarias(secundarias);
        }
        {
            int[] turno = new int [5];
            
            for (int i = 0; i < 5; i++)
                turno[i] = 45;

            ficha.setTurno(turno);
        }
        {
            Arma[] arma;
            arma = new Arma[4];
            
            for (int i = 0; i < 4; i++) {
                String [] criticos = new String [2];
                criticos[0] = "FIL";
                criticos[1] = "CON";
                arma[i] = new Arma();
                arma[i].setCritico(criticos);
                arma[i].setDamage(0);
                arma[i].setEntereza(0);
                arma[i].setNombre("Arma " + String.valueOf(i+1));
                arma[i].setRotura(0);
                arma[i].setPresencia(0);
            }
            ficha.setArma(arma);
        }
        {
            Armadura[] armadura;
            armadura = new Armadura[3];
            
            for (int i = 0; i < 3; i++) {
                armadura[i] = new Armadura();
                
                armadura[i].setNombre("Nada");
                int []defensa = new int[7];
                for (int j = 0; j < 7; j++) {
                    defensa[j] = 0;
                }
                
                armadura[i].setDefensa(defensa);
                armadura[i].setPosicion("NO");
            }
            ficha.setArmadura(armadura);
        }
        {
            int[] convocatoria;
            convocatoria = new int [4];
            for (int i = 0; i < 4; i++) {
                    convocatoria[i] = 0;
            }
            ficha.setConvocatoria(convocatoria);
        }
        
        ficha.setPotencialPsiquico(0);
        
        ficha.setNotas("");
        
        return ficha;
    }
}
