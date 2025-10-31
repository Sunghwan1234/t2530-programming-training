package jump2;

import java.awt.geom.*;

public class CArea {
  private CP p1, p2;
  /** Left Top, Right Bottom */
  public CP lt, rb;
  public double l, t, r, b;

  public CArea() {}
  public CArea(CP p1, CP p2) {
    this.p1 = p1; this.p2 = p2;
    this.setCorners();
  }
  public CArea(double x1, double y1, double x2, double y2) {
    this.p1 = new CP(x1, y1); this.p2 = new CP(x2, y2);
    this.setCorners();
  }
  /** */
  public void setCorners() {
    this.lt.x = p1.x<p2.x ? p1.x : p2.x;
    this.lt.y = p1.y<p2.y ? p1.y : p2.y;
    this.rb.x = p1.x>p2.x ? p1.x : p2.x;
    this.rb.y = p1.y>p2.y ? p1.y : p2.y;
    setVars();
  }
  public void setVars() {
    l = lt.x; t = lt.y;
    r = rb.x; b = rb.y;
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
    this.setCorners();
    return new Rectangle2D.double(l,t,l-r,t-b);
  }

}
