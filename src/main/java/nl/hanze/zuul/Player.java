package nl.hanze.zuul;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private List<Item> backpack;
    private int maxCarrierAmount;

    Player(String nameOfPlayer) {
        this.name = nameOfPlayer;
        setMaxCarrierAmmount();
         backpack = new ArrayList<>();
    }

    public Item getBackpackItem(String itemName) {
        for (Item item : backpack) {
            if(item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getMaxCarrierAmmount() {
        return maxCarrierAmount;
    }

    public void addItemToBackPack(Item item) {
        this.backpack.add(item);
    }

    private void setMaxCarrierAmmount() {
        Random rnd = new Random();
        this.maxCarrierAmount = rnd.nextInt(5)*1000 +1000;
    }

    public int getBackPackWeight() {
        int weight =0;
        for (Item item : backpack) {
            weight += item.getWeight();
        }
        return weight;
    }

    public void printBackPack() {
        if(backpack.size()==0){
            System.out.println("Your backpack seems to be empty");
        }else{
            System.out.println("In your backpack you find:");
        }

        for (Item item : backpack) {
            System.out.println("- "+ item.getDescription());
        }
    }

    public void removeItemFromBackPack(Item item) {
        this.backpack.remove(item);
    }
}
