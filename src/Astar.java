import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * The A* search algorithms over a 2D map using points (X,Y), an ariel space estimation for the
 * heuristic cost function and a limit to the number of iterations (no closed list).
 */
public class Astar extends Searcher<Point> {
    private Comparator<State<Point>> comparator = null; //comparator for the priority queue
    private Searchable<Point> problem; //The recent search problem

    /**
     * Searches the given problem and keep it's solution and total cost.
     *
     * @param problem         problem to search in
     * @param limitIterations limits the number of iterations performed.
     */
    @Override
    public void search(Searchable<Point> problem, int limitIterations) {
        this.problem = problem;
        queue.clear();
        if (limitIterations < 0)
            limitIterations = Integer.MAX_VALUE;
        queue.add(problem.getStart());
        State<Point> current = null;
        int time = 0;

        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(problem.getGoal()))
                break;
            else if (current.getDepth() >= limitIterations)
                continue;
            List<State<Point>> children = problem.getChildStates(current, time++);
            Collections.reverse(children);
            for (State<Point> child : children)
                if (!queue.contains(child)) {
                    queue.add(child);
                }
        }

        solution = new Stack<>();
        if (current != null && current.equals(problem.getGoal())) {
            totalCost = current.getCost();
            while (current != null) {
                solution.push(current.getState());
                current = current.getCameFrom();
            }
        }
        // there is no path
    }

    /**
     * @return the comparator for the priority queue, based on f(n)=g(n)+h(n), the time of
     * creation and the direction from the father state.
     */
    @Override
    protected Comparator<State<Point>> getComparator() {
        if (comparator == null)
            comparator = ((o1, o2) -> {
                double diff = (o1.getCost() + problem.getEstimationForState(o1))
                        - (o2.getCost() + problem.getEstimationForState(o2));
                if (diff != 0)
                    return (int) diff;
                int time = o1.getCreationTime() - o2.getCreationTime();
                if (time != 0)
                    return time;
                int d1 = Astar.directionOfState(o1), d2 = Astar.directionOfState(o2);
                return d1 - d2;
            });
        return comparator;
    }

    /**
     * Returns the direction of the state w.r.t it's father state
     *
     * @param state State to get it's direction
     * @return An integer representing a direction. Right is 0 and keeps on clockwise (RD 1, D 2,..)
     */
    private static int directionOfState(State<Point> state) {
        int p_x = state.getCameFrom().getState().getX(),
                p_y = state.getCameFrom().getState().getY(),
                x = state.getState().getX(), y = state.getState().getY();

        /* Kind of Enum */
        int right = 0, right_down = 1, down = 2, left_down = 3, left = 4, left_up = 5, up = 6,
                right_up = 7;

        if (p_y < y) { // right
            if (p_x < x) //down
                return right_down;
            else if (p_x == x)  // no x change
                return right;
            else  /// up
                return right_up;
        } else if (p_y == y) {  // no y change
            if (p_x < x) //down
                return down;
            else if (p_x == x)  // no x change
                return -1;
            else  /// up
                return up;
        } else {  // left
            if (p_x < x) //down
                return left_down;
            else if (p_x == x)  // no x change
                return left;
            else  /// up
                return left_up;
        }
    }
}
