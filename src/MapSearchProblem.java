import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSearchProblem extends Searchable<Point> {
    private List<char[]> map;

    public enum TILES {
        START,
        END,
        ROAD,
        DIRT,
        HILL,
        WATER;
    }

    private static final Map<TILES, Double> costsPerTile;
    static {
        costsPerTile = new EnumMap<TILES, Double>(TILES.class);
        costsPerTile.put(TILES.START, 0.0);
        costsPerTile.put(TILES.END, 0.0);
        costsPerTile.put(TILES.ROAD, 1.0);
        costsPerTile.put(TILES.DIRT, 3.0);
        costsPerTile.put(TILES.HILL, 10.0);
        costsPerTile.put(TILES.WATER, null);
    }

    public MapSearchProblem(List<char[]> map) {
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

    @Override
    public List<State<Point>> getChildStates(State<Point> state) {
        return null;
    }
}
