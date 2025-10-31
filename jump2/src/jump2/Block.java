package jump2;

import java.awt.*;
import java.util.*;
import java.awt.geom.*;

import jump2.Blocks;

public class Block {
  int width = Blocks.width;
  int height = Blocks.height;

  double bx, by, r;
  char type, s;
  Color[] colors = {Color.pink,Color.yellow,Color.red,Color.cyan,Color.green};
  boolean killer=false;
  boolean disabled=false;
  public Block(double x, double y, double r, String t) {
      this.bx=x;
      this.by=y;
      this.r=r;
      this.type = t.charAt(0);
      this.s = t.charAt(1);
      System.out.println("Block created: "+x+";"+y+";"+r+";"+type+"."+s);
  }
  public double rx() {return bx-Blocks.scroll;}
  public CP center() {return new CP(rx()+width/2,by+height/2);}
  public Area getOrbArea() {
      int extra = 0;
      return new Area(new Ellipse2D.Double(rx()-extra,by-extra,width+(extra*2),height+(extra*2)));
  }
  public CArea getColCA() {
      switch (type) {
          case 'b': return new CArea(rx(),by,rx()+width,by+height);
          case 'p': // Pad
              //return new Rectangle2D.Double(rx(), by+height-(height/4),width,height/4);

              CArea area = new CArea(
                rx(),       by+height-(height/4),
                rx()+width, by+height
              );
              area.rotate(r, center());

              CP[] points = new CP[] {
                  new CP(rx(),       by+height-(height/4)), // LEFT TOP
                  new CP(rx()+width, by+height-(height/4)), // RIGHT TOP
                  new CP(rx()+width, by+height), // RIGHT BOTTOM
                  new CP(rx(),       by+height) // LEFT BOTTOM
              };
              //points = CP.rotateArray(r, center(), points);
              //int[][] intArray = CP.returnIntArray(points);
              //Polygon poly = new Polygon(intArray[0], intArray[1], 4);
              //return poly.getBounds2D();
              return area;
          default: return new CArea(rx(),by,rx()+width,by+height);
      }
  }
  public CArea getDeathCA() {
      switch (type) {
          case 'b':
              return new CArea(rx(),by+1,rx()+width,by+1+height-2);
          case 's': // Spike
                CArea area = new CArea(
                    rx()+3, by+12,
                    rx()+width-3, by
                );
                area.rotate(r,center());
                return area;

              //CP center = center();
              //CP leftTop = new CP(rx()+3, by+12);
              //CP rightBottom = new CP(rx()+width-3, by);
              //leftTop.rotateSelf(r, center);
              //rightBottom.rotateSelf(r, center);
              //return new Rectangle2D.Double(leftTop.x,leftTop.y,rightBottom.x-leftTop.x,rightBottom.y-leftTop.y);
      default: return new CArea();
      }
  }
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
  public void collide(Player p, Graphics2D g) {
      switch (type) {
          case 'b': // Block
              if (CArea.col(getColCA(),p.getColCA()) && Math.abs(p.posY - by) < 10) { // 80 - 80+10 = 90
                  Game.inPlay = false;
                  killer=true;
                  System.out.println("Death by block with dist: "+(p.posY - by));
              }
              if (CArea.col(getColCA(),p.getColCA()) && Math.abs(p.posY - by) >= 10 ) {
                  p.onGround = true;
                  p.posY = by - p.height*p.gravity;
              } break;
              
          case 's': // Spike
              if (CArea.col(getDeathCA(),p.getDeathCA())) {
                  Game.inPlay = false;
                  killer=true;
              } break;
          case 'o': // Orb
              if (areaCollide(getOrbArea(), p.getColArea())) {
                  System.out.println("Orb: "+s);
                  p.orbContact = s;
                  disabled = true;
              } break;
          case 'p': // Pad
              if (CArea.col(getColCA(),p.getColCA())) {
                  switch (s) {
                      case '0': // Yellow
                          p.velY = -5*p.gravity; break;
                      case '1':
                          p.velY = -3.8*p.gravity; break;
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
      double x=rx(), y=by;
      int S = Character.getNumericValue(s);
      float fx=(float)x, fy=(float)y; int ix=(int)x, iy=(int)y;
      CP center = new CP(x+width/2,y+height/2);
      Stroke stroke = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
      switch (type) {
          case 'b': // Block
              CP[] gradient = new CP[] {
                  new CP(fx+width/2,fy), // Top middle
                  new CP(fx+width/2,fy+height) // Bottom middle
              };
              gradient = CP.rotateArray(r, center, gradient);
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
              CP[] points = new CP[] {
                  new CP(x,y+height), // Left Bottom
                  new CP(x+width/2,y), // Middle Top
                  new CP(x+width,y+height) // Right Bottom
              };
              points = CP.rotateArray(r, center, points);
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
              g.setPaint(colors[S]);
              g.fillArc(ix, iy+height-(height/4), width, height/2, 180, -180);

              g.setPaint(Color.cyan);
              g.fill(getColCA().getRect()); // Debug collision area
              break;
          default: return;
      }
      g.setPaint(Color.cyan);
      g.fill(getColCA().getRect()); // Debug collision area
      g.setPaint(Color.magenta);
      g.fill(getDeathCA().getRect()); // Debug death area
      if (killer) {
          g.setPaint(Color.red);
          g.fill(getDeathCA().getRect());
      }
  }
}
