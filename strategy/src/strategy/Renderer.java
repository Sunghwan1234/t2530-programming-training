package strategy;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// import java.awt.Point;
// import java.awt.MouseInfo;
// import java.awt.PointerInfo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

public class Renderer extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
    public static final int Window_X = 1080;
    public static final int Window_Y = 580;

    public double[] MousePosition = new double[2];
    public double[] MouseDragPositions = new double[4];
    public static boolean MousePressed = false;

    public BufferedImage iimage;
    public BufferedImage simage;

    AffineTransform AF = new AffineTransform();

    //private final Mouse mouse;
    private final Timer timer;
    private final Robots bots;

    public Renderer() throws IOException {
        this.bots = new Robots(
            new int[][] {
                {50, 50},
                {50, 50},
                {50, 50}
            },
            new int[][] {
                {90, 100, 0}, 
                {90, 200, 0}, 
                {90, 300, 0}
            });

        this.iimage= ImageIO.read(new File("image.png"));
        int w = iimage.getWidth();
        int h = iimage.getHeight();
        this.simage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AF.scale(0.7, 0.7);
        AffineTransformOp scaleOp = new AffineTransformOp(AF, AffineTransformOp.TYPE_BILINEAR);
        this.simage = scaleOp.filter(iimage, simage);


        //this.mouse = new Mouse();
        addMouseListener(this);
        addMouseMotionListener(this);

        this.timer = new Timer(1, this);
        this.timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    @Override
    public void paint(Graphics g) { // draw and do crap, once per tick 

        // Draw background
        g.setColor(Color.white);
        g.fillRect(0, 0, Window_X, Window_Y);
        // Draw board
        g.setColor(Color.white);
        g.drawImage(simage, 0, 0, null);

        bots.render(g, MouseDragPositions, MousePosition);

        g.setColor(Color.white);
        g.drawString(MousePosition[0] + ", " + MousePosition[1], 10, 20);
        g.drawString(MouseDragPositions[0] + ", " + MouseDragPositions[1], 10, 40);
        g.drawString(MouseDragPositions[2] + ", " + MouseDragPositions[3], 10, 60);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) { // timer thingy
        timer.start();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        MousePosition[0] = me.getX();
        MousePosition[1] = me.getY();
    }
    @Override
    public void mouseMoved(MouseEvent me) {
        MousePosition[0] = me.getX();
        MousePosition[1] = me.getY();
    }
    @Override
    public void mousePressed(MouseEvent me) {
        MouseDragPositions[0] = me.getX();
        MouseDragPositions[1] = me.getY();
        MousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        MouseDragPositions[2] = me.getX();
        MouseDragPositions[3] = me.getY();
        MousePressed = false;
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
