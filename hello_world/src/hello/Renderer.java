package hello;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Renderer extends JPanel implements ActionListener {
    private final Timer timer;
    private final LEDs led;

    public Renderer() {
        this.led = new LEDs();

        led.run();

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
        
        led.periodic();
        led.render(g);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) { // timer thingy
        timer.start();

        repaint();
    }
}
