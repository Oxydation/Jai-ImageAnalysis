package at.itb13.imaging.filter;

import at.itb13.imaging.ImageProcessor;
import at.itb13.imaging.entities.Coordinate;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;
import sun.awt.image.ImageWatched;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by Mathias on 16.11.2015.
 */
public class DataSink implements Writeable<LinkedList<Coordinate>>, Runnable {
    public String _targetFile;
    public Readable<LinkedList<Coordinate>> _readable;
    private static int _counter;
    private int _limit = 1; // 0... unlimited, 1 .. only one, ... etc

    @Override
    public void run() {
        try {
            LinkedList<Coordinate> input = null;
            while((input = _readable.read()) != null && input.size() > 0 && (_counter < _limit || _limit == 0)){
                write(input);
            }
            System.out.println(String.format("Duration: %fs", (System.currentTimeMillis() - ImageProcessor.getMillis())/1000.0));
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    public Writer _writer;

    public DataSink(String targetFile, Readable<LinkedList<Coordinate>> readable) {
        _targetFile = targetFile;
        _readable = readable;
    }

    public DataSink(String targetFile) throws IOException {
        _targetFile = targetFile;
    }

    public DataSink(String targetFile, Readable<LinkedList<Coordinate>> readable, int limit) {
        _targetFile = targetFile;
        _readable = readable;
        _limit = limit;
    }

    @Override
    public void write(LinkedList<Coordinate> value) throws StreamCorruptedException {

        try {
            String fileName = String.format("%s%s", _counter++, _targetFile);
            _writer = new FileWriter(new File(fileName));
           // System.out.println("Writing quality check to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (value != null && _writer != null) {
            try {
                for (Coordinate coordinate : value) {
                    _writer.write(coordinate.toString() + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    _writer.flush();
                    _writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public int getLimit() {
        return _limit;
    }

    public void setLimit(int limit) {
        _limit = limit;
    }
}
