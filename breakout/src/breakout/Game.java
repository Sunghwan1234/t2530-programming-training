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

    public static final int Width = 750, Height = 500;

    public static int score = 0;
    public int Ycontacts = 0;
    public int Xcontacts = 0;

    private final Player player;

    private final Ball ball;

    private final Board board;

    private final Timer timer;

    // bricks

    public Game() {
        this.player = new Player(
            Width / 2 - Player.Width / 2, // 750/2 - 100/2 = 375 - 50 = 325 = X position of player
            Height - Player.Height * 5 // 500 - 10*5 = 500 - 50 = 450 = Y position of player
            );

        this.ball = new Ball(Width / 2, Height - Player.Height * 8);
        // normal position at 400,400; right next to first brick: 65,25
        this.ball.posX = 400;
        this.ball.posY = 400;
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
        g.fillRect(0, 0, Width, Height);

        // Brick
        board.periodic(g, ball);

        // Draw Player
        player.render(g);

        // Draw ball
        ball.render(g, player);

        // Draw Score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 12, 16);
        g.drawString("BX: " + Math.round(ball.posX), 12, 30);
        g.drawString("BY: " + Math.round(ball.posY), 12, 42);
        g.drawString("Ycontacts: " + this.Ycontacts, 12, 78);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 37: // Left
                player.moveLeft();
                break;
            case 39: // Right
                player.moveRight();
                break;
            default: // Everything else
                break; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        repaint();
    }
    
}
