package jump;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.PI;

public class Blocks {

    private static final int Width = 20, Height = 20;

    // Blocks 2D Array
    // the internal array settings: posX, posY, type
    // NOTHING: 0
    // SPIKE: 1
    // BLOCK: 2
    public double block[][] = new double[1000][4];
    public int blocks = 0;
    public double defY;

    public Blocks(int defY) { // setup and nothing
        this.defY = defY; // 450
    }
    public void die(String errmsg) {
        System.out.println("- - - - - - - ");
        System.out.println(" > you died < "); // DEATH
        System.out.println(errmsg);
        System.out.println("- - - - - - - ");
        if (!Game.debug) {System.exit(1);}
    }
    public void collision(double posX, double posY,double rot, double t, Player p) { // COLLISION - - - - - - - - - - - -

        int blocktype = (int) Math.floor(t/10);
        // Collision Points: ((x,y),(x2,y2))
        //double[][] cPoints = new double[2][2];

        //     p.posX // Player left x
        //     p.posY // Player top Y
        double pbY = p.posY+Player.HEIGHT; // player bottom y
        double prX = p.posX+Player.WIDTH;  // player right x

        switch (blocktype) { // BLOCK COLLISION
            case 1:
                // TESTED: Block X and Y is top left
                double brX = posX+Width;  // Block Right X
                double bbY = posY+Height; // Block Top Y

                if (prX > posX &&     // Is player right  X > left block collision
                    p.posX < brX &&   // Is player (left) x < right block collision
                    pbY > posY &&     // Is player bottom y > top block collision
                    p.posY < bbY      // Is player (top)  y < bottom block collision
                ) { if ( // Checking Top/Bottom block collision
                        (p.gravity== 1 && p.posY < bbY-3 && p.posY > posY) || // Player Top Y > Block Top Y +5 (Gravity 1)
                        (p.gravity==-1 && pbY > posY+3 && pbY < bbY)          // Player Bottom Y > Block top Y+5 (Gravity -1)
                    ) {die("T/B Col. Y:"+p.posY+" BY: "+posY);
                } else if ( // Side Block Collision
                        p.posY > posY-5 && // Player Top Y >= Block Top Y - 5
                        p.posY < posY+5    // Player Top Y <= Block Top Y + 5
                    ) {die("S Col. Y: "+p.posY+" BY: "+posY);
                } else if ( // 1 pixel lifesaver
                    (p.gravity==-1 && pbY > posY+1) ||  // Is player bottom y > top block collision
                    (p.gravity==1 && p.posY < bbY-1)    // Is player (top)  y < bottom block collision
                ) {
                        p.velY = 0;
                        p.posY -= p.gravity; 
                        if (p.gravity==1){p.posY = posY-Player.HEIGHT;} else {p.posY=posY+Player.HEIGHT;}
                        p.jumpable = true;
                        // System.out.println("block.collision:inblock");
                    }
                }
                break;
            case 2:  // SPIKE COLLISION - - - -
                // Player Y at ground is 430
                // X, Y is Top Left
                // double[][] cPoints = new double[2][2];
                // double crX = posX+Width-7; // collision right x
                // double cbY = posY+Height; // collision bottom y
                // cPoints[0][0] = posX+7; cPoints[0][1] = cbY;
                // cPoints[1][0] = crX; cPoints[1][1] = posY+6;

                double newpoints[][] = RotationPoints(posX,posY+Height,blocktype, rot); // THIS WORKS! or does it?
                //System.out.println(Arrays.deepToString(cPoints));
                //System.out.println(Arrays.deepToString(newpoints));
                if ( // CURRENTLY WORKING
                    prX > newpoints[0][0] &&    // Is player right  X > left spike collision
                    p.posX < newpoints[1][0] && // Is player (left) x < right spike collision
                    pbY > newpoints[1][1] &&    // Is player bottom y > top spike collision
                    p.posY < newpoints[0][1]    // Is player (top)  y < bottom spike collision
                    ) {die("Spike Col." + Arrays.deepToString(newpoints));}
                break;
            case 3:  // ORB collision
                // A bigger hitbox is used
                int hitboxaddition = 3;
                if (prX > posX-hitboxaddition &&          // P RX > L col
                    p.posX < posX+Width+hitboxaddition && // P LX < R col
                    pbY > posY-hitboxaddition &&          // P BY > T col
                    p.posY < posY+Height+hitboxaddition   // P TY < B col
                ) {p.orbcontact = (int)t;}
                break;
            case 4: // PAD collision
                // A little small hitbox
                if (prX > posX+3 &&                 // P RX > LX
                    p.posX < posX+Width-3 &&        // P LX < RX
                    pbY > posY+Height-(Height/4) && // P BY > TY
                    p.posY < posY+Height            // P TY < BY
                ) { p.posY -= p.gravity*4;
                    switch ((int) t) {
                        case 40: // Yellow
                            p.velY = -5*p.gravity;
                            break;
                        case 41:
                            p.velY = -3.8*p.gravity;
                            break;
                        case 42:
                            p.velY = -6.7*p.gravity;
                            break;
                        case 43:
                            p.gravity *= -1;
                            p.velY = p.gravity*2;
                            break;
                        default:break;
                    }
                }
                break;
            default:break;
        }
    }
    public double[][] RotationPoints(double X, double Y, int t, double ro) {
        double[][] output = new double[2][2];
        switch (t) {
            case 2: // SPIKE Collisions: WORKING?
                int RotN = (int) (((ro % 40 + 40) % 40)/10);
                //System.out.println(RotN);
                //System.out.println(ro);
                double[] RotConst1 = {7,0,7,6,7};
                double[] RotConst2 = {13,14,13,20,13};
                
                output[0][0] = X + RotConst1[RotN]; // Left Collision
                output[0][1] = Y - RotConst1[RotN+1]; // Bottom Collision
                output[1][0] = X + RotConst2[RotN]; // Right Collision
                output[1][1] = Y - RotConst2[RotN+1]; // Top Collision

                //System.out.println(RotN);
                //System.out.println(offset2);
                break;
            default:break;
        }
        return output;
    }

