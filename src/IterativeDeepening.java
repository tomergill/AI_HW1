import java.util.*;

public class IterativeDeepening extends Searcher<Point> {
    private Comparator<State<Point>> comparator = null;

    @Override
    public void search(Searchable<Point> problem) {
        int limit_depth = -1;
        State<Point> finish;
        do {
            finish = DFSWithDepthLimit(problem, ++limit_depth);
        } while (!finish.equals(problem.getGoal()));

        solution = new Stack<>();
        totalCost = 0;
        while (finish != null) {
            solution.push(finish.getState());
            totalCost += finish.getCost();
            finish = finish.getCameFrom();
        }
    }

    private State<Point> DFSWithDepthLimit(Searchable<Point> problem, int limit) {
        int time = 0;
        return null; //todo this
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
