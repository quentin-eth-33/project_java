package manager;

import building.*;
import resident.Resident;
import resident.Worker;
import resource.Resource;
import resource.ResourceManager;
import resource.ResourceType;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class Manager {
    private Map<BuildingType, List<Building>> buildings;
    private ResourceManager resourceManager;
    private int remainingFoodShortage;
    private boolean gameOver = false;

    private static  final Resource residentCost = new Resource(ResourceType.FOOD, 1);
    private static  final Resource workerCost = new Resource(ResourceType.FOOD, 1);

    public Manager() {
        this.buildings = new HashMap<>();
        this.resourceManager = ResourceManager.getInstance();
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

    public void removeBuilding(Building building) {
        BuildingType buildingType = building.getType();
        List<Building> buildingList = buildings.get(buildingType);
        if (buildingList != null) {
            buildingList.remove(building);
        }
    }

    public void upgradeBuilding(Building building) {
        boolean canUpgrade = true;
        if(building.getMaxLevel() <3){
            for (Resource resource : building.getConstructionCost()) {
                int cost = resource.getQuantity() * (building.getCurrentLevel()+1);
                if(resourceManager.getResource(resource.getType()).getQuantity() < cost){
                    canUpgrade = false;
                    break;
                }
            }
            if(canUpgrade){
                for (Resource resource : building.getConstructionCost()) {
                    int cost = resource.getQuantity() / 2;
                    resourceManager.getResource(resource.getType()).setQuantity(
                            resourceManager.getResource(resource.getType()).getQuantity() - cost);
                }

                building.increaseLevel();
                building.setMaxWorkers(building.getMaxWorkers() * 2);
                building.setMaxResidents(building.getMaxResidents() * 2);

                System.out.println("Le nombre de travailleur et d'habitant du bâtiment " + building.getType() + "a doublée");
            }
            else{
                System.out.println("Pas assez d'ouvier pour améliorer Le batiment: "+building.getType()+" | ID: "+building.getId());
            }

        }
        else{
            System.out.println("Le batiment: "+building.getType()+" | ID: "+building.getId()+" est niveau max");
        }
    }

    public int getNbBuildingType(){
        return buildings.keySet().size();
    }


    public void addResidents(Building building, int numResidents) {
        int availableResidents = building.getMaxResidents() - building.getResidentList().size();
        if(numResidents> availableResidents ){
            System.out.println("Vous n'avez pas assez de place pour ajouter "+numResidents+" habitants");
            return;
        }
        System.out.println("MaxResident: " + building.getMaxResidents() + " | CurrentResident: " + building.getResidentList().size() + " | availableResident: " + availableResidents);

        int totalFoodCost = numResidents * residentCost.getQuantity();
        if (totalFoodCost > resourceManager.getResource(ResourceType.FOOD).getQuantity()) {
            System.out.println("Vous n'avez pas assez de nourriture pour ajouter ces résidents.");
            return;
        }

        for (int i = 0; i < numResidents; i++) {
            building.getResidentList().add(new Resident(building));
        }

        resourceManager.getResource(ResourceType.FOOD).setQuantity(resourceManager.getResource(ResourceType.FOOD).getQuantity() - totalFoodCost);

        System.out.println(numResidents + " Habitants ajoutés au bâtiment " + building.getType() + " | ID: " + building.getId() +
                ". Coût : " + totalFoodCost + " unité(s) de nourriture.");
    }

    public void removeResidents(Building building, int numResidents) {
        int currentResidents = building.getResidentList().size();
        if (numResidents > currentResidents) {
            System.out.println("Nombre de Habitant à supprimer dépasse le nombre actuel de resident.");
            return;
        }

        if (numResidents > 0) {
            building.getResidentList().subList(0, numResidents).clear();
        }
        System.out.println(numResidents + " Habitant(s) retiré(s) du bâtiment " + building.getType() + ".");
    }

    public void addWorkers(Building building, int numWorkers) {
        int availableWorkers = building.getMaxWorkers() - building.getWorkerList().size();
        if(numWorkers> availableWorkers ){
            System.out.println("Vous n'avez pas assez de place pour ajouter "+numWorkers+ " travailleurs");
            return;
        }
        System.out.println("MaxWorker: " + building.getMaxWorkers() + " | CurrentWorker: " + building.getWorkerList().size() + " | availableWorkers: " + availableWorkers);
        int totalFoodCost = numWorkers * workerCost.getQuantity();
        if (totalFoodCost > resourceManager.getResource(ResourceType.FOOD).getQuantity()) {
            System.out.println("Vous n'avez pas assez de nourriture pour ajouter ces travailleurs.");
            return;
        }

        if (numWorkers > 0) {
            building.getWorkerList().subList(0, numWorkers).clear();
        }

        resourceManager.getResource(ResourceType.FOOD).setQuantity(resourceManager.getResource(ResourceType.FOOD).getQuantity() - totalFoodCost);

        System.out.println(numWorkers + " travailleurs ajoutés au bâtiment " + building.getType() + " | ID: " + building.getId() +
                ". Coût : " + totalFoodCost + " unité(s) de nourriture.");
    }




    public void removeWorkers(Building building, int numWorkers) {
        int currentWorkers = building.getWorkerList().size();
        if (numWorkers > currentWorkers) {
            System.out.println("Nombre de travailleurs à supprimer dépasse le nombre actuel de travailleur.");
            return;
        }

        for(int i=0; i<numWorkers; i++){
            building.getWorkerList().remove(i);
        }
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
                if(building.isConstructionComplete()){
                    totalFoodRequired += building.getResidentList().size() + building.getWorkerList().size();
                }
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


