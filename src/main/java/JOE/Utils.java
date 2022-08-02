package JOE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    public Gson gson;
    private static Utils instance = null;
    private Utils() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }
}
