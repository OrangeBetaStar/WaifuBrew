package start.Loader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileExporter {

    final String RESOUCE_PATH = WaifuBrew.getInstance().RESOURCE_PATH;

    public FileExporter() {

    }

    public void exportSettings(HashMap<String, Integer> configNum, HashMap<String, String> configStr) {
        // The highest JSON object
        JSONObject user = new JSONObject();
        // Config array "[" will go inside "{"
        JSONArray configs = new JSONArray();
        // Setting object "{" will go inside "["
        JSONObject settings = new JSONObject();
        for (String name: configNum.keySet().toArray(new String[0])){
            settings.put(name, configNum.get(name).toString());
        }
        for (String name: configStr.keySet().toArray(new String[0])){
            settings.put(name, configStr.get(name));
        }
        configs.add(settings);
        user.put("user", configs);
        try (FileWriter file = new FileWriter(RESOUCE_PATH + "user.json")) {
            file.write(user.toJSONString());
            System.out.println("Successfully saved a settings to file.");
        } catch (IOException e) {
            System.out.println("There was error while trying to write save file.");
        }
    }

    public void exportGameSaves() {

    }
}
