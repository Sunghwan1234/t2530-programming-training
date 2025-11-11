package lve;

import java.awt.*;

import lve.lib.CP;

public class Block {
    double bx, by, br;
    char type, subtype;
    String fullType;

    public Block(double x, double y, double r, String t) {
        this.bx=x;
        this.by=y;
        this.br=r;
        this.fullType = t;
    }
    public CP rp() {return new CP(bx-Editor.ScreenX,by-Editor.ScreenY);}
    public void render(Graphics2D g) {
        Blocks.renderBlock(rp(), br, fullType, g);
    }
}
