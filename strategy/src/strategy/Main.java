package strategy;

import java.io.IOException;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame();

        window.setSize(Renderer.Window_X, Renderer.Window_Y);
        window.setResizable(true);

        window.setTitle("strategy idk");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new Renderer());

        window.setVisible(true);
    }
}
