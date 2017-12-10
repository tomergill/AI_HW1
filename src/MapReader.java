import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for reading a file to get the used Algorithm, the size of the map and the whole map.
 */
public class MapReader {
    private List<char[]> map = null;
    private String algo = null;
    private int rows = -1, cols = -1;

    /**
     * Ctor. reads the file and initializes this' members
     *
     * @param path Path to file
     * @throws IOException
     */
    public MapReader(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        algo = reader.readLine();
        cols = rows = Integer.parseInt(reader.readLine());
        map = new ArrayList<>();
        for (int i = 0; i < rows; i++)
            map.add(reader.readLine().toCharArray());
        reader.close();
    }

    /**
     * @return List of char arrays (matrix)
     */
    public List<char[]> getMap() {
        return map;
    }

    /**
     * @return The name of the algorithm to use from the file.
     */
    public String getAlgo() {
        return algo;
    }

    /**
     * @return The number of rows in the map.
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return The number of columns in the map.
     */
    public int getCols() {
        return cols;
    }
}
