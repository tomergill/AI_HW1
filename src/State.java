/**
 * Class of a general state in a Searchable problem, with cost.
 *
 * @param <T> Type of state.
 */
public class State<T> {
    private T state;
    private double cost; //g(n), n=this
    private State<T> cameFrom;
    private int depth;
    private int creationTime;

    /**
     * Constructor.
     *
     * @param state        The state this object represents.
     * @param cost         g(n) where n is this state.
     * @param father       The State we came from, can be null.
     * @param creationTime The time this state was created.
     * @param depth        The deopth of this state down the current path
     */
    public State(T state, double cost, State<T> father, int depth, int creationTime) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = father;
        this.depth = depth;
        this.creationTime = creationTime;
    }

    /**
     * Constructor.
     *
     * @param state        The state this object represents.
     * @param cost         g(n) where n is this state.
     * @param father       The State we came from, can be null.
     * @param creationTime The time this state was created.
     */
    public State(T state, double cost, State<T> father, int creationTime) {
        this(state, cost, father, father != null ? father.depth + 1 : 0, creationTime);
    }

    /**
     * Constructor.
     *
     * @param state        The state this object represents.
     * @param cost         g(n) where n is this state.
     * @param creationTime The time this state was created.]
     */
    public State(T state, double cost, int creationTime) {
        this(state, cost, null, creationTime);
    }

    /**
     * Constructor.
     *
     * @param state        The state this object represents.
     * @param cost         g(n) where n is this state.
     * @param creationTime The time this state was created.
     * @param depth        The deopth of this state down the current path
     */
    public State(T state, double cost, int depth, int creationTime) {
        this(state, cost, null, depth, creationTime);
    }

    /**
     * @return The time this state was created.
     */
    public int getCreationTime() {
        return creationTime;
    }

    /**
     * Gets the state.
     *
     * @return the state.
     */
    public T getState() {
        return state;
    }

    /**
     * Gets the cost.
     *
     * @return Cost of getting to this state from the Start state.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets the father state.
     *
     * @return the state we came from, or null if there isn't one.
     */
    public State<T> getCameFrom() {
        return cameFrom;
    }

    /**
     * Possibly adds a new state we came from, by checking whether it's new cost is less than the
     * one before.
     *
     * @param father The new state we came from.
     * @return true if father is now this.cameFrom, false otherwise.
     */
    public boolean newCameFrom(State<T> father, int creationTime) {
        if (equals(father) || father.getCost() >= cameFrom.cost)
            return false;
        this.cost += father.cost - cameFrom.cost;
        cameFrom = father;
        this.depth = father.depth + 1;
        this.creationTime = creationTime;
        return true;

    }

    /**
     * Sets the creation time of this state.
     *
     * @param creationTime New creation time.
     */
    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return The depth of this state in it's path.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Checks if object is equal to this state.
     *
     * @param o Object to check.
     * @return True if equals, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State<?> state1 = (State<?>) o;

        return state.equals(state1.state);
    }

    /**
     * @return the hashcode of this state.
     */
    @Override
    public int hashCode() {
        return state.hashCode();
    }

    /**
     * @return String representation of this state: "State{state=this.state, cost=this.cost,
     * cameFrom=this.cameFrom}"
     */
    @Override
    public String toString() {
        return "State{" +
                "state=" + state +
                ", cost=" + cost +
                ", cameFrom=" + (cameFrom == null ? "null" : cameFrom) +
                ", depth=" + depth +
                ", creationTime=" + creationTime +
                '}';
    }
}
