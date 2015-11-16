package at.itb13.imaging.filter;

import at.itb13.imaging.entities.Coordinate;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mathias on 16.11.2015.
 */
public class DataSink implements Writeable<LinkedList<Coordinate>> {
    public String _targetFile;
    public Writer _writer;

    public DataSink(String targetFile) throws IOException {
        _targetFile = targetFile;
        _writer = new FileWriter(new File(targetFile));
    }

    @Override
    public void write(LinkedList<Coordinate> value) throws StreamCorruptedException {
        if (value != null && _writer != null) {
            try {
                for (Coordinate coordinate : value) {
                    _writer.write(coordinate.toString() + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
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
