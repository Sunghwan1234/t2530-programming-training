package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

    public static final double WIDTH = 100, HEIGHT = 10;

    // The x position of the player
    public double posX;

    // The y position of the player
    public final double posY;

    public Player(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void moveLeft() {
        this.posX -= 10;
        checkBounds();
    }

    public void moveRight() {
        this.posX += 10;
        checkBounds();
    }

    private void checkBounds() {
        this.posX = Math.min(this.posX, Game.WINDOW_WIDTH);
        this.posX = Math.max(this.posX, 0);
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) this.posX, (int) this.posY, (int) WIDTH, (int) HEIGHT);
    }
}
