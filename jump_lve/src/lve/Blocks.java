package lve;

import java.awt.*;
import java.util.List;
import java.awt.geom.*;
import java.io.FileNotFoundException;
// do not use arraylist, it is garbage
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Blocks {
    public final int width = 20, height = 20;
    public float scroll = 0;

    public Block block[] = new Block[1000];
    public int blockCount = 0;
    public int lastBlockN;

    class Block {
        double x, y, r;
        char t, st;
        Paint paint;
        Stroke stroke;
        Shape outline, fill;
        public Block(double x, double y, double r, String t) {
            this.x=x;
            this.y=y;
            this.r=r;
            this.t=t.charAt(0);
            this.st=t.charAt(1);
        }
        public double SX() {return x-scroll;}
        public Area getCollisionArea() {
            Area area = new Area();
            switch (t) {
                case 'b':
                    area=new Area(new Rectangle2D.Double(SX(),y,width,height/3));
                default: break;
            }
            return area;
        }
        public Area getDeathArea() {
            Area area = new Area();
            switch (t) {
                case 'b':
                    area=new Area(new Rectangle2D.Double(SX(),y+1,width,height-1));
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
            double sx = SX();
            if (t=='b'|| t=='1') {
                paint = new GradientPaint((float)sx+width/2,(float)y,Color.white,(float)sx+width/2,(float)y+height,Color.black);
                fill = new Rectangle2D.Double(sx,y,width,height);
                stroke = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
                outline = new Rectangle2D.Double(sx-1,y,width,height);
            } else {
                return;
            }
            g.setPaint(paint);
            g.fill(fill);
            g.setPaint(Color.white);
            g.setStroke(stroke);
            g.draw(outline);
        }
    }

    public void importLV(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file); // I use Scanner.
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {lines.add(sc.nextLine());}
        String[] arr = lines.toArray(new String[0]); // The scanner output goes to String array arr[x][v]
        // System.out.println(Arrays.deepToString(arr));

        String[] blockdata;
        for (int t=0;t<arr.length;t++) {
            blockdata = arr[t].split(";",0);
            block[blockCount] = new Block(
                Double.parseDouble(blockdata[0])+400,
                Double.parseDouble(blockdata[1]),
                Double.parseDouble(blockdata[2]),
                blockdata[3]);
            //System.out.println(Arrays.deepToString(blockdata));

            if (block[blockCount].x > block[lastBlockN].x) {lastBlockN = blockCount;}
            blockCount += 1;
        }

        // System.out.println(Arrays.deepToString(block));
        //System.out.println("Leveldata import complete with "+blocks+" blocks");
    }

    public void render(Graphics2D g) {
        for (int i=0;i<blockCount;i++) {
            block[i].render(g);
        }
    }
}
