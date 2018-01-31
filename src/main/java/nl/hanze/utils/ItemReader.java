package nl.hanze.utils;

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
        room.addItem(item[1],item[2].replace("\"","" ),Integer.parseInt(item[3]),Boolean.valueOf(item[4]));
    }

}


