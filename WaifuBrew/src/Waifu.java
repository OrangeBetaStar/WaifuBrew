public class Waifu {
    int weight; //it's kg
    int height; //it's cm
    int[] threeSizes = new int[3];
    String name;
    Mood mood;

    public Waifu(){
        weight = 0;
        height = 0;
        threeSizes[0] = 0;
        threeSizes[1] = 0;
        threeSizes[2] = 0;
        name = "";
        mood = Mood.NORMAL;
    }

    public Waifu(String name, int weight, int height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
    }

    public Waifu(String name, int weight, int height, int[] threeSizes) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.threeSizes = threeSizes;
    }

    public Waifu(String name, int weight, int height, int[] threeSizes, Mood mood) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.threeSizes = threeSizes;
        this.mood = mood;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int Height) {
        this.height = Height;
    }

    public int[] getThreeSizes() {
        return threeSizes;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Mood getMood() {
        return this.mood;
    }
}
