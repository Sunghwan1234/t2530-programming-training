package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Board {
    public final int[][] bricks;
    private final int rows, cols; // Rows and Columns are swapped (Cry about it)
    public final int width, height;

    public Board(int rows, int cols) {
        this.bricks = new int[rows+2][cols+2];
        this.rows = rows;
        this.cols = cols;

        this.width = Game.width/(rows+2);
        this.height = (Game.height/2)/(cols+2);

        System.out.println("Summoned "+rows+" Rows and "+cols+" Cols");

        for(int x = 0; x < rows+1; x++) {
            for(int y = 0; y < cols+1; y++) {
                bricks[x][y] = 1;
            }
        }
    }

    public void tick(Graphics g, Ball ball) {
        for(int x = 1; x < rows+1; x++) {
            for(int y = 1; y < cols+1; y++) {
                if(bricks[x][y] <= 0) continue;
                renderBrick(x*width, y*height, bricks[x][y], g);
                checkCollision(x,y, ball);
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

    private void checkCollision(int x, int y, Ball b) {
        boolean coll=false;
        int dmt=Ball.diameter;
        int bx=x*width, by=y*height;

        if (b.collRefresh > 0) {return;}
        if (
            b.posX > bx-dmt &&   // Ball on Left
            b.posX < bx+width &&    // Ball on right
            b.posY > by-dmt &&   // Ball on Top
            b.posY < by+height      // Ball on Bottom
        ) {
            if ( // Ball Left/Right
                b.posY > by-(dmt/2) &&   // Ball on Top
                b.posY < by+height-(dmt/2)      // Ball on Bottom
            ) {
                b.velX *=-1;
                coll=true;
            }
            if ( // Ball Top/Bottom
                b.posX > bx-(dmt/2) &&   // Ball on Left
                b.posX < bx+width-(dmt/2)    // Ball on right
            ) {
                b.velY*=-1;
                coll=true;
            }
            if (coll) {
                b.collRefresh=10;
                bricks[x][y] -= 1;
            }
        }
    }
}
