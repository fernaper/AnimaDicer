package animadicer;

import animadicer.connection.Descargar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
                
                boolean solkar = false;
                try {
                    solkar = "Raza:".equals(sheet.getRow(22).getCell(CellReference.convertColStringToIndex("F")).getStringCellValue());
                } catch(IllegalStateException ex) {}
                
                if (solkar) {
                    // Ficha Solkar v7.5
                    this.ficha = cargarSolkarV75(this.seleccionado.getAbsolutePath());
                } else {
                    cargarVersionAlf(vExcel, sheet);
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
    
    private void cargarVersionAlf(String version, Sheet sheet) {
        if (Descargar.newVersion(version, "v1.0.8")) {
            // v1.0.9 o más
            this.ficha = cargarAlfV109(sheet, this.seleccionado.getAbsolutePath());
        } else if ("v1.0.7".equals(version)) {
            // v1.0.7
            this.ficha = cargarAlfV107(sheet, this.seleccionado.getAbsolutePath());
        } else if ("v1.0.6".equals(version)) {
            //v1.0.6
            this.ficha = cargarAlfV106(sheet, this.seleccionado.getAbsolutePath());
        } else if ("Capacidades Físicas".equals(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("G")).getStringCellValue())) {
            // v1.0.0
            this.ficha = cargarAlfViejo(sheet, this.seleccionado.getAbsolutePath());
        } else {
            // v1.0.5
            this.ficha = cargarAlfV105(sheet, this.seleccionado.getAbsolutePath());
        }
    }
    
    private static void guardarVersionAlf(Ficha ficha, String version) {
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
            if (Descargar.newVersion(version, "v1.0.8")) {
                //v1.0.9 o más
                try {
                    sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).setCellValue(ficha.getVidaActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar la vida actual");}
                try {
                    sheet.getRow(14).getCell(CellReference.convertColStringToIndex("BF")).setCellValue(ficha.getZeonActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
                try {
                    sheet.getRow(17).getCell(CellReference.convertColStringToIndex("AG")).setCellValue(ficha.getKiActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el ki actual");}
                try {
                    sheet.getRow(96).getCell(CellReference.convertColStringToIndex("B")).setCellValue(ficha.getNotas());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
            } else if ("v1.0.7".equals(version)) {
                //v1.0.7
                try {
                    sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).setCellValue(ficha.getVidaActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar la vida actual");}
                try {
                    sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BF")).setCellValue(ficha.getZeonActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
                try {
                    sheet.getRow(16).getCell(CellReference.convertColStringToIndex("AG")).setCellValue(ficha.getKiActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el ki actual");}
                try {
                    sheet.getRow(96).getCell(CellReference.convertColStringToIndex("B")).setCellValue(ficha.getNotas());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
            } else if ("v1.0.6".equals(version)) {
                //v1.0.6
                try {
                    sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).setCellValue(ficha.getVidaActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar la vida actual");}
                try {
                    sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BD")).setCellValue(ficha.getZeonActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
                try {
                    sheet.getRow(16).getCell(CellReference.convertColStringToIndex("AG")).setCellValue(ficha.getKiActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el ki actual");}
                try {
                    sheet.getRow(96).getCell(CellReference.convertColStringToIndex("B")).setCellValue(ficha.getNotas());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
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
            try {
                FileOutputStream outputStream = new FileOutputStream(ficha.getPath());
                workbook.write(outputStream);
                workbook.close();
            } catch(FileNotFoundException ex) {
            }
            
        } catch (NullPointerException | CargaException | IOException ex) {
            throw new GuardaException("Error al guardar en el documento excel.");
        }
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

            boolean solkar = false;
            try {
                solkar = "Raza:".equals(sheet.getRow(22).getCell(CellReference.convertColStringToIndex("F")).getStringCellValue());
            } catch(IllegalStateException ex) {}

            if (solkar) {
                // Ficha Solkar v7.5
                sheet = workbook.getSheetAt(1);
                try {
                    sheet.getRow(10).getCell(CellReference.convertColStringToIndex("P")).setCellValue(ficha.getVidaActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar la vida actual");}
                try {
                    sheet.getRow(57).getCell(CellReference.convertColStringToIndex("G")).setCellValue(ficha.getNotas());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
                sheet = workbook.getSheetAt(5);
                try {
                    sheet.getRow(16).getCell(CellReference.convertColStringToIndex("M")).setCellValue(ficha.getZeonActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el zeon actual");}
                sheet = workbook.getSheetAt(4);
                try {
                    sheet.getRow(23).getCell(CellReference.convertColStringToIndex("F")).setCellValue(ficha.getKiActual());
                } catch (NullPointerException ex) {throw new GuardaException("Error al guardar el ki actual");}
            } else {
                guardarVersionAlf(ficha, vExcel);
            }
            inputStream.close();
            try {
                FileOutputStream outputStream = new FileOutputStream(ficha.getPath());
                workbook.write(outputStream);
                workbook.close();
            } catch(FileNotFoundException ex) {
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
    
    private Ficha cargarAlfViejo(Sheet sheet, String path) throws CargaException {
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
            armadura = new Armadura[4];
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
            
            armadura[3] = new Armadura();
            armadura[3].setNombre("Nada");
            int []defensa = new int[7];
            for (int j = 0; j < 7; j++) {
                defensa[j] = 0;
            }

            armadura[3].setDefensa(defensa);
            armadura[3].setPosicion("Ninguna");
            
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
        
        ficha.setNotas("");
        return ficha;
    }
    
    private Ficha cargarAlfV105(Sheet sheet, String path) throws CargaException {
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
            ficha.setCansancioActual((int)(sheet.getRow(85).getCell(CellReference.convertColStringToIndex("O")).getNumericCellValue()));
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
            armadura = new Armadura[4];
            for (int i = 0; i < 4; i++) {
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
        
        ficha.setNotas("");
        return ficha;
    }
    
    private Ficha cargarAlfV106(Sheet sheet, String path) throws CargaException {
        ficha = new Ficha(path);
        
        ficha.setNombre(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setCategoria(sheet.getRow(1).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setNivel((int)(sheet.getRow(2).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
        ficha.setVida((int)(sheet.getRow(6).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue()));
        ficha.setZeon((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("BF")).getNumericCellValue()));
        ficha.setCansancio((int)(sheet.getRow(85).getCell(CellReference.convertColStringToIndex("O")).getNumericCellValue()));
        
        try {
            ficha.setVidaActual((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setVidaActual(ficha.getVida());
        }
        try {
            ficha.setZeonActual((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BD")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setZeonActual(this.ficha.getZeon());
        }
        try {
            ficha.setCansancioActual((int)(sheet.getRow(87).getCell(CellReference.convertColStringToIndex("O")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setCansancioActual(ficha.getCansancio());
        }
        
        {
            ficha.setKi((int)(sheet.getRow(15).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue()));
            ficha.setKiActual((int)(sheet.getRow(16).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue()));
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
            combate[4] = (int)(sheet.getRow(21).getCell(CellReference.convertColStringToIndex("BS")).getNumericCellValue());

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
            armadura = new Armadura[4];
            for (int i = 0; i < 4; i++) {
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

        ficha.setPotencialPsiquico((int)(sheet.getRow(5).getCell(CellReference.convertColStringToIndex("BJ")).getNumericCellValue()));
        
        ficha.setNotas(sheet.getRow(96).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue());
        return ficha;
    }
    
    private Ficha cargarAlfV107(Sheet sheet, String path) throws CargaException {
        ficha = new Ficha(path);
        
        ficha.setNombre(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setCategoria(sheet.getRow(1).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setNivel((int)(sheet.getRow(2).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
        ficha.setVida((int)(sheet.getRow(6).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue()));
        ficha.setZeon((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("BH")).getNumericCellValue()));
        {
            ficha.setCansancio((int)(sheet.getRow(85).getCell(14).getNumericCellValue()));
            
            try {
                ficha.setCansancioActual((int)(sheet.getRow(87).getCell(14).getNumericCellValue()));
            } catch (NullPointerException ex) {
                ficha.setCansancioActual(ficha.getCansancio());
            }
        }
        try {
            ficha.setVidaActual((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setVidaActual(ficha.getVida());
        }
        try {
            ficha.setZeonActual((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("BF")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setZeonActual(this.ficha.getZeon());
        }       
        {
            ficha.setKi((int)(sheet.getRow(15).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue()));
            ficha.setKiActual((int)(sheet.getRow(16).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue()));
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
            combate[3] = (int)(sheet.getRow(11).getCell(CellReference.convertColStringToIndex("AT")).getNumericCellValue());
            combate[4] = (int)(sheet.getRow(21).getCell(CellReference.convertColStringToIndex("BU")).getNumericCellValue());

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
            armadura = new Armadura[4];
            for (int i = 0; i < 4; i++) {
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

        ficha.setPotencialPsiquico((int)(sheet.getRow(5).getCell(CellReference.convertColStringToIndex("BL")).getNumericCellValue()));
        
        ficha.setNotas(sheet.getRow(96).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue());
        return ficha;
    }
    
    private Ficha cargarAlfV109(Sheet sheet, String path) throws CargaException {
        ficha = new Ficha(path);
        
        ficha.setNombre(sheet.getRow(0).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setCategoria(sheet.getRow(1).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
        ficha.setNivel((int)(sheet.getRow(2).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue()));
        ficha.setVida((int)(sheet.getRow(6).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue()));
        ficha.setZeon((int)(sheet.getRow(12).getCell(CellReference.convertColStringToIndex("BH")).getNumericCellValue()));
        {
            ficha.setCansancio((int)(sheet.getRow(89).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue()));
            
            try {
                ficha.setCansancioActual((int)(sheet.getRow(91).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue()));
            } catch (NullPointerException ex) {
                ficha.setCansancioActual(ficha.getCansancio());
            }
        }
        try {
            ficha.setVidaActual((int)(sheet.getRow(8).getCell(CellReference.convertColStringToIndex("U")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setVidaActual(ficha.getVida());
        }
        try {
            ficha.setZeonActual((int)(sheet.getRow(14).getCell(CellReference.convertColStringToIndex("BF")).getNumericCellValue()));
        } catch (NullPointerException ex) {
            ficha.setZeonActual(this.ficha.getZeon());
        }       
        {
            ficha.setKi((int)(sheet.getRow(16).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue()));
            ficha.setKiActual((int)(sheet.getRow(17).getCell(CellReference.convertColStringToIndex("AG")).getNumericCellValue()));
        }
        {
            int[] atributos = new int [8];
            for (int i = 0; i < 8; i++) {
                atributos[i] = (int)(sheet.getRow(9+i).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue());
            }
            ficha.setAtributos(atributos);
        }
        {
            int[] combate = new int [5];
            combate[0] = (int)(sheet.getRow(41).getCell(CellReference.convertColStringToIndex("C")).getNumericCellValue());
            combate[1] = (int)(sheet.getRow(41).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue());
            combate[2] = (int)(sheet.getRow(41).getCell(CellReference.convertColStringToIndex("G")).getNumericCellValue());
            combate[3] = (int)(sheet.getRow(11).getCell(CellReference.convertColStringToIndex("AT")).getNumericCellValue());
            combate[4] = (int)(sheet.getRow(25).getCell(CellReference.convertColStringToIndex("BU")).getNumericCellValue());

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
            armadura = new Armadura[4];
            for (int i = 0; i < 4; i++) {
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
                    convocatoria[i] = (int)(sheet.getRow(81+i).getCell(CellReference.convertColStringToIndex("W")).getNumericCellValue());
            }
            ficha.setConvocatoria(convocatoria);
        }

        ficha.setPotencialPsiquico((int)(sheet.getRow(5).getCell(CellReference.convertColStringToIndex("BL")).getNumericCellValue()));
        
        ficha.setNotas(sheet.getRow(96).getCell(CellReference.convertColStringToIndex("B")).getStringCellValue());
        return ficha;
    }
    
    private Ficha cargarSolkarV75(String path) throws CargaException {
        ficha = new Ficha(path);
        
        Sheet sheet = workbook.getSheetAt(1);
        
        ficha.setNombre(sheet.getRow(3).getCell(CellReference.convertColStringToIndex("L")).getStringCellValue());
        ficha.setCategoria(sheet.getRow(4).getCell(CellReference.convertColStringToIndex("L")).getStringCellValue());
        ficha.setNivel((int)(sheet.getRow(4).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue()));
        ficha.setVida((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue()));
        ficha.setCansancio((int)(sheet.getRow(15).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue()));
        
        try {
            ficha.setVidaActual((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue()));
            if (ficha.getVidaActual() < ficha.getVida())
                ficha.setVidaActual(ficha.getVida());
        } catch (NullPointerException ex) {
            ficha.setVidaActual(ficha.getVida());
        }
        
        try {
            ficha.setCansancioActual((int)(sheet.getRow(15).getCell(CellReference.convertColStringToIndex("P")).getNumericCellValue()));
            if (ficha.getCansancioActual() == 0)
                ficha.setCansancioActual(ficha.getCansancio());
        } catch (NullPointerException ex) {
            ficha.setCansancioActual(ficha.getCansancio());
        }
        
        {
            int[] atributos = new int [8];
            for (int i = 0; i < 8; i++) {
                atributos[i] = (int)(sheet.getRow(10+i).getCell(CellReference.convertColStringToIndex("G")).getNumericCellValue());
            }
            ficha.setAtributos(atributos);
        }
        {
            int[] res = new int [6];
            for (int i = 0; i < 6; i++) {
                res[i] = (int)(sheet.getRow(46+i).getCell(CellReference.convertColStringToIndex("J")).getNumericCellValue());
            }
            ficha.setRes(res);
        }
        {
            int[] secundarias = new int [38];
            for (int i = 0; i < 6; i++) { // Atléticas
                try {
                    secundarias[i] = (int)(sheet.getRow(21+i).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
                } catch(IllegalStateException ex) {
                    secundarias[i] = -30;
                }
            }
            for (int i = 0; i < 3; i++) { // Vigor
                try {
                    secundarias[i+6] = (int)(sheet.getRow(44+i).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
                } catch(IllegalStateException ex) {
                    secundarias[i+6] = -30;
                }
            }
            for (int i = 0; i < 13; i++) { // Perceptivas e Intelectuales
                try {
                    secundarias[i+9] = (int)(sheet.getRow(31+i).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
                } catch(IllegalStateException ex) {
                    secundarias[i+9] = -30;
                }
            }
            for (int i = 0; i < 4; i++) { // Sociales
                try {
                    secundarias[i+22] = (int)(sheet.getRow(27+i).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
                } catch(IllegalStateException ex) {
                    secundarias[i+22] = -30;
                }
            }
            for (int i = 0; i < 12; i++) { // Subterfugio y Creativas
                try {
                    secundarias[i+26] = (int)(sheet.getRow(47+i).getCell(CellReference.convertColStringToIndex("Q")).getNumericCellValue());
                } catch(IllegalStateException ex) {
                    secundarias[i+26] = -30;
                }
            }

            ficha.setSecundarias(secundarias);
        }
        ficha.setNotas(sheet.getRow(57).getCell(CellReference.convertColStringToIndex("G")).getStringCellValue());
        
        sheet = workbook.getSheetAt(2);
        
        {
            int[] combate = new int [5];
            combate[0] = (int)(sheet.getRow(21).getCell(CellReference.convertColStringToIndex("Y")).getNumericCellValue());
            combate[1] = (int)(sheet.getRow(22).getCell(CellReference.convertColStringToIndex("Y")).getNumericCellValue());
            combate[2] = (int)(sheet.getRow(23).getCell(CellReference.convertColStringToIndex("Y")).getNumericCellValue());
            combate[3] = (int)(sheet.getRow(92).getCell(CellReference.convertColStringToIndex("Y")).getNumericCellValue());
            combate[4] = (int)(sheet.getRow(108).getCell(CellReference.convertColStringToIndex("Y")).getNumericCellValue());

            ficha.setCombate(combate);
        }
        {
            int[] convocatoria;
            convocatoria = new int [4];
            for (int i = 0; i < 4; i++) {
                    convocatoria[i] = (int)(sheet.getRow(94+i).getCell(CellReference.convertColStringToIndex("Y")).getNumericCellValue());
            }
            ficha.setConvocatoria(convocatoria);
        }        
        sheet = workbook.getSheetAt(3);
        {
            int[] turno = new int [5];
            turno[0] = (int)(sheet.getRow(19).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());
            turno[1] = (int)(sheet.getRow(26).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());
            turno[2] = (int)(sheet.getRow(37).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());
            turno[3] = (int)(sheet.getRow(48).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());
            turno[4] = (int)(sheet.getRow(54).getCell(CellReference.convertColStringToIndex("N")).getNumericCellValue());

            ficha.setTurno(turno);
        }
        {
            Arma[] arma;
            arma = new Arma[4];
            {
                String [] criticos = new String [2];
                String nombre = sheet.getRow(25).getCell(CellReference.convertColStringToIndex("K")).getStringCellValue();
                arma[0] = new Arma();
                arma[0].setNombre(nombre);
                criticos[0] = sheet.getRow(28).getCell(CellReference.convertColStringToIndex("I")).getStringCellValue();
                criticos[1] = sheet.getRow(28).getCell(CellReference.convertColStringToIndex("J")).getStringCellValue();
                arma[0].setCritico(criticos);
                arma[0].setDamage((int)(sheet.getRow(26).getCell(CellReference.convertColStringToIndex("R")).getNumericCellValue()));
                arma[0].setEntereza((int)(sheet.getRow(28).getCell(CellReference.convertColStringToIndex("K")).getNumericCellValue()));
                arma[0].setRotura((int)(sheet.getRow(28).getCell(CellReference.convertColStringToIndex("L")).getNumericCellValue()));
                arma[0].setPresencia((int)(sheet.getRow(28).getCell(CellReference.convertColStringToIndex("M")).getNumericCellValue()));
            }
            {
                String [] criticos = new String [2];
                String nombre = sheet.getRow(36).getCell(CellReference.convertColStringToIndex("K")).getStringCellValue();
                arma[1] = new Arma();
                arma[1].setNombre(nombre);
                criticos[0] = sheet.getRow(39).getCell(CellReference.convertColStringToIndex("I")).getStringCellValue();
                criticos[1] = sheet.getRow(39).getCell(CellReference.convertColStringToIndex("J")).getStringCellValue();
                arma[1].setCritico(criticos);
                arma[1].setDamage((int)(sheet.getRow(37).getCell(CellReference.convertColStringToIndex("R")).getNumericCellValue()));
                arma[1].setEntereza((int)(sheet.getRow(39).getCell(CellReference.convertColStringToIndex("K")).getNumericCellValue()));
                arma[1].setRotura((int)(sheet.getRow(39).getCell(CellReference.convertColStringToIndex("L")).getNumericCellValue()));
                arma[1].setPresencia((int)(sheet.getRow(39).getCell(CellReference.convertColStringToIndex("M")).getNumericCellValue()));
            }
            {
                String [] criticos = new String [2];
                String nombre = sheet.getRow(47).getCell(CellReference.convertColStringToIndex("K")).getStringCellValue();
                arma[2] = new Arma();
                arma[2].setNombre(nombre);
                criticos[0] = sheet.getRow(50).getCell(CellReference.convertColStringToIndex("I")).getStringCellValue();
                criticos[1] = sheet.getRow(50).getCell(CellReference.convertColStringToIndex("J")).getStringCellValue();
                arma[2].setCritico(criticos);
                arma[2].setDamage((int)(sheet.getRow(48).getCell(CellReference.convertColStringToIndex("R")).getNumericCellValue()));
                arma[2].setEntereza((int)(sheet.getRow(50).getCell(CellReference.convertColStringToIndex("K")).getNumericCellValue()));
                arma[2].setRotura((int)(sheet.getRow(50).getCell(CellReference.convertColStringToIndex("L")).getNumericCellValue()));
                arma[2].setPresencia((int)(sheet.getRow(50).getCell(CellReference.convertColStringToIndex("M")).getNumericCellValue()));
            }
            {
                String [] criticos = new String [2];
                String nombre = sheet.getRow(53).getCell(CellReference.convertColStringToIndex("K")).getStringCellValue();
                arma[3] = new Arma();
                arma[3].setNombre(nombre);
                criticos[0] = sheet.getRow(56).getCell(CellReference.convertColStringToIndex("I")).getStringCellValue();
                criticos[1] = sheet.getRow(56).getCell(CellReference.convertColStringToIndex("J")).getStringCellValue();
                arma[3].setCritico(criticos);
                arma[3].setDamage((int)(sheet.getRow(54).getCell(CellReference.convertColStringToIndex("R")).getNumericCellValue()));
                arma[3].setEntereza((int)(sheet.getRow(56).getCell(CellReference.convertColStringToIndex("K")).getNumericCellValue()));
                arma[3].setRotura((int)(sheet.getRow(56).getCell(CellReference.convertColStringToIndex("L")).getNumericCellValue()));
                arma[3].setPresencia((int)(sheet.getRow(56).getCell(CellReference.convertColStringToIndex("M")).getNumericCellValue()));
            }
            ficha.setArma(arma);
        }
        {
            Armadura []armadura;
            armadura = new Armadura[4];
            for (int i = 0; i < 4; i++) {
                armadura[i] = new Armadura();
                armadura[i].setNombre(sheet.getRow(11+i).getCell(CellReference.convertColStringToIndex("C")).getStringCellValue());
                int []defensa = new int[7];
                for (int j = 0; j < 7; j++) {
                    defensa[j] = (int)(sheet.getRow(11+i).getCell(6+j).getNumericCellValue());
                }
                
                armadura[i].setDefensa(defensa);
                armadura[i].setPosicion(sheet.getRow(11+i).getCell(CellReference.convertColStringToIndex("F")).getStringCellValue());
            }
            ficha.setArmadura(armadura);
        }
        sheet = workbook.getSheetAt(4);
        {
            ficha.setKi((int)(sheet.getRow(23).getCell(CellReference.convertColStringToIndex("E")).getNumericCellValue()));
            try {
                ficha.setKiActual((int)(sheet.getRow(23).getCell(CellReference.convertColStringToIndex("F")).getNumericCellValue()));
                if (ficha.getKiActual() == 0)
                    ficha.setKiActual(ficha.getKi());
            } catch(NullPointerException ex) {
                ficha.setKiActual(this.ficha.getKi());
            }
        }
        sheet = workbook.getSheetAt(5);
        ficha.setZeon((int)(sheet.getRow(16).getCell(CellReference.convertColStringToIndex("K")).getNumericCellValue()));
        try {
            ficha.setZeonActual((int)(sheet.getRow(16).getCell(CellReference.convertColStringToIndex("M")).getNumericCellValue()));
            if (ficha.getZeonActual() == 0)
                ficha.setZeonActual(ficha.getZeon());
        } catch (NullPointerException ex) {
            ficha.setZeonActual(this.ficha.getZeon());
        }
        
        sheet = workbook.getSheetAt(7);
        try {
            ficha.setPotencialPsiquico((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("H")).getNumericCellValue()));
        } catch (java.lang.IllegalStateException ex){
            sheet = workbook.getSheetAt(9);
            ficha.setPotencialPsiquico((int)(sheet.getRow(10).getCell(CellReference.convertColStringToIndex("H")).getNumericCellValue()));
        }
        return ficha;
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
            armadura = new Armadura[4];
            
            for (int i = 0; i < 4; i++) {
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
