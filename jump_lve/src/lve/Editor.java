package lve;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Editor extends JPanel implements ActionListener, KeyListener{
    public static final int WIN_WIDTH=1200, WIN_HEIGHT=600;
    public static final int WIDTH = WIN_WIDTH, HEIGHT = WIN_HEIGHT-37;
    public static int groundHeight = HEIGHT-20;

    public static boolean KeyPressed[] = new boolean[100];
    public static double ScreenX=0, ScreenY=0;

    public static final String[] BLOCK_TYPES = {"b","s","p","o"};

    public static final boolean IMPORT_LEVEL = true;

    private final Timer timer;
    private final Blocks blocks;
    private final Placer placer;

    public static class customPoint {
        double x, y;
        public customPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
        double dist(double dx, double dy) {return Math.sqrt((x-dx)*(x-dx)+(y-dy)*(y-dy));}
        double dist(customPoint p) {return Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y));}
        customPoint rotate(double r, customPoint c) {
            double dx=x-c.x, dy=y-c.y; // Distance of x & y to center
            double angle=Math.toRadians(r); // Angle in radians
            double rx=c.x+dx*Math.cos(angle)-dy*Math.sin(angle); // Rotated x
            double ry=c.y+dx*Math.sin(angle)+dy*Math.cos(angle); // Rotated y
            return new customPoint(rx, ry);
        }
        void rotateSelf(double r, customPoint c) {
            double dx=c.x-c.x, dy=c.y-c.y; // Distance of x & y to center
            double angle=Math.toRadians(r); // Angle in radians
            this.x=c.x+dx*Math.cos(angle)-dy*Math.sin(angle); // Rotated x
            this.y=c.y+dx*Math.sin(angle)+dy*Math.cos(angle); // Rotated y
        }
        static customPoint[] rotateArray(double r, customPoint c, customPoint[] p) {
            customPoint[] rp = new customPoint[p.length];
            for (int i=0;i<p.length;i++) {rp[i] = p[i].rotate(r, c);}
            return rp;
        }

        customPoint translate(double dx, double dy) {return new customPoint(x+dx, y+dy);}
        customPoint scale(double s, customPoint c) {return new customPoint(c.x+(x-c.x)*s, c.y+(y-c.y)*s);}
        customPoint scale(double sx, double sy, Point c) {return new customPoint(c.x+(x-c.x)*sx, c.y+(y-c.y)*sy);}
        customPoint midpoint(customPoint p) {return new customPoint((x+p.x)/2, (y+p.y)/2);}
        @Override
        public String toString() {return "("+x+", "+y+")";}
    }

    public Editor() {
        this.blocks = new Blocks();
        this.placer = new Placer();

        if (IMPORT_LEVEL) {
            try {
                blocks.importLV(new File("levelimport.txt"));
                Placer.blocksplaced=blocks.blockCount;
            } catch (FileNotFoundException e) {e.printStackTrace();}
        }

        this.timer = new Timer(1, this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);

        this.timer.start();
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2=(Graphics2D) g;
        // BG and Ground
        g.setColor(Color.black); g.fillRect(0, 0, WIN_WIDTH, WIN_HEIGHT);
        g.setColor(Color.white); g.drawRect(-1, WIN_HEIGHT-50, WIN_WIDTH, 15);

        // Running code
        placer.actions(blocks, g2);
        blocks.render(g2);
        

        // rendering
        // scores
        g.setColor(Color.white);
        String[] write = {
            "ScreenX: " + ScreenX,
            "Block X: " + placer.x,
            "Block Y: " + placer.y,
            "BlockType: "+placer.type,
            "Block Rotation: "+placer.r,
            "Block Count: "+blocks.blockCount
        };
        for (int i=0;i<write.length;i++) {g.drawString(write[i],12,16+i*14);}

        g.dispose();
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
        public void keyPressed(KeyEvent e) { // - - - - - - - - - - CONTROLS - - - - - - - - - - \\
           KeyPressed[e.getKeyCode()] = true;
           System.out.println("KeyPressed: "+e.getKeyCode());
        }
    
        @Override
        public void keyReleased(KeyEvent e) {
            KeyPressed[e.getKeyCode()] = false;
        }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
