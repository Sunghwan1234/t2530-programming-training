package lib;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JumpLibTester {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setSize(200, 200);
        window.setResizable(true);

        window.setTitle("Tester");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new Tester());

        window.setVisible(true);
    }
    public class Tester implements ActionListener, KeyListener {

        public Tester() {
          
        }

        @Override
        public void keyTyped(KeyEvent e) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
        }

        @Override
        public void keyPressed(KeyEvent e) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
        }

        @Override
        public void keyReleased(KeyEvent e) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }
}
