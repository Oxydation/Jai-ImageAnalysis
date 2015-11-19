package at.itb13.imaging;

import Catalano.Imaging.FastBitmap;
import at.itb13.imaging.entities.Coordinate;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.imaging.enumerations.Mode;
import at.itb13.imaging.filter.*;
import at.itb13.pipesandfilter.filter.ImageSource;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;
import at.itb13.pipesandfilter.pipes.BufferedSyncPipe;
import at.itb13.pipesandfilter.pipes.Pipe;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Mathias on 09.11.2015.
 */
public class ImageProcessor {

    private String _sourceFile;
    private static long  _millis;

    private int _limit = 1;

    public ImageProcessor() {

    }

    public ImageProcessor(String sourceFile) {
        _sourceFile = sourceFile;
    }

    public void processImage(Mode mode) {

        _millis = System.currentTimeMillis();

        LinkedList<Coordinate> nominalCoordiantes = new LinkedList<>();
        nominalCoordiantes.add(new Coordinate(10, 80));
        nominalCoordiantes.add(new Coordinate(75, 80));
        nominalCoordiantes.add(new Coordinate(135, 80));
        nominalCoordiantes.add(new Coordinate(200, 80));
        nominalCoordiantes.add(new Coordinate(265, 80));
        nominalCoordiantes.add(new Coordinate(330, 80));
        nominalCoordiantes.add(new Coordinate(395, 80));
        int tolerance = 5;

        switch (mode) {
            case PULL: {

                // Pull from source to target
                ImageSource is = new ImageSource(_sourceFile, _limit);

                Pipe<PicturePack> pipe = new Pipe<>(is);
                ROIFilter roiFilter = new ROIFilter((Readable<PicturePack>) pipe);
                Pipe<PicturePack> pipe1 = new Pipe<>((Readable<PicturePack>) roiFilter);
                ThresholdFilter thresholdFilter = new ThresholdFilter((Readable<PicturePack>) pipe1);

                Pipe<PicturePack> pipe2 = new Pipe<>((Readable<PicturePack>) thresholdFilter);
                MedianFilter medianFilter = new MedianFilter((Readable<PicturePack>) pipe2);
                Pipe<PicturePack> pipe3 = new Pipe<>((Readable<PicturePack>) medianFilter);

                BallFilter ballFilter = new BallFilter((Readable<PicturePack>) pipe3);
                Pipe<PicturePack> pipe4 = new Pipe<>((Readable<PicturePack>) ballFilter);
                CalcCentroidsFilter calcCentroidsFilter = new CalcCentroidsFilter(pipe4);
                Pipe<LinkedList<Coordinate>> pipe5 = new Pipe<>((Readable<LinkedList<Coordinate>>) calcCentroidsFilter);

                QSCentroidsFilter qsCentroidsFilter = new QSCentroidsFilter((Readable<LinkedList<Coordinate>>) pipe5, nominalCoordiantes);
                qsCentroidsFilter.setxTolerance(tolerance);
                qsCentroidsFilter.setyTolerance(tolerance);

                Pipe<LinkedList<Coordinate>> pipe6 = new Pipe<>((Readable<LinkedList<Coordinate>>) qsCentroidsFilter);
                DataSink dataSink = new DataSink("output.txt", pipe6, _limit);

                Thread thread = new Thread(dataSink);
                thread.start();
                break;
            }

            case PUSH: {
                // Push from source to target
                DataSink dataSink = null;
                try {
                    dataSink = new DataSink("output.txt");
                    dataSink.setLimit(_limit);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
                ImageSource is = new ImageSource(_sourceFile, pipe,_limit);

                Thread thread = new Thread(is);
                thread.run();
                break;
            }

            case THREADED: {
                int bufferSize = 100;
                BufferedSyncPipe<PicturePack> pipe = new BufferedSyncPipe<>(bufferSize);
                ImageSource imageSource = new ImageSource(_sourceFile, pipe, _limit);

                BufferedSyncPipe<PicturePack> pipe1 = new BufferedSyncPipe<>(bufferSize);

                ROIFilter roiFilter = new ROIFilter(pipe, pipe1);

                BufferedSyncPipe<PicturePack> pipe2 = new BufferedSyncPipe<>(bufferSize);

                ThresholdFilter thresholdFilter = new ThresholdFilter(pipe1, pipe2);

                BufferedSyncPipe<PicturePack> pipe3 = new BufferedSyncPipe<>(bufferSize);

                MedianFilter medianFilter = new MedianFilter(pipe2, pipe3);

                BufferedSyncPipe<PicturePack> pipe4 = new BufferedSyncPipe<>(bufferSize);
                BufferedSyncPipe<LinkedList<Coordinate>> pipe5 = new BufferedSyncPipe<>(bufferSize);

                BallFilter ballFilter = new BallFilter(pipe3, pipe4);
                CalcCentroidsFilter calcCentroidsFilter = new CalcCentroidsFilter(pipe4, pipe5);

                BufferedSyncPipe<LinkedList<Coordinate>> pipe6 = new BufferedSyncPipe<>(bufferSize);

                QSCentroidsFilter qsCentroidsFilter = new QSCentroidsFilter(pipe5, pipe6, nominalCoordiantes);
                qsCentroidsFilter.setxTolerance(tolerance);
                qsCentroidsFilter.setyTolerance(tolerance);

                DataSink dataSink = new DataSink("output.txt", pipe6, 0);

                // Create and start threads
                Thread threadDataSink = new Thread(dataSink);
                Thread threadDataSource = new Thread(imageSource);
                Thread threadROI = new Thread(roiFilter);
                Thread threadThreshold = new Thread(thresholdFilter);
                Thread threadMedian = new Thread(medianFilter);
                Thread threadBall = new Thread(ballFilter);
                Thread threadCC = new Thread(calcCentroidsFilter);
                Thread threadQS = new Thread(qsCentroidsFilter);

                threadDataSource.start();
                threadROI.start();
                threadThreshold.start();
                threadMedian.start();
                threadBall.start();
                threadCC.start();
                threadQS.start();
                threadDataSink.start();
                break;
            }
        }
    }

    public String getSourceFile() {
        return _sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        _sourceFile = sourceFile;
    }

    public static long getMillis() {
        return _millis;
    }

    public int getLimit() {
        return _limit;
    }

    public void setLimit(int limit) {
        _limit = limit;
    }

    public static void setMillis(long millis) {
        _millis = millis;
    }

    public static void showImage(FastBitmap fastBitmap) {
        JOptionPane.showMessageDialog(null, fastBitmap.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showImage(FastBitmap fastBitmap, String title) {
        JOptionPane.showMessageDialog(null, fastBitmap.toIcon(), title, JOptionPane.PLAIN_MESSAGE);
    }
}
