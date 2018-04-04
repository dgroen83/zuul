package nl.hanze.DataObjects;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room  {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "currentroom")
    private int currentRoom;

    @ManyToMany(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "exit_id")
    @JoinTable(
            name = "rooms_has_exit",
            joinColumns = @JoinColumn(name = "rooms_id"),
            inverseJoinColumns = @JoinColumn(name = "roomIdExit"))
    private Map<Exit, Room> exits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Exit, Room> getExits() {
        return exits;
    }

    public void setExits(Map<Exit, Room> exits) {
        this.exits = exits;
    }

    /**
     * Return a description of the room in the form:
     * You are in the kitchen.
     * Exits: north west
     *
     * @return A long description of this room
     */
    @Transient
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();

    }
    @Transient
    private String getExitString() {

        final StringBuilder returnString = new StringBuilder().append("Exits: ");
        exits.forEach((k,v) -> returnString.append(" ").append(k.getDirection()));
        return returnString.toString();

    }

}
