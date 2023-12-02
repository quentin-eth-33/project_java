package gameInterface;


import building.*;
import manager.Manager;
import resource.ResourceType;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Manager gameManager;
    private Scanner scanner;

    private int timeUnit;

    public UserInterface(Manager gameManager) {
        this.gameManager = gameManager;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        this.timeUnit = 1;

        while (true) {
            printGameState();

            System.out.println("\nOptions :");
            System.out.println("1. Passer une unité de temps");
            System.out.println("2. Construire un bâtiment");
            System.out.println("3. Ajouter des travailleurs / habitants à un bâtiment");
            System.out.println("4. Supprimer des travailleurs / habitants d'un bâtiment");
            System.out.println("5. Faire evoluer un batiment");
            System.out.println("6. Quitter");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    actionPerfomed();
                    break;
                case 2:
                    buildBuilding();
                    break;
                case 3:
                    addPeople();
                    break;
                case 4:
                    removePeople();
                    break;
                case 5:
                    upgradeBuilding();
                    break;
                case 6:
                    System.out.println("Merci d'avoir joué !");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }

            System.out.println("----------------------------------------------------------------------");
        }
    }
    private void actionPerfomed(){
        gameManager.manageResources();
        timeUnit++;
    }

    private void buildBuilding() {
        System.out.println("\nTypes de bâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }

        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= BuildingType.values().length) {
            BuildingType selectedBuildingType = BuildingType.values()[choice - 1];

            actionPerfomed();
            gameManager.addBuilding(getBuilding(selectedBuildingType));

            System.out.println("Bâtiment " + selectedBuildingType.name() + " en construction.");
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }

    private void upgradeBuilding() {
        System.out.println("\nBâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }

        int buildingChoice = scanner.nextInt();


        if (buildingChoice >= 1 && buildingChoice <= BuildingType.values().length) {
            BuildingType selectedBuildingType = BuildingType.values()[buildingChoice - 1];
            List<Building> buildings = gameManager.getBuildings(selectedBuildingType);


            // Affiche la liste des bâtiments du type sélectionné
            displayBuildings(buildings);

            System.out.println("Entrez l'ID du bâtiment à améliorer :");
            int selectedBuildingID = scanner.nextInt();


                Building building = gameManager.getBuildingById(selectedBuildingID);
                if(building != null){
                    gameManager.upgradeBuilding(building);
                    actionPerfomed();
                }
                else {
                    System.out.println("Batiment non trouvee.");
                }
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }


    private void addPeople() {
        System.out.println("\nTypes de bâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }
        System.out.println("Choisissez le bâtiment où ajouter des personnes :");
        int selectedBuildingIndex = scanner.nextInt();

        BuildingType selectedBuildingType = BuildingType.values()[selectedBuildingIndex - 1];
        List<Building> buildings = gameManager.getBuildings(selectedBuildingType);

        System.out.println("Bâtiments disponibles pour " + selectedBuildingType.name() + " :");
        displayBuildings(buildings);

        if (selectedBuildingIndex >= 1 && selectedBuildingIndex <= buildings.size()) {

            System.out.println("Entrez l'ID du batiment "+buildings.get(0).getType().name()+" où ajouter des personnes :");
            int batID = scanner.nextInt();

            Building building = gameManager.getBuildingById(batID);
            boolean isBuilt = building.isConstructionComplete();
            if(building != null){
                if(isBuilt){
                    System.out.println("Entrez 1 pour ajouter des habitants | Entrez 2 pour ajouter des travailleurs");
                    int peopleToAdd = scanner.nextInt();
                    if(peopleToAdd ==1){

                        System.out.println("Entrez le nombre d'habitants à ajouter:");
                        int numResident = scanner.nextInt();
                        gameManager.addResidents(building, numResident);
                        actionPerfomed();
                    }else if(peopleToAdd ==2){
                        System.out.println("Entrez le nombre de travailleurs à ajouter:");
                        int numWorker = scanner.nextInt();
                        gameManager.addWorkers(building, numWorker);
                        actionPerfomed();
                    }else{
                        System.out.println("Choix non valide. Veuillez réessayer.");
                    }
                }
                else{
                    System.out.println("Le Batiment est en construction.");
                }
            }
            else {
                System.out.println("Batiment non trouvee.");
            }
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }

    }


    private void removePeople() {
        System.out.println("\nTypes de bâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }
        System.out.println("Choisissez le bâtiment où supprimer les personnes :");
        int selectedBuildingIndex = scanner.nextInt();

        BuildingType selectedBuildingType = BuildingType.values()[selectedBuildingIndex - 1];
        List<Building> buildings = gameManager.getBuildings(selectedBuildingType);

        System.out.println("Bâtiments disponibles pour " + selectedBuildingType.name() + " :");
        displayBuildings(buildings);

        if (selectedBuildingIndex >= 1 && selectedBuildingIndex <= buildings.size()) {

            System.out.println("Entrez l'ID du bâtiment " + buildings.get(0).getType().name() + " où supprimer des personnes  :");
            int batID = scanner.nextInt();

            Building building = gameManager.getBuildingById(batID);
            boolean isBuilt = building.isConstructionComplete();
            if (building != null) {
                if(isBuilt){
                    System.out.println("Entrez 1 pour supprimer des habitants | Entrez 2 pour ajouter des travailleurs");
                    int peopleToRemove = scanner.nextInt();
                    if(peopleToRemove ==1){

                        System.out.println("Entrez le nombre d'habitants à supprimer:");
                        int numResident = scanner.nextInt();
                        gameManager.removeResidents(building, numResident);
                        actionPerfomed();
                    }else if(peopleToRemove ==2){
                        System.out.println("Entrez le nombre de travailleurs à supprimer:");
                        int numWorker = scanner.nextInt();
                        gameManager.removeWorkers(building, numWorker);
                        actionPerfomed();
                    }else{
                        System.out.println("Choix non valide. Veuillez réessayer.");
                    }
                }
                else{
                    System.out.println("Le Batiment est en construction.");
                }

            } else {
                System.out.println("Bâtiment non trouvé.");
            }
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }



    private void displayBuildings(List<Building> buildings) {
        for (Building building : buildings) {
            if(building.isConstructionComplete()){
                System.out.println("ID: "+building.getId()+" Type: " + building.getType().name() +
                        " (Habitants : " + building.getResidentList().size() + "/" + building.getMaxResidents() +
                        ", Travailleurs : " + building.getWorkerList().size() + "/" + building.getMaxWorkers() + ")");
            }
        }
    }

    private void printBuildingState() {
        System.out.println("\n=== État actuel des bâtiments ===");
        for (BuildingType buildingType : BuildingType.values()) {
            List<Building> buildings = gameManager.getBuildings(buildingType);
            if (!buildings.isEmpty()) {
                System.out.println(buildingType.name() + " :");
                for (Building building : buildings) {
                    if (building.isConstructionComplete()) {
                        System.out.println("  - ID: "+building.getId()+" | " + building.getType().name() +
                                " (Construit, Habitants : " + building.getResidentList().size() + "/" + building.getMaxResidents() +
                                ", Travailleurs : " + building.getWorkerList().size() + "/" + building.getMaxWorkers() + ")");
                    } else {
                        System.out.println("  - ID: "+building.getId()+" | " + building.getType().name() +
                                " (En construction, Temps restant : " + (building.getConstructionTime() - building.getConstructionTimeElapsed()) +
                                " unité(s) de temps)");
                    }
                }
            }
        }
    }
    private Building getBuilding(BuildingType buildingType){
        BuildingFactory buildingFactory;
        BuildingFactory building;
        switch (buildingType) {
            case WOODEN_CABIN:
                buildingFactory = new WoodenCabinFactory();
                break;
            case HOUSE:
                buildingFactory = new HouseFactory();
                break;
            // Ajoutez d'autres cas pour chaque type de bâtiment
            case TOOL_FACTORY:
                buildingFactory = new ToolFactory();
                break;
            case STEEL_MILL:
                buildingFactory = new SteelMillFactory();
                break;
            case CEMENT_PLANT:
                buildingFactory = new CementPlantFactory();
                break;
            case LUMBER_MILL:
                buildingFactory = new LumberMillFactory();
                break;
            // Ajoutez d'autres cas au besoin
            default:
                throw new IllegalArgumentException("Type de bâtiment non pris en charge");
        }
        return buildingFactory.createBuilding();
    }



    private void printGameState() {
        System.out.println("\n=== État actuel du jeu (Unité de temps : " + timeUnit + ") ===");
        System.out.println("Ressources actuelles :");
        for (ResourceType resourceType : ResourceType.values()) {
            System.out.println(resourceType + ": " + gameManager.getResourceManager().getResource(resourceType).getQuantity());
        }
        printBuildingState();
    }
}
