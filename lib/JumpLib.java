package lib;

import java.awt.geom.*;

public class JumpLib {
  public JumpLib() {

  }
  public class CP {
  double x, y;
  public CP(double x, double y) {
      this.x = x;
      this.y = y;
  }
  double dist(double dx, double dy) {return Math.sqrt((x-dx)*(x-dx)+(y-dy)*(y-dy));}
  double dist(CP p) {return Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y));}
  public CP rotate(double r, CP c) { // FIXED
      double angle = Math.toRadians(r); // Angle in radians
      double rx = c.x + (x-c.x)*Math.cos(-angle) - (y-c.y)*Math.sin(-angle); // Rotated x
      double ry = c.y + (x-c.x)*Math.sin(-angle) + (y-c.y)*Math.cos(-angle); // Rotated y
      return new CP(rx, ry);
  }
  public void rotateSelf(double r, CP c) { // FIXED
      double angle = Math.toRadians(r); // Angle in radians
      this.x = c.x + (this.x-c.x)*Math.cos(-angle) - (this.y-c.y)*Math.sin(-angle); // Rotated x
      this.y = c.y + (this.x-c.x)*Math.sin(-angle) + (this.y-c.y)*Math.cos(-angle); // Rotated y
  }
  /** Rotates an array of points by angle r from centerpoint c */
  public static CP[] rotateArray(double r, CP c, CP[] p) {
      CP[] rp = new CP[p.length];
      for (int i=0;i<p.length;i++) {rp[i] = p[i].rotate(r, c);}
      return rp;
  }
  /** [0] is X, [1] is Y. */
  static int[][] returnIntArray(CP[] points) {
    int[][] intpoints = new int[2][points.length];
    for (int i=0;i<points.length;i++) {
      intpoints[0][i] = (int) points[i].x;
      intpoints[1][i] = (int) points[i].y;
    }
    return intpoints;
  }
  CP translate(double dx, double dy) {return new CP(x+dx, y+dy);}
  CP scale(double s, CP c) {return new CP(c.x+(x-c.x)*s, c.y+(y-c.y)*s);}
  CP scale(double sx, double sy, CP c) {return new CP(c.x+(x-c.x)*sx, c.y+(y-c.y)*sy);}
  CP midpoint(CP p) {return new CP((x+p.x)/2, (y+p.y)/2);}
  @Override
  public String toString() {return "("+x+", "+y+")";}
}

  public class CArea {
    private CP p1=new CP(0,0), p2=new CP(0,0); // Original Points of the Area
    /** Left Top, Right Bottom */
    public CP lt = new CP(0,0), rb = new CP(0,0); // Left Top and Right Bottom points
    private double l, t, r, b, w,h;
  
    public CArea() {}
    public CArea(CP p1, CP p2) {
      this.p1 = p1;
      this.p2 = p2;
      this.setCorners();
    }
    public CArea(CP[] pointList) {
      this.p1 = pointList[0];
      this.p2 = pointList[1];
      this.setCorners();
    }
    public CArea(double x1, double y1, double x2, double y2) {
      this.p1 = new CP(x1, y1);
      this.p2 = new CP(x2, y2);
      this.setCorners();
    }
    /** Sets the corner Points using p1 p2 */
    public void setCorners() {
      this.lt.x = p1.x<p2.x ? p1.x : p2.x;
      this.lt.y = p1.y<p2.y ? p1.y : p2.y;
      this.rb.x = p1.x>p2.x ? p1.x : p2.x;
      this.rb.y = p1.y>p2.y ? p1.y : p2.y;
      this.setVars();
    }
    private void setVars() {
      l = lt.x; t = lt.y;
      r = rb.x; b = rb.y;
      w = r-l; h = b-t;
    }
  
    public void rotate(double r, CP c) {
      p1.rotateSelf(r,c);
      p2.rotateSelf(r,c);
      setCorners();
    }
    /** Sets p1p2 to ltrb. */
    public void cornerPoints() {
      setCorners();
      this.p1 = this.lt;
      this.p2 = this.rb;
    }
    public static boolean col(CArea a1, CArea a2) {
      a1.setCorners(); a2.setCorners();
      return (
        a1.l<a2.r &&
        a1.r>a2.l &&
        a1.t<a2.b &&
        a1.b>a2.t
      );
    }
    public Rectangle2D getRect() {
      setCorners();
      return new Rectangle2D.Double(l,t,w,h);
    }
  }
}