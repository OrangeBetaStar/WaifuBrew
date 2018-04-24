package parser;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import parser.exception.DialogueDataMissingException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/*
 * parse dialogue info in JSON format
 */
public class DialogueParser {

    private String fileName;
    private String[] dialogueList;

    public DialogueParser(String fileName) {
        this.fileName = fileName;
    }

    public void parse() throws IOException, DialogueDataMissingException, JSONException {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(fileName));
            JSONObject obj = (JSONObject) object;
            JSONArray data = (JSONArray) obj.get("route");
            Iterator i = data.iterator();

            this.dialogueList = new String[data.size()];

            while (i.hasNext()) {
                JSONObject o = (JSONObject) i.next();
                String text = (String) o.get("text");
                /*
                Long l = (Long) o.get("line");
                int index = l.intValue();
                this.dialogueList[index] = text;
                */
                // System.out.println("test: "+text);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String[] getDialogueList() {
        return this.dialogueList;
    }
}
