package jump2;

import java.awt.*;
import java.util.List;

import java.awt.geom.*;
import java.io.FileNotFoundException;
// do not use arraylist, it is garbage
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Blocks {
    public static final int width = 20, height = 20;

    public Block block[] = new Block[1000]; // Block container List of Class Block
    public double lastBlockX; // Win condition
    public int blockCount = 0;

    public static int scroll = 0;
    /**
     * Block Class: x, y, r, type
     */
    
    public double[] rotatePoint(double x, double y, double r, double cx, double cy) {
        double dx=(cx+width/2)-cx, dy=(cy+height/2)-cy; // Distance of x & y to center
        double angle=Math.toRadians(r); // Angle in radians
        double rx=cx+dx*Math.cos(angle)-dy*Math.sin(angle); // Rotated x
        double ry=cy+dx*Math.sin(angle)+dy*Math.cos(angle); // Rotated y
        return new double[] {rx,ry};
    }
    /**Imports level data from a .txt file.
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
        //System.out.println(Arrays.deepToString(arr));
        String[] blockdata;
        for (String arr1 : arr) { // For every item in arr (that contains x;y;rotation;type)
            blockdata = arr1.split(";", 0); // The String of x;y;rotation;type gets split to array blockdata[0-3]
            int blockTypeIndex = Character.getNumericValue(blockdata[3].charAt(0)) - 1;
            String blocktype = Game.BLOCK_TYPES[blockTypeIndex] + blockdata[3].charAt(1);
            //System.out.println(blocktype);
            block[blockCount] = new Block( // Creates the Block object!
                    Double.parseDouble(blockdata[0])+400,
                    Double.parseDouble(blockdata[1]),
                    Double.parseDouble(blockdata[2]),
                    blocktype);
            if (block[blockCount].bx > lastBlockX) {lastBlockX = block[blockCount].bx;}
            //System.out.println(Arrays.deepToString(blockdata));
            blockCount += 1;
        }
        // System.out.println(Arrays.deepToString(block));
        System.out.println("Leveldata import complete with "+blockCount+" blocks");
    }

    public void tick(Graphics2D g, Player player) {
        for (int i=0;i<blockCount;i++) {
            if (block[i].rx()<1200 && block[i].rx()>0) {
                block[i].render(g);
            }
            if (block[i].rx()<player.posX+40 && block[i].rx()>player.posX-20) {
                if (!block[i].disabled) {block[i].collide(player, g);}
            }
            
        }
    }
}
