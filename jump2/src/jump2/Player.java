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
    public char orbContact = ' '; // ' ' = none, '0' = yellow, '1' = pink, '2' = red, '3' = cyan

    public Player() {}

    public Area getColArea() {return new Area(new Rectangle2D.Double(posX-2,posY+height,width+2,1));} // Collision area (top of player)
    public Area getDeathArea() {return new Area(new Rectangle2D.Double(posX,posY,width+1,height));}

    public void tick(Graphics2D g) {
        if (posY > Game.groundHeight-1) { // Under Ground
            velY=0;
            posY=Game.groundHeight-1;
            jumpable = true;
            orbContact = ' ';
        } else {
            velY+=0.19;
            jumpable = false;
        }

        if (orbCooldown && !Game.jumpKey) {orbCooldown = false;}
        if (Game.jumpKey) {
            if (jumpable) { // Normal Jump
                posY -= gravity*2;
                velY = gravity*-3.8;
                orbCooldown = true;
            } else if (!(orbContact==0) && !orbCooldown) {
                posY -= gravity;
                switch (orbContact) {
                    case '0': // YELLOW
                        velY = gravity*-4.1; break;
                    case '1': // PINK
                        velY = gravity*-3.4; break;
                    case '2': // RED
                        velY = gravity*-6.7; break;
                    case '3': // CYAN
                        gravity*=-1;
                        velY = gravity*3; break;
                    default:break;
                }
                orbContact = ' ';
                orbCooldown = true;
            }
        }
        
        posY += velY; // Velocity applied to position
        paint(g);
    }

    public void paint(Graphics2D g) {
        g.setPaint(Color.green);
        g.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
        g.draw(new Rectangle2D.Double(posX,posY,width,height));
        //g.setPaint(Color.cyan);
        //g.fill(getDeathArea().getBounds2D()); // Debug death area
    }
}
