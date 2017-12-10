import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Abstract Class for a search algorithm using states.
 * It uses a Priority Queue for the run of the algorithm, a stack for the solution and it's total
 * cost.
 *
 * @param <T>
 */
public abstract class Searcher<T> {
    protected PriorityQueue<State<T>> queue;
    protected Stack<T> solution = null;
    protected double totalCost;

    /**
     * Ctor. Uses the getComparator() comparator for the priority queue.
     */
    public Searcher() {
        queue = new PriorityQueue<>(getComparator());
        solution = null;
        totalCost = 0;
    }

    /**
     * Searches the problem up to the limit or finding the goal.
     *
     * @param problem         Problem to search in.
     * @param limitIterations Can limit the depth / iterations of the algorithm. If < 0 there is
     *                        no limit (Int.MAX)
     */
    public abstract void search(Searchable<Point> problem, int limitIterations);

    /**
     * @return A stack holding the solution, where the head is the start and the tail is the goal.
     */
    public Stack<T> getSolution() {
        return solution;
    }

    /**
     * @return The total cost of the solution.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * @return A comparator for the priority queue for this algorithm.
     */
    protected abstract Comparator<State<T>> getComparator();

    /**
     * @return true if a solution was found, false otherwise.
     */
    public boolean foundSolution() {
        return solution != null && !solution.empty();
    }
}
