package JOE;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Sheets {
    /**
     * Structure of this type
     * Map has a key for each sheet with a list of rows
     * Each row has a map of cells
     */
    @SerializedName("objects")
    public TreeMap<String,List<TreeMap<String, Object>>> columns;
    public Sheets(TreeMap<String,List<TreeMap<String, Object>>> columns) {
        this.columns = columns;
    }
}
