package jump2;

import java.awt.*;
import java.awt.geom.*;

public class Player {
    public double width = 20, height = 20;
    public int posX = 50; public double posY = Game.groundHeight-height;
    public double velY = 0;
    public int gravity = 1;
    //public int air = 0;
    public boolean onGround = false;
    public boolean jumpable = false;
    public boolean keyCooldown = false;
    public char orbContact = ' '; // ' ' = none, '0' = yellow, '1' = pink, '2' = red, '3' = cyan

    public Player() {}

    public CArea getColCA() {return new CArea(posX,posY,posX+width,posY+height);}
    public CArea getDeathCA() {return new CArea(posX+2,posY+1,posX+2+width-4,posY+1+height-2);}
    public Area getColArea() {return new Area(getColRect());} // Collision area (bottom)
    public Rectangle2D getColRect() {return new Rectangle2D.Double(posX,posY,width,height);} // Collision area (bottom)
    //public Area getDeathArea() {return new Area(new Rectangle2D.Double(posX+2,posY+1,width-4,height-2));}
    //public Rectangle2D getDeathRect() {return new Rectangle2D.Double(posX+2,posY+1,width-4,height-2);}

    public void tick(Graphics2D g) {
        if (!Game.inPlay) {paint(g); return;}
        if (posY > Game.groundHeight-1) { // Under Ground
            posY = Game.groundHeight;
            onGround=true;
        } else {
            velY+=0.19*gravity;
            jumpable = false;
        }

        if (onGround) { // ON GROUND?
            velY=0;
            keyCooldown = false;
            jumpable = true;
            orbContact = ' ';
            onGround=false;
        }

        if (keyCooldown && !Game.jumpKey) {keyCooldown = false;}

        if (Game.jumpKey) {
            if (jumpable) { // Normal Jump
                posY -= gravity*2;
                velY = gravity*-3.8;
                keyCooldown = true;
                jumpable = false;
            } else if (!(orbContact==' ') && !keyCooldown) { // Orb Jump
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
                keyCooldown = true;
            }
        }
        
        posY += velY; // Velocity applied to position
        paint(g);
    }

    public void paint(Graphics2D g) {
        Color color, color2;
        if (gravity==1) {color = Color.green;} else {color = Color.cyan;}
        if (orbContact==' ') {color2 = Color.white;} else {color2 = Color.magenta;}
        g.setPaint(color);
        g.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
        g.fill(new Rectangle2D.Double(posX,posY,width,height));
        g.setPaint(color2);
        g.draw(new Rectangle2D.Double(posX,posY,width,height));
        //g.setPaint(Color.cyan);
        //g.fill(getColRect()); // Debug death area
        //g.setPaint(Color.magenta);
        //g.fill(getDeathRect()); // Debug death area
    }
}
