package lve;

import javax.swing.JPanel;
import javax.swing.Timer;

import lve.Block;
import lve.Blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

public class Editor extends JPanel implements ActionListener, KeyListener{
    public static final int WinWidth=750,WinHeight=500;
    public static boolean KeyPressed[] = new boolean[100];
    public static double ScreenX = 0;

    public static final boolean LevelImport = true;

    private final Timer timer;

    private final Blocks blocks;
    private final Block block;

    public Editor() {
        this.blocks = new Blocks();
        this.block = new Block();

        if (LevelImport) {
            try {
                blocks.importLvdata("levelimport.txt");
                Block.blocksplaced=blocks.blockc;
            } catch (FileNotFoundException e) {e.printStackTrace();}
        }

        this.timer = new Timer(1, this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
new Timer(1, this);
        this.timer.start();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }
    @Override
    public void paint(Graphics g) {
        // BG and Ground
        g.setColor(Color.black); g.fillRect(0, 0, WinWidth, WinHeight);
        g.setColor(Color.white); g.drawRect(-1, WinHeight-50, WinWidth, 15);

        // Running code
        block.actions(blocks, g);
        //blocks.actions(g);

        // rendering
        // scores
        g.setColor(Color.white);
        String[] write = {
            "ScreenX: " + ScreenX,
            "Block X: " + block.posX,
            "Block Y: " + block.posY,
            "BlockType: "+block.blocktype,
            "Block Rotation: "+block.dir,
            "Block Count: "+blocks.blockc
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
        timer.start(); repaint();
    }
}
