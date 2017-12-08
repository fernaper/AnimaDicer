/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animadicer.ventana;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Fernando
 */
public class RectDraw extends JPanel {
    private Color color;
    
    public RectDraw (Color color) {
        this.color = color;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0,110,8,10);
        g.setColor(color);
        g.fillRect(0,110,8,10);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(180, 20);
    }
}