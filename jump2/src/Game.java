import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener { 
    // Initiate your variables here
    public static final int width = 800, height = 600;

    private final Timer timer;

    private final Player player;

    boolean keyDown = false;

    public Game() { // Initiate all your entities here
        this.player = new Player();

        this.timer = new Timer(1, this);
        this.timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    @Override
    public void paint(Graphics g) { // Render and execute all your entities here 

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        player.periodic(g);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32: // Space bar
                keyDown = true;
                break;
            default: break; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 32: // Space bar
                keyDown = false;
                break;
            default: break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

