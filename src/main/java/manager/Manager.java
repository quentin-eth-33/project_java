package manager;

import building.Building;
import building.BuildingType;
import resident.Resident;
import resource.ResourceManager;
import resource.Resource;
import resource.ResourceType;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class Manager {
    private Map<BuildingType, List<Building>> buildings;
    private ResourceManager resourceManager;

    public Manager() {
        this.buildings = new HashMap<>();
        this.resourceManager = new ResourceManager();
        initializeBuildings();
    }

    private void initializeBuildings() {
        for (BuildingType buildingType : BuildingType.values()) {
            buildings.put(buildingType, new ArrayList<>());
        }
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
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
                List<Resource> consumption = building.getType().getConsumption();
                List<Resource> production = building.getType().getProduction();

                for (Resource resource : consumption) {
                    int consumedQuantity = resource.getQuantity();
                    resourceManager.getResource(resource.getType())
                            .setQuantity(resourceManager.getResource(resource.getType()).getQuantity() - consumedQuantity);
                }

                for (Resource resource : production) {
                    int producedQuantity = resource.getQuantity();
                    resourceManager.getResource(resource.getType())
                            .setQuantity(resourceManager.getResource(resource.getType()).getQuantity() + producedQuantity);
                }
            }
        }
    }


    public void manageFoodConsumption() {
        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                List<Resident> residents = building.getResidents();

                // Consommation de nourriture par les habitants
                for (Resident resident : residents) {
                    Resource foodResource = resourceManager.getResource(ResourceType.FOOD);
                    int foodConsumption = residents.size(); // Chaque habitant consomme une unité de nourriture
                    if (foodResource.getQuantity() >= foodConsumption) {
                        foodResource.setQuantity(foodResource.getQuantity() - foodConsumption);
                    } else {
                        // Gérer les conséquences de la famine, par exemple, diminuer la population ou le bonheur des habitants
                    }
                }
            }
        }
    }
}