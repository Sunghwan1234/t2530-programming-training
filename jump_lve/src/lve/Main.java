package lve;

import javax.swing.JFrame;

import lve.Editor;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setSize(Editor.windowWidth, Editor.windowHeight);
        window.setResizable(false);
        window.setTitle("Jump Level Editor");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new Editor());

        window.setVisible(true);
        
    }   
}
