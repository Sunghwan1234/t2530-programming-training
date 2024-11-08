package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
    public double posX, posY;
    public double velX, velY;
    public int collRefresh = 0;

    public boolean playerContact = false; // Prevent ball from getting stuck inside the player

    public static final int diameter = 10;

    public Ball(double posX, double posY,double velX, double velY) {
        this.posX = posX; // 325
        this.posY = posY; // 450
        this.velX = velX;
        this.velY = velY;
    }

    public void tick(Graphics g, Player p) {
        this.posX += this.velX;
        this.posY += this.velY;
        collision(p);

        render(g);
    }

    public void collision(Player p) {
        if (posX <= 0 || posX >= Game.width-Game.widthCorrection-diameter) { // Left/Right
            velX = -velX;}
        if (posY <= 0) {velY = -velY;} // Top
        if (posY >= Game.height) { // Bottom
            System.out.println("Game over, you lose!");
            System.exit(0);
        }

        if (
            posX >= p.posX && // Right of Plr
            posX+diameter <= p.posX + p.width && // Left of Plr
            posY+diameter >= p.posY && // Top of Plr
            posY <= p.posY + p.height // Bottom of Plr (When will we use this? We will never know)
        ) {
            if (!playerContact) {
                velY *= -1;
                velX += p.velX/2;

                playerContact = true;
            }
        } else {
            playerContact = false;
        }

        if (collRefresh>0) {
            collRefresh-=1;
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) posX, (int) posY, diameter, diameter);
    }
}