    public void generate() { // This generates a test-used pattern or something.
        
        block[0][0] = 500; block[0][1] = defY; block[0][3] = 40; blocks = 1;
        //  for(int i=1; i<100; i++) {
        //     block[i][1] = defY;// block[i][0] = block[i-1][0]+20; block[i][2]=0;
        //     // test build: 2 11112 repeat
        //     block[i][0] = block[i-1][0]+20; block[i][2] = 20; i+=1;
        //     block[i][0] = block[i-1][0]+20; block[i][1] = defY; block[i][2] = 10; i+=1;
        //     block[i][0] = block[i-1][0]+20; block[i][1] = defY; block[i][2] = 10; i+=1;
        //     block[i][0] = block[i-1][0]+20;block[i][1] = defY; block[i][2] = 10; i+=1;
        //     block[i][0] = block[i-1][0]+20; block[i][1] = defY; block[i][2] = 10;
        //     blocks = i;
        // }
        
        System.out.println("spikes.generation:complete");
        //System.out.println(Arrays.deepToString(block));
    }

    public void importLvdata(String file) throws FileNotFoundException { // This imports the level.txt file.
        Scanner sc = new Scanner(new File(file)); // I use Scanner.
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {lines.add(sc.nextLine());}
        String[] arr = lines.toArray(new String[0]); // The scanner output goes to String array arr[x][v]
        // System.out.println(Arrays.deepToString(arr));
        
        
        for (int t=0;t<arr.length;t++) {
            String blockdata[] = arr[t].split(";",0);
            block[blocks][0]=Double.parseDouble(blockdata[0])+400;
            block[blocks][1]=Double.parseDouble(blockdata[1]);
            block[blocks][2]=Double.parseDouble(blockdata[2]);
            block[blocks][3]=Double.parseDouble(blockdata[3]);
            System.out.println(Arrays.deepToString(blockdata));
            blocks += 1;
        }

        // System.out.println(Arrays.deepToString(block));
        System.out.println("Leveldata import complete with "+blocks+" blocks");
    }
    public void render(Graphics g, Player p) { // RENDERING - - - - - - - - - - - - - - - - - - - - - - - - -
        for (int i = 0; i < blocks; i++) { block[i][0] -= 2; // moving the block
            if (!(block[i][3] == 0) && block[i][0] < Game.WinWidth && block[i][0] > -40) {
                if (block[i][0] < 200) { // only if the object is close to the player
                    this.collision(block[i][0], block[i][1], block[i][2],block[i][3], p); // Collision
                }
                int blockx = (int) block[i][0]; int blocky = (int) block[i][1]; // rendering setup
                Double blockt = block[i][3]; char Sblockt = blockt.toString().charAt(1);
                int Pblockt = (int) Math.floor(blockt/10);
                //System.out.println(subblockt);

                Graphics2D g2d = (Graphics2D)g; // graphics2d for rotating
                AffineTransform old = g2d.getTransform();
                g2d.rotate(Math.toRadians(block[i][2]),blockx+10,blocky+10);
                if (Pblockt == 3 || Pblockt == 4) {
                    switch (Sblockt) { // Color
                        case '0': g.setColor(Color.yellow); break;
                        case '1': g.setColor(Color.pink); break;
                        case '2': g.setColor(Color.red); break;
                        case '3': g.setColor(Color.cyan); break;
                        default:break;}
                }
                switch (Pblockt) {
                    case 1: // BLOCK drawing
                        g.setColor(Color.white); g.drawRect(blockx, blocky, Width, Height); break;
                    case 2: // SPIKE drawing
                        g.setColor(Color.red); g.drawPolygon(
                            new int[] {blockx, blockx+Width, blockx+Width/2},
                            new int[] {blocky+Height, blocky+Height, blocky},3);
                        break;
                    case 3: // ORB drawing
                        g.fillOval(blockx+1, blocky+1, Width-1, Height-1); break;
                    case 4: // PAD drawing
                       g.fillArc(blockx, blocky+Height-(Height/4), Width, Height/2, 180, -180); break;
                    default:break;}
                g2d.setTransform(old);
            }
        }
        if (block[blocks-1][0] < -20) { // WINNING
                    System.out.println("- - - - - - - - - - - - - - - - -");
                    System.out.println("You made it to the end. Congrats.");
                    System.out.println("- - - - - - - - - - - - - - - - -");
                    System.exit(1);}
    }
}
