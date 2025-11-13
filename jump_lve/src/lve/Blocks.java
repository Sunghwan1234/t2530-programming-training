package lve;

import java.awt.*;
import java.util.List;

import java.awt.geom.*;
import java.io.FileNotFoundException;
// do not use arraylist, it is garbage
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

import lve.lib.*;

public class Blocks {
    public static final int width = 20, height = 20;

    public Block block[] = new Block[1000]; // Block container List of Class Block
    public int blockCount = 0;

    public static void renderBlock(CP xy, double r, String t, Graphics2D g) {
        Color[] colors = {Color.pink,Color.yellow,Color.red,Color.cyan,Color.green};
        char type = t.charAt(0);
        double x=xy.x, y=xy.y;
        int S = Character.getNumericValue(t.charAt(1));
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
                CArea padArea = new CArea(
                    new CP(ix,iy+height-6),
                    new CP(ix+width,iy+height+3)
                );
                padArea.rotate(r, center);


                g.setPaint(colors[S]);
                g.fillArc((int)padArea.p1.x, (int)padArea.p1.y, (int)padArea.xd, (int)padArea.yd,(int) (180-r), -180);
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

        Scanner scanner = new Scanner(file); // I use Scanner.
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {lines.add(scanner.nextLine());}
        String[] arr = lines.toArray(String[]::new); // The scanner output goes to String array arr[x][v]
        //System.out.println(Arrays.deepToString(arr));

        String[] blockData;
        for (String arr1 : arr) { // For every item in arr (that contains x;y;rotation;type)
            if (!arr1.contains(";")) {
                continue;
            }
            blockData = arr1.split(";", 0); // The String of x;y;rotation;type gets split to array blockdata[0-3]

            String blocktype;
            if (new String(Editor.BLOCK_TYPES).contains(""+blockData[3].charAt(0))) {
                blocktype = blockData[3];
            } else {
                int blockTypeIndex = Character.getNumericValue(blockData[3].charAt(0)) - 1;
                blocktype = new String(new char[] {Editor.BLOCK_TYPES[blockTypeIndex], blockData[3].charAt(1)});
            }

            
            //System.out.println(blocktype);
            block[blockCount] = new Block( // Creates the Block object!
                    Double.parseDouble(blockData[0])+400,
                    Double.parseDouble(blockData[1]),
                    Double.parseDouble(blockData[2]),
                    blocktype);
            //System.out.println(Arrays.deepToString(blockdata));
            blockCount += 1;
        }
        // System.out.println(Arrays.deepToString(block));
        //System.out.println("Leveldata import complete with "+blocks+" blocks");
        scanner.close();
    }

    public void render(Graphics2D g) {
        for (int i=0;i<blockCount;i++) {
            block[i].render(g);
        }
    }
}
