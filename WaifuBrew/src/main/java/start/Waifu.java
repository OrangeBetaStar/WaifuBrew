package start;

import java.awt.*;

public class Waifu {
    private Characters name;
    private Mood mood;
    private String dialogue;
    private Point[] position = new Point[2];

    public Waifu() {
        // Shouldn't use this.
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
        this.position[0] = initPoint;
        this.position[1] = finalPoint;
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

    public Point[] getPosition() {
        return this.position;
    }

    public void addMood(Mood mood) {
        this.mood = mood;
    }

    public void addDialogue(String dialogue) {
        this.dialogue = dialogue;
    }
}
