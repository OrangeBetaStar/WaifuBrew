package start.Containers;

public enum Characters {
    // Must be in alphabetically in order with increasing number

    Ichigaya_Arisa(0),
    Kibbleru(1),
    Nishikino_Maki(2),
    Yazawa_Nico(3);

    public final static int length = Characters.values().length;
    private int value;

    private Characters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }



}
