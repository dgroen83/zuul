package nl.hanze.DAO;


import nl.hanze.DataObjects.Room;
import org.junit.Test;

import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;


public class RoomActorTest {

    @Test
    public void getRoomDescription() {
        RoomActor actor = new RoomActor(Persistence.createEntityManagerFactory("nl.hanze.DataObjects"));
       // RoomExitActor actorE = new RoomExitActor(Persistence.createEntityManagerFactory("nl.hanze.DataObjects"));
        Room room = actor.getRoomFromDb();
        System.out.println(room.getLongDescription());

      //  RoomExits exits = actorE.getRoomExitFromDb();
        assertEquals(room.getDescription(), "outside the main entrance of the university");
    }

    @Test
    public void getCurrentRoom(){
        RoomActor actor = new RoomActor(Persistence.createEntityManagerFactory("nl.hanze.DataObjects"));
        // RoomExitActor actorE = new RoomExitActor(Persistence.createEntityManagerFactory("nl.hanze.DataObjects"));
        Room room = actor.getCurrentRoom();
        //  RoomExits exits = actorE.getRoomExitFromDb();
        assertEquals(room.getDescription(), "outside the main entrance of the university");
    }

}
