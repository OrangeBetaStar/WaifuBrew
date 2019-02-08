package start.Parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DefaultLoader {

    private String fileName;
    private String category;
    private HashMap<String, String> settingLoad;
    private ArrayList<HashMap> multipleObjectArrayList = new ArrayList<>();
    public DefaultLoader(String fileName, String category) {
        this.fileName = fileName;
        this.category = category;
    }

    public void parse() {
        JSONParser parser = new JSONParser();

        try {
            Object object = parser.parse(new FileReader(fileName));
            JSONObject obj = (JSONObject) object;
            JSONArray data = (JSONArray) obj.get(category);
            Iterator i = data.iterator();
            while (i.hasNext()) {
                JSONObject o = (JSONObject) i.next();
                String[] arrayString = (String[])o.keySet().toArray(new String[0]);
                settingLoad = new HashMap<>();
                for(String keyThings : arrayString) {
                    settingLoad.put(keyThings, (String) o.get(keyThings));
                }
                multipleObjectArrayList.add(settingLoad);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file \"" + fileName + "\" was not found to load settings! Loading to default.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LastObject will be just HashMap (good for single user config settings)
    public HashMap getLastObject() {
        if(settingLoad != null) {
            return settingLoad;
        }
        return null;
    }

    // MultipleObject will be Arraylist with HashMap inside. (good for multiple user saves)
    public ArrayList getMultipleObject() {
        if(!multipleObjectArrayList.isEmpty()) {
            return multipleObjectArrayList;
        }
        return null;
    }
}
