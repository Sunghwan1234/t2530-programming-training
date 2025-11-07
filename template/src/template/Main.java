package template;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(Renderer.WIN_WIDTH, Renderer.WIN_HEIGHT);
        frame.setResizable(false);

        frame.setTitle("Template 2D Renderer");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new Renderer());

        frame.setVisible(true);
    }
}
