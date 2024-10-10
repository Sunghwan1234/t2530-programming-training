package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
    public double posX, posY;
    public double velX, velY;
    public int contactrefresh = 0;

    public static final int radius = 10;

    public Ball(double posX, double posY) {
        this.posX = posX; // 325
        this.posY = posY; // 450
    }

    public void render(Graphics g, Player player) {
        // Update
        this.posX += this.velX;
        this.posY += this.velY;

        // Collisions
        if (contactrefresh > 0) {
            contactrefresh -= 1;
            System.out.println(contactrefresh);
        }
        checkCollisions(player);

        // Render
        g.setColor(Color.WHITE);
        g.fillOval((int) posX, (int) posY, radius, radius);
    }

    private void checkCollisions(Player player) {
        // Top, left and right walls
        if (posX <= 0 || posX >= Game.WINDOW_WIDTH - 30) {
            velX = -velX;
        }
        if (posY <= 0) {
            velY = -velY;
        }

        // Player collision
        if (
            posX > player.posX && // bx > playerx(325 if middle)
            posX < player.posX + Player.WIDTH && // bx < 325(playerx) + 100(playerwidth)
            posY >= player.posY - radius && // by >= 450(playery) - ball radius (10 default)
            posY < player.posY + Player.HEIGHT // by < 450 + 10(height)
            ) {
            velY *= -1 - Math.random() / 10;
            velX += Math.random() / 10;
        }

        // Ground
        if (posY >= Game.WINDOW_HEIGHT) {
            System.out.println("Game over, you lose!");
            //System.exit(0);
        }
    }
}
