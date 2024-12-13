package lve;

import java.awt.*;
import java.awt.geom.*;
import java.util.Arrays;

public class Placer {
    private static final int width = 20, height = 20;
    public double posX=0, posY=430, dir=0, blocktype=10;

    double x, y, r;
    char t, st;
    Paint paint;
    Stroke stroke;
    Shape outline, fill;

    public static int blocksplaced;

    public void render(Graphics2D g) {
        switch (t) {
            case 'b':
                paint = new GradientPaint((float)x+width/2,(float)y,Color.white,(float)x+width/2,(float)y+height,Color.black);
                fill = new Rectangle2D.Double(x,y,width,height);
                stroke = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
                outline = new Rectangle2D.Double(x-1,y,width,height);
                break;
            default: break;
        }
        g.setPaint(paint);
        g.fill(fill);
        g.setPaint(Color.white);
        g.setStroke(stroke);
        g.draw(outline);
    }
}
