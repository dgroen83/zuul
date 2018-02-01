package nl.hanze.utils;

import nl.hanze.zuul.Item;
import nl.hanze.zuul.Room;

import java.io.*;
import java.util.Map;

public class ItemReader {

    public static void placeItemsInRooms(Map<Room, String> rooms) {
         BufferedReader reader = null;
        String line;
        String cvsSplitBy = ";";
        File file;
        try {
            ClassLoader classLoader = ItemReader.class.getClassLoader();
            if (!(null == classLoader.getResource("items/items.csv"))) {
             file = new File(classLoader.getResource("items/items.csv").getFile());
                reader = new BufferedReader(new FileReader(file));
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] item = line.split(cvsSplitBy);
                    for (Map.Entry<Room, String> entry : rooms.entrySet()) {
                        if(item[0].equals(entry.getValue())){
                            placeItemInRoom(entry.getKey(),item);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void placeItemInRoom(Room room, String[] item){
        String name= item[1];
        String description =item[2].replace("\"","" );
        int weight= Integer.parseInt(item[3]);
        boolean canBePickedUp =Boolean.valueOf(item[4]);
        String reasonCantBePickedUp = (item.length>5)?item[5]:"";

        room.addItem(new Item.ItemBuilder().setName(name).setDescription(description)
                .setWeight(weight).setCanBePickedUp(canBePickedUp).setReasonCantBePickedUp(reasonCantBePickedUp).createItem());

    }

}


