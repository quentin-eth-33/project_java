package building;

import resident.Resident;
import resident.Worker;
import resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private static int maxLevel =3;
    private static int nextId = 1;
    private int id;
    private BuildingType type;  // Utilisez BuildingType au lieu de String
    private int maxResidents;
    private int maxWorkers;
    private int constructionTime;
    private List<Resource> constructionCost;
    private List<Resource> consumption;
    private List<Resource> production;

    private int constructionTimeElapsed;
    private int currentLevel;
    private List<Resident> residentList = new ArrayList<>();;
    private List<Worker> workerList = new ArrayList<>();;


    public Building(BuildingType type, int maxResidents, int maxWorkers, int constructionTime, List<Resource> constructionCost,
                    List<Resource> consumption, List<Resource> production, int nbResident, int nbWorker) {
        this.type = type;
        this.maxResidents = maxResidents;
        this.maxWorkers = maxWorkers;
        this.constructionTime = constructionTime;
        this.production = new ArrayList<>(production);
        this.constructionCost = new ArrayList<>(constructionCost);
        this.consumption = new ArrayList<>(consumption);
        this.constructionTimeElapsed = 0;
        this.id = nextId++;
        this.currentLevel = 1;
        for(int i = 0; i< nbResident; i++){
            residentList.add(new Resident(this));
        }
        for(int i = 0; i< nbWorker; i++){
            workerList.add(new Worker(this));
        }
    }

    public List<Resident> getResidentList(){
        return this.residentList;
    }

    public List<Worker> getWorkerList(){
        return this.workerList;
    }

    public int getMaxLevel(){
        return this.maxLevel;
    }

    public int getCurrentLevel(){
        return this.currentLevel;
    }

    public void increaseLevel(){
        this.currentLevel = currentLevel+1;
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

}
