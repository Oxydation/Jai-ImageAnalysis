package at.itb13.imaging.filter;

import Catalano.Imaging.Filters.Opening;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.filter.DataTransformationFilter;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.security.InvalidParameterException;

/**
 * Created by Mathias on 16.11.2015.
 */
public class BallFilter extends DataTransformationFilter<PicturePack> {
    private int _maskSize = 5;

    private static int _counter = 0;

    public BallFilter(at.itb13.pipesandfilter.interfaces.Readable<PicturePack> input, Writeable<PicturePack> output) throws InvalidParameterException {
        super(input, output);
    }

    public BallFilter(Readable<PicturePack> input) throws InvalidParameterException {
        super(input);
    }

    public BallFilter(Writeable<PicturePack> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(PicturePack entity) {
        Opening opening = new Opening(_maskSize);
        opening.applyInPlace(entity.getEdited());

        // Save image to file
        entity.getEdited().saveAsPNG(String.format("ballfilter_result_%s.png", _counter++));
        // ImageProcessor.showImage(entity.getEdited(), "Ball");
    }

    public int getMaskSize() {
        return _maskSize;
    }

    public void setMaskSize(int maskSize) {
        _maskSize = maskSize;
    }
}
