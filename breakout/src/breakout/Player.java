package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

    public double width = 100, height = 10;

    // The position of the player
    public double posX, posY;

    public double velX=0, velY=0;
    public double acc=0.4; // Acceleration
    public double maxV=8; // Max Velocity
    public double friction=0.93; // Friction to slow down the paddle when inactive.

    public Player(double width, double height, double posX, double posY) {
        this.width = width; this.height = height;
        this.posX = posX; this.posY = posY;
    }

    public void tick(Graphics g) {
        velocity();

        render(g);
    }
    
    private void velocity() {
        boolean keyPressed = false; // Define
        for (int i=0;i<Game.keyStates.length;i++) {
            if (Game.keyStates[i]) { // Is key pressed?
                keyPressed=true;
                switch (i) {
                    case 0: velX-=acc; // Left
                        break;
                    case 1: velX+=acc; // Right
                        break;
                    default: break;
                }
            }
        }
        if (!keyPressed) {velX*=friction;} // Friction
        bound();
        posX+=velX; // Move the player
    }
    private void bound() {
        this.posX = Math.min(posX, Game.width-Game.widthCorrection-width);
        this.posX = Math.max(posX, 0);

        velX = Math.max(velX, -maxV); // Minimum (yes its counterintuitive)
        velX = Math.min(velX, maxV); // Maximum
    }
    private void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) posX, (int) posY, (int) width, (int) height);
    }
}
