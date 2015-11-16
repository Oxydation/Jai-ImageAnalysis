package at.itb13.imaging;

import Catalano.Imaging.FastBitmap;
import at.itb13.imaging.entities.Coordinate;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.imaging.enumerations.Mode;
import at.itb13.imaging.filter.*;
import at.itb13.pipesandfilter.filter.ImageSource;
import at.itb13.pipesandfilter.interfaces.Writeable;
import at.itb13.pipesandfilter.pipes.Pipe;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;

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

                DataSink dataSink = null;
                try {
                    dataSink = new DataSink("output.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LinkedList<Coordinate> nominalCoordiantes = new LinkedList<>();
                nominalCoordiantes.add(new Coordinate(10, 80));
                nominalCoordiantes.add(new Coordinate(75, 80));
                nominalCoordiantes.add(new Coordinate(135, 80));
                nominalCoordiantes.add(new Coordinate(200, 80));
                nominalCoordiantes.add(new Coordinate(265, 80));
                nominalCoordiantes.add(new Coordinate(330, 80));
                nominalCoordiantes.add(new Coordinate(395, 80));
                int tolerance = 5;

                Pipe<LinkedList<Coordinate>> pipe6 = new Pipe<>(dataSink);
                QSCentroidsFilter qsCentroidsFilter = new QSCentroidsFilter((Writeable<LinkedList<Coordinate>>) pipe6, nominalCoordiantes);
                qsCentroidsFilter.setxTolerance(tolerance);
                qsCentroidsFilter.setyTolerance(tolerance);

                Pipe<LinkedList<Coordinate>> pipe5 = new Pipe<>((Writeable<LinkedList<Coordinate>>) qsCentroidsFilter);
                CalcCentroidsFilter calcCentroidsFilter = new CalcCentroidsFilter(pipe5);
                Pipe<PicturePack> pipe4 = new Pipe<>((Writeable<PicturePack>) calcCentroidsFilter);
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
