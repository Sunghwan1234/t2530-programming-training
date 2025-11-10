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
      private final double width=50, height=50;
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

          CP p = new CP(100,110);
          CP p2 = new CP(100,110);

          CP c = new CP(100, 100);

          CP rp = p.rotate(r,c);
          p2.rotateSelf(r,c);

          g2.setPaint(Color.green);
          g2.drawRect((int)c.x,(int)c.y,5,5);

          g2.setPaint(Color.red);
          g2.drawRect((int)rp.x,(int)rp.y,5,5);

          g2.setPaint(Color.magenta);
          g2.drawRect((int)p2.x,(int)p2.y,5,5);
          
          
          g2.setPaint(Color.WHITE);
          g2.drawString("R: "+r,0,30 );
          int h = 40;
          for (int i=0;i<key.length;i++) {
            if (key[i]) {
              g2.drawString(i+" Key Pressed", 1, h);
              h+=15;

              switch (i) {
                case 87: y-=1; break;
                case 83: y+=1; break;
                case 65: x-=1; break;
                case 68: x+=1; break;
                case 81: r-=1; break;
                case 69: r+=1; break;
                default:break;
              }
            }
          }
          g.dispose();
        }

        

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
          key[e.getKeyCode()]=true;
          switch (0) {
                case 87: y-=1; break;
                case 83: y+=1; break;
                case 65: x-=1; break;
                case 68: x+=1; break;
                case 69: r+=1; break;
                default:break;
              }
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
