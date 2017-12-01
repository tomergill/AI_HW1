import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IterativeDeepening extends Searcher<Point> {
    private int time;
    private Comparator<State<Point>> comparator = null;

    public IterativeDeepening() {
        time = 0;
    }

    @Override
    public void search(Searchable<Point> problem) {
        int depth = 0;
    }

    @Override
    protected Comparator<State<Point>> getComparator() {
        if (comparator == null) {
            comparator = new Comparator<State<Point>>() {
                @Override
                public int compare(State<Point> o1, State<Point> o2) {
                    if (o1.getCost() > o2.getCost())
                        return 1;
                    else if (o1.getCost() < o2.getCost())
                        return -1;
                    else if (o1.getCreationTime() < o2.getCreationTime())
                        return 1;
                    else if (o1.getCreationTime() > o2.getCreationTime())
                        return -1;
                    return 0;
                }
            };
        }
        return comparator;
    }
}
