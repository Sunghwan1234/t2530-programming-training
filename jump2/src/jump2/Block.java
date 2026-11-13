package jump2;

import java.awt.*;
import java.awt.geom.*;

import lib.*;

/** Block Class v1 */
public class Block extends lib.Block {
    public int width = Blocks.width;
    public int height = Blocks.height;

    private boolean killer=false;
    public boolean disabled=false;

    public Block(double x, double y, double r, String t) {
        super(x, y, r, t);
        System.out.println("Block created: "+x+";"+y+";"+r+";"+type+"."+subtype);
    }
    /** Returns the updated x position. */
    public double rx() {return bx-Blocks.scroll;}
    /** Returns the center of the block. */
    public CP center() {return new CP(rx()+width/2,by+height/2);}
    /** Gets the geom.Area of the Circular area of the Orb. */
    public Area getOrbArea() {
        int extra = 0;
        return new Area(new Ellipse2D.Double(rx()-extra,by-extra,width+(extra*2),height+(extra*2)));
    }
    /** Returns a CustomArea for Collision. */
    public CArea getColCA() {
        switch (type) {
            case 'p': // Pad
                //return new Rectangle2D.Double(rx(), by+height-(height/4),width,height/4);
                CArea area = new CArea(
                    rx()+2,       by+height-(height/5),   // Left Top
                    rx()+width-2, by+height               // Right Bottom
                );
                area.rotate(r, center());
                area.cornerPoints();
                return area;
            default:
                return new CArea(rx(), by, rx()+width, by+height);
        }
    }
    /** Returns a CustomArea for Death COllision. */
    public CArea getDeathCA() {
        switch (type) {
            case 'b':
                return new CArea(
                    rx(), by+1,
                    rx()+width, by+1+height-2);
            case 's': // Spike
                CArea area = new CArea(
                    rx()+5, by+8, // Left Top
                    rx()+width-5, by+height
                );
                area.rotate(r, center());
                return area;
            default: return new CArea();
        }
    }
    /** Simple detection of 2 geom.Area */
    public boolean areaCollide(Area area1, Area area2) {
        boolean collide = false;
        Area collide1 = new Area(area1);
        collide1.subtract(area2);
        if (!collide1.equals(area1)) {
            collide = true;
        }
        Area collide2 = new Area(area2);
        collide2.subtract(area1);
        if (!collide2.equals(area2)) {
            collide = true;
        }
        return collide;
    }
    /** Handles all collision with the player. */
    public void collide(Player p, Graphics2D g) {
        switch (type) {
            case 'b':
                if (CArea.col(getDeathCA(), p.getDeathCA()) && Math.abs(p.posY - by) < 10) { // 80 - 80+10 = 90
                    Game.inPlay = false;
                    killer=true;
                    System.out.println("Death by block with dist: "+(p.posY - by));
                }
                if (CArea.col(getColCA(), p.getColCA()) && Math.abs(p.posY - by) >= 10 ) {
                    p.onGround = true;
                    p.posY = by - p.height*p.gravity;
                } break;
                
            case 's': // Spike
                if (CArea.col(getDeathCA(), p.getDeathCA())) {
                    Game.inPlay = false;
                    killer=true;
                } break;
            case 'o': // Orb
                if (areaCollide(getOrbArea(), p.getColArea())) {
                    System.out.println("Orb: "+subtype);
                    p.orbContact = subtype;
                    disabled = true;
                } break;
            case 'p': // Pad
                if (CArea.col(getColCA(),p.getColCA())) {
                    p.posY-=p.gravity;
                    switch (subtype) {
                        case '0': // Pink
                            p.velY = -3.8*p.gravity; break;
                        case '1': // Yellow
                            p.velY = -5.5*p.gravity; break;
                        case '2':
                            p.velY = -6.7*p.gravity; break;
                        case '3':
                            p.gravity *= -1;
                            p.velY = p.gravity*4; break;
                        default:break;
                    }
                    disabled = true;
                } break;
            default: break;
        }
    }
    public void render(Graphics2D g) {
        BlockDrawer.render(g,this, Blocks.scroll, 0);
        g.setPaint(Color.cyan);
        if (disabled) {
            g.setPaint(Color.red);
        }
        g.draw(getColCA().getRect()); // Debug collision area
        g.setPaint(Color.magenta);
        g.draw(getDeathCA().getRect()); // Debug death area
        if (killer) {
            g.setPaint(Color.red);
            g.fill(getDeathCA().getRect());
        }
        
    }
}
