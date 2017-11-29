import java.util.List;

public abstract class Searchable<T> {
    protected State<T> start;
    protected State<T> goal;

    protected Searchable() {
        start = null;
        goal = null;
    }

    public State<T> getStart() {
        return start;
    }

    public State<T> getGoal() {
        return goal;
    }

    public abstract List<State<T>> getChildStates(State<T> state);
}
