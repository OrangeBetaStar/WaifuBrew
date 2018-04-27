package start;

import java.awt.*;

public class Waifu {
    private Characters name;
    private Mood mood;
    private String dialogue;
    private Point initPoint;
    private Point finalPoint;

    public Waifu() {

    }

    public Waifu(Characters name) {
        this.name = name;
    }

    public Waifu(Characters name, Mood mood, String dialogue) {
        this.name = name;
        this.mood = mood;
        this.dialogue = dialogue;
    }

    public Waifu(Characters name, Mood mood, String dialogue, Point initPoint, Point finalPoint) {
        this.name = name;
        this.mood = mood;
        this.dialogue = dialogue;
        this.initPoint = initPoint;
        this.finalPoint = finalPoint;
    }

    public Characters getName() {
        return this.name;
    }

    public Mood getMood() {
        return this.mood;
    }

    public String getDialogue() {
        return this.dialogue;
    }

    public void addMood(Mood mood) {
        this.mood = mood;
    }

    public void addDialogue (String dialogue) {
        this.dialogue = dialogue;
    }
}
