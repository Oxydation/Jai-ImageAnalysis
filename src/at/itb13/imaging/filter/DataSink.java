package at.itb13.imaging.filter;

import at.itb13.imaging.entities.Coordinate;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by Mathias on 16.11.2015.
 */
public class DataSink implements Writeable<LinkedList<Coordinate>>, Runnable {
    public String _targetFile;
    public Readable<LinkedList<Coordinate>> _readable;
    private static int _counter;

    @Override
    public void run() {
        try {
            write(_readable.read());
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

    @Override
    public void write(LinkedList<Coordinate> value) throws StreamCorruptedException {

        try {
            String fileName = String.format("%s%s", _counter++, _targetFile);
            _writer = new FileWriter(new File(fileName));
            System.out.println("Writing quality check to file: " + fileName);
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
}
