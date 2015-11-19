package at.itb13.imaging;

import at.itb13.imaging.enumerations.Mode;
import at.itb13.imaging.filter.ROIFilter;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Mode mode = Mode.THREADED;
        ImageProcessor imageProcessor = new ImageProcessor("loetstellen.jpg");
        imageProcessor.setLimit(50);
        imageProcessor.processImage(mode);

        if(mode == Mode.PUSH){
            System.out.println(String.format("Duration: %fs", (System.currentTimeMillis() - ImageProcessor.getMillis())/1000.0));
        }
    }
}