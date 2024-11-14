package lve;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

import lve.Blocks;
import lve.Editor;

public class Block {
    private static final int Width = 20, Height = 20;
    public double posX=0, posY=430, dir=0, blocktype=10;

    public static int blocksplaced;

    public Block() {}
    
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        int X=(int)(posX);int Y=(int)posY;
        Double Type=blocktype;char SType=Type.toString().charAt(1);int PType=(int) Math.floor(Type/10);
        // System.out.println(PbT);
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(dir),X+10,Y+10);
        switch (SType) { // Color
            case '0':
                g2d.setColor(Color.yellow);break;
            case '1':
                g2d.setColor(Color.pink);break;
            case '2':
                g2d.setColor(Color.red);break;
            case '3':
                g2d.setColor(Color.cyan);break;
            default:
                g2d.setColor(Color.white);break;
            }
        switch (PType) {
            case 1:
                g2d.setColor(Color.white); g.fillRect(X,Y,Width,Height);
                break;
            case 2:
                g2d.setColor(Color.red); g.fillPolygon(
                new int[] {X, X+Width, X+Width/2}
                ,new int[] {Y+Height, Y+Height, Y},3);
                break;
            case 3: // Orb
                g2d.fillOval(X+1, Y+1, Width-1, Height-1);
                break;
            case 4: // Pad
                g2d.fillArc(X, Y+Height-(Height/4), Width, Height/2, 180, -180);
                break;
            default:break;
        }
        g2d.setTransform(old);
    }

    public void actions(lve.Blocks blocks, Graphics g) {
    }
}
