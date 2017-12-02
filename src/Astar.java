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
        int i = -1;
        int time = 0;

        while (!queue.isEmpty() && ++i < limitIterations) {
            current = queue.poll();
            if (current.equals(problem.getGoal()))
                break;
            queue.addAll(problem.getChildStates(current, time++));
        }

        solution = new Stack<>();
        if (current != null && i == limitIterations)
            return;
        if (current.equals(problem.getGoal())) {
            totalCost = current.getCost();
            while (current != null) {
                solution.push(current.getState());
                current = current.getCameFrom();
            }
        }
    }

    /**
     * @return the comparator for the priority queue, based on f(n)=g(n)+h(n) and the time of
     * creation
     */
    @Override
    protected Comparator<State<Point>> getComparator() {
        if (comparator == null)
            comparator = ((o1, o2) -> {
                double diff = (o1.getCost() + problem.getEstimationForState(o1))
                        - (o2.getCost() + problem.getEstimationForState(o2));
                if (diff != 0)
                    return (int) diff;
                return o1.getCreationTime() - o2.getCreationTime();
            });
        return comparator;
    }
}