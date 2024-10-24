package breakout;

import javax.swing.JFrame;
// important notes
// 0, 0 is top left corner
// y goes UP as objects go DOWN
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setSize(Game.width, Game.height);
        window.setResizable(false);

        window.setTitle("Team 2530 Breakout");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new Game());

        window.setVisible(true);
    }
}