package jump2;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;

public class Game extends JPanel implements ActionListener, KeyListener { 
    // CONSTANTS
    public static final int WIN_WIDTH = 1200, WIN_HEIGHT = 600;
    public static final int width = WIN_WIDTH, height = WIN_HEIGHT-37;
    public static int groundHeight = 430;

    public static final String[] BLOCK_TYPES = {"b","s","o","p"};

    private final Timer timer;

    private final Player player;
    private final Blocks blocks;
    
    // Game settings
    public static final boolean levelImport = true; // Import level from text file
    public static final String levelPath = "Level.txt"; // Level path

    public static boolean inPlay = true;

    public static int test=0;

    public static boolean jumpKey = false;

    public Game() { // Initiate all your entities here
        player = new Player();
        blocks = new Blocks();
        if (levelImport) {try {
            blocks.importLV(new File(levelPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }}
        timer = new Timer(16, this);
        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }
    @Override
    public void paint(Graphics g) { // Render and execute all your entities here 
        Graphics2D g2 = (Graphics2D) g;
        if (inPlay) {
            Blocks.scroll+=2;
        }
        // Draw background
        g.setColor(Color.black);
        g.fillRect(0, 0, WIN_WIDTH, WIN_HEIGHT);
        g.setColor(Color.white);
        g.fillRect(0,groundHeight+20,width,20);

        player.pad=false;
        blocks.tick(g2, player);
        player.tick(g2);

        

        stringWriter(g, new String[] {
            "test: " + Game.test,
            "vel: " + player.velY,
            "y: " + player.posY,
            "x: " + player.posX,
            "orb: " + player.orbContact,
            "jumpable: " + player.jumpable,
            "KCD: " + player.keyCooldown,
            "gravity: " + player.gravity
        });

        g.dispose();
    }

    public void stringWriter(Graphics g, String[] s) {
        g.setColor(Color.white);
        for (int i=0;i<s.length;i++) {g.drawString(s[i],12,16+i*10);}
    }

    public void reset() {
        inPlay = true;
        Blocks.scroll=0;
        player.gravity=1;
        player.posY = Game.groundHeight-player.height;
        player.velY=0;
        player.orbContact=' ';
        player.jumpable=false;
        player.keyCooldown=false;
        for (int i = 0;i<blocks.blockCount;i++) {
            blocks.block[i].disabled = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {repaint();}

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32: // Space bar
                jumpKey = true;
                break;
            case 37: // LEFT
                //blocks.b.x-=1;
                break;
            case 38: // UP
                reset();
                break;
            case 39: // RIGHT
                //blocks.b.x+=1;
                break;
            case 40: // DOWN
                Game.inPlay = true;
                break;
            default: break; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 32: // Space bar
                jumpKey = false;
                break;
            default: break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

