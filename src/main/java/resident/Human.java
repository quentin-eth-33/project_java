package resident;

import building.Building;

public abstract class Human {
    private static int nextId = 1;
    private int id;
    private Building location;

    public Human(Building residence) {
        this.id = nextId++;
        this.location = residence;
    }

    public int getId() {
        return id;
    }

    public Building getLocation() {
        return location;
    }
    public void setLocation(Building location){
        this.location = location;
    }

}