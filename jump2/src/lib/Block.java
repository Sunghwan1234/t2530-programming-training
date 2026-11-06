package lib;

import java.awt.*;
import java.awt.geom.*;

public abstract class Block {
    final int width = 10;
    final int height = 10;

    public double bx, by, r; // Position & Rotation
    public char type, subtype; // Block Type & subtypes
    public static Color[] colors = {Color.pink,Color.yellow,Color.red,Color.cyan,Color.green};
    /** Creates a Block. t is a String, 2 chars. */
    public Block(double x, double y, double r, String t) {
        this.bx=x;
        this.by=y;
        this.r=r<0?360-r:r;
        this.type = t.charAt(0);
        this.subtype = t.charAt(1);
    }
}
