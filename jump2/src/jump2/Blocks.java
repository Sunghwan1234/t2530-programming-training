package jump2;

import java.awt.*;
import java.util.List;

import jump2.Game.CPoint;

import java.awt.geom.*;
import java.io.FileNotFoundException;
// do not use arraylist, it is garbage
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Blocks {
    public final int width = 20, height = 20;

    public Block block[] = new Block[1000]; // Block container List of Class Block
    public int blockCount = 0;

    public int scroll = 0;
    /**
     * Block Class: x,y,r,type,s
     * 
     */
    class Block {
        double x, y, r;
        char type, s;
        Paint paint;
        Stroke stroke;
        Shape outline, fill;
        public Block(double x, double y, double r, String t) {
            this.x=x;
            this.y=y;
            this.r=r;
            this.type = t.charAt(0);
            this.s = t.charAt(1);
        }
        public CPoint rp() {return new CPoint(x-scroll,y);}
        public Area getCollisionArea() {
            Area area = new Area();
            switch (type) {
                case 'b':
                    area=new Area(new Rectangle2D.Double(rp().x,rp().y,width,height/3));
                default: break;
            }
            return area;
        }
        public Area getDeathArea() {
            Area area = new Area();
            switch (type) {
                case 'b':
                    area=new Area(new Rectangle2D.Double(rp().x,rp().y+1,width,height-1));
                default: break;
            }
            return area;
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
        public void render(Graphics2D g) {
            renderBlock(g,rp().x,rp().y,r,type,s);
        }
    }
    public double[] rotatePoint(double x, double y, double r, double cx, double cy) {
        double dx=(cx+width/2)-cx, dy=(cy+height/2)-cy; // Distance of x & y to center
        double angle=Math.toRadians(r); // Angle in radians
        double rx=cx+dx*Math.cos(angle)-dy*Math.sin(angle); // Rotated x
        double ry=cy+dx*Math.sin(angle)+dy*Math.cos(angle); // Rotated y
        return new double[] {rx,ry};
    }
    public int[][] returnIntArray(CPoint[] points) {
        int[][] intpoints = new int[2][points.length];
        for (int i=0;i<points.length;i++) {
            intpoints[0][i] = (int) points[i].x;
            intpoints[1][i] = (int) points[i].y;
        }
        return intpoints;
    }
    public void renderBlock(Graphics2D g, double x, double y, double r, char t, char s) {
        Color[] colors = {Color.pink,Color.yellow,Color.red,Color.cyan,Color.green};
        int S = Character.getNumericValue(s);

        float fx=(float)x, fy=(float)y;
        int ix=(int)x, iy=(int)y;

        CPoint center = new CPoint(x+width/2,y+height/2);

        Stroke stroke = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        switch (t) {
            case 'b': // Block
                CPoint[] gradient = new CPoint[] {
                    new CPoint(fx+width/2,fy), // Top middle
                    new CPoint(fx+width/2,fy+height) // Bottom middle
                };
                gradient = CPoint.rotateArray(r, center, gradient);

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
                CPoint[] points = new CPoint[] {
                    new CPoint(x,y+height), // Left Bottom
                    new CPoint(x+width/2,y), // Middle Top
                    new CPoint(x+width,y+height) // Right Bottom
                };
                points = CPoint.rotateArray(r, center, points);
                Polygon poly = new Polygon(returnIntArray(points)[0],returnIntArray(points)[1],3);

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
                break;
            default: return;
        }
        
    }
    /**
     * Imports level data from a .txt file.
     * Order:
     * arr = [x;y;rotation;type,...]
     * blockdata = [x,y,rotation,type]
     */
    public void importLV(File file) throws FileNotFoundException {
        blockCount=0;

        Scanner sc = new Scanner(file); // I use Scanner.
        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {lines.add(sc.nextLine());}
        String[] arr = lines.toArray(String[]::new); // The scanner output goes to String array arr[x][v]
        System.out.println(Arrays.deepToString(arr));

        String[] blockdata;
        for (String arr1 : arr) { // For every item in arr (that contains x;y;rotation;type)
            blockdata = arr1.split(";", 0); // The String of x;y;rotation;type gets split to array blockdata[0-3]

            int blockTypeIndex = Character.getNumericValue(blockdata[3].charAt(0)) - 1;
            String blocktype = Game.BLOCK_TYPES[blockTypeIndex] + blockdata[3].charAt(1);
            System.out.println(blocktype);
            block[blockCount] = new Block( // Creates the Block object!
                    Double.parseDouble(blockdata[0])+400,
                    Double.parseDouble(blockdata[1]),
                    Double.parseDouble(blockdata[2]),
                    blocktype);
            //System.out.println(Arrays.deepToString(blockdata));
            blockCount += 1;
        }
        // System.out.println(Arrays.deepToString(block));
        //System.out.println("Leveldata import complete with "+blocks+" blocks");
    }

    public void tick(Graphics2D g, Player player) {
        for (int i=0;i<blockCount;i++) {
            block[i].render(g);
            
        }
    }
}
