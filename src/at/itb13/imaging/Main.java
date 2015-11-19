package at.itb13.imaging;

import at.itb13.imaging.enumerations.Mode;
import at.itb13.imaging.filter.ROIFilter;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        ImageProcessor imageProcessor = new ImageProcessor("loetstellen.jpg");
        imageProcessor.setLimit(50);
        imageProcessor.processImage(Mode.PULL);
    }
}