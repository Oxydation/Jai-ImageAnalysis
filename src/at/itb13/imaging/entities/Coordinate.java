package at.itb13.imaging.entities;

/**
 * Created by Mathias on 09.11.2015.
 */
public class Coordinate {
    private int _x;
    private int _y;

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
}
