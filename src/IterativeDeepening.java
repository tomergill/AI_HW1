import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IterativeDeepening extends Searcher<Point> {
    private Map<State<Point>, Integer> insertTime;
    private int time;

    public IterativeDeepening() {
        insertTime = new HashMap<>();
        time = 0;
    }

    @Override
    public void search(Searchable<Point> problem) {

    }

    private void addNewStatesToQueue(List<State<Point>> statesToAdd) {
        for (State<Point> s:statesToAdd) {
            insertTime.put(s, time);
            queue.add(s);
        }
        time++;
    }

    @Override
    protected Comparator<State<Point>> getComparator() {
        return new Comparator<State<Point>>() {
            @Override
            public int compare(State<Point> o1, State<Point> o2) {
                if (o1.getCost() > o2.getCost())
                    return 1;
                else if (o1.getCost() < o2.getCost())
                    return -1;
                return 0;
            }
        };
    }
}
