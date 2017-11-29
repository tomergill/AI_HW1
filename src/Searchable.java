import java.util.List;

public interface Searchable<T> {
    State<T> getStartState();
    State<T> getGoalState();
    List<State<T>> getChildStates(State<T> state);
}
