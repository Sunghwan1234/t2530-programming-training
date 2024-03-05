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
    int NumberOfLeds = 40;

    int[] LedBuffer = new int[NumberOfLeds*4*4/3];

    int[] RenderingLedData = new int[NumberOfLeds*4];

    int[] LedRGBWData = new int[NumberOfLeds*4];

    final int indexR = 1;
    final int indexG = 0;
    final int indexB = 2;
    final int indexW = 3;

    double rainbow = 0;

    public LEDs() {}

    public static void print(String s) {System.out.println(s);}
    public static void print(int s) {System.out.println(s);}

    public void run() {
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
        // setRGB(3, 255, 0, 0);

        // setRGBW(0, 1, 255, 2, 3);
        // setRGBW(1, 1, 255, 2, 3);
        // setRGBW(2, 1, 255, 2, 3);
        // setRGBW(3, 1, 255, 2, 3);




        // print(Arrays.toString(LedRGBWData));
        // pushData();
        // print(Arrays.toString(LedBuffer));
    }
    public void periodic() {
        rainbow += 0.05;
        for (int i=0;i<NumberOfLeds-1;i++) {
            double j = i*(PI-0.05);
            setDataRGBW(i,
            (int) (sin(rainbow+j)*255),
            (int) (sin(rainbow+2+j)*255),
            (int) (sin(rainbow+4+j)*255),0);}

        //print(Arrays.toString(LedRGBWData));
        //print((int) (sin(rainbow)*255));
        pushData();
    }

    public void setRGB(int index, int R, int G, int B) { // CLASS FILE
        LedBuffer[index*4] = B;
        LedBuffer[(index*4)+1] = G;
        LedBuffer[(index*4)+2] = R;
        LedBuffer[(index*4)+3] = 0;
    }
    public Color getLED(int index) { // CLASS FILE
        return new Color(
            (int) ((LedBuffer[index * 4 + 2] & 0xFF) / 255.0),
            (int) ((LedBuffer[index * 4 + 1] & 0xFF) / 255.0),
            (int) ((LedBuffer[index * 4] & 0xFF) / 255.0));
            // LedBuffer[index * 4 + 2],
            // LedBuffer[index * 4 + 1],
            // LedBuffer[index * 4]);
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
    public void pushData() {
        for (int i=0; i<NumberOfLeds-1;i++) {
            setRGBW(i, LedRGBWData[i * 4],
            LedRGBWData[i * 4 + 1],
            LedRGBWData[i * 4 + 2],
            LedRGBWData[i * 4 + 3]);
        }
    }

    public void setRGBW(int index, int R, int G, int B, int W) {
        int offsetindex = index + (int) Math.floor(index/3); // Index offset ONLY FOR ENCODING INDO GRBGRBGRBGRB

        int[] prevRGBW = getDataRGBW(index);
        if (index>0) {prevRGBW = getDataRGBW(index-1);}

        int[] nextRGBW = getDataRGBW(index+1);

        //print(prevRGB.toString());
        /** HOW TO READ R G B comments
         * I will use C and V as example
         * C=C: Use C to output as C. basically just normal setRGB
         * C=V: Use V to output as C. for example G=W (W is the input value)
         * C=+V: Use the next index's V to output
         * C=-V: Use the previous index's V to output.
         * 
         * REMEMBER: you use the INDEX, not the OFFSETINDEX
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

    public void render(Graphics g) { // This works :D
        for (int i=0;i<LedBuffer.length/4;i+=1) {
            RenderingLedData[i*3 + 1] = LedBuffer[i*4 + 2];
            RenderingLedData[i*3 + 0] = LedBuffer[i*4 + 1];
            RenderingLedData[i*3 + 2] = LedBuffer[i*4 + 0];
        }
        //System.out.println(Arrays.toString(LED));

        for (int i=0;i<RenderingLedData.length/4;i+=1) {
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
