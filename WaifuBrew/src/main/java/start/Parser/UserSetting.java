package start.Parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

public class UserSetting {

    private String fileName;
    private HashMap<String, String> settingLoad;
    public UserSetting(String fileName) {
        this.fileName = fileName;
    }

    public void parse() {
        JSONParser parser = new JSONParser();

        try {
            Object object = parser.parse(new FileReader(fileName));
            JSONObject obj = (JSONObject) object;
            JSONArray data = (JSONArray) obj.get("user");
            Iterator i = data.iterator();
            settingLoad = new HashMap<>();
            while (i.hasNext()) {
                JSONObject o = (JSONObject) i.next();

                // Convert keyset to array to iterate through with control
                String[] arrayString = (String[])o.keySet().toArray(new String[0]);

                for(String keyThings : arrayString) {
                    settingLoad.put(keyThings, (String) o.get(keyThings));
                    System.out.println(keyThings + " : " + o.get(keyThings));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file \"user.json\" was not found to load settings! Loading to default.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap getLoadedSettings() {
        if(settingLoad != null) {
            return settingLoad;
        }
        return null;
    }
}
