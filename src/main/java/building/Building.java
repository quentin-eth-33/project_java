package building;

import resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private static int nextId = 1;
    private int id;
    private BuildingType type;  // Utilisez BuildingType au lieu de String
    private int maxResidents;
    private int maxWorkers;
    private int constructionTime;
    private List<Resource> constructionCost;
    private List<Resource> consumption;
    private List<Resource> production;
    private int currentResidentCapacity;
    private int currentWorkerCapacity;
    private int constructionTimeElapsed;


    public Building(BuildingType type, int maxResidents, int maxWorkers, int constructionTime, List<Resource> constructionCost,
                    List<Resource> consumption, List<Resource> production, int currentResidentCapacity, int currentWorkerCapacity) {
        this.type = type;
        this.maxResidents = maxResidents;
        this.maxWorkers = maxWorkers;
        this.constructionTime = constructionTime;
        this.production = new ArrayList<>(production);
        this.constructionCost = new ArrayList<>(constructionCost);
        this.consumption = new ArrayList<>(consumption);
        this.currentResidentCapacity = currentResidentCapacity;
        this.currentWorkerCapacity = currentWorkerCapacity;
        this.constructionTimeElapsed = 0;
        this.id = nextId++;
    }

    public int getId() {
        return id;
    }
    public boolean isConstructionComplete() {
        return constructionTimeElapsed >= this.constructionTime;
    }

    public void progressConstructionTime() {
        if (!isConstructionComplete()) {
            constructionTimeElapsed++;
        }
    }

    public int getConstructionTimeElapsed(){
        return this.constructionTimeElapsed;
    }
    public BuildingType getType() {
        return type;
    }

    public int getMaxResidents() {
        return maxResidents;
    }

    public void setMaxResidents(int maxResidents) {
        this.maxResidents = maxResidents;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public void setMaxWorkers(int maxWorkers) {
        this.maxWorkers = maxWorkers;
    }

    public int getConstructionTime() {
        return constructionTime;
    }

    public void setConstructionTime(int constructionTime) {
        this.constructionTime = constructionTime;
    }

    public List<Resource> getConstructionCost() {
        return new ArrayList<>(constructionCost);
    }

    public void setConstructionCost(List<Resource> constructionCost) {
        this.constructionCost = new ArrayList<>(constructionCost);
    }

    public List<Resource> getConsumption() {
        return new ArrayList<>(consumption);
    }

    public void setConsumption(List<Resource> consumption) {
        this.consumption = new ArrayList<>(consumption);
    }

    public List<Resource> getProduction() {
        return new ArrayList<>(production);
    }

    public void setProduction(List<Resource> production) {
        this.production = new ArrayList<>(production);
    }

    public int getCurrentResidentCapacity() {
        return currentResidentCapacity;
    }

    public void setCurrentResidentCapacity(int currentResidentCapacity) {
        this.currentResidentCapacity = currentResidentCapacity;
    }

    public int getCurrentWorkerCapacity() {
        return currentWorkerCapacity;
    }

    public void setCurrentWorkerCapacity(int currentWorkerCapacity) {
        this.currentWorkerCapacity = currentWorkerCapacity;
    }
}
