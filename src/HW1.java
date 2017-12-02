import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class HW1 {
    public static void main(String[] args) {

        assert args.length == 1;
        try {
            System.out.println("File is " + args[0] + "\n");
            MapReader reader = new MapReader(args[0]);
            MapSearchProblem problem = new MapSearchProblem(reader.getMap());

            Searcher<Point> algo;
            switch (reader.getAlgo()) {
                case "IDS":
                    algo = new IterativeDeepening();
                    break;
                case "A*":
                    algo = new Astar();
                    break;
                default:
                    algo = new IterativeDeepening();
                    break;
            }


            System.out.println("The map is:\n \t01234");
            for (int i = 0; i < reader.getMap().size(); i++) {
                System.out.println(i + "\t" + new String(reader.getMap().get(i)));
            }
            System.out.println(); /*
            System.out.println("Start is " + problem.getStart());
            System.out.println("Goal is " + problem.getGoal());
            System.out.println();
            List<State<Point>> children = printStateAndChildren(problem, problem.start);
            for (int i = 0; i < 4; i++) {
                children = printStateAndChildren(problem, children.get(2));
            }
            */

            /* searching, limited to number of nodes in map */
            algo.search(problem, reader.getRows() * reader.getCols() + 1);
            FileWriter writer = new FileWriter("output.txt");
            if (algo.foundSolution()) {
                String route = getRouteFromSolutionStack(algo.getSolution());
                if (route != null) {
                    writer.write(route + " " + algo.getTotalCost());
                    System.out.println(route + " " + algo.getTotalCost()); //todo remove
                    writer.close();
                    return;
                }
            }
            writer.write("no path");
            System.out.println("no path"); //todo remove
            writer.close();

        } catch (IOException e) {
            System.err.println("Problem with file: IOException" + e.toString());
            e.printStackTrace();
        }
    }

    private static String getRouteFromSolutionStack(Stack<Point> solution) {
        if (solution == null) return null;
        Point prev = solution.pop(), next;
        StringBuilder builder = new StringBuilder("");
        while (!solution.empty()) {
            next = solution.pop();
            int dx = next.getX() - prev.getX(), dy = next.getY() - prev.getY();
            switch (dx) {
                case -1:
                    switch (dy) {
                        case -1:
                            builder.append("LU");
                            break;
                        case 0:
                            builder.append('U');
                            break;
                        case 1:
                            builder.append("RU");
                            break;
                        default:
                            return null;
                    }
                    break;
                case 0:
                    switch (dy) {
                        case -1:
                            builder.append('L');
                            break;
                        case 1:
                            builder.append('R');
                            break;
                        default:
                            return null;
                    }
                    break;
                case 1:
                    switch (dy) {
                        case -1:
                            builder.append("LD");
                            break;
                        case 0:
                            builder.append('D');
                            break;
                        case 1:
                            builder.append("RD");
                            break;
                        default:
                            return null;
                    }
                    break;
                default:
                    return null;
            }
            prev = next;
            builder.append("-");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private static List<State<Point>> printStateAndChildren(MapSearchProblem problem, State<Point>
            state) {
        System.out.println("States from state " + state + " are: ");
        List<State<Point>> children = problem.getChildStates(state, 0);
        for (State<Point> s : children) {
            System.out.println("*\t" + s);
        }
        System.out.println();
        return children;
    }
}
