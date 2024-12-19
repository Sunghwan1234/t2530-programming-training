package lve;

import java.awt.*;
import java.awt.geom.*;
import java.util.Arrays;

public class Placer {
    private static final int width = 20, height = 20;

    double x=0, y=430, r=0;
    String type="b1";
    Paint paint;
    Stroke stroke;
    Shape outline, fill;

    public static int blocksplaced;

    public void render(Graphics2D g) {
        char t=type.charAt(0), st=type.charAt(1);
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
