package animadicer.ventana;

import animadicer.Dado;
import animadicer.Log;
import animadicer.Settings;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Fernando
 */
public class ArmaGUI {
    Settings settings;
    Log log;
    int id;
    
    JTextField nombre;
    JTextField damage;
    JTextField critico1;
    JTextField critico2;
    
    JLabel enterezaB;
    JTextField enterezaBase;
    JTextField enterezaLibre;
    JTextField enterezaDado;
    JTextField enterezaResultado;
    
    JLabel roturaB;
    JTextField roturaBase;
    JTextField roturaLibre;
    JTextField roturaDado;
    JTextField roturaResultado;
    
    
    JLabel presenciaB;
    JTextField presenciaBase;
    JTextField presenciaLibre;
    JTextField presenciaDado;
    JTextField presenciaResultado;
    
    public ArmaGUI(Settings settings, Log log, int id) {
        this.settings = settings;
        this.log = log;
        this.id = id;
        
        this.nombre = new JTextField();
        this.damage = new JTextField();
        this.critico1 = new JTextField();
        this.critico2 = new JTextField();
        
        this.enterezaB = new JLabel();
        this.enterezaBase = new JTextField();
        this.enterezaLibre = new JTextField();
        this.enterezaDado = new JTextField();
        this.enterezaResultado = new JTextField();
        
        this.roturaB = new JLabel();
        this.roturaBase = new JTextField();
        this.roturaLibre = new JTextField();
        this.roturaDado = new JTextField();
        this.roturaResultado = new JTextField();
        
        this.presenciaB = new JLabel();
        this.presenciaBase = new JTextField();
        this.presenciaLibre = new JTextField();
        this.presenciaDado = new JTextField();
        this.presenciaResultado = new JTextField();
        
        initBotones();
        editable();
        alinear();
        letra();
    }
    
    public void dadosFisicos() {
        enterezaDado.setEditable(settings.getFisicos());
        roturaDado.setEditable(settings.getFisicos());
        presenciaDado.setEditable(settings.getFisicos());
    }
    
    public void listenerFisicos() {
        enterezaDado.addKeyListener(new java.awt.event.KeyAdapter() {
            int i;
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char vchar = evt.getKeyChar();
                if (!Character.isDigit(vchar) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE) {
                    evt.consume();
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                int d = 1;
                try {
                    d = Integer.parseInt (enterezaDado.getText());
                } catch (NumberFormatException ex){}
                if (d > 10){
                    enterezaDado.setText("10");
                }else if (d <= 0) {
                    enterezaDado.setText("1");
                }
                b_MouseClicked(null,0);
            }
        });
        roturaDado.addKeyListener(new java.awt.event.KeyAdapter() {
            int i;
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char vchar = evt.getKeyChar();
                if (!Character.isDigit(vchar) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE) {
                    evt.consume();
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                int d = 1;
                try {
                    d = Integer.parseInt (roturaDado.getText());
                } catch (NumberFormatException ex){}
                if (d > 10){
                    roturaDado.setText("10");
                }else if (d <= 0) {
                    roturaDado.setText("1");
                }
                b_MouseClicked(null, 1);
            }
        });
        presenciaDado.addKeyListener(new java.awt.event.KeyAdapter() {
            int i;
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char vchar = evt.getKeyChar();
                if (!Character.isDigit(vchar) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE) {
                    evt.consume();
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                int d = 1;
                try {
                    d = Integer.parseInt (presenciaDado.getText());
                } catch (NumberFormatException ex){}
                if (d > 10){
                    presenciaDado.setText("10");
                }else if (d <= 0) {
                    presenciaDado.setText("1");
                }
                
                b_MouseClicked(null, 2);
            }
        });
    }
    
    public void resetDados() {
        {
            int libre = 0;
            try {
                libre = Integer.parseInt(this.enterezaLibre.getText());
            } catch (NumberFormatException e) {

            }
            this.enterezaDado.setText("0");
            this.enterezaResultado.setText(String.valueOf(libre + Integer.parseInt(this.enterezaBase.getText())));
        }
        {
            int libre = 0;
            try {
                libre = Integer.parseInt(this.roturaLibre.getText());
            } catch (NumberFormatException e) {

            }
            this.roturaDado.setText("0");
            this.roturaResultado.setText(String.valueOf(libre + Integer.parseInt(this.roturaBase.getText())));
        }
        {
            int libre = 0;
            try {
                libre = Integer.parseInt(this.presenciaLibre.getText());
            } catch (NumberFormatException e) {

            }
            this.presenciaDado.setText("0");
            this.presenciaResultado.setText(String.valueOf(libre + Integer.parseInt(this.presenciaBase.getText())));
        }
    }
    
