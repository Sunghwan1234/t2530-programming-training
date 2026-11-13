package lib;

import java.awt.*;
import java.awt.geom.*;

public class BlockDrawer {
    private static int width=20, height=20;

    public static Color[] colors = {Color.pink,Color.yellow,Color.red,Color.cyan,Color.green};
    public static void render(Graphics2D g, Block b, double sx, double sy) {
        int S = Character.getNumericValue(b.subtype);
        double x = b.bx-sx; double y = b.by-sy; int ix=(int)x, iy=(int)y;
        CP center = new CP(x+width/2,y+height/2);
        Stroke stroke = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        switch (b.type) {
            case 'b': // Block
                CP[] gradient = new CP[] {
                    new CP(x+width/2,y), // Top middle
                    new CP(x+width/2,y+height) // Bottom middle
                };
                gradient = CP.rotateArray(b.r, center, gradient);
                g.setPaint(new GradientPaint(
                    (float) gradient[0].x, (float) gradient[0].y, Color.white, // at Top middle
                    (float) gradient[1].x, (float) gradient[1].y, Color.black // at Bottom middle
                ));
                g.fill(new Rectangle2D.Double(x,y,width,height));
                g.setStroke(stroke);
                g.setPaint(Color.white);
                g.draw(new Rectangle2D.Double(x,y,width,height));
                break;
            case 's': // Spike
                float fx=(float)x, fy=(float)y;
                CP[] points = new CP[] {
                    new CP(x,y+height), // Left Bottom
                    new CP(x+width/2,y), // Middle Top
                    new CP(x+width,y+height) // Right Bottom
                };
                points = CP.rotateArray(b.r, center, points);
                int[][] intArray = CP.returnIntArray(points);
                Polygon poly = new Polygon(intArray[0],intArray[1],3);

                g.setPaint(new GradientPaint(fx+width/2,fy,Color.red,fx+width/2,fy+height,Color.white));
                g.fill(poly);
                g.setStroke(stroke);
                g.draw(poly);
                break;
            case 'o': // Orb
                g.setPaint(colors[S]);
                g.fillOval(ix, iy, width, height);
                g.setPaint(Color.white);
                g.setStroke(stroke);
                g.drawOval(ix, iy, width, height);
                g.drawOval(ix+3, iy+3, width-6, height-6);
                break;
            case 'p': // Pad
                CArea padArea = new CArea(
                    new CP(ix,iy+height-6),
                    new CP(ix+width,iy+height+3)
                );
                padArea.rotate(b.r, center);


                g.setPaint(colors[S]);
                g.fillArc((int)padArea.p1.x, (int)padArea.p1.y, (int)padArea.getW(), (int)padArea.getH(),(int) (180-b.r), -180);
                break;
            default: return;
        }
    }
}
