import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

/**
 * Class representing a search problem over a map represented by a list of char arrays (char
 * matrix), managing a cost, estimation and possible children for each state, including start and
 * goal states.
 * The estimation is defined to be the direct airspace (max{|x - goal.x|, |y - goal.y|})
 */
public class MapSearchProblem extends Searchable<Point> {
    private List<char[]> map;
    private double[][] estimation;

    /**
     * Kinds of tiles in the map.
     */
    public enum TILE {
        START,
        END,
        ROAD,
        DIRT,
        HILL,
        WATER
    }

    private static final Map<TILE, Double> costsPerTile;

    static {
        costsPerTile = new EnumMap<>(TILE.class);
        costsPerTile.put(TILE.START, 0.0);
        costsPerTile.put(TILE.END, 0.0);
        costsPerTile.put(TILE.ROAD, 1.0);
        costsPerTile.put(TILE.DIRT, 3.0);
        costsPerTile.put(TILE.HILL, 10.0);
        costsPerTile.put(TILE.WATER, null);
    }

    /**
     * Ctor. Initializes the start and goal states, and also computes the estimation for each point.
     *
     * @param map The char "matrix" that is the map
     */
    public MapSearchProblem(List<char[]> map) {
        assert map != null;
        this.map = map;
        /* Find the start and goal points */
        Point startPoint = null;
        Point goalPoint = null;
        int rows = map.size(), cols = map.get(0).length;
        if (map.get(0)[0] == 'S' && map.get(rows - 1)[cols - 1] == 'G') {
            startPoint = new Point(0, 0);
            goalPoint = new Point(rows - 1, cols - 1);
        } else {
            for (int i = 0; i < map.size() && (startPoint == null || goalPoint == null); i++) {
                char[] temp = map.get(i);
                for (int j = 0; j < map.get(0).length && (startPoint == null || goalPoint == null);
                     j++) {
                    if (temp[j] == 'S')
                        startPoint = new Point(i, j);
                    else if (temp[j] == 'G')
                        goalPoint = new Point(i, j);
                }
            }
        }

        this.start = new State<>(startPoint, 0.0, null, 0, -1);
        this.goal = new State<>(goalPoint, -1, null, 0, -1);

        /* estimation */
        estimation = new double[map.size()][map.get(0).length];
        for (int i = 0; i < estimation.length; i++) {
            for (int j = 0; j < estimation[0].length; j++) {
                /* Airspace */
                int x2 = goal.getState().getX(), y2 = goal.getState().getY();
//                estimation[i][j] = sqrt(((double) i - x2) * ((double) i - x2)
//                        + ((double) j - y2) * ((double) j - y2));
                estimation[i][j] = max(abs(i - x2), abs(j - y2));
            }
        }
    }

    /**
     * @param c char to convert
     * @return Converts the char from the map to the appropriate enum tile.
     */
    private static TILE charToTile(char c) {
        switch (c) {
            case 'S':
                return TILE.START;
            case 'G':
                return TILE.END;
            case 'H':
                return TILE.HILL;
            case 'R':
                return TILE.ROAD;
            case 'W':
                return TILE.WATER;
            case 'D':
                return TILE.DIRT;
            default:
                return null;
        }
    }

    /**
     * Checks whether a point is in the map's boundaries
     *
     * @param p Point to check
     * @return true if p is in the map, otherwise false
     */
    private boolean isPointNotInMap(Point p) {
        return p == null
                || p.getX() < 0 || p.getX() >= map.size()
                || p.getY() < 0 || p.getY() >= map.get(0).length;
    }

    /**
     * Gets a list of all the states that can be rached from state, in an clockwise order
     * starting in right direction. The function creates all the new states and gives them the
     * time of creation, and state as father.
     *
     * @param state          The father state.
     * @param timeOfCreation The time given to all the newly created states.
     * @return A list of all the children states from state.
     */
    @Override
    public List<State<Point>> getChildStates(State<Point> state, int timeOfCreation) {
        if (state == null)
            return null;
        List<State<Point>> list = new LinkedList<>();
        int x = state.getState().getX(), y = state.getState().getY();
        Point points[] = {
                new Point(x, y + 1),      // right
                new Point(x + 1, y + 1),  // right-down
                new Point(x + 1, y),      // down
                new Point(x + 1, y - 1), //left-down
                new Point(x, y - 1),     //left
                new Point(x - 1, y - 1), //left-up
                new Point(x - 1, y),     //up
                new Point(x - 1, y + 1)  // right-up
        };

        for (Point p : points) {
            if (isPointNotInMap(p))
                continue;
            TILE tile = charToTile(map.get(p.getX())[p.getY()]);
            if (tile == TILE.WATER || state.getState().equals(p)
                    || (state.getCameFrom() != null && state.getCameFrom().getState().equals(p)))
                continue;
            if (p.getX() != x && p.getY() != y  //corner
                    && (map.get(x)[p.getY()] == 'W' || map.get(p.getX())[y] == 'W'))
                continue;
            list.add(new State<>(p, state.getCost() + costsPerTile.get(tile), state,
                    state.getDepth() + 1, timeOfCreation));
        }

        return list;
    }

    /**
     * @param state State to estimate
     * @return An estimation for the given state
     */
    @Override
    public double getEstimationForState(State<Point> state) {
        if (isPointNotInMap(state.getState()))
            return -1;
        return estimation[state.getState().getX()][state.getState().getY()];
    }
}
