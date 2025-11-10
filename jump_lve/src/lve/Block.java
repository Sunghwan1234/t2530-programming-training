package lve;

import java.awt.*;

import lve.lib.CP;

public class Block {
    double bx, by, br;
    char type, subtype;

    public Block(double x, double y, double r, String t) {
        this.bx=x;
        this.by=y;
        this.br=r;
        this.type = t.charAt(0);
        this.subtype = t.charAt(1);
    }
    public CP rp() {return new CP(bx-Editor.ScreenX,by-Editor.ScreenY);}
    public void render(Graphics2D g) {
        Blocks.renderBlock(rp(), br, type, subtype, g);
    }
}
