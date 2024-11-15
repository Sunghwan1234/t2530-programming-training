import java.awt.*;

public class Player {
    public final int x = 40; public double y = Game.height-100;
    public double velY = 0;
    public int gravity = 1;
    public int width = 20, height = 20;
    public int air = 0;
    public boolean jumpable = false;

    public Player() {

    }

    public void periodic(Graphics g) {
        jump();
        physics();

        paint(g);
    }

    public void jump() {
        if (jumpable) {
            if (Game.keyDown) {
                velY -= gravity*3.8;
                jumpable=false;
            }
        }
    }

    public void physics() {
        if (y>Game.height-20-height-1) {
            velY=0;
            y=Game.height-20-height-1;
            jumpable = true;

        } else {
            velY+=0.19;
        }

        y+=velY;
    }

    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.drawRect((int) x,(int) y,width,height);
    }
}
