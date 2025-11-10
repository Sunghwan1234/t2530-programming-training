package lib;

import java.awt.Polygon;
import java.awt.geom.*;

public class CArea {
  public CP p1=new CP(0,0), p2=new CP(0,0); // Original Points of the Area
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
  public Rectangle2D getPRect() {
    return new Rectangle2D.Double(p1.x,p1.y,p2.x-p1.x,p2.y-p1.y);
  }
  public Rectangle2D getRect() {
    setCorners();
    return new Rectangle2D.Double(l,t,w,h);
  }
  public Polygon getPoly() {
    setCorners();
    return new Polygon(new int[] {(int)l,(int)p2.x}, new int[] {(int)p1.y,(int)p2.y},2);
  }
}
