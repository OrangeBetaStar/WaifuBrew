public class Waifu {
    private int weight; //it's kg
    private int height; //it's cm
    private int threeSizes[] = {0,0,0};
    private String name;
    private Mood mood;

    public Waifu(){
        this.weight = 0;
        this.height = 0;
        this.name = "";
        this.mood = Mood.NORMAL;
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
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
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
