package at.itb13.imaging.entities;

/**
 * Created by Mathias on 09.11.2015.
 */
public class Coordinate {
    private int _x;
    private int _y;

    private boolean _isInTolerance;

    public boolean isInTolerance() {
        return _isInTolerance;
    }

    public void setIsInTolerance(boolean isInTolerance) {
        _isInTolerance = isInTolerance;
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) {
        _x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) {
        _y = y;
    }

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        _x = x;
        _y = y;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate coordinate = (Coordinate) obj;
            if (coordinate.getX() == this.getX() && coordinate.getY() == this.getY()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("X: %s; Y: %s | In tolerance: %b", _x, _y, _isInTolerance);
    }
}
