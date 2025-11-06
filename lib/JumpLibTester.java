package lib;

import javax.swing.JFrame;

public class JumpLibTester {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setSize(500, 500);
        window.setResizable(true);

        window.setTitle("Tester");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new Tester());

        window.setVisible(true);
    }
}
