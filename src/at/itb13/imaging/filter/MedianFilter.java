package at.itb13.imaging.filter;

import Catalano.Imaging.Filters.Median;
import at.itb13.imaging.ImageProcessor;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.filter.DataTransformationFilter;
import at.itb13.pipesandfilter.interfaces.*;
import at.itb13.pipesandfilter.interfaces.Readable;

import java.security.InvalidParameterException;

/**
 * Created by Mathias on 16.11.2015.
 */
public class MedianFilter extends DataTransformationFilter<PicturePack>{

    private int _medianRadius = 4;

    public MedianFilter(at.itb13.pipesandfilter.interfaces.Readable<PicturePack> input, Writeable<PicturePack> output) throws InvalidParameterException {
        super(input, output);
    }

    public MedianFilter(Readable<PicturePack> input) throws InvalidParameterException {
        super(input);
    }

    public MedianFilter(Writeable<PicturePack> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(PicturePack entity) {
        Median median = new Median(_medianRadius);
        median.applyInPlace(entity.getEdited());

        // ImageProcessor.showImage(entity.getEdited(), "Median");
    }

    public int getMedianRadius() {
        return _medianRadius;
    }

    public void setMedianRadius(int medianRadius) {
        _medianRadius = medianRadius;
    }
}
