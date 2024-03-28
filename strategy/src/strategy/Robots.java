package strategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Robots {
    public int[][] botd = new int[3][2];
    public int[][] botp = new int[3][3];
    public boolean botdrag = false;
    public int draggingbot = -1;

    public Robots(int[][] _botd, int[][] _botp) {
        this.botd = _botd;
        this.botp = _botp;
    }

    public void render(Graphics g, double[] MDP, double[] MP) {
        int mouseSX = (int) MDP[0];
        int mouseSY = (int) MDP[1];

        int botx; int boty;
        int botw; int both;
        int botb = 10;

        for (int i=0; i<3; i++) {
            botx = botp[i][0]; boty = botp[i][1];
            botw = botd[i][0]; both = botd[i][1];

        if (Renderer.MousePressed) {
            int cbotlx = botx-(botw/2); int cbotty = boty-(both/2);
            int cbotrx = cbotlx+botw; int cbotby = cbotty+both;

            if (mouseSX > cbotlx && // Mouse inside Left X
                mouseSX < cbotrx &&
                mouseSY > cbotty &&
                mouseSY < cbotby)
            {
                botdrag = true;
                draggingbot = i;
            }
        } else {
            botdrag = false;
            draggingbot = -1;
        }
        if (i == draggingbot) {
            botp[i][0] = (int) MP[0]; botp[i][1] = (int) MP[1];
        }
            int botbw = botw-botb; int botbh = both-botb;
            g.setColor(Color.white); g.fillRect(botx-(botw/2), boty-(both/2), botw, both);
            g.setColor(Color.black); g.fillRect(botx-(botbw/2), boty-(botbh/2), botbw, botbh);
        }
    }
}
