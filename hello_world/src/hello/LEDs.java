package hello;

import static java.lang.Math.floor;
import static java.lang.Math.min;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

import java.awt.Color;
import java.awt.Graphics;

import java.util.Arrays;

public class LEDs {
    boolean key = false;

    int NumberOfLeds = 50; // Number of leds. change accordingly.
    double timer = 0; // timer. any questions?

    int[] RenderingLedData = new int[NumberOfLeds*4]; // LED RGBW. FORMAT: GRBW | Used by the Renderer
    int[] LedBuffer = new int[NumberOfLeds*4*4/3]; // WPILIB. FORMAT: BGR | Used by WPILIB

    // NEW CODE TO ADD
    int[] LedRGBWData = new int[NumberOfLeds*4]; // ME! FORMAT: RGBW | Data to store the RGBW data before pushing to the LEDS.

    public LEDs() {}

    public static void print(String s) {System.out.println(s);}
    public static void print(int s) {System.out.println(s);}

    public void run() {
        //print(Arrays.toString(LedRGBWData));
    }
    public void periodic() {
        timer += 1;
        //blink(50, 120, new Color(0,255,0),new Color(255,0,0));
        rainbow(0.1,0.1,0.5);
         // for (int i=0;i<12;i++) {
        //     if (i % 2 == 1) {
        //         setRGBW(i, 0, 1, 255, 3);
        //     } else {
        //         setRGBW(i, 0, 255, 2, 3);
        //     }
        // }
        //for (int i=0;i<20;i++) {setDataRGBW(i,11,222,33,44);}

        // setRGB(0, 0, 255, 0);
        // setRGB(1, 255, 0, 0);
        // setRGB(2, 0, 0, 255);
        // setRGB(3, 0, 0, 0);

        //setDataRGBW(0,0,255,0,100);
        //print(Arrays.toString(LedRGBWData));
        pushData();
    }
    public void rainbow(double Speed, double Change, double Vibrance) {
        /** RAINBOW LIGHTS!!!
         * Speed: 0.0 ~ 1.0
         * Change: 0.0 ~ 1.0
         * Vibrance: 0.0 ~ 1.0 [TESTING]
         * 
         * rainbow: The current color the first LED.
         * i: Which LED?
         * j: Change. Since the change is inside of SIN, adding PI adds nothing
         */
        double rainbow = timer*Speed/10;
        for (int i=0;i<NumberOfLeds-1;i++) {
            double j = i*(PI-Change);
            setDataRGBW(i,
            (int) (sin(rainbow+j)*255),
            (int) (sin(rainbow+(PI*Vibrance)+j)*255),
            (int) (sin(rainbow+(PI*Vibrance*2)+j)*255),0);}
    }
    public void blink(int Percent, int Length, Color color1, Color color2) {
        int blinktime = (int) timer % Length;
        for (int i=0;i<NumberOfLeds-1;i++) {
            if (Length*(100-Percent)/100 <= blinktime && blinktime <= Length-1) {
                setDataRGBW(i, color1.getRed(),color1.getGreen(),color1.getBlue(),0);
            } else {
                setDataRGBW(i, color2.getRed(),color2.getGreen(),color2.getBlue(),0);
            }
        }
    }

    public int[] getDataRGBW(int index) {
        return new int[] {
            LedRGBWData[index * 4],
            LedRGBWData[index * 4 + 1],
            LedRGBWData[index * 4 + 2],
            LedRGBWData[index * 4 + 3]};
    }
    public void setDataRGBW(int index, int R, int G, int B, int W) {
        LedRGBWData[index * 4] = R;
        LedRGBWData[index * 4 + 1] = G;
        LedRGBWData[index * 4 + 2] = B;
        LedRGBWData[index * 4 + 3] = W;
    }
    public void pushData() { // This sets the RGBW
        for (int i=0; i<NumberOfLeds-1;i++) {
            setRGBW(i, LedRGBWData[i * 4],
            LedRGBWData[i * 4 + 1],
            LedRGBWData[i * 4 + 2],
            LedRGBWData[i * 4 + 3]);
        }
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
         * WPIPID hates simplicity, so I have to make this!!!
         * 
         * YOUR input:      RGB W|RG BW|R GBW|RGBW|...
         * input:           RGB|R GB|RG B|RGB|RGB|...
         * 
         * setLED:          BGR|B GR|BG R|BGR|BGR|...
         *                    reorders correctly
         * WPILIB sends:    GRB|G RB|GR B|GRB|GRB|...
         * output:          GRB W|GR BW|G RBW|GRBW|...
         */


        switch (index % 3) {
            case 0:
                setRGB(offsetindex, R, G, B); // R=R G=G B=B
                setRGB(offsetindex+1, nextRGBW[1], W, nextRGBW[0]); // R=+G G=W B=+R
                break;
            case 1:
                setRGB(offsetindex, G, prevRGBW[3], R); // R=G G>-W B=R
                setRGB(offsetindex+1, W, B, nextRGBW[1]); // R=W G=B B=+G FOR B, REMEMBER THAT THE INDEX is 1
                break;
            case 2:
                setRGB(offsetindex, prevRGBW[3], prevRGBW[2], G); // R=-W G=-B B=G
                setRGB(offsetindex+1, B, R, W); // R=B G=R B=W
                break;
            default:break;
        }
    }

    public void setRGB(int index, int R, int G, int B) { // CLASS FILE | encodes in BGR, for some reason???
        LedBuffer[index*4] = B;
        LedBuffer[(index*4)+1] = G;
        LedBuffer[(index*4)+2] = R;
        LedBuffer[(index*4)+3] = 0; // I think its a seperator, maybe for the W, but never used and useless.
    }
    public Color getLED(int index) { // CLASS FILE | what is this lol you get led data (except the 0) in RGB format
        return new Color(
            (int) ((LedBuffer[index * 4 + 2] & 0xFF) / 255.0),
            (int) ((LedBuffer[index * 4 + 1] & 0xFF) / 255.0),
            (int) ((LedBuffer[index * 4] & 0xFF) / 255.0));
            // LedBuffer[index * 4 + 2],
            // LedBuffer[index * 4 + 1],
            // LedBuffer[index * 4]);
    }

    public void render(Graphics g) { // VIRTUAL LED | This replaces the actual LEDs for a virtual one. should work perfectly :D
        for (int i=0;i<LedBuffer.length/4;i+=1) { // This converts the LedBuffer to the rendering data, which is GRB. Done inside the CLASS
            RenderingLedData[i*3 + 1] = LedBuffer[i*4 + 2]; // G 
            RenderingLedData[i*3 + 0] = LedBuffer[i*4 + 1]; // R
            RenderingLedData[i*3 + 2] = LedBuffer[i*4 + 0]; // B
        }
        //System.out.println(Arrays.toString(LED));

        for (int i=0;i<RenderingLedData.length/4;i+=1) { // The actual rendering. This is the LEDS.
            int W = RenderingLedData[(i*4)+3]/2;
            Color RGBW = new Color(
                min(abs( RenderingLedData[(i*4)+1]) + W, 255),
                min(abs( RenderingLedData[(i*4)]) + W, 255),
                min(abs( RenderingLedData[(i*4)+2]) + W, 255));
            g.setColor(RGBW);
            
            g.fillRect(i*20,0,20,20);
        }
    }
}
