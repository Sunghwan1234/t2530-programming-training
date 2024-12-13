import java.awt.*;
import java.awt.geom.*;

public class Player {
    public double width = 20, height = 20;
    public int x = 50; public double y = Game.groundHeight-height;
    public double velY = 0;
    public int gravity = 1;
    public int air = 0;
    public boolean jumpable = false;

    public Player() {}

    public Area getColArea() {
        return new Area(new Rectangle2D.Double(x,y+height-1,width,1));
    }
    public Area getDeathArea() {
        return new Area(new Rectangle2D.Double(x,y,width,height));
    }

    public void tick(Graphics2D g) {
        jump();
        physics();

        paint(g);
    }

    public void jump() {
        if (jumpable) {
            if (Game.keyDown) {
                velY -= gravity*3.8;
                jumpable=false;
            }
        }
    }

    public void physics() {
        if (y>Game.groundHeight-height-1) {
            velY=0;
            y=Game.groundHeight-height-1;
            jumpable = true;

        } else {
            velY+=0.19;
        }

        y+=velY;
    }

    public void paint(Graphics2D g) {
        g.setPaint(Color.green);
        g.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
        g.drawRect((int) x,(int) y,(int)width,(int)height);
    }
}
