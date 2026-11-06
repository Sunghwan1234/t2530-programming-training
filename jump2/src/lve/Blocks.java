package lve;

import java.awt.*;
import java.util.List;

import lib.*;

import java.awt.geom.*;
import java.io.FileNotFoundException;
// do not use arraylist, it is garbage
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Blocks {
    public static final int width = 20, height = 20;

    public Block block[] = new Block[1000]; // Block container List of Class Block
    public int blockCount = 0;

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
                    Double.parseDouble(blockData[0]),
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
