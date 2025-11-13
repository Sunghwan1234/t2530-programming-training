package lve;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.*;

import lve.lib.CP;

public class Placer {

    public double x=0, y=430, r=0;
    private int[] blockType = {0,0};

    private BufferedWriter writer;

    private boolean[] keypressed = new boolean[100];

    public static int blocksplaced = 0;
    
    public Placer() {}
    
    public String getType() {
        String types = new String(new char[] {Editor.BLOCK_TYPES[blockType[0]],Character.forDigit(blockType[1],10)});
        return types;
    }

    public void place(Blocks blocks) {
        blocks.block[blocks.blockCount] = new Block(x,y,r,getType());
        blocks.blockCount+=1;

        try {
            writer = new BufferedWriter(new FileWriter("levelExport.txt"));
        } catch (IOException e) {
            System.err.println("Failed to initialize writer: " + e.getMessage());
            writer = null;
        }

        try {
            writer.write(x + ";" + y + ";" + r + ";" + getType().charAt(0) + getType().charAt(1));
            writer.newLine(); // Writes a new line separator
            writer.close();
            System.out.println(x + ";" + y + ";" + r + ";" + getType().charAt(0) + getType().charAt(1));
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
        blocksplaced++;        
    }

    public void actions(Blocks blocks, Graphics2D g) {
        int KeyActionKeys[] = {32, 49,51,88,90, 81,69, 37,38,39,40,87,65,83,68}; // Space,A,D,Z,C, Q,E,Left,Up,Right,Down,Shift,WASD
        for (int i=0;i<KeyActionKeys.length;i++) {
            int actionkeyN = KeyActionKeys[i];
            if (Editor.KeyPressed[actionkeyN] && !keypressed[actionkeyN]) {
                switch (actionkeyN) {
                    case 32: place(blocks);  break; // Space
                    case 49: blockType[0]--; break; // 1
                    case 51: blockType[0]++; break; // 3
                    case 88: blockType[1]--; break; // X 
                    case 90: blockType[1]++; break; // Z
                    case 81: r-=90; if (r<0) {r=270;} break; // Q
                    case 69: r+=90; if (r>=360) {r=0;} break; // E
                    case 37: Editor.ScreenX-=20; if (Editor.KeyPressed[16] /* Shift Key */) {continue;} else {break;}  // Left
                    case 38: Editor.ScreenY-=20; if (Editor.KeyPressed[16]) {continue;} else {break;}  // Up
                    case 39: Editor.ScreenX+=20; if (Editor.KeyPressed[16]) {continue;} else {break;}  // Right
                    case 40: Editor.ScreenY+=20; if (Editor.KeyPressed[16]) {continue;} else {break;}  // Down
                    case 87: y-=20; if (Editor.KeyPressed[16]) {continue;} else {break;}  // W
                    case 65: x-=20; if (Editor.KeyPressed[16]) {continue;} else {break;}  // A
                    case 83: y+=20; if (Editor.KeyPressed[16]) {continue;} else {break;}  // S
                    case 68: x+=20; if (Editor.KeyPressed[16]) {continue;} else {break;}  // D
                    
                    default:break;
                }
                keypressed[actionkeyN]=true;
            } else if (!Editor.KeyPressed[actionkeyN]) {keypressed[actionkeyN]=false;}}
        blockType[0] = Math.floorMod(blockType[0], Editor.BLOCK_TYPES.length);
        blockType[1] = Math.floorMod(blockType[1], 9);
        r = r<0?360-r:r % 360;
        if(y>430){y=430;}
        //System.out.println(blockType[0]);
    }

    public void render(Blocks blocks, Graphics2D g) {
        Blocks.renderBlock(new CP(x-Editor.ScreenX, y-Editor.ScreenY), r, getType(),g);
    }
}
