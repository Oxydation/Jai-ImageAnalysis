package at.itb13.jai;

import at.itb13.jai.enumerations.Mode;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        ImageProcessor imageProcessor = new ImageProcessor("loetstellen.jpg");
        imageProcessor.processImage(Mode.PUSH);


        // Create a frame for display.
        JFrame frame = new JFrame();
        frame.setTitle("DisplayJAI: lena512.jpg");

        // Get the JFrame’ ContentPane.
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Add to the JFrame’ ContentPane an instance of JScrollPane
        // containing the DisplayJAI instance.
        contentPane.add(new JScrollPane(dj), BorderLayout.CENTER);

        // Add a text label with the image information.
        contentPane.add(new JLabel(imageInfo), BorderLayout.SOUTH);

        // Set the closing operation so the application is finished.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // adjust the frame size.
        frame.setVisible(true); // show the frame.
    }
}
