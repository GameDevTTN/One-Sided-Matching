/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ylo019
 */
public class GUICounter extends JFrame {
    
    int count = 0;
    long startTime = 0;
    long diff = 0;
    JPanel counter = new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
            g.drawString("" + count, 20, 20);
            g.drawString(diff + " s", 20, 50);
        }
    };
    
    public GUICounter() {
        startTime = System.currentTimeMillis();
        this.add(counter);
        this.setSize(100, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void inc() {
        ++count;
        diff = System.currentTimeMillis() - startTime;
        diff /= 1000;
        repaint();
    }
    
}
