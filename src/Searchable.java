import java.util.List;

/**
 * Abstract class for a search problem, using states.
 * Holds the start state and the goal state, estimation for each state (not mandatory) and also
 * manages what sates can be reached from each state.
 *
 * @param <T> Type of state, depends on the problem.
 */
public abstract class Searchable<T> {
    protected State<T> start;
    protected State<T> goal;

    /**
     * Ctor.
     */
    protected Searchable() {
        start = null;
        goal = null;
    }

    /**
     * @return The start state.
     */
    public State<T> getStart() {
        return start;
    }

    /**
     * @return The goal state.
     */
    public State<T> getGoal() {
        return goal;
    }

    /**
     * Gets a list of all the states that can be reached from state.
     * The function creates all the new states and gives them the time of creation.
     *
     * @param state          The father state.
     * @param timeOfCreation The time given to all the newly created states.
     * @return A list of all the children states from state.
     */
    public abstract List<State<T>> getChildStates(State<T> state, int timeOfCreation);

    /**
     * @param state State to estimate
     * @return An estimation for the given state
     */
    public abstract double getEstimationForState(State<T> state);
}
