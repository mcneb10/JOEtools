package JOE;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This class is a representation of a JOE file designed to be serialized into a JSON file
 * It contains a list of S
 */
public class JOEFile {
    /**
     * Wrapper for sheets
     */
    public Sheets content;
    @SerializedName("spreadsheetInfo")
    public SheetFormatDescriptor sheetFormatDescriptor;
    public int version;

    public JOEFile(Sheets content, SheetFormatDescriptor sheetFormatDescriptor, int version) {
        this.content = content;
        this.sheetFormatDescriptor = sheetFormatDescriptor;
        this.version = version;
    }
}
