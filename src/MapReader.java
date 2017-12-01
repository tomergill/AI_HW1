import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for reading a file to get the used Algorithm, the size of the map
 */
public class MapReader {
    private List<char[]> map = null;
    private String algo = null;
    private int rows = -1, cols = -1; //

    public MapReader(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        algo = reader.readLine();
        cols = rows = Integer.parseInt(reader.readLine());
        map = new ArrayList<>();
        for (int i = 0; i < rows; i++)
            map.add(reader.readLine().toCharArray());
        reader.close();
    }

    public List<char[]> getMap() {
        return map;
    }

    public String getAlgo() {
        return algo;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
