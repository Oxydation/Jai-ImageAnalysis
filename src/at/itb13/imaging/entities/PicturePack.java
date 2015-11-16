package at.itb13.imaging.entities;

import Catalano.Imaging.FastBitmap;

/**
 * Created by Mathias on 09.11.2015.
 */
public class PicturePack {

    private Coordinate _coordinate;

    private FastBitmap _original;
    private FastBitmap _edited;


    public PicturePack() {
    }

    public PicturePack(FastBitmap original) {
        _original = original;
    }

    public FastBitmap getOriginal() {
        return _original;
    }

    public void setOriginal(FastBitmap original) {
        _original = original;
    }

    public FastBitmap getEdited() {
        return _edited;
    }

    public void setEdited(FastBitmap edited) {
        _edited = edited;
    }

    public Coordinate getCoordinate() {
        return _coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        _coordinate = coordinate;
    }
}
