/**
 * Immutable Point class, has x and y values.
 */
public class Point {

    /**
     * x and y values.
     */
    private int x, y;

    /**
     * Constructor.
     *
     * @param x x-value of point.
     * @param y y-value of point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x value of the point.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y value of the point.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks whether the object are equal or not, by x and y values.
     *
     * @param o Oject to check if equal to this.
     * @return true if equals, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    /**
     * @return Automatic Intellij hashcode.
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    /**
     * @return String representation of the point, "point={x=this.x, y=this.y}"
     */
    @Override
    public String toString() {
//        return "Point{" +
//                "x=" + x +
//                ", y=" + y +
//                '}';
        return "(" + x + ", " + y + ")";
    }
}
