package parser;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import parser.exception.DialogueDataMissingException;
import start.Containers.Characters;
import start.Containers.Mood;
import start.Loader.Waifu;

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
    private String tempMoveString = "";
    private List<String> subNameString;
    private List<String> subMoodString;
    private List<List<Waifu>> packagedDialogue = new ArrayList<List<Waifu>>();

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
                if (o.get("name") != null) {
                    tempNameString = (String) o.get("name");
                    tempMoodString = (String) o.get("mood");
                    subNameString = Arrays.asList(tempNameString.split(","));
                    subMoodString = Arrays.asList(tempMoodString.split(","));
                    List<Characters> sceneCharList = new ArrayList<Characters>();
                    List<Mood> sceneMoodList = new ArrayList<Mood>();
                    for (String s : subNameString) {
                        if (s.toLowerCase().contains("nico")) {
                            sceneCharList.add(Characters.Yazawa_Nico);
                            //    System.out.println("Added: Nico");
                        } else if (s.toLowerCase().contains("maki")) {
                            sceneCharList.add(Characters.Nishikino_Maki);
                            //    System.out.println("Added: Maki");
                        } else if (s.toLowerCase().contains("arisa")) {
                            sceneCharList.add(Characters.Ichigaya_Arisa);
                            //    System.out.println("Added: Arisa");
                        } else if (s.toLowerCase().contains("kibb")) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String[] getDialogueList() {
        return dialogueList;
    }

    // Below packages the everything from JSON into single arraylist of waifus
    public List<List<Waifu>> getPackagedDialogue() {
        int lineCounter = 0;
        int characterCounterPerLine = 0;
        while (lineCounter < characterList.size()) {
            List<Waifu> tempWaifu = new ArrayList<Waifu>();
            //    System.out.println("lineCounter"+lineCounter);
            while (characterCounterPerLine < characterList.get(lineCounter).size()) {
                //   System.out.println("characterCounterPerLine"+characterCounterPerLine);
                //   System.out.println("lineCounter: "+lineCounter+" characterCounterPerLine: "+characterCounterPerLine+" character: "+characterList.get(lineCounter).get(characterCounterPerLine)+" mood: "+moodList.get(lineCounter).get(characterCounterPerLine)+" dialogue: "+dialogueList[lineCounter]);
                Waifu e = new Waifu(characterList.get(lineCounter).get(characterCounterPerLine), moodList.get(lineCounter).get(characterCounterPerLine), dialogueList[lineCounter]);
                tempWaifu.add(e);
                characterCounterPerLine++;
            }
            lineCounter++;
            packagedDialogue.add(tempWaifu);
            characterCounterPerLine = 0;
        }
        return packagedDialogue;
    }
}
