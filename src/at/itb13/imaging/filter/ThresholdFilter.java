package at.itb13.imaging.filter;

import Catalano.Imaging.Filters.Invert;
import Catalano.Imaging.Filters.Merge;
import Catalano.Imaging.Filters.Threshold;
import at.itb13.imaging.ImageProcessor;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.filter.DataTransformationFilter;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.security.InvalidParameterException;

/**
 * Created by Mathias on 16.11.2015.
 */
public class ThresholdFilter extends DataTransformationFilter<PicturePack> {
    public ThresholdFilter(at.itb13.pipesandfilter.interfaces.Readable<PicturePack> input, Writeable<PicturePack> output) throws InvalidParameterException {
        super(input, output);
    }

    public ThresholdFilter(Readable<PicturePack> input) throws InvalidParameterException {
        super(input);
    }

    public ThresholdFilter(Writeable<PicturePack> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(PicturePack entity) {
        Threshold threshold = new Threshold(50, true);
        entity.getEdited().toGrayscale();
        threshold.applyInPlace(entity.getEdited());

        Invert invert = new Invert();
        invert.applyInPlace(entity.getEdited());

        //ImageProcessor.showImage(entity.getEdited(), "Threshold");

    }
}
