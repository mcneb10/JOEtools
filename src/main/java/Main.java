import JOE.*;
import com.google.gson.Gson;

import java.util.*;

public class Main {
    public static void main(String[] args) {

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
