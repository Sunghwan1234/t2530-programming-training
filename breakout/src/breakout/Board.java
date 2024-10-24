package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Board {
    public final int[][] bricks;
    private final int rows, cols;
    public final int width, height;

    public Board(int rows, int cols) {
        this.bricks = new int[rows+2][cols+2];
        this.rows = rows;
        this.cols = cols;

        this.width = Game.width/(rows+2);
        this.height = (Game.height/2)/(cols+2);

        System.out.println(rows +" "+ cols);

        for(int x = 0; x < rows+1; x++) {
            for(int y = 0; y < cols+1; y++) {
                bricks[x][y] = 1;
            }
        }
    }

    public void periodic(Graphics g, Ball ball) {
        for(int x = 1; x < rows+1; x++) {
            for(int y = 1; y < cols+1; y++) {
                if(bricks[x][y]==0) continue;
                renderBrick(x*width, y*height, bricks[x][y], g);
                checkCollision(x*width, y*height, bricks[x][y], ball);
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
        g.fillRect(bx, by, width, height); 
        g.setColor(Color.BLACK); g.drawRect(bx, by, width, height);
    }

    public void checkCollision(int bx, int by, int bs, Ball ball) {
        // radius = 10 x1 and y1 starts at 1 (first brick top-left position)
        if (ball.contactrefresh > 0) {return;}
        
    }
}
