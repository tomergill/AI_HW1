import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public abstract class Searcher<T> {
    protected PriorityQueue<State<T>> queue;
    protected Stack<T> solution = null;
    protected double totalCost;

    public Searcher() {
        queue = new PriorityQueue<>(getComparator());
        solution = null;
        totalCost = 0;
    }

    public abstract void search(Searchable<Point> problem, int limitIterations);

    public Stack<T> getSolution() {
        return solution;
    }

    public double getTotalCost() {
        return totalCost;
    }

    protected abstract Comparator<State<T>> getComparator();

    public boolean foundSolution() {
        return solution != null && !solution.empty();
    }
}
