package lve;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Editor extends JPanel implements ActionListener, KeyListener{
    public static final int WIN_WIDTH=1200, WIN_HEIGHT=600;
    public static final int WIDTH = WIN_WIDTH, HEIGHT = WIN_HEIGHT-37;
    public static final int groundHeight = 430+20; // 20 is block height

    public static boolean KeyPressed[] = new boolean[100];
    public static double ScreenX=0, ScreenY=0;

    public static final char[] BLOCK_TYPES = {'b','s','o','p'};

    public static final boolean IMPORT_LEVEL = true;

    private final Timer timer;
    private final Blocks blocks;
    private final Placer placer;
    
    public Editor() {
        this.blocks = new Blocks();
        this.placer = new Placer();

        if (IMPORT_LEVEL) {
            try {
                blocks.importLV(new File("levelimport.txt"));
                Placer.blocksplaced=blocks.blockCount;
            } catch (FileNotFoundException e) {e.printStackTrace();}
        }

        this.timer = new Timer(16, this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);

        this.timer.start();
    }
    @Override
    public void paint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        // BG and Ground
        g.setColor(Color.black); g.fillRect(0, 0, WIN_WIDTH, WIN_HEIGHT);
        g.setColor(Color.white); g.drawRect(-1, (int) (groundHeight-ScreenY), WIN_WIDTH, 15);

        // Running code
        placer.actions(blocks, g2);
        blocks.render(g2);
        placer.render(blocks, g2);

        // rendering
        // scores
        g.setColor(Color.white);
        String[] write = {
            "ScreenX: " + ScreenX,
            "Block X: " + placer.x,
            "Block Y: " + placer.y,
            "BlockType: "+placer.getType().charAt(0)+placer.getType().charAt(1),
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



        //System.out.println("KeyPressed: "+e.getKeyCode());
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
