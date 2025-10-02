package lve;

import java.awt.*;
import java.awt.geom.*;
import java.util.Arrays;

public class Placer {
    private static final int width = 20, height = 20;

    double x=0, y=430, r=0;
    int[] blockType = {0,0};

    Paint paint;
    Stroke stroke;
    Shape outline, fill;

    private boolean[] keypressed = new boolean[100];
    

    public static int blocksplaced;

    public char[] getCharTypes() {
        String type = Editor.BLOCK_TYPES[blockType[0]]+blockType[1];
        char[] types = {type.charAt(0),type.charAt(1)};
        return types;
    }

    public void place(Blocks blocks) {
        blocks.block[blocks.blockCount] = blocks.new Block(
            x,y,r,Editor.BLOCK_TYPES[blockType[0]]+blockType[1]
        );
        blocks.blockCount+=1;
        System.out.println("Placed Block: "+blocks.block[blocks.blockCount-1].type+" at ("+blocks.block[blocks.blockCount-1].x+","+blocks.block[blocks.blockCount-1].y+")");
        blocks.blockCount+=1;
    }

    public void actions(Blocks blocks, Graphics2D g) {
        int KeyActionKeys[] = {4, 20,23,45,22, 81,69, 37,38,39,40,87,65,83,68}; // Space,A,D,Z,C, Q,E,Left,Up,Right,Down,Shift,WASD
        for (int i=0;i<KeyActionKeys.length;i++) {
            int actionkeyN = KeyActionKeys[i];
            if (Editor.KeyPressed[actionkeyN] && !keypressed[actionkeyN]) {
                switch (actionkeyN) {
                    case 4: place(blocks);    break; // Space
                    case 20: blockType[0]++; break; // A
                    case 23: blockType[0]--; break; // D
                    case 45: blockType[1]++; break; // Z
                    case 22: blockType[1]--; break; // C
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
        if(blockType[0]>9){blockType[0]=0;} if(blockType[1]>9){blockType[1]=1;}
        if (r<=-90) {r=270;} if (r>=360) {r=0;}
        if(y>430){y=430;}

        render(blocks, g);
    }


    public void render(Blocks blocks, Graphics2D g) {
        blocks.renderBlock(g, x, y, r, getCharTypes()[0], getCharTypes()[1]);
    //     switch (blockType[0]) {
    //         case 0:
    //             paint = new GradientPaint((float)x+width/2,(float)y,Color.white,(float)x+width/2,(float)y+height,Color.black);
    //             fill = new Rectangle2D.Double(x,y,width,height);
    //             stroke = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
    //             outline = new Rectangle2D.Double(x-1,y,width,height);
    //             break;
    //         default: return;
    //     }
    //     g.setPaint(paint);
    //     g.fill(fill); // error
    //     g.setPaint(Color.white);
    //     g.setStroke(stroke);
    //     g.draw(outline);
    }
}
