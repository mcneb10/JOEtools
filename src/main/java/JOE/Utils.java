package JOE;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.util.logging.Logger;

public class Utils {
    public Gson gson;
    public Logger logger;
    private static Utils instance = null;
    private Utils() {
        // Copied ToNumberPolicy.LONG_OR_DOUBLE
        // Replaced long with int and double with float
        ToNumberStrategy toNumberStrategy = in -> {
            String value = in.nextString();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException longE) {
                try {
                    Float d = Float.valueOf(value);
                    if ((d.isInfinite() || d.isNaN()) && !in.isLenient()) {
                        throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + "; at path " + in.getPreviousPath());
                    }
                    return d;
                } catch (NumberFormatException doubleE) {
                    throw new JsonParseException("Cannot parse " + value + "; at path " + in.getPreviousPath(), doubleE);
                }
            }
        };
        gson = new GsonBuilder().setPrettyPrinting().setObjectToNumberStrategy(toNumberStrategy).create();
        logger = Logger.getLogger("com.mcneb10.JOEtool");
    }
    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }
}
