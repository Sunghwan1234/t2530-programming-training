package jump2;

import java.awt.*;
import java.awt.geom.*;

public class Player {
    public double width = 20, height = 20;
    public int posX = 50; public double posY = Game.groundHeight-height;
    public double velY = 0;
    public int gravity = 1;
    //public int air = 0;
    public boolean jumpable = false;
    public boolean orbCooldown = false;
    public int orbContact = 0;

    public Player() {}

    public Area getColArea() {return new Area(new Rectangle2D.Double(posX,posY+height-1,width,1));}
    public Area getDeathArea() {return new Area(new Rectangle2D.Double(posX,posY,width,height));}

    public void tick(Graphics2D g) {
        if (posY > Game.groundHeight-1) { // Under Ground
            velY=0;
            posY=Game.groundHeight-1;
            jumpable = true;
        } else {
            velY+=0.19;
            jumpable = false;
        }

        if (orbCooldown && orbContact==0 && !Game.jumpKey) {orbCooldown = false;}
        if (Game.jumpKey) {
            if (!(orbContact==0) && !orbCooldown) {
                posY -= gravity;
                switch (orbContact) {
                    case 30: // YELLOW
                        velY = gravity*-4.1; break;
                    case 31: // PINK
                        velY = gravity*-3.4; break;
                    case 32: // RED
                        velY = gravity*-6.7; break;
                    case 33: // CYAN
                        gravity*=-1;
                        velY = gravity*3; break;
                    default:break;
                }
                orbCooldown = true;
            } else if (jumpable) {
                posY -= gravity*2;
                velY = gravity*-3.8;
                orbCooldown = true;
            }
        }
        
        posY += velY; // Velocity applied to position
        paint(g);
    }

    public void paint(Graphics2D g) {
        g.setPaint(Color.green);
        g.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
        g.drawRect((int) posX,(int) posY,(int)width,(int)height);
    }
}
