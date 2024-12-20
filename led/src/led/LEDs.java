package led;

import static java.lang.Math.*;

import java.awt.Graphics;
import java.awt.Color;

import java.util.Arrays;

public class LEDs {
    boolean key = false;
    double test = 0;

    int ledCount = 50; // Number of leds. change accordingly.

    int[] m_buffer = new int[ledCount*4*4/3]; // WPILIB. FORMAT: BGR | Used by WPILIB

    // NEW CODE TO ADD
    int[] LedRGBWData = new int[ledCount*4]; // ME! FORMAT: RGBW | Data to store the RGBW data before pushing to the LEDS.

    public class Color {
        public int r,g,b;

        /** Creates 0 Color */ Color() {this.r=0;this.g=0;this.b=0;}
        Color(double r, double g, double b) {
            this.r=(int)r; this.g=(int)g; this.b=(int)b;
        }
        Color(int r,int g, int b) {
            this.r=r; this.g=g; this.b=b;
        }

        public void setHSV(int h, final int s, final int v) {
            if (s == 0) {
                this.r=v;this.g=v;this.b=v;;
            }
              // Loosely based on
              // https://en.wikipedia.org/wiki/HSL_and_HSV#HSV_to_RGB
              // The hue range is split into 60 degree regions where in each region there
              // is one rgb component at a low value (m), one at a high value (v) and one
              // that changes (X) from low to high (X+m) or high to low (v-X)
          
              // Difference between highest and lowest value of any rgb component
              final int chroma = (s * v) / 255;
              // Because hue is 0-180 rather than 0-360 use 30 not 60
              final int region = (h / 30) % 6;
              // Remainder converted from 0-30 to 0-255
              final int remainder = (int) Math.round((h % 30) * (255 / 30.0));
              // Value of the lowest rgb component
              final int m = v - chroma;
              // Goes from 0 to chroma as hue increases
              final int X = (chroma * remainder) >> 8;
          
              switch (region) {
                case 0:
                  this.r=v; this.g=X + m; this.b=m;
                case 1:
                  this.r=v - X; this.g=v; this.b=m;
                case 2:
                  this.r=m; this.g=v; this.b=X + m;
                case 3:
                  this.r=m; this.g= v - X;this.b= v;
                case 4:
                  this.r=X + m;this.g= m;this.b= v;
                default:
                  this.r=v; this.g=m; this.b=v - X;
              }
              print(r+" "+g+" "+b);
        }
    }

    public static void print(String s) {System.out.println(s);}
    public static void print(int s) {System.out.println(s);}
    public static void print(double s) {System.out.println(s);}

    public void run() {
        //print(Arrays.toString(LedRGBWData));
    }
    public void periodic() {
        //blink(50, 120, new Color(0,255,0),new Color(255,0,0));
        //rainbow(0.1,0.1,test);
        rainbow(Renderer.Timer, test, 0.03, 0);
        //print(Arrays.toString(LedRGBWData));
        pushData();
    }
    public void rainbow(int timer, double speed, double change, int w) {
        double rainbow = ((double) timer*speed/100) % 1;
        for (int i=0;i<ledCount-1;i++) {
            //setDataRGBW(i, Color.getColor(null, Color.HSBtoRGB((float) (rainbow-(i*Change)), (float) 1, (float) 1)), W);
            java.awt.Color c = java.awt.Color.getHSBColor((float) (rainbow-(i*change)+0.000001), (float) 1, (float) 1);
            setDataRGBW(i, new Color(c.getRed(),c.getGreen(),c.getBlue()), w);
        }
        //setAllDataRGBW(rc,0);
    }
    public void oldrainbow(double Speed, double Change, double Vibrance) {
        /** RAINBOW LIGHTS!!!
         * Speed: 0.0 ~ 1.0
         * Change: 0.0 ~ 1.0
         * Vibrance: 0.0 ~ 1.0 [TESTING]
         * rainbow: The current color the first LED.
         * i: Which LED?
         * j: Change. Since the change is inside of SIN, adding PI adds nothing
         */
        double rainbow = Renderer.Timer*Speed/10;
        for (int i=0;i<ledCount-1;i++) {
            double j = i*(PI-Change);
            setDataRGBW(i,
            (int) (sin(rainbow+j)*255),
            (int) (sin(rainbow+(Vibrance)+j)*255),
            (int) (sin(rainbow+(Vibrance*2)+j)*255),0);}
    }
    public void blink(int Percent, int Length, Color color1, Color color2) {
        int blinktime = (int) Renderer.Timer % Length;
        for (int i=0;i<ledCount-1;i++) {
            if (Length*(100-Percent)/100 <= blinktime && blinktime <= Length-1) {
                setDataRGBW(i, color1,0);
            } else {
                setDataRGBW(i, color2.r,color2.g,color2.b,0);
            }
        }
    }

    public void setAllDataRGBW(int R, int G, int B, int W) {
        for (int i=0;i<LedRGBWData.length/4;i++) {
            LedRGBWData[i * 4] = R; LedRGBWData[i * 4 + 1] = G; LedRGBWData[i * 4 + 2] = B;
            LedRGBWData[i * 4 + 3] = W;}
    }
    public void setAllDataRGBW(Color color, int W) {
        for (int i=0;i<LedRGBWData.length/4;i++) {
            LedRGBWData[i * 4] = color.r; LedRGBWData[i * 4 + 1] = color.g; LedRGBWData[i * 4 + 2] = color.b;
            LedRGBWData[i * 4 + 3] = W;}
    }

