package nl.hanze.DataObjects;

import javax.persistence.*;

@Entity
@Table(name = "exit_directions")
public class Exit {
    public Exit() {
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name= "direction")
    private String direction;

    public Exit(String direction) {
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Transient
    public int hashCode(){
        return direction.hashCode();
    }

    @Transient
    public boolean equals(Object o){
        try {
            return ((Exit) o).getDirection().equals(direction);
        } catch (ClassCastException e){
            e.printStackTrace();
        }
        return false;
    }
}
