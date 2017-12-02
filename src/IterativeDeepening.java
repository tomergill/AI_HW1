import java.util.*;

public class IterativeDeepening extends Searcher<Point> {
    private Comparator<State<Point>> comparator = null;

    @Override
    public void search(Searchable<Point> problem, int limitIterations) {
        if (limitIterations < 0)
            limitIterations = Integer.MAX_VALUE; //infinitely until finds solution
        if (problem == null)
            return;
        int limit_depth = 0;
        State<Point> finish;
        do {
            finish = DFSWithDepthLimit(problem, ++limit_depth);
        } while (finish != null && !finish.equals(problem.getGoal())
                && limit_depth < limitIterations);

        if (finish != null)
            totalCost = finish.getCost();
        solution = new Stack<>();
        if (limit_depth < limitIterations) { //not finished because of limit, but because found
            while (finish != null) {
                solution.push(finish.getState());
                finish = finish.getCameFrom();
            }
        }
    }

    private State<Point> DFSWithDepthLimit(Searchable<Point> problem, int limit) {
        /* Initialization */
        int time = 0;
        queue.clear();
        queue.add(problem.getStart());
        State<Point> current = null;
        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(problem.getGoal()))
                return current;
            if (current.getDepth() >= limit)
                continue;
            List<State<Point>> children = problem.getChildStates(current, time);
            Collections.reverse(children);
            for (State<Point> s : children) {
                s.setCreationTime(time++);
                queue.add(s);
            }
        }

        if (current != null && current.getDepth() >= limit) //stopped because IDS' limit
            return current;

        return null; //there is no solution
    }

    @Override
    protected Comparator<State<Point>> getComparator() {
        if (comparator == null) {
            comparator = new Comparator<State<Point>>() {
                @Override
                public int compare(State<Point> o1, State<Point> o2) {
                    if (o1.getCreationTime() < o2.getCreationTime())
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
