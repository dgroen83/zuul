package nl.hanze.DAO;


import nl.hanze.DataObjects.Room;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class RoomActor {
    private EntityManagerFactory emf;



    public RoomActor(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Room getRoomFromDb() {
        EntityManager em = emf.createEntityManager();
        Room e = em.find(Room.class, 1);
        em.close();

        return e;
    }

    public Room getCurrentRoom(){
        EntityManager em = emf.createEntityManager();
        String queryStr ="SELECT r FROM Room r WHERE r.currentRoom = 1";
        TypedQuery<Room> query =
                em.createQuery(queryStr, Room.class);
        List<Room> rooms= query.getResultList();
        return rooms.get(0);

    }


}
