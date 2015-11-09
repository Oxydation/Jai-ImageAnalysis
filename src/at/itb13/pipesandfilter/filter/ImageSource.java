package at.itb13.pipesandfilter.filter;


import at.itb13.pipesandfilter.interfaces.Writeable;
import at.itb13.pipesandfilter.interfaces.Readable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.File;
import java.io.Reader;
import java.io.StreamCorruptedException;

/**
 * Created by Mathias on 30.10.2015.
 */
public class ImageSource<T> implements Readable<T>, Runnable {
    private File _file;
    private Writeable<T> _writeable;


    public ImageSource(File file) {
        _file = file;
    }

    public ImageSource(File file, Writeable<T> output) {
        _writeable = output;
        _file = file;
    }

    @Override
    public T read() throws StreamCorruptedException {
        PlanarImage image = JAI.create("fileload", new File("loetstellen.jpg"));
        return (T) image;
    }

    @Override
    public void run() {
        if (_writeable != null) {
            T input = null;
            try {
                while ((input = read()) != null) {
                    _writeable.write(input);
                }
                _writeable.write(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
