import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapSearchProblem extends Searchable<Point> {
    private List<char[]> map;

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
            startPoint = new Point(0,0);
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

        this.start = new State<>(startPoint, 0.0, null);
        this.goal = new State<>(goalPoint, -1, null);
    }

    private TILE charToTile(char c) {
        switch (c) {
            case 'S': return TILE.START;
            case 'G': return TILE.END;
            case 'H': return TILE.HILL;
            case 'R': return TILE.ROAD;
            case 'W': return TILE.WATER;
            case 'D': return TILE.DIRT;
            default: return null;
        }
    }

    private boolean isPointInMap(Point p) {
        return p != null
                && p.getX() >= 0 && p.getX() < map.size()
                && p.getY() >= 0 && p.getY() < map.get(0).length;
    }

    @Override
    public List<State<Point>> getChildStates(State<Point> state) {
        if (state == null)
            return null;
        List<State<Point>> list = new LinkedList<>();
        int x = state.getState().getX(), y = state.getState().getY();
        int[] diffs = {-1, 0, 1};

        for (int i:diffs) {
            for (int j: diffs){
                if (i == 0 && j == 0)
                    continue;
                Point p = new Point(x+i, y+j);
                if (!isPointInMap(p))
                    continue;
                TILE tile = charToTile(map.get(x + i)[y + j]);
                if (tile == TILE.WATER)
                    continue;
                //Duplicate Pruning
                else if (state.getCameFrom() != null && p.equals(state.getCameFrom().getState()))
                    continue;
                else if (p.equals(start.getState())) //never add start
                    continue;

                list.add(new State<>(p, state.getCost() + costsPerTile.get(tile),
                        state));
            }
        }
        return list;
    }
}