    public int[] getDataRGBW(int index) {
        return new int[] {
            LedRGBWData[index * 4], LedRGBWData[index * 4 + 1], LedRGBWData[index * 4 + 2],
            LedRGBWData[index * 4 + 3]};
    }
    public void setDataRGBW(int index, int R, int G, int B, int W) {
        LedRGBWData[index * 4] = R; LedRGBWData[index * 4 + 1] = G; LedRGBWData[index * 4 + 2] = B;
        LedRGBWData[index * 4 + 3] = W;
    }
    public void setDataRGBW(int index, Color color, int W) {
        LedRGBWData[index * 4] = color.r; LedRGBWData[index * 4 + 1] = color.g; LedRGBWData[index * 4 + 2] = color.b;
        LedRGBWData[index * 4 + 3] = W;
    }

    public void pushData() { // This sets the RGBW
        for (int i=0; i<ledCount-1;i++) {
            setRGBW(i, LedRGBWData[i * 4], LedRGBWData[i * 4 + 1], LedRGBWData[i * 4 + 2], LedRGBWData[i * 4 + 3]);}
    }

    public void setRGBW(int index, int R, int G, int B, int W) {
        int offsetindex = index + (int) Math.floor(index/3); // Index offset ONLY FOR ENCODING INTO GRBGRBGRBGRB

        int[] prevRGBW = getDataRGBW(index);
        if (index>0) {prevRGBW = getDataRGBW(index-1);}

        int[] nextRGBW = getDataRGBW(index+1);

        //print(prevRGB.toString());
        /** HOW TO READ R G B comments
         * I will use C (Current) and V (Value) as example
         * C=C: Use C to output as C. basically just normal setRGB
         * C=V: Use the V to output as C. for example G=W (W is the input value from here)
         * C=+V: Use the next index's V to output
         * C=-V: Use the previous index's V to output.
         * 
         * REMEMBER: you use the INDEX, not the OFFSETINDEX
         * 
         * How tf this encodes?
         * RGBGWRWBGBRW
         * WPIPID hates simplicity, so I have to make this!!!
         * Lowercase: Previous or After's
         * 
         * Cases:           case0 case1 case2 case0
         * YOUR input:      RGB W|RG BW|R GBW|RGBW|... 
         * case 0:          RGB g Wr          RGB    
         * case 1:              G wR WB g
         * case 2:                   wb G BRW    
         * input:           RGB|R GB|RG B|RGB|RGB|...
         *                    reorders correctly
         * setLED:          BGR|B GR|BG R|BGR|BGR|...
         *                    reorders correctly
         * WPILIB sends:    GRB|G RB|GR B|GRB|GRB|...
         * output:          GRB W|GR BW|G RBW|GRBW|...
         */

        switch (index % 3) {
            case 0:
                setRGB(offsetindex, R, G, B); // R=R G=G B=B
                setRGB(offsetindex+1, nextRGBW[1], W, nextRGBW[0]); // R<+G G<W B<+R
                break;
            case 1:
                setRGB(offsetindex, G, prevRGBW[3], R); // R<G G<-W B<R
                setRGB(offsetindex+1, W, B, nextRGBW[1]); // R=W G=B B=+G FOR B, REMEMBER THAT THE INDEX is 1
                break;
            case 2:
                setRGB(offsetindex, prevRGBW[3], prevRGBW[2], G); // R=-W G=-B B=G
                setRGB(offsetindex+1, B, R, W); // R=B G=R B=W
                break;
            default:break;
        }
    }

    /* CLASS FILES */
    public void setRGB(int index, int r, int g, int b) { // CLASS FILE | encodes in BGR, for some reason???
        m_buffer[index * 4] = b;
        m_buffer[(index * 4) + 1] =  g;
        m_buffer[(index * 4) + 2] =  r;
        m_buffer[(index * 4) + 3] = 0; // I think its a seperator, maybe for the W, but never used and useless.
    }
    public Color getLED(int index) { // CLASS FILE | what is this lol you get led data (except the 0) in RGB format
        return new Color( // Since this is the java.awt Color, it needs to be converted to int.
            (int) ((m_buffer[index * 4 + 2] & 0xFF) / 255.0),
            (int) ((m_buffer[index * 4 + 1] & 0xFF) / 255.0),
            (int) ((m_buffer[index * 4] & 0xFF) / 255.0));
            // LedBuffer[index * 4 + 2],
            // LedBuffer[index * 4 + 1],
            // LedBuffer[index * 4]);
    }

    public void render(Graphics g) { // VIRTUAL LED | This replaces the actual LEDs for a virtual one. should work perfectly :D
        int[] RenderingLedData = new int[ledCount*4];
        for (int i=0;i<m_buffer.length/4;i+=1) { // This converts the LedBuffer to the rendering data, which is GRB. Done inside the CLASS
            RenderingLedData[i*3 + 1] = m_buffer[i*4 + 2]; // G 
            RenderingLedData[i*3 + 0] = m_buffer[i*4 + 1]; // R
            RenderingLedData[i*3 + 2] = m_buffer[i*4 + 0]; // B
        }
        //System.out.println(Arrays.toString(LED));

        for (int i=0;i<RenderingLedData.length/4;i+=1) { // The actual rendering. This is the LEDS.
            int W = RenderingLedData[(i*4)+3]/2;
            java.awt.Color RGBW = new java.awt.Color(
                min(abs( RenderingLedData[(i*4)+1]) + W, 255),
                min(abs( RenderingLedData[(i*4)]) + W, 255),
                min(abs( RenderingLedData[(i*4)+2]) + W, 255));
            //System.out.println();
            g.setColor(RGBW);
            
            g.fillRect(i*20,0,20,20);
        }
    }
}
