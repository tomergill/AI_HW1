/**
 * Class of a general state in a Searchable problem, with cost.
 * @param <T> Type of state.
 */
public class State<T> {
    private T state;
    private double cost; //f(n), n=this
    private State<T> cameFrom;

    /**
     * Constructor.
     * @param state The state this object represents.
     * @param cost f(n) where n is this state.
     * @param father The State we came from, can be null.
     */
    public State(T state, double cost, State<T> father) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = father;
    }

    /**
     * Gets the state.
     * @return the state.
     */
    public T getState() {
        return state;
    }

    /**
     * Gets the cost.
     * @return Cost of getting to this state from the Start state.
     */
    public double getCost() {
        return cost;
    }

//    public void setCost(double cost) {
//        this.cost = cost;
//    }

    /**
     * Gets the father state.
     * @return the state we came from, or null if there isn't one.
     */
    public State<T> getCameFrom() {
        return cameFrom;
    }

    /**
     * Possibly adds a new state we came from, by checking whether it's new cost is less than the
     * one before.
     * @param father The new state we came from.
     * @return true if father is now this.cameFrom, false otherwise.
     */
    public boolean newCameFrom(State<T> father)
    {
        if (equals(father) || father.getCost() >= cameFrom.cost)
            return false;
        this.cost += father.cost - cameFrom.cost;
        cameFrom = father;
        return true;

    }

    /**
     * Checks if object is equal to this state.
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
                ", cameFrom=" + cameFrom +
                '}';
    }
}
