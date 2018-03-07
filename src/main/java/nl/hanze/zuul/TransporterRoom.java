package nl.hanze.zuul;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TransporterRoom extends Room{
    private Map<Room, String> rooms = new HashMap<>();

    public TransporterRoom(String description, Map<Room,String> rooms) {
        super(description);
        this.rooms = rooms;
    }

    @Override
    public Room getExit(String discription){
        super.setExit(" north", new Room("fake"));
        super.setExit(" south", new Room("fake"));
        super.setExit(" west", new Room("fake"));
        super.setExit(" east", new Room("fake"));
        return findRandomRoom();
    }

    private Room findRandomRoom() {
        Random random = new Random();
        int selector = random.nextInt(rooms.size());
        int loop = 0;
        for (Map.Entry<Room, String> entry : rooms.entrySet()) {
            if (loop==selector){
                System.out.println("Zaaaaapppp. de transporter room transported you to the "+ entry.getValue());
                return entry.getKey();
            }
            loop++;
        }
        return null;
    }

}
