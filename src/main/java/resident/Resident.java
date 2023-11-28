package resident;
import building.Building;
public class Resident {
    private Building residence;

    public Resident(Building residence) {
        this.residence = residence;
    }

    public Building getResidence() {
        return residence;
    }
}