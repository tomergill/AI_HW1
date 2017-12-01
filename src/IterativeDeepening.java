import java.util.*;

public class IterativeDeepening extends Searcher<Point> {
    private Comparator<State<Point>> comparator = null;

    @Override
    public void search(Searchable<Point> problem) {
        if (problem == null)
            return;
        int limit_depth = 0;
        State<Point> finish;
        do {
            finish = DFSWithDepthLimit(problem, ++limit_depth);
        } while (finish != null && !finish.equals(problem.getGoal()));

        solution = new Stack<>();
        totalCost = 0;
        while (finish != null) {
            solution.push(finish.getState());
            totalCost += finish.getCost();
            finish = finish.getCameFrom();
        }
    }

    private State<Point> DFSWithDepthLimit(Searchable<Point> problem, int limit) {
        /* Initialization */
        int time = 0;
        queue.clear();
        queue.add(problem.getStart());
        Map<State<Point>, State<Point>> visited = new HashMap<>();
        State<Point> current = null;
        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(problem.getGoal()))
                return current;
            if (current.getDepth() >= limit)
                continue;
            for (State<Point> s : problem.getChildStates(current, time)) {
                if (!visited.containsKey(s)) { //first time visit
                    s.setCreationTime(time++);
                    visited.put(s, s);
                    queue.add(s);
                } else  if (queue.contains(s)) { //in stack waiting, updating it's cost and time
                    for (State<Point> temp : queue) { //find it in queue
                        if (temp.equals(s)) {
                            if (temp.getCost() > s.getCost()) {
                                queue.remove(temp);
                                queue.add(s);
                            }
                            break;
                        }
                    }

                }
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
                        return -1;
                    else if (o1.getCreationTime() > o2.getCreationTime())
                        return 1;
                    return 0;
                }
            };
        }
        return comparator;
    }
}
