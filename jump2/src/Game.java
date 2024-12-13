import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener { 
    // Initiate your variables here
    public static final int windowWidth = 1200, windowHeight = 600;
    public static final int width = windowWidth, height = windowHeight-37;
    public static int groundHeight = height-20;

    public static int test=0;

    private final Timer timer;

    private final Player player;
    private final Blocks blocks;

    public static boolean keyDown = false;

    public Game() { // Initiate all your entities here
        player = new Player();
        blocks = new Blocks();

        timer = new Timer(1, this);
        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }
    
    @Override
    public void paint(Graphics g) { // Render and execute all your entities here 
        Graphics2D g2 = (Graphics2D) g;
        // Draw background
        g.setColor(Color.black);
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.white);
        g.fillRect(0,groundHeight,width,20);

        
        blocks.tick(g2, player);
        player.tick(g2);

        stringWriter(g, new String[] {
            "test: " + Game.test,
            "vel: " + player.velY,
            "y: " + player.y,
            "x: " + player.x
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
            case 37: // LEFT
                blocks.b.x-=1;
                break;
            case 38: // UP
            blocks.b.y-=1;
                break;
            case 39: // RIGHT
            blocks.b.x+=1;
                break;
            case 40: // DOWN
            blocks.b.y+=1;
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
    public void keyTyped(KeyEvent e) {}
}

