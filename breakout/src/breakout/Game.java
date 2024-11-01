package breakout;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener {

    public static final int width = 750, height = 500, widthCorrection = 16 ;

    public static boolean[] keyStates = new boolean[2];

    public static int score = 0;

    private final Player player;
    private final Ball ball;
    private final Board board;

    private final Timer timer;

    // bricks

    public Game() {
        this.player = new Player(
            100,
            10,
            width/2 - 50,
            height-50
            );

        this.ball = new Ball(400, 400, 0, -10);
        // normal position at 400,400; right next to first brick: 65,25
        this.ball.velY = -1.5;
        this.ball.velX = -0.25;

        this.board = new Board(8, 10);

        this.timer = new Timer(1, this);
        this.timer.start();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        // Brick
        board.tick(g, ball);

        // Ticking objects
        player.tick(g);
        ball.tick(g, player);

        // Draw Score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 12, 16);
        g.drawString("BX: " + Math.round(ball.posX), 12, 30);
        g.drawString("BY: " + Math.round(ball.posY), 12, 42);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 37: // Left
                keyStates[0]=true; break;
            case 39: // Right
                keyStates[1]=true; break;
            default: // Everything else
                break; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 37: // Left
                keyStates[0]=false; break;
            case 39: // Right
                keyStates[1]=false; break;
            default: // Everything else
                break; 
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        repaint();
    }
    
}
