import java.io.FileWriter;
import java.io.IOException;
//import java.util.List;
import java.util.Stack;

/***
 * Main class. Reads input.txt (or other file), and then runs the requested algorithm (in the
 * file) on the map from the file. Then writes the path and it's cost to "output.txt" file.
 */
public class java_ex1 {
    /***
     * Main method of program. If args isn't another file, the input will be taken from "input.txt"
     * @param args can contain name of input file, other wise empty.
     */
    public static void main(String[] args) {
        String file = "input.txt";

        if (args.length == 1) {
            file = args[0];
        } else if (args.length > 1) {
            System.out.println("Too many argumants, expected 0 or 1 only.");
            System.exit(1);
        }

        try {
            System.out.println("File is " + file + "\n");

            // read file & create problem class
            MapReader reader = new MapReader(file);
            MapSearchProblem problem = new MapSearchProblem(reader.getMap());

            // get the Algo from the file
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
            System.out.println();

            /* searching, limited to number of nodes in map */
            algo.search(problem, reader.getRows() * reader.getCols() + 1);
            FileWriter writer = new FileWriter("output.txt");

            // if found a solution write the path to the file and it's cost
            if (algo.foundSolution()) {
                String route = getRouteFromSolutionStack(algo.getSolution());
                if (route != null) {
                    writer.write(route + " " + (int) algo.getTotalCost());
                    System.out.println(route + " " + algo.getTotalCost()); //todo remove
                    writer.close();
                    return;
                }
            }

            // no solution found
            writer.write("no path");
            System.out.println("no path"); //todo remove
            writer.close();

        } catch (IOException e) {
            System.err.println("Problem with file: IOException" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Creates a description of the path, where R is right, L is Left, U is up and D is down and
     * RD, LD, RU and LU are also possible. Each direction is separated with '-'.
     *
     * @param solution Stack of points from start to finish.
     * @return String representing the path
     */
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

//    /**
//     * Debug Method. Ignore.
//     *
//     * @param problem
//     * @param state
//     * @return
//     */
//    private static List<State<Point>> printStateAndChildren(MapSearchProblem problem, State<Point>
//            state) {
//        System.out.println("States from state " + state + " are: ");
//        List<State<Point>> children = problem.getChildStates(state, 0);
//        for (State<Point> s : children) {
//            System.out.println("*\t" + s);
//        }
//        System.out.println();
//        return children;
//    }
}
