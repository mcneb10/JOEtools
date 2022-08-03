package JOE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.logging.Logger;

public class Utils {
    public Gson gson;
    public Logger logger;
    private static Utils instance = null;
    private Utils() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        logger = Logger.getLogger("com.mcneb10.JOEtool");
    }
    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }
}
