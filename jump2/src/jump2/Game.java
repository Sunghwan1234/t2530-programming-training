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
    public static final int WIN_WIDTH = 1200, windowHeight = 600;
    public static final int width = WIN_WIDTH, height = windowHeight-37;
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
        timer = new Timer(1, this);
        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }
    @Override
    public void paint(Graphics g) { // Render and execute all your entities here 
        Graphics2D g2 = (Graphics2D) g;
        if (inPlay) {
            blocks.scroll+=1;
        }
        // Draw background
        g.setColor(Color.black);
        g.fillRect(0, 0, WIN_WIDTH, windowHeight);
        g.setColor(Color.white);
        g.fillRect(0,groundHeight+20,width,20);

        
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
        blocks.scroll=0;
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
    /** CustomPoint for rotation */
    public static class CP {
        double x, y;
        public CP(double x, double y) {
            this.x = x;
            this.y = y;
        }
        double dist(double dx, double dy) {return Math.sqrt((x-dx)*(x-dx)+(y-dy)*(y-dy));}
        double dist(CP p) {return Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y));}
        CP rotate(double r, CP c) { // FIXED
            double angle = Math.toRadians(r); // Angle in radians
            double rx = c.x + (x-c.x)*Math.cos(-angle) - (y-c.y)*Math.sin(-angle); // Rotated x
            double ry = c.y + (x-c.x)*Math.sin(-angle) + (y-c.y)*Math.cos(-angle); // Rotated y
            return new CP(rx, ry);
        }
        void rotateSelf(double r, CP c) { // FIXED
            double angle = Math.toRadians(r); // Angle in radians
            this.x = c.x + (this.x-c.x)*Math.cos(-angle) - (this.y-c.y)*Math.sin(-angle); // Rotated x
            this.y = c.y + (this.x-c.x)*Math.sin(-angle) + (this.y-c.y)*Math.cos(-angle); // Rotated y
        }
        /** Rotates an array of points by angle r from centerpoint c */
        static CP[] rotateArray(double r, CP c, CP[] p) {
            CP[] rp = new CP[p.length];
            for (int i=0;i<p.length;i++) {rp[i] = p[i].rotate(r, c);}
            return rp;
        }
        /** [0] is X, [1] is Y. */
        static int[][] returnIntArray(CP[] points) {
        int[][] intpoints = new int[2][points.length];
        for (int i=0;i<points.length;i++) {
            intpoints[0][i] = (int) points[i].x;
            intpoints[1][i] = (int) points[i].y;
        }
        return intpoints;
    }
        CP translate(double dx, double dy) {return new CP(x+dx, y+dy);}
        CP scale(double s, CP c) {return new CP(c.x+(x-c.x)*s, c.y+(y-c.y)*s);}
        CP scale(double sx, double sy, Point c) {return new CP(c.x+(x-c.x)*sx, c.y+(y-c.y)*sy);}
        CP midpoint(CP p) {return new CP((x+p.x)/2, (y+p.y)/2);}
        @Override
        public String toString() {return "("+x+", "+y+")";}
    }

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

