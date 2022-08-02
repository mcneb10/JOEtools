package JOE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements functions for converting between JSON, the internal JOE format, and JOE files
 */
public class JOEConverter {
    public static JOEFile jsonToInternalJOE(File jsonFile) {
        String json = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                json+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Utils.getInstance().gson.fromJson(json,JOEFile.class);
    }
    public static String internalJOEToJSON(JOEFile joeFile) {
        return Utils.getInstance().gson.toJson(joeFile);
    }
    //TODO: write joeToInternalJOE
    public static JOEFile joeToInternalJOE(File joeFile) {
        List<String> strings = new ArrayList<>();
        List<String> ints = new ArrayList<>();
        List<String> floats = new ArrayList<>();
        List<List<String>> stringarrays = new ArrayList<>();

    }
    //TODO: write asserted byte reader
    private static byte readByte() {

    }
    //TODO: write internalJOEtoJOE
    public static void internalJOEtoJOE(JOEFile joe, File output) {

    }
    public static void jsonToJOE(File json, File output) {
        internalJOEtoJOE(jsonToInternalJOE(json), output);
    }
    public static void joeToJSON(File joe, File output) {
        String json = internalJOEToJSON(joeToInternalJOE(joe));
        PrintWriter out = null;
        try {
            out = new PrintWriter(output);
            out.print(json);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
