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
            boolean canBuild = true;
            for (Resource cost : buildingType.getConstructionCost()) {
                if (resourceManager.getResource(cost.getType()).getQuantity() < cost.getQuantity()) {
                    canBuild = false;
                    System.out.println("Ressources insuffisantes pour construire le bâtiment.");
                    break;
                }
            }

            if (canBuild) {
                for (Resource cost : buildingType.getConstructionCost()) {
                    resourceManager.getResource(cost.getType())
                            .setQuantity(resourceManager.getResource(cost.getType()).getQuantity() - cost.getQuantity());
                }
                buildingList.add(new Building(buildingType));
            }
        }
    }
    public void removeBuilding(BuildingType buildingType, Building building) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null) {
            buildingList.remove(building);
        }
    }

    public void upgradeBuilding(BuildingType buildingType, int indexBuilding, int numResidents) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null && !buildingList.isEmpty()) {
            Building building = buildingList.get(indexBuilding);

            int upgradeCost = numResidents; // Chaque habitant ajouté coûte 1 unité d'or
            int availableGold = resourceManager.getResource(ResourceType.GOLD).getQuantity();

            if (upgradeCost > availableGold) {
                System.out.println("Vous n'avez pas assez d'or pour effectuer cette amélioration.");
                return;
            }

            // Effectuer l'amélioration
            building.setCurrentResidents(building.getCurrentResidents() + numResidents);
            resourceManager.getResource(ResourceType.GOLD).setQuantity(availableGold - upgradeCost);

            System.out.println(numResidents + " habitant(s) ajouté(s) au bâtiment " + buildingType.name() +
                    ". Coût : " + upgradeCost + " unité(s) d'or.");
        }
    }


    public void addWorkers(BuildingType buildingType, int indexBuilding, int numWorkers) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null && !buildingList.isEmpty()) {
            Building building = buildingList.get(indexBuilding);

            int availableWorkers = building.getType().getMaxWorkers() - building.getCurrentWorkers();
            System.out.println("MaxWorker: "+ building.getType().getMaxWorkers()+" | CurrentWorker: "+building.getCurrentWorkers()+" | availableWorkers: "+availableWorkers);
            if (numWorkers > availableWorkers) {
                System.out.println("Nombre de travailleurs ajoutés dépasse la limite autorisée.");
                return;
            }

            // Ajouter les travailleurs
            building.setCurrentWorkers(building.getCurrentWorkers() + numWorkers);
            System.out.println(numWorkers + " travailleursss ajoutés au bâtiment " + buildingType.name() + ".");
        }
    }


    public void removeWorkers(BuildingType buildingType, int indexBuilding, int numWorkers) {
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null && !buildingList.isEmpty()) {
            Building building = buildingList.get(indexBuilding);

            int currentWorkers = building.getCurrentWorkers();

            if (numWorkers > currentWorkers) {
                System.out.println("Nombre de travailleurs à supprimer dépasse le nombre actuel de travailleur.");
                return;
            }

            // Supprimer les travailleurs
            building.setCurrentWorkers(Math.max(0, currentWorkers - numWorkers));
            System.out.println(numWorkers + " travailleur(s) retiré(s) du bâtiment " + buildingType.name() + ".");
        }
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
                    List<Resource> production = building.getType().getProduction();
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
                    List<Resource> consumption = building.getType().getConsumption();
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
                totalFoodRequired += building.getCurrentResidents() + building.getCurrentWorkers();
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


