import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener { 
    // Initiate your variables here
    public static final int windowWidth = 1000, windowHeight = 600;
    public static final int width = windowWidth, height = windowHeight-38;

    public static int test=0;

    private final Timer timer;

    private final Player player;

    public static boolean keyDown = false;

    public Game() { // Initiate all your entities here
        player = new Player();

        timer = new Timer(1, this);
        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }
    
    @Override
    public void paint(Graphics g) { // Render and execute all your entities here 

        // Draw background
        g.setColor(Color.black);
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.white);
        g.fillRect(0,height-20,width,20);

        player.periodic(g);

        stringWriter(g, new String[] {
            "test: " + Game.test,
            "vel: " + player.velY,
            "y: " + player.y
        });

        g.dispose();
    }

    public void stringWriter(Graphics g, String[] s) {
        g.setColor(Color.white);
        for (int i=0;i<s.length;i++) {g.drawString(s[i],12,16+i*10);}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32: // Space bar
                keyDown = true;
                test+=1;
                break;
            default: break; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32: // Space bar
                keyDown = false;
                break;
            default: break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

