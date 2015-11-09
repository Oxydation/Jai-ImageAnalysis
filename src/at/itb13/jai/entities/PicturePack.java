package at.itb13.jai.entities;

import Catalano.Imaging.FastBitmap;

/**
 * Created by Mathias on 09.11.2015.
 */
public class PicturePack {
    // Original
    // Edited
    // Metadata (z.b. 0/0 Pixel)

    private FastBitmap _original;
    private FastBitmap _edited;

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

    public PicturePack() {
    }

    public PicturePack(FastBitmap original) {
        _original = original;
    }
}
