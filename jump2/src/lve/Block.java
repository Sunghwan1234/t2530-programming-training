package lve;

import java.awt.*;

import lib.*;

public class Block extends lib.Block {
    public Block(double x, double y, double r, String t) {
        super(x, y, r, t);
    }
    public void render(Graphics2D g) {
        BlockDrawer.render(g, this, Editor.ScreenX, Editor.ScreenY);
    }
}
