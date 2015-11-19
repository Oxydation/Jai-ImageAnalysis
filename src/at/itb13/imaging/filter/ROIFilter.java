package at.itb13.imaging.filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Merge;
import at.itb13.imaging.ImageProcessor;
import at.itb13.imaging.entities.Coordinate;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.filter.DataTransformationFilter;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.security.InvalidParameterException;

/**
 * Created by Mathias on 16.11.2015.
 */
public class ROIFilter extends DataTransformationFilter<PicturePack> {
    public ROIFilter(at.itb13.pipesandfilter.interfaces.Readable<PicturePack> input, Writeable<PicturePack> output) throws InvalidParameterException {
        super(input, output);
    }

    public ROIFilter(Readable<PicturePack> input) throws InvalidParameterException {
        super(input);
    }

    public ROIFilter(Writeable<PicturePack> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(PicturePack entity) {
        FastBitmap roi = new FastBitmap(entity.getOriginal().toBufferedImage().getSubimage(0, 60, entity.getOriginal().getWidth(), entity.getOriginal().getHeight() / 4));

        entity.setEdited(roi);
        entity.setCoordinate(new Coordinate(0, 60));
        //ImageProcessor.showImage(entity.getEdited(), "ROI");
    }
}
