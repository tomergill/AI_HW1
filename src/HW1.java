import java.io.IOException;
import java.util.List;

public class HW1 {
    public static void main(String[] args) {
        assert args.length == 1;
        try {
            System.out.println("File is " + args[0] + "\n");
            MapReader reader = new MapReader(args[0]);
            MapSearchProblem problem = new MapSearchProblem(reader.getMap());

            System.out.println("The map is:\n \t01234");
            for (int i = 0; i < reader.getMap().size(); i++) {
                System.out.println(i + "\t" + new String(reader.getMap().get(i)));
            }
            System.out.println();
            System.out.println("Start is " + problem.getStart());
            System.out.println("Goal is " + problem.getGoal());
            System.out.println();
            List<State<Point>> children = printStateAndChildren(problem, problem.start);
            for (int i = 0; i < 4; i++) {
                children = printStateAndChildren(problem, children.get(2));
            }


        } catch (IOException e) {
            System.err.println("IOException" + e.toString());
            e.printStackTrace();
        }
    }

    private static List<State<Point>> printStateAndChildren(MapSearchProblem problem, State<Point>
            state) {
        System.out.println("States from state " + state + " are: ");
        List<State<Point>> children = problem.getChildStates(state);
        for (State<Point> s: children) {
            System.out.println("*\t" + s);
        }
        System.out.println();
        return children;
    }
}
