package lib;

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
