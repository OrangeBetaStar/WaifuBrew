package start.Parser;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import start.Parser.ParserException.DialogueDataMissingException;
import start.Containers.Characters;
import start.Containers.Mood;
import start.Loader.Waifu;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DialogueParser {

    private String fileName;
    private String[] dialogueList;
    private HashMap<Integer, String> backgroundList = new HashMap<>();
    private List<List<Mood>> moodList = new ArrayList<List<Mood>>(); // Dynamic 2D arraylist
    private List<List<Characters>> characterList = new ArrayList<List<Characters>>(); // Dynamic 2D arraylist
    private String[] tempAttributeString = new String[3]; // [0]name [1]mood [2]bg
    private List<List<Waifu>> packagedDialogue = new ArrayList<>();
    private int index;

    public DialogueParser(String fileName) {
        this.fileName = fileName;
    }

    public void parse() throws IOException, DialogueDataMissingException, JSONException {
        List<String> subNameString;
        List<String> subMoodString;
        JSONParser parser = new JSONParser();

        try {
            Object object = parser.parse(new FileReader(fileName));
            JSONObject obj = (JSONObject) object;
            JSONArray data = (JSONArray) obj.get("route");
            Iterator i = data.iterator();
            String stringProcessor[];

            this.dialogueList = new String[data.size()];

            while (i.hasNext()) {
                JSONObject o = (JSONObject) i.next();
                if (o.get("name") != null) {
                    tempAttributeString[0] = (String) o.get("name");
                    tempAttributeString[1] = (String) o.get("mood");
                    tempAttributeString[2] = (String) o.get("bg");
                    subNameString = Arrays.asList(tempAttributeString[0].split(","));
                    subMoodString = Arrays.asList(tempAttributeString[1].split(","));
                    if (subMoodString.size() == 1 || subMoodString.size() == subNameString.size()) {
                        List<Characters> sceneCharList = new ArrayList<>();
                        List<Mood> sceneMoodList = new ArrayList<>();
                        for (String s : subNameString) {
                            nextChar:
                            for(Characters chars : Characters.values()) {
                                stringProcessor = chars.toString().split("_");
                                for(String nameParts : stringProcessor) {
                                    if(s.contains(nameParts)) {
                                        sceneCharList.add(chars);
                                        break nextChar;
                                    }
                                }
                            }
                        }
                        do {
                            for (String m : subMoodString) {
                                nextMood:
                                for(Mood moods : Mood.values()) {
                                    stringProcessor = moods.toString().split("_");
                                    for(String moodParts : stringProcessor) {
                                        if(m.contains(moodParts)) {
                                            sceneMoodList.add(moods);
                                            break nextMood;
                                        }
                                    }
                                }
                            }

                        } while (sceneCharList.size() != sceneMoodList.size());

                        if (tempAttributeString[2] != null) {
                            backgroundList.put(index, tempAttributeString[2]);
                            tempAttributeString[2] = null;
                        }
                        characterList.add(sceneCharList);
                        moodList.add(sceneMoodList);
                        this.dialogueList[index] = o.get("text") == null ? "" : o.get("text").toString();
                        index++;
                    }
                    else {
                        // throw new DialogueDataMissingException("There was imbalance of CHAR:MOOD in JSON. Index: " + (index + 1) + ". The line was skipped automatically.");
                        System.out.println("There was imbalance of CHAR:MOOD in JSON. Index: " + (index + 1) + ". The line was skipped automatically.");
                    }

                }
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
            while (characterCounterPerLine < characterList.get(lineCounter).size()) {
                if (backgroundList.get(lineCounter) != null) {
                    Waifu e = new Waifu(characterList.get(lineCounter).get(characterCounterPerLine), moodList.get(lineCounter).get(characterCounterPerLine), dialogueList[lineCounter], backgroundList.get(lineCounter));
                    tempWaifu.add(e);
                } else {
                    Waifu e = new Waifu(characterList.get(lineCounter).get(characterCounterPerLine), moodList.get(lineCounter).get(characterCounterPerLine), dialogueList[lineCounter]);
                    tempWaifu.add(e);
                }
                characterCounterPerLine++;
            }
            lineCounter++;
            packagedDialogue.add(tempWaifu);
            characterCounterPerLine = 0;
        }
        return packagedDialogue;
    }
}
