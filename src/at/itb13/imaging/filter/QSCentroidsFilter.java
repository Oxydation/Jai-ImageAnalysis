package at.itb13.imaging.filter;

import at.itb13.imaging.entities.Coordinate;
import at.itb13.pipesandfilter.filter.DataTransformationFilter;
import at.itb13.pipesandfilter.interfaces.Readable;
import at.itb13.pipesandfilter.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mathias on 16.11.2015.
 */
public class QSCentroidsFilter extends DataTransformationFilter<LinkedList<Coordinate>> {
    private LinkedList<Coordinate> _nominalCoordiantes;
    private int _xTolerance = 2;
    private int _yTolerance = 2;

    public QSCentroidsFilter(at.itb13.pipesandfilter.interfaces.Readable<LinkedList<Coordinate>> input, Writeable<LinkedList<Coordinate>> output, LinkedList<Coordinate> nominalCoordiantes) throws InvalidParameterException {
        super(input, output);
        _nominalCoordiantes = nominalCoordiantes;
    }

    public QSCentroidsFilter(Readable<LinkedList<Coordinate>> input, LinkedList<Coordinate> nominalCoordiantes) throws InvalidParameterException {
        super(input);
        _nominalCoordiantes = nominalCoordiantes;
    }

    public QSCentroidsFilter(Writeable<LinkedList<Coordinate>> output, LinkedList<Coordinate> nominalCoordiantes) throws InvalidParameterException {
        super(output);
        _nominalCoordiantes = nominalCoordiantes;
    }

    @Override
    protected void process(LinkedList<Coordinate> entity) {
        for (Coordinate coordinate : entity) {
            for (Coordinate coordinate1 : _nominalCoordiantes) {
                if (isInTolerance(coordinate1, coordinate)) {
                    coordinate.setIsInTolerance(true);
                    break;
                }
            }
        }
    }

    private boolean isInTolerance(Coordinate nominal, Coordinate value) {
        if (Math.abs(value.getX() - nominal.getX()) <= _xTolerance && Math.abs(value.getY() - nominal.getY()) <= _yTolerance) {
            return true;
        }
        return false;
    }

    public LinkedList<Coordinate> getNominalCoordiantes() {
        return _nominalCoordiantes;
    }

    public void setNominalCoordiantes(LinkedList<Coordinate> nominalCoordiantes) {
        _nominalCoordiantes = nominalCoordiantes;
    }

    public int getxTolerance() {
        return _xTolerance;
    }

    public void setxTolerance(int xTolerance) {
        _xTolerance = xTolerance;
    }

    public int getyTolerance() {
        return _yTolerance;
    }

    public void setyTolerance(int yTolerance) {
        _yTolerance = yTolerance;
    }
}
