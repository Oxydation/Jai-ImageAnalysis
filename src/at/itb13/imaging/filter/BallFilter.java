package at.itb13.imaging.filter;

import Catalano.Fuzzy.CentroidDefuzzifier;
import Catalano.Imaging.Filters.Closing;
import Catalano.Imaging.Filters.Opening;
import at.itb13.imaging.ImageProcessor;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.filter.DataTransformationFilter;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.Optional;

/**
 * Created by Mathias on 16.11.2015.
 */
public class BallFilter extends DataTransformationFilter<PicturePack> {
    private int _maskSize = 5;

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

        ImageProcessor.showImage(entity.getEdited(), "Ball");
    }

    public int getMaskSize() {
        return _maskSize;
    }

    public void setMaskSize(int maskSize) {
        _maskSize = maskSize;
    }
}
