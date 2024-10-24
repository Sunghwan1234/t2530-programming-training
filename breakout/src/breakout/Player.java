package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

    public double width = 100, height = 10;

    // The x position of the player
    public double posX;

    // The y position of the player
    public final double posY;

    public Player(double width, double height, double posX, double posY) {
        this.width = width; this.height = height;
        this.posX = posX; this.posY = posY;
    }

    public void moveLeft() {
        this.posX -= 100;
        checkBounds();
    }

    public void moveRight() {
        this.posX += 100;
        checkBounds();
    }

    private void checkBounds() {
        this.posX = Math.min(posX, Game.width-width);
        this.posX = Math.max(posX, 0);
    }

    private void velocity() {
        
    }

    public void periodic(Graphics g) {
        velocity();

        render(g);
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) posX, (int) posY, (int) width, (int) height);
    }
}
