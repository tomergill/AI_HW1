import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class MapSearchProblem extends Searchable<Point> {
    private List<char[]> map;
    private double[][] estimation;

    public enum TILE {
        START,
        END,
        ROAD,
        DIRT,
        HILL,
        WATER;
    }

    private static final Map<TILE, Double> costsPerTile;

    static {
        costsPerTile = new EnumMap<TILE, Double>(TILE.class);
        costsPerTile.put(TILE.START, 0.0);
        costsPerTile.put(TILE.END, 0.0);
        costsPerTile.put(TILE.ROAD, 1.0);
        costsPerTile.put(TILE.DIRT, 3.0);
        costsPerTile.put(TILE.HILL, 10.0);
        costsPerTile.put(TILE.WATER, null);
    }

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
                double x2 = goal.getState().getX(), y2 = goal.getState().getY();
                estimation[i][j] = sqrt(((double) i - x2) * ((double) i - x2)
                        + ((double) j - y2) * ((double) j - y2));
            }
        }
    }

    private TILE charToTile(char c) {
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

    private boolean isPointInMap(Point p) {
        return p != null
                && p.getX() >= 0 && p.getX() < map.size()
                && p.getY() >= 0 && p.getY() < map.get(0).length;
    }

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
        State<Point> new_state;

        for (Point p : points) {
            if (!isPointInMap(p))
                continue;
            TILE tile = charToTile(map.get(p.getX())[p.getY()]);
            if (tile == TILE.WATER || state.getState().equals(p)
                    || (state.getCameFrom() != null && state.getCameFrom().getState().equals(p)))
                continue;
            if (p.getX() != x && p.getY() != y  //corner
                    && (map.get(x)[p.getY()] == 'W' || map.get(p.getX())[y] == 'W'))
                continue;
            list.add(new State<Point>(p, state.getCost() + costsPerTile.get(tile), state,
                    state.getDepth() + 1, timeOfCreation));
        }

        return list;
    }

    @Override
    public double getEstimationForState(State<Point> state) {
        if (!isPointInMap(state.getState()))
            return -1;
        return estimation[state.getState().getX()][state.getState().getY()];
    }
}
