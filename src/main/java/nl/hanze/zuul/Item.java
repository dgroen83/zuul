package nl.hanze.zuul;

public class Item {
    private String name;
    private String description;
    private int weight;
    private boolean canBePickedUp;
    private String reasonCantBePickedUp;


    Item(String name, String description, int weight, boolean canBePickedUp, String reasonCantBePickedUp) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.canBePickedUp = canBePickedUp;
        this.reasonCantBePickedUp = reasonCantBePickedUp;
    }


    public static class ItemBuilder {
        private String name;
        private String description;
        private int weight;
        private boolean canBePickedUp;
        private String reasonCantBePickedUp;


        public ItemBuilder() {
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public ItemBuilder setCanBePickedUp(boolean canBePickedUp) {
            this.canBePickedUp = canBePickedUp;
            return this;
        }

        public ItemBuilder setReasonCantBePickedUp(String reasonCantBePickedUp) {
            this.reasonCantBePickedUp = reasonCantBePickedUp;
            return this;
        }
        public Item createItem() {
            return new Item(name, description, weight, canBePickedUp, reasonCantBePickedUp);
        }

    }

    public String getName() {
        return this.name;
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

    public String getReasonCantBePickedUp() {
        return reasonCantBePickedUp;
    }
}
