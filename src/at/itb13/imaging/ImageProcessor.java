package at.itb13.imaging;

import Catalano.Imaging.FastBitmap;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.imaging.enumerations.Mode;
import at.itb13.imaging.filter.*;
import at.itb13.pipesandfilter.filter.ImageSource;
import at.itb13.pipesandfilter.interfaces.Writeable;
import at.itb13.pipesandfilter.pipes.Pipe;

import javax.swing.*;

/**
 * Created by Mathias on 09.11.2015.
 */
public class ImageProcessor {

    private String _sourceFile;

    public ImageProcessor() {

    }

    public ImageProcessor(String sourceFile) {
        _sourceFile = sourceFile;
    }

    public void processImage(Mode mode) {
        switch (mode) {
            case PULL:
                // Pull from source to target
                break;

            case PUSH:
                // Push from source to target

                DataSink dataSink = new DataSink("output.png");
                Pipe<PicturePack> pipe4 = new Pipe<>( dataSink);
                BallFilter ballFilter = new BallFilter((Writeable<PicturePack>) pipe4);
                Pipe<PicturePack> pipe3 = new Pipe<>((Writeable<PicturePack>) ballFilter);
                MedianFilter medianFilter = new MedianFilter((Writeable<PicturePack>) pipe3);
                Pipe<PicturePack> pipe2 = new Pipe<>((Writeable<PicturePack>) medianFilter);
                ThresholdFilter thresholdFilter = new ThresholdFilter((Writeable<PicturePack>) pipe2);
                Pipe<PicturePack> pipe1 = new Pipe<>((Writeable<PicturePack>) thresholdFilter);
                ROIFilter roiFilter = new ROIFilter((Writeable<PicturePack>) pipe1);
                Pipe<PicturePack> pipe = new Pipe<>((Writeable<PicturePack>) roiFilter);
                ImageSource is = new ImageSource(_sourceFile, pipe);

                Thread thread = new Thread(is);
                thread.run();

                break;
        }
    }

    public String getSourceFile() {
        return _sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        _sourceFile = sourceFile;
    }

    public static void showImage(FastBitmap fastBitmap) {
        JOptionPane.showMessageDialog(null, fastBitmap.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showImage(FastBitmap fastBitmap, String title) {
        JOptionPane.showMessageDialog(null, fastBitmap.toIcon(), title, JOptionPane.PLAIN_MESSAGE);
    }
}
