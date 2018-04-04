package nl.hanze.zuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static nl.hanze.dbconnection.JdbcZuulConnection.selectRoomDecriptionOnName;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room extends nl.hanze.DataObjects.Room{
    private String description;
    private List<Item> itemsInRoom;
    private HashMap<String, Room> exits;        // stores exits of this room.

    public Room() {

    }

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {
        this(description,null);
    }

    public Room(String roomDescr, HashMap<String, Room> exits) {
        this.description = roomDescr;
        this.exits = exits;
        itemsInRoom = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     *
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setExits(List<String[]> exits) {

        for (String[] exitList : exits) {
            this.exits.put(exitList[0], new Room(selectRoomDecriptionOnName(exitList[1])));
        }

    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Return a description of the room in the form:
     * You are in the kitchen.
     * Exits: north west
     *
     * @return A long description of this room
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();

    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     *
     * @return Details of the room's exits.
     */
    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     *
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {


        return exits.get(direction);
    }

    public String printItemsInRoom() {
        String items = "In the room you see :";
        for (Item item : itemsInRoom) {
            items += "\n- " + item.getDescription() + " (" + item.getName() + ")";
            //items.append("\n- ").append(item.getDescription()).append(" (").append(item.getName()).append(")");
        }
        return items;
    }

    public List<Item> getItemsInRoom() {
        return itemsInRoom;
    }

    public void addItem(String name, String description, int weight, boolean canBePickedUp) {
        this.itemsInRoom.add(new Item.ItemBuilder().setName(name).setDescription(description)
                .setWeight(weight).setCanBePickedUp(canBePickedUp).createItem());
    }

    public void addItem(Item item) {
        this.itemsInRoom.add(item);
    }

    public void removeItemFromRoom(Item i) {
        this.itemsInRoom.remove(i);
    }

    public boolean areItemsLeftInTheRoom() {
        return itemsInRoom.size() != 0;
    }

    public Item getItemByString(String strItem) {
        for (Item item : itemsInRoom) {
            if (item.getName().equals(strItem)) {
                return item;
            }
        }
        return null;
    }


}


