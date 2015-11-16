package at.itb13.imaging.filter;

import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.io.StreamCorruptedException;

/**
 * Created by Mathias on 16.11.2015.
 */
public class DataSink implements Writeable<PicturePack> {
    public String _targetFile;

    public DataSink(String targetFile) {
        _targetFile = targetFile;
    }

    @Override
    public void write(PicturePack value) throws StreamCorruptedException {
        if(value != null){
            value.getEdited().saveAsPNG(_targetFile);
        }
    }
}
