import JOE.*;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.File;

public class Main {
    //TODO: Implement command line stuff
    static class CommandOpts {
        @Option(names = {"-e", "--encode"}, description = "Encode JSON as JOE file")
        boolean encode;

        @Option(names = {"-d", "--decode"}, description = "Decode JOE file to JSON")
        boolean decode;

        @Parameters(paramLabel = "INPUTFILE", description = "Input File", defaultValue = "there is no file.")
        String inputfile;

        @Option(names = { "-o", "--output" }, description = "Output File", defaultValue = "there is no file.")
        String outputfile;

        @Option(names = { "-h", "--help" }, description = "Display help and exit", usageHelp = true)
        boolean help;
    }
    // Since assert may be disabled, implement assert that can't be disabled
    private static void assertForceful(boolean condition, String errormessage) {
        if(!condition) {
            error(errormessage);
            System.exit(-1);
        }
    }
    private static void error(String errormessage) {
        System.err.println("ERROR: "+errormessage);
    }
    public static void main(String[] args) throws Exception {
        CommandOpts commandOpts = new CommandOpts();
        CommandLine cmdLine = new CommandLine(commandOpts);
        cmdLine.parseArgs(args);
        // Quit if help is requested
        if(commandOpts.help) {
            cmdLine.usage(System.out);
            System.exit(-1);
        }
        // Sanity Checks
        assertForceful(!(commandOpts.decode&&commandOpts.encode), "You can't encode and decode at the same time!");
        assertForceful(commandOpts.encode || commandOpts.decode || commandOpts.help, "No options specified.\n See help with option --help or -h");
        assertForceful(!commandOpts.inputfile.equals("there is no file."), "No input file specified.");
        File outputfile = commandOpts.outputfile.equals("there is no file.") ? new File(commandOpts.outputfile+".out"): new File(commandOpts.outputfile);
        if(commandOpts.decode) {
            JOEConverter.joeToJSON(new File(commandOpts.inputfile), outputfile);
        } else if(commandOpts.encode) {
            JOEConverter.jsonToJOE(new File(commandOpts.inputfile), outputfile);
        }
    }

    /*
        Example (this will completely recreate the file swcFiles\1543871454\patches\abtests\ab_naval_event_data.json):
        // Content
        TreeMap<String,List<TreeMap<String, Object>>> data = new TreeMap<>();
        List<TreeMap<String, Object>> const1 = new ArrayList<>();
        TreeMap<String, Object> row1 = new TreeMap<>();
        TreeMap<String, Object> row2 = new TreeMap<>();
        row1.put("objectType","Rule");
        row1.put("uid","crate_day_of_the_week_reward");
        row1.put("value","lc_daily_navalEvt lc_daily_navalEvt lc_daily_navalEvt lc_daily_navalEvt lc_daily_navalEvt lc_daily_navalEvt lc_daily_navalEvt");
        row2.put("objectType","Rule");
        row2.put("uid","mobile_connector_video_reward_crate");
        row2.put("value","lc_daily_navalEvt");
        const1.add(row1);
        const1.add(row2);
        data.put("GameConstants",const1);
        Sheets sheets = new Sheets(data);
        // SheetFormatDescriptor
        List<SheetFormatDescriptor.Page> pages = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        columns.add("uid");
        columns.add("objectType");
        columns.add("value");
        SheetFormatDescriptor.Page page = new SheetFormatDescriptor.Page("GameConstants",columns);
        pages.add(page);
        SheetFormatDescriptor sheetFormatDescriptor =
                new SheetFormatDescriptor(
                        "-1067347454",
                        "2/21/18 10:54 PM",
                        "4",
                        "PLAYDEF_1",
                        "ab_naval_event_data",
                        pages
                );
        JOEFile joeFile = new JOEFile(sheets,sheetFormatDescriptor,4);
        System.out.println(gson.toJson(joeFile));
     */
}
