package nl.hanze.zuul;

public class Item {
    private String name;
    private String description;
    private int weigth;

    public Item(String name, String description, int weight) {
        this.name = name;
        this.description = description;
        this.weigth = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }


}