    private void initBotones() {
        this.enterezaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/boton.png")));
        this.roturaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/boton.png")));
        this.presenciaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/boton.png")));
        
        this.enterezaB.addMouseListener(new java.awt.event.MouseAdapter() {
            JLabel j;
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                log.addLog(log.queBoton("Arma", 0+id*3) + ": " + b_MouseClicked(evt, 0));
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_MouseEntered(evt,j);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_MouseExited(evt,j);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                b_MousePressed(evt,j);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                b_MouseEntered(evt,j);
            }

            public MouseListener init (JLabel i) {
                this.j = i;
                return this;
            }
        }.init(this.enterezaB));
        this.roturaB.addMouseListener(new java.awt.event.MouseAdapter() {
            JLabel j;
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                log.addLog(log.queBoton("Arma", 1+id*3) + ": " + b_MouseClicked(evt, 1));
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_MouseEntered(evt,j);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_MouseExited(evt,j);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                b_MousePressed(evt,j);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                b_MouseEntered(evt,j);
            }

            public MouseListener init (JLabel i) {
                this.j = i;
                return this;
            }
        }.init(this.roturaB));
        this.presenciaB.addMouseListener(new java.awt.event.MouseAdapter() {
            JLabel j;
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                log.addLog(log.queBoton("Arma", 2+id*3) + ": " + b_MouseClicked(evt, 2));
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_MouseEntered(evt,j);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_MouseExited(evt,j);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                b_MousePressed(evt,j);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                b_MouseEntered(evt,j);
            }

            public MouseListener init (JLabel i) {
                this.j = i;
                return this;
            }
        }.init(this.presenciaB));
    }
    
    private void editable() {
        this.nombre.setEditable(false);
        this.damage.setEditable(false);
        this.critico1.setEditable(false);
        this.critico2.setEditable(false);
        this.enterezaBase.setEditable(false);
        this.enterezaDado.setEditable(false);
        this.enterezaResultado.setEditable(false);
        this.roturaBase.setEditable(false);
        this.roturaDado.setEditable(false);
        this.roturaResultado.setEditable(false);
        this.presenciaBase.setEditable(false);
        this.presenciaDado.setEditable(false);
        this.presenciaResultado.setEditable(false);
    }
    
    private void alinear() {
        this.nombre.setHorizontalAlignment(JTextField.CENTER);
        this.damage.setHorizontalAlignment(JTextField.CENTER);
        this.critico1.setHorizontalAlignment(JTextField.CENTER);
        this.critico2.setHorizontalAlignment(JTextField.CENTER);
        
        this.enterezaBase.setHorizontalAlignment(JTextField.CENTER);
        this.enterezaLibre.setHorizontalAlignment(JTextField.CENTER);
        this.enterezaDado.setHorizontalAlignment(JTextField.CENTER);
        this.enterezaResultado.setHorizontalAlignment(JTextField.CENTER);
        
        this.roturaBase.setHorizontalAlignment(JTextField.CENTER);
        this.roturaLibre.setHorizontalAlignment(JTextField.CENTER);
        this.roturaDado.setHorizontalAlignment(JTextField.CENTER);
        this.roturaResultado.setHorizontalAlignment(JTextField.CENTER);
        
        this.presenciaBase.setHorizontalAlignment(JTextField.CENTER);
        this.presenciaLibre.setHorizontalAlignment(JTextField.CENTER);
        this.presenciaDado.setHorizontalAlignment(JTextField.CENTER);
        this.presenciaResultado.setHorizontalAlignment(JTextField.CENTER);
    }
    
    private void letra() {
        enterezaResultado.setFont(enterezaResultado.getFont().deriveFont(Font.BOLD));
        roturaResultado.setFont(roturaResultado.getFont().deriveFont(Font.BOLD));
        presenciaResultado.setFont(presenciaResultado.getFont().deriveFont(Font.BOLD));
    }
    
    private String b_MouseClicked(java.awt.event.MouseEvent evt, int i) {   
        // Hace el cálculo con el dado nuevo
        Dado d = new Dado(settings);
        if (settings.getFisicos()) {
            int truco = 1;
            try {
                switch (i) {
                    case 0:
                        truco = Integer.parseInt(enterezaDado.getText());
                        break;
                    case 1:
                        truco = Integer.parseInt(roturaDado.getText());
                        break;
                    default:
                        truco = Integer.parseInt(presenciaDado.getText());
                        break;
                }
            } catch (NumberFormatException ex) {}
            
            d.trucarDado(truco);
        } else {
            if (i < 2)
                d.lanzarD10();
            else
                d.lanzarDado(false);
        }
        
        if (evt != null && !javax.swing.SwingUtilities.isLeftMouseButton(evt)) {
            d.reset();
            d.trucarDado(0);
        }
        
        if (i < 2) {
            switch (d.getResultado()) {
                case 10:
                    if (i == 0)
                        this.enterezaResultado.setForeground(Color.GREEN);
                    else
                        this.roturaResultado.setForeground(Color.GREEN);
                    break;
                case 1:
                    if (i == 0)
                        this.enterezaResultado.setForeground(Color.RED);
                    else
                        this.roturaResultado.setForeground(Color.RED);
                    break;
                default:
                    if (i == 0)
                        this.enterezaResultado.setForeground(Color.BLACK);
                    else
                        this.roturaResultado.setForeground(Color.BLACK);
            }
        } else {
            if (evt != null && !javax.swing.SwingUtilities.isLeftMouseButton(evt)) {
                this.presenciaResultado.setForeground(Color.BLACK);
            } else if (d.getResultado() >= 90) {
                this.presenciaResultado.setForeground(Color.GREEN);
            } else if (d.getResultado() <= 3) {
                this.presenciaResultado.setForeground(Color.RED);
            } else {
                this.presenciaResultado.setForeground(Color.BLACK);
            }
        }
        int libre = 0;
        
        try {
            switch (i) {
                case 0:
                    libre = Integer.parseInt(this.enterezaLibre.getText());
                    break;
                case 1:
                    libre = Integer.parseInt(this.roturaLibre.getText());
                    break;
                default:
                    libre = Integer.parseInt(this.presenciaLibre.getText());
                    break;
            }
        } catch (NumberFormatException e) {
            
        }
        
        switch (i) {
            case 0:
                if (!settings.getFisicos())
                    this.enterezaDado.setText(String.valueOf(d.getResultado()));
                this.enterezaResultado.setText(String.valueOf(Integer.parseInt(this.enterezaBase.getText()) + libre + d.getResultado()));
                if (evt != null && !javax.swing.SwingUtilities.isLeftMouseButton(evt))
                    return null;
                return (this.enterezaBase.getText() + " + " + libre + " + " + String.valueOf(d.getResultado()) + " = " + String.valueOf(Integer.parseInt(this.enterezaBase.getText()) + libre + d.getResultado()));
            case 1:
                if (!settings.getFisicos())
                    this.roturaDado.setText(String.valueOf(d.getResultado()));
                this.roturaResultado.setText(String.valueOf(Integer.parseInt(this.roturaBase.getText()) + libre + d.getResultado()));
                if (evt != null && !javax.swing.SwingUtilities.isLeftMouseButton(evt))
                    return null;
                return (this.roturaBase.getText() + " + " + libre + " + " + String.valueOf(d.getResultado()) + " = " + String.valueOf(Integer.parseInt(this.roturaBase.getText()) + libre + d.getResultado()));
            default:
                if (!settings.getFisicos())
                    this.presenciaDado.setText(String.valueOf(d.getResultado()));
                this.presenciaResultado.setText(String.valueOf(Integer.parseInt(this.presenciaBase.getText()) + libre + d.getResultado()));
                if (evt != null && !javax.swing.SwingUtilities.isLeftMouseButton(evt))
                    return null;
                return (this.presenciaBase.getText() + " + " + libre + " + " + String.valueOf(d.getResultado()) + " = " + String.valueOf(Integer.parseInt(this.presenciaBase.getText()) + libre + d.getResultado()));
        }
    }
    
    private void b_MouseEntered(java.awt.event.MouseEvent evt, JLabel boton) {        
        ImageIcon II = new ImageIcon(getClass().getResource("/imagenes/botonIn.png"));
        boton.setIcon(II);
    }
    
    private void b_MouseExited(java.awt.event.MouseEvent evt, JLabel boton) {                                           
        ImageIcon II = new ImageIcon(getClass().getResource("/imagenes/boton.png"));
        boton.setIcon(II);
    }

    private void b_MousePressed(java.awt.event.MouseEvent evt, JLabel boton) {                                           
        ImageIcon II = new ImageIcon(getClass().getResource("/imagenes/botonPre.png"));
        boton.setIcon(II);
    }
}
