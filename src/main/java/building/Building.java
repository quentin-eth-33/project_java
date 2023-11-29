package building;

import resident.Resident;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private BuildingType type;
    private int currentResidents;
    private int currentWorkers;
    private List<Resident> residents;

    public Building(BuildingType type) {
        this.type = type;
        this.currentResidents = 0;
        this.currentWorkers = 0;
        this.residents = new ArrayList<>();
        initializeResidents();
    }

    private void initializeResidents() {
        for (int i = 0; i < currentResidents; i++) {
            residents.add(new Resident(this));
        }
    }

    public BuildingType getType() {
        return type;
    }

    public int getCurrentResidents() {
        return currentResidents;
    }

    public void setCurrentResidents(int currentResidents) {
        this.currentResidents = currentResidents;
    }

    public int getCurrentWorkers() {
        return currentWorkers;
    }

    public void setCurrentWorkers(int currentWorkers) {
        this.currentWorkers = currentWorkers;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void evolveBuilding() {
        System.out.println("Évolution du bâtiment : " + type);
    }

    public void displayBuildingInfo() {
        System.out.println("Type de bâtiment : " + type);
        System.out.println("Nombre actuel d'habitants : " + currentResidents);
        System.out.println("Nombre actuel de travailleurs : " + currentWorkers);
        System.out.println("Coût de construction : " + type.getConstructionCost());
        System.out.println("Production : " + type.getProduction());
        System.out.println("Consommation : " + type.getConsumption());
        System.out.println("Liste des habitants : " + residents.size());
    }
}