package lib;

import lib.CP;
import lib.CArea;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tester extends JPanel implements ActionListener, KeyListener {
      private boolean key[] = new boolean[100];
      
      private final Timer timer;

      private double x=100,y=100;
      private double r=0;

      private CArea a;

        public Tester() {
          


          timer = new Timer(1, this);
          timer.start();
          setFocusable(true);
          setFocusTraversalKeysEnabled(false);
          addKeyListener(this);
        }

        @Override
        public void paint(Graphics g) {
          Graphics2D g2 = (Graphics2D) g;
          g2.setPaint(Color.black);
          g2.fillRect(0,0,500,500);


          a = new CArea(x,y,x+10,y+10);
          a.rotate(r,new CP(x+5,y+5));
          a.setCorners();
          g2.setPaint(Color.white);
          g2.fill(a.getRect());
          g2.setPaint(Color.green);
          g2.draw(a.getRect());

          g2.setPaint(Color.RED);
          g2.drawOval((int)a.lt.x,(int)a.lt.y,1,1);
          g2.drawOval((int)a.rb.x,(int)a.lt.y,1,1);
          g2.drawOval((int)a.rb.x,(int)a.rb.y,1,1);
          g2.drawOval((int)a.lt.x,(int)a.rb.y,1,1);
          
          g2.setPaint(Color.WHITE);
          int h = 20;
          for (int i=0;i<key.length;i++) {
            if (key[i]) {
              g2.drawString(i+" Key Pressed", 1, h);
              h+=15;

              switch (i) {
                case 87: y-=1; break;
                case 83: y+=1; break;
                case 65: x-=1; break;
                case 68: x+=1; break;
                default:break;
              }
            }
          }
          
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
          key[e.getKeyCode()]=true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
          key[e.getKeyCode()]=false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          repaint();
        }
    }
