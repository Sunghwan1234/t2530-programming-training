package hello;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class Renderer extends JPanel implements ActionListener, KeyListener {
    private final Timer timer;
    private final LEDs leds;

    public Renderer() {
        this.leds = new LEDs();

        //leds.run();

        this.timer = new Timer(1, this);
        this.timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    @Override
    public void paint(Graphics g) { // draw and do crap, once per tick 

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 100);
        
        leds.periodic();
        leds.render(g);

        g.setColor(Color.white);
        g.drawString("Timer: " + leds.timer, 0, 10+20);

        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) { // - - - - - - - - - - CONTROLS - - - - - - - - - - \\
        // Left arrow: 37
        switch(e.getKeyCode()) {
            case 32: // Space bar
                leds.key = true;
                break;
            default: break; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32: // Space bar
                leds.key = false;
                break;
            default: break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) { // timer thingy
        timer.start();

        repaint();
    }
}
