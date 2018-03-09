package nl.hanze.zuul;

import java.util.*;

public class TransporterRoom extends Room{
    private List<Room> rooms;

    public TransporterRoom(String description, Collection<Room> rooms) {
        super(description);
        this.rooms = new ArrayList<>(rooms);
        setExit("trannsporter", this);
    }

    @Override
    public Room getExit(String discription){
        return findRandomRoom();
    }

    private Room findRandomRoom() {
        Random random = new Random();
        int selector = random.nextInt(rooms.size());
        return rooms.get(selector);
    }

}
