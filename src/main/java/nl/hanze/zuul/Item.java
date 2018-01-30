package nl.hanze.zuul;

public class Item {
    private String name;
    private String description;
    private int weight;

    private boolean canBePickedUp = true;

    Item(String name, String description, int weight, boolean canBePickedUp) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.canBePickedUp = canBePickedUp;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean canBePickedUp() {
        return canBePickedUp;
    }

    public int getWeight() {
        return weight;
    }



}
