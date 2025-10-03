package jump2;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setSize(Game.WIN_WIDTH, Game.windowHeight);
        window.setResizable(true);

        window.setTitle("jump2");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new Game());

        window.setVisible(true);
    }
}
