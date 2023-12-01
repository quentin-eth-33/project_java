package manager;

import building.*;
import resource.ResourceManager;
import resource.Resource;
import resource.ResourceType;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Manager {
    private Map<BuildingType, List<Building>> buildings;
    private ResourceManager resourceManager;
    private int remainingFoodShortage;
    private boolean gameOver = false;

    public Manager() {
        this.buildings = new HashMap<>();
        this.resourceManager = new ResourceManager();
        this.remainingFoodShortage = 0;
        initializeBuildings();
    }

    private void initializeBuildings() {
        for (BuildingType buildingType : BuildingType.values()) {
            buildings.put(buildingType, new ArrayList<>());
        }
    }
    public Building getBuildingById(int buildingId) {
        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                if (building.getId() == buildingId) {
                    return building;
                }
            }
        }
        return null;
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


    public void addBuilding(Building building) {
        List<Building> buildingList = buildings.get(building.getType());
        if (buildingList != null) {
            boolean canBuild = true;
            for (Resource cost : building.getConstructionCost()) {
                if (resourceManager.getResource(cost.getType()).getQuantity() < cost.getQuantity()) {
                    canBuild = false;
                    System.out.println("Ressources insuffisantes pour construire le bâtiment.");
                    break;
                }
            }

            if (canBuild) {
                for (Resource cost : building.getConstructionCost()) {
                    resourceManager.getResource(cost.getType())
                            .setQuantity(resourceManager.getResource(cost.getType()).getQuantity() - cost.getQuantity());
                }
                buildingList.add(building);
            }
        }
    }

    public void removeBuilding(BuildingType buildingType, Building building) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null) {
            buildingList.remove(building);
        }
    }

    public void upgradeBuilding(Building building) {
        int upgradeCost = building.getCurrentResidentCapacity() / 2;
        for (Resource resource : building.getConstructionCost()) {
            int cost = resource.getQuantity() / 2;
            resourceManager.getResource(resource.getType()).setQuantity(
                    resourceManager.getResource(resource.getType()).getQuantity() - cost
            );
        }

        int availableGold = resourceManager.getResource(ResourceType.GOLD).getQuantity();

        if (upgradeCost > availableGold) {
            System.out.println("Vous n'avez pas assez d'or pour effectuer cette amélioration.");
            return;
        }

        // Effectuer l'amélioration
        building.setCurrentWorkerCapacity(building.getCurrentWorkerCapacity() * 2);
        building.setCurrentResidentCapacity(building.getCurrentResidentCapacity() * 2);

        System.out.println("Le nombre de travailleur et d'habitant du bâtiment " + building.getType() + "a doublée");
    }



    public void addWorkers(Building building, int numWorkers) {
        int availableWorkers = building.getMaxWorkers() - building.getCurrentWorkerCapacity();
        System.out.println("MaxWorker: "+ building.getMaxWorkers()+" | CurrentWorker: "+building.getCurrentWorkerCapacity()+" | availableWorkers: "+availableWorkers);
        if (numWorkers > availableWorkers) {
            System.out.println("Nombre de travailleurs ajoutés dépasse la limite autorisée.");
            return;
        }

        building.setCurrentWorkerCapacity(building.getCurrentWorkerCapacity() + numWorkers);
        System.out.println(numWorkers + " travailleurs ajoutés au bâtiment " + building.getType() + ".");
    }




    public void removeWorkers(Building building, int numWorkers) {
        int currentWorkers = building.getCurrentWorkerCapacity();
        if (numWorkers > currentWorkers) {
            System.out.println("Nombre de travailleurs à supprimer dépasse le nombre actuel de travailleur.");
            return;
        }

        building.setCurrentWorkerCapacity(Math.max(0, currentWorkers - numWorkers));
        System.out.println(numWorkers + " travailleur(s) retiré(s) du bâtiment " + building.getType() + ".");
    }

    public void construction(){
        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                if (!building.isConstructionComplete()) {
                    building.progressConstructionTime();
                    if (building.isConstructionComplete()) {
                        System.out.println("Construction de " + building.getType().name() + " terminée!");
                    }
                }
            }
        }
    }

    public void manageResources() {
        produceResources();
        consumeResources();
        manageFoodConsumption();
        construction();
    }

    private void produceResources() {
        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                if(building.isConstructionComplete()){
                    List<Resource> production = building.getProduction();
                    for (Resource resource : production) {
                        int producedQuantity = resource.getQuantity();
                        resourceManager.getResource(resource.getType())
                                .setQuantity(resourceManager.getResource(resource.getType()).getQuantity() + producedQuantity);
                    }
                }
            }
        }
    }

    private void consumeResources() {
        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                if(building.isConstructionComplete()) {
                    List<Resource> consumption = building.getConsumption();
                    for (Resource resource : consumption) {
                        int consumedQuantity = resource.getQuantity();
                        ResourceType resourceType = resource.getType();
                        int currentQuantity = resourceManager.getResource(resourceType).getQuantity();

                        if (currentQuantity >= consumedQuantity) {
                            resourceManager.getResource(resourceType)
                                    .setQuantity(currentQuantity - consumedQuantity);
                        } else {
                            // Game Over si les ressources sont insuffisantes
                            System.out.println("Game Over! Ressources insuffisantes pour la consommation de " +
                                    consumedQuantity + " unités de " + resourceType.name() + ".");
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }
    private void manageFoodConsumption() {
        int totalFoodRequired = 0;

        for (List<Building> buildingList : buildings.values()) {
            for (Building building : buildingList) {
                totalFoodRequired += building.getCurrentResidentCapacity() + building.getCurrentWorkerCapacity();
            }
        }

        int nbFoodAvailable = resourceManager.getResource(ResourceType.FOOD).getQuantity();

        if (totalFoodRequired > nbFoodAvailable) {
            System.out.println("Game Over! Nourriture insuffisante pour le besoin total de " +
                    totalFoodRequired + " unités.");
            System.exit(0);
        } else {
            resourceManager.getResource(ResourceType.FOOD).setQuantity(nbFoodAvailable - totalFoodRequired);
        }
    }
}


