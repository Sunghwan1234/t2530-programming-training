package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Board {
    public final int[][] bricks;
    private final int rows, cols;
    public final int width = 40, height = 40;

    public Board() {
        this.rows = (Game.WINDOW_WIDTH-(width*2))/width;
        this.cols = (Game.WINDOW_HEIGHT/2-(height))/height;

        System.out.println(this.rows);
        System.out.println(this.cols);

        this.bricks = new int[rows][cols];

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < cols; y++) {
                bricks[x][y] = 1;
            }
        }
    }

    public void periodic(Graphics g, Ball ball) {
        for(int x = 1; x < rows; x++) {
            for(int y = 1; y < cols; y++) {
                if(bricks[x][y]==0) continue;
                renderBrick((x)*width, (y)*width, bricks[x][y], g);
                checkCollision((x)*width, (y)*width, bricks[x][y], ball);
            }
        }
    }

    public void renderBrick(int bx, int by, int bs, Graphics g) {
        // cols = 10, rows = 10; rows are up down, cols are <->
        // width = 750, height = 250
        //so if x=1,y=1; rect would be (75*1, 25*1, 75, 25)
        switch (bs) {
            case 1: g.setColor(Color.GRAY); break;
            default: g.setColor(Color.GRAY); break;
        }
        g.fillRect(bx, by, bx+width, by+height); 
        g.setColor(Color.BLACK); g.drawRect(bx, by, bx+width, by+height);
    }

    public void checkCollision(int bx, int by, int bs, Ball ball) {
        // radius = 10 x1 and y1 starts at 1 (first brick top-left position)
        if (ball.contactrefresh > 0) {return;}
        for(int x1 = 1; x1 < rows; x1++) {
            for(int y1 = 1; y1 < cols; y1++) {

            }
        }
    }
}
