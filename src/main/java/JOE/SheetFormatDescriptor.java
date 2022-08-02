package JOE;

import java.util.List;

/**
 * This Class provides a description of the structure of the JOE file's sheets
 * unknown variables kept for compatibility
 */
public class SheetFormatDescriptor {
    // Hash Format unknown
    public String hash;
    /**
     * Sheet Import Time
     * This time will be set to
     */
    public String importTime;
    /**
     * Importer Version?
     */
    public String importerVersion = "1";
    /**
     * Sheet format
     *      Unknown what this means
     *      Everything seems to have it set as <b>PLAYDEF_1</b>
     */
    public String spreadsheetFormat = "PLAYDEF_1";
    /**
     * Name of imported JOE file
     */
    public String spreadsheetName;
    /**
     * Actual description of sheet structure
     */
    public List<Page> pages;

    /**
     * Representation of <b>individual</b> sheet/page structure
     */
    public static class Page {
        public String pageName;
        public List<String> columns;
        public Page(String pageName, List<String> columns) {
            this.pageName = pageName;
            this.columns = columns;
        }
    }

    public SheetFormatDescriptor(String hash, String importTime, String importerVersion, String spreadsheetFormat, String spreadsheetName, List<Page> pages) {
        this.hash = hash;
        this.importTime = importTime;
        this.importerVersion = importerVersion;
        this.spreadsheetFormat = spreadsheetFormat;
        this.spreadsheetName = spreadsheetName;
        this.pages = pages;
    }
}
