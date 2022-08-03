package JOE;

import com.google.common.primitives.Bytes;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements functions for converting between JSON, the internal JOE format, and JOE files
 */
public class JOEConverter {
    private static final Logger logger = Utils.getInstance().logger;
    /**
     * Converts json to internal JOE structure
     * @param jsonFile the JSON file to convert
     * @return the internal JOE structure class
     */
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

    /**
     * Converts internal JOE structure to JSON
     * @param joeFile the JOE file to convert
     * @return the JSON result
     */
    public static String internalJOEToJSON(JOEFile joeFile) {
        return Utils.getInstance().gson.toJson(joeFile);
    }
    private static List<Byte> byteList;
    //TODO: add error handling
    public static JOEFile joeToInternalJOE(File joeFile) {
        List<String> strings = new ArrayList<>();
        List<Integer> ints = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        List<List<String>> stringArrays = new ArrayList<>();
        List<String> sheetNames = new ArrayList<>();
        InputStream inputStream;
        byte[] bytes = null;
        try {
            inputStream = joeFile.toURI().toURL().openStream();
            bytes = inputStream.readAllBytes();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert bytes != null;
        byteList = new ArrayList<>(Bytes.asList(bytes));
        Utils.getInstance().logger.info("Size of JOE file: "+byteList.size());
        String joe = new String(popBytes(3));
        assert joe.equals("JOE") : "Invalid JOE header: "+joe;
        long version = decodeVariableLength();
        assert version == 0 : "Invalid JOE version: "+version;
        int stringCount = decodeInt();
        logger.log(Level.INFO, "String Count: "+stringCount);
        for(int i = 0; i<stringCount;i++) {
            long stringLength = decodeVariableLength();
            strings.add(new String(popBytes((int)stringLength)));
        }
        int intCount = decodeInt();
        logger.log(Level.INFO, "Int Count: "+intCount);
        for(int i = 0; i<intCount;i++) {
            ints.add(decodeInt());
        }
        int floatCount = decodeInt();
        logger.log(Level.INFO, "Float Count: "+floatCount);
        for(int i = 0; i<floatCount;i++) {
            floats.add(decodeFloat());
        }
        int stringArrayCount = decodeInt();
        logger.log(Level.INFO, "String Array Count: "+stringArrayCount);
        for(int i = 0; i<stringArrayCount;i++) {
            long arrayLength = decodeVariableLength();
            List<String> stringArray = new ArrayList<>();
            for(long j = 0;j<arrayLength;j++) {
                long stringNo = decodeVariableLength();
                stringArray.add(strings.get((int)stringNo));
            }
            stringArrays.add(stringArray);
        }
        long sheetCount = decodeVariableLength();
        logger.log(Level.INFO,"Sheet Count: "+sheetCount);
        for(int i = 0; i < sheetCount; i++) {
            String sheetName = strings.get((int)decodeVariableLength());
            sheetNames.add(sheetName);
        }
        TreeMap<String,List<TreeMap<String, Object>>> data = new TreeMap<>();
        List<SheetFormatDescriptor.Page> pages = new ArrayList<>();
        for(int i = 0;i<sheetCount;i++) {
            long columnCount = decodeVariableLength();
            List<String> columns = new ArrayList<>();
            List<Byte> columnTypes = new ArrayList<>();
            // Parse sheet columns
            for(int j = 0; j < columnCount; j++) {
                // This is the column type
                // We only really need it for decoding cells
                byte columnType = popByte();
                columnTypes.add(columnType);
                String columnName = strings.get((int) decodeVariableLength());
                columns.add(columnName);
            }
            long cellCount = decodeVariableLength();
            // Parse sheet cells
            int columnIndex = 0;
            List<TreeMap<String, Object>> groups = new ArrayList<>();
            TreeMap<String, Object> group = new TreeMap<>();
            //Temporary storage location for cells
            for(int j = 0; j < cellCount; j++) {
                Object value = null;
                long binData = decodeVariableLength();
                if(binData != 0) {
                    // The sheet class's functions all seem to subtract one from the value
                    // Obtained from the JOE file
                    // Will do the same for now
                    switch (columnTypes.get(columnIndex)) {
                        case 0:
                            // String
                            value = strings.get((int) binData - 1);
                            break;
                        case 1:
                            // Boolean
                            value = (--binData != 0);
                            break;
                        case 2:
                            // PositiveInt
                            value = (int) binData;
                            break;
                        case 3:
                            // Signed Int
                            value = ints.get((int) binData - 1);
                            break;
                        case 4:
                            // Float
                            value = floats.get((int) binData - 1);
                            break;
                        case 5:
                            // String Array
                            value = stringArrays.get((int) binData - 1);
                            break;
                    }
                } else {
                    value = 0;
                }
                group.put(columns.get(columnIndex),value);
                if(++columnIndex == columnCount) {
                    columnIndex = 0;
                    groups.add(group);
                    group = new TreeMap<>();
                }
            }
            data.put(sheetNames.get(i),groups);
            pages.add(new SheetFormatDescriptor.Page(sheetNames.get(i),columns));
        }
        Sheets sheets = new Sheets(data);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
        SheetFormatDescriptor sheetFormatDescriptor = new SheetFormatDescriptor(
                "UNKNOWN",
                format.format(new Date()),
                "1",
                "PLAYDEF_1",
                joeFile.getName().replaceFirst("\\.json.*$|\\.joe.*$",""),
                pages);
        // No idea what version means either, so setting it to mimic JSON files (they have it set as 4)
        return new JOEFile(sheets, sheetFormatDescriptor, 4);
    }
    private static int decodeInt() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(popBytes(4));
        return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
    private static float decodeFloat() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(popBytes (4));
        return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }
    /**
     * This function decodes a variable length
     * This is probably the most complicate part of JOE files
     * basically, it is a big endian number divided into 7 bit chunks
     * Bit #7 (MSB) indicates that the variable length number is over if it is zero
     * The original function uses uint, but that does not exist in Java, so I used a long instead
     * @return A representation of the variable length in long format
     */
    private static long decodeVariableLength() {
        long res = 0;
        while(byteList.size() > 0) {
            byte b = popByte();
            if((b & 0b10000000) == 0) {
                // If bit #7 is not set
                // Shift over the result and put the last 7 bit chunk on the end
                res = (res << 7 | b);
                return res;
            }
            // Remove bit #7 and stick bits to the end of the result
            res = (res << 7 | (b & 0b01111111));
        }
        logger.log(Level.SEVERE, "Out of bytes when reading variable length number!");
        System.exit(1);
        return 0;
    }
    private static byte[] popBytes(int count) {
        byte[] res = new byte[count];
        for(int i=0;i<count;i++) {
            res[i]=byteList.remove(0);
        }
        return res;
    }
    private static byte popByte() {
        return byteList.remove(0);
    }
    //TODO: write internalJOEtoJOE
    public static void internalJOEtoJOE(JOEFile joe, File output) {

    }
    public static void jsonToJOE(File json, File output) {
        internalJOEtoJOE(jsonToInternalJOE(json), output);
    }
    public static void joeToJSON(File joe, File output) {
        String json = internalJOEToJSON(joeToInternalJOE(joe));
        PrintWriter out;
        try {
            out = new PrintWriter(output);
            out.print(json);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
