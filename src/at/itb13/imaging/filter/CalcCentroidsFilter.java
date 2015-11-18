/* this filter expects the bonding discs to be completely white: pixel value of 255 on a scale of 0..255
 * all other pixels in the image are expected to have a pixel value < 255
 * use this filter adapting eventually the package name 
 */
package at.itb13.imaging.filter;

import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import at.itb13.imaging.ImageProcessor;
import at.itb13.imaging.entities.Coordinate;
import at.itb13.imaging.entities.PicturePack;
import at.itb13.pipesandfilter.filter.DataEnrichmentFilter;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

public class CalcCentroidsFilter extends DataEnrichmentFilter<PicturePack, LinkedList<Coordinate>> {

    public CalcCentroidsFilter(Readable<PicturePack> input, Writeable<LinkedList<Coordinate>> output) throws InvalidParameterException {
        super(input, output);
    }

    public CalcCentroidsFilter(Readable<PicturePack> input) throws InvalidParameterException {
        super(input);
    }

    public CalcCentroidsFilter(Writeable<LinkedList<Coordinate>> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected boolean fillEntity(PicturePack nextVal, LinkedList<Coordinate> entity) {
        BlobDetection blobDetection = new BlobDetection();
        List<Blob> blobList = blobDetection.ProcessImage(nextVal.getEdited());

        for (Blob blob : blobList) {
            entity.add(new Coordinate(blob.getCenter().y + nextVal.getCoordinate().getX(), blob.getCenter().x + + nextVal.getCoordinate().getY()));
        }
        return true;
    }


    @Override
    protected LinkedList<Coordinate> getNewEntityObject() {
        return new LinkedList<>();
    }

}
