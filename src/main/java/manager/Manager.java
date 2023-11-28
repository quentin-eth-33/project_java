package manager;

import building.Building;
import building.BuildingType;
import resident.Resident;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class Manager {
    private Map<BuildingType, List<Building>> buildings;

    public Manager() {
        this.buildings = new HashMap<>();
        initializeBuildings();
    }

    private void initializeBuildings() {
        for (BuildingType buildingType : BuildingType.values()) {
            buildings.put(buildingType, new ArrayList<>());
        }
    }

    public List<Building> getBuildings(BuildingType buildingType) {
        return buildings.get(buildingType);
    }

    public void addBuilding(BuildingType buildingType) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null) {
            buildingList.add(new Building(buildingType));
        }
    }
    public void removeBuilding(BuildingType buildingType, Building building) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null) {
            buildingList.remove(building);
        }
    }

    public void addWorkers(BuildingType buildingType, int indexBuilding, int numWorkers) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null && !buildingList.isEmpty()) {
            Building building = buildingList.get(indexBuilding);
            building.setCurrentWorkers(building.getCurrentWorkers() + numWorkers);
        }
    }


    public void removeWorkers(BuildingType buildingType, int indexBuilding, int numWorkers) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null && !buildingList.isEmpty()) {
            Building building = buildingList.get(indexBuilding);
            int currentWorkers = building.getCurrentWorkers();
            building.setCurrentWorkers(Math.max(0, currentWorkers - numWorkers));
        }
    }

    public void manageResources() {
        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                // Implémentez la logique de consommation et de production des ressources
            }
        }
    }

    public void manageFoodConsumption() {
        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                for (Resident resident : building.getResidents()) {
                    // Implémentez la logique de consommation de nourriture par les habitants
                }
            }
        }
    }
}