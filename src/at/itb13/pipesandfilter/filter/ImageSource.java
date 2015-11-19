package at.itb13.pipesandfilter.filter;


import Catalano.Imaging.FastBitmap;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.io.StreamCorruptedException;

/**
 * Created by Mathias on 30.10.2015.
 */
public class ImageSource implements Readable<PicturePack>, Runnable {
    private String _file;
    private Writeable<PicturePack> _writeable;
    private boolean _isFlatRate;
    private int _limit = 1; // 1 ... only one time, 0 .. unlimited, >1 as given

    public ImageSource(String file, int limit) {
        _file = file;
    }

    public ImageSource(String file, Writeable<PicturePack> output) {
        _writeable = output;
        _file = file;
    }

    public ImageSource(String file, Writeable<PicturePack> output, int limit) {
        _writeable = output;
        _file = file;
        _limit = limit;
    }

    @Override
    public PicturePack read() throws StreamCorruptedException {
        FastBitmap fb = new FastBitmap(_file);
        return new PicturePack(fb);
    }

    @Override
    public void run() {
        if (_writeable != null) {
            PicturePack input = null;
            try {
                if (isFlatRate()) {
                    int counter = 0;
                    while ((input = read()) != null && counter < getLimit()) {
                        System.out.println("Source: read input and write output");
                        _writeable.write(input);
                        counter++;
                    }
                } else {
                    _writeable.write(read());
                }
                _writeable.write(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getLimit() {
        return _limit;
    }

    public void setLimit(int limit) {
        _limit = limit;
    }

    public String getFile() {
        return _file;
    }

    public void setFile(String file) {
        _file = file;
    }

    public Writeable<PicturePack> getWriteable() {
        return _writeable;
    }

    public void setWriteable(Writeable<PicturePack> writeable) {
        _writeable = writeable;
    }

    public boolean isFlatRate() {
        return _isFlatRate;
    }

    public void setIsFlatRate(boolean isFlatRate) {
        _isFlatRate = isFlatRate;
    }

}
