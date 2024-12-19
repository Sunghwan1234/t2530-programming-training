package lve;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Editor extends JPanel implements ActionListener, KeyListener{
    public static final int windowWidth=1200, windowHeight=600;
    public static final int width = windowWidth, height = windowHeight-37;
    public static int groundHeight = height-20;

    public static boolean KeyPressed[] = new boolean[100];
    public static double ScreenX=0,ScreenY=0;

    public static final boolean LevelImport = true;

    private final Timer timer;

    private final Blocks blocks;
    private final Placer placer;

    public Editor() {
        this.blocks = new Blocks();
        this.placer = new Placer();

        if (LevelImport) {
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
        g.setColor(Color.black); g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.white); g.drawRect(-1, windowHeight-50, windowWidth, 15);

        // Running code
        blocks.render(g2);
        placer.render(g2);

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
