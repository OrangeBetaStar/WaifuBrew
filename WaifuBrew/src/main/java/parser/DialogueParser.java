package parser;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import parser.exception.DialogueDataMissingException;
import start.Characters;
import start.Mood;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DialogueParser {

    private String fileName;
    private String[] dialogueList;
    private List<List<Mood>> moodList = new ArrayList<List<Mood>>(); // Dynamic 2D arraylist
    private List<List<Characters>> characterList = new ArrayList<List<Characters>>(); // Dynamic 2D arraylist
    private String tempNameString = "";
    private String tempMoodString = "";
    private List<String> subNameString;
    private List<String> subMoodString;
    
    /*
    List<List<Foo>> list = new ArrayList<List<Foo>>();

    List<Foo> row1 = new ArrayList<Foo>();
    row1.add(new Foo());
    row1.add(new Foo());
    row1.add(new Foo());
    list.add(row1);

    List<Foo> row2 = new ArrayList<Foo>();
    row2.add(new Foo());
    row2.add(new Foo());

    list.add(row2);
     */

    private int index;

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
                if(o.get("name") != null) {
                    tempNameString = (String) o.get("name");
                    tempMoodString = (String) o.get("mood");
                    subNameString = Arrays.asList(tempNameString.split(","));
                    subMoodString = Arrays.asList(tempMoodString.split(","));
                    List<Characters> sceneCharList = new ArrayList<Characters>();
                    List<Mood> sceneMoodList = new ArrayList<Mood>();
                    for (String s : subNameString) {
                        if(s.toLowerCase().contains("nico")) {
                            sceneCharList.add(Characters.Yazawa_Nico);
                        //    System.out.println("Added: Nico");
                        }
                        else if(s.toLowerCase().contains("maki")) {
                            sceneCharList.add(Characters.Nishikino_Maki);
                        //    System.out.println("Added: Maki");
                        }
                        else if(s.toLowerCase().contains("arisa")) {
                            sceneCharList.add(Characters.Ichigaya_Arisa);
                        //    System.out.println("Added: Arisa");
                        }
                        else if(s.toLowerCase().contains("kibb")) {
                            sceneCharList.add(Characters.Kibbleru);
                        //    System.out.println("Added: Kibb");
                        }
                    }

                    do {
                        for (String m : subMoodString) {
                            if (m.toLowerCase().contains("happy")) {
                                sceneMoodList.add(Mood.HAPPY);
                            } else if (m.toLowerCase().contains("sad")) {
                                sceneMoodList.add(Mood.SAD);
                            } else if (m.toLowerCase().contains("normal")) {
                                sceneMoodList.add(Mood.NORMAL);
                            } else if (m.toLowerCase().contains("angry")) {
                                sceneMoodList.add(Mood.ANGRY);
                            }
                        }

                    } while (sceneCharList.size() != sceneMoodList.size());

                    characterList.add(sceneCharList);
                    moodList.add(sceneMoodList);
                }
                this.dialogueList[index] = (String) o.get("text");

                index++;
            }

            // LIST VIEWER (CHARACTERS FIRST / MOODS LATER)

            /*
            for(List<Characters> n : characterList){
                for(Characters m : n) {
                    System.out.print(m+" ");
                }
                System.out.println();
            }
            for(List<Mood> n : moodList){
                for(Mood m : n) {
                    System.out.print(m+" ");
                }
                System.out.println();
            }
            */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<List<Mood>> getMoodList() {
        return this.moodList;
    }

    public List<List<Characters>> getCharacterList() {
        return this.characterList;
    }

    public String[] getDialogueList() {
        return this.dialogueList;
    }
}
