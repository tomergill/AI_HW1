import java.util.*;

/**
 * Class for the Iterative Deepening for DFS (IDS) search algorithm.
 * The stack is replaced with a priority queue instead.
 */
public class IterativeDeepening extends Searcher<Point> {
    private Comparator<State<Point>> comparator = null;

    /**
     * Searches the problem using IDS.
     *
     * @param problem         Problem to search in.
     * @param limitIterations Can limit the depth / iterations of the algorithm. If < 0 there is
     */
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

    /**
     * Searches a DFS search in problem up to depth of limit.
     *
     * @param problem Problem to search in.
     * @param limit   Limit of depth.
     * @return The goal state if found, otherwise null.
     */
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
                if (!queue.contains(s))
                    queue.add(s);
            }
        }

        if (current != null && current.getDepth() >= limit) //stopped because IDS' limit
            return current;

        return null; //there is no solution
    }

    /**
     * @return Comparator for the priority queue, that always takes the newer state.
     */
    @Override
    protected Comparator<State<Point>> getComparator() {
        if (comparator == null) {
            comparator = (o1, o2) -> {
                if (o1.getCreationTime() < o2.getCreationTime())
                    return 1;
                else if (o1.getCreationTime() > o2.getCreationTime())
                    return -1;
                return 0;
            };
        }
        return comparator;
    }
}
