public class Waifu {
     int weight; //it's kg
     int height; //it's cm

    public Waifu(){
        weight = 0;
        height = 0;
    }

    public Waifu(int weight, int height) {
        this.weight = weight;
        this.height = height;
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
}
