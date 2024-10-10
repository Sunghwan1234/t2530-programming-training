package org.frcteam2530;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.io.FileOutputStream;
import java.io.IOException;

public class VisionMain {

    static {
        // Loads the OpenCV library so that we can call code written in its native language.
        OpenCV.loadLocally();
    }

    public static void main(String[] args) throws IOException {
        // Take a photo with the laptop webcam or other camera
        Mat image = takePicture();

        // Detect faces in the image
        MatOfRect facesDetected = detectFaces(image);

        // For every face detected in the image, draw a rectangle around it
        for(Rect face : facesDetected.toArray()) {
            // The rectangle method requires an image, 2 points (the top left and bottom right corners of the face), a color, and a thickness
            Imgproc.rectangle(image, face.tl(), face.br(), new Scalar(255, 255, 255), 3);
        }

        // This writes the image to a file so that we can see it
        writeImageToFile(image, "image.png");
    }

    /**
     * Takes a photo with the first available camera device.
     */
    public static Mat takePicture() {
        VideoCapture camera = new VideoCapture(0);

        if(!camera.isOpened()) throw new RuntimeException("Failed to open video capture device!");

        Mat mat = new Mat();
        camera.read(mat);

        return mat;
    }

    /**
     * Detect all faces in an image.
     *
     * @param image The image to search for faces in.
     * @return A {@link MatOfRect} containing the faces as bounding boxes.
     */
    public static MatOfRect detectFaces(Mat image) {
        MatOfRect facesDetected = new MatOfRect();
        final String filename = VisionMain.class.getClassLoader().getResource("haarcascade_frontalface_alt.xml").getFile();

        CascadeClassifier cascadeClassifier = new CascadeClassifier();

        final int minFaceSize = Math.round(image.rows() * 0.1f);

        cascadeClassifier.load(filename);
        cascadeClassifier.detectMultiScale(image,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );

        return facesDetected;
    }

    /**
     * Writes the provided image to a file on the disk.
     *
     * @param image The image to write
     * @param filePath A path to the file
     * @throws IOException If errors occur with writing to disk
     */
    public static void writeImageToFile(Mat image, String filePath) throws IOException {
        MatOfByte bytes = new MatOfByte();
        Imgcodecs.imencode(".png", image, bytes);

        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(bytes.toArray());
        fileOutputStream.close();
    }
}
