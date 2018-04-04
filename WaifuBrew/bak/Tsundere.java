public class Tsundere extends Waifu{

    private Mood mood;
    public Tsundere () {
        super();
    }

    public Tsundere(String name, int weight, int height) {
        super(name, weight, height);
        this.mood = Mood.ANGRY;
    }

}
