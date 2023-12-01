package gameInterface;

import building.Building;
import building.BuildingType;
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
            System.out.println("3. Ajouter des travailleurs à un bâtiment");
            System.out.println("4. Supprimer des travailleurs d'un bâtiment");
            System.out.println("5. Augmenter le nombre d'habitant d'un batiment");
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
                    addWorkers();
                    break;
                case 4:
                    removeWorkers();
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
            gameManager.addBuilding(selectedBuildingType);

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

        System.out.println("Entrez le nombre d'habitants à ajouter lors de l'amélioration :");
        int numResidents = scanner.nextInt();

        if (buildingChoice >= 1 && buildingChoice <= BuildingType.values().length) {
            BuildingType selectedBuildingType = BuildingType.values()[buildingChoice - 1];
            List<Building> buildings = gameManager.getBuildings(selectedBuildingType);

            // Affiche la liste des bâtiments du type sélectionné
            displayBuildings(buildings);

            System.out.println("Choisissez le bâtiment à améliorer :");
            int selectedBuildingIndex = scanner.nextInt();

            if (selectedBuildingIndex >= 1 && selectedBuildingIndex <= buildings.size()) {
                gameManager.upgradeBuilding(selectedBuildingType, selectedBuildingIndex - 1, numResidents);
                actionPerfomed();
            } else {
                System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }


    private void addWorkers() {
        System.out.println("\nTypes de bâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }
        System.out.println("Choisissez le bâtiment où ajouter des travailleurs :");
        int selectedBuildingIndex = scanner.nextInt();

        BuildingType selectedBuildingType = BuildingType.values()[selectedBuildingIndex - 1];
        List<Building> buildings = gameManager.getBuildings(selectedBuildingType);

        System.out.println("Bâtiments disponibles pour " + selectedBuildingType.name() + " :");
        displayBuildings(buildings);

        if (selectedBuildingIndex >= 1 && selectedBuildingIndex <= buildings.size()) {

            System.out.println("Entrez le numéro du batiment "+buildings.get(0).getType().name()+" où ajouter des travailleurs  :");
            int numBat = scanner.nextInt();
            if (numBat >= 1 && numBat <= buildings.size()) {
                System.out.println("Entrez le nombre de travailleurs à ajouter :");
                int numWorkers = scanner.nextInt();

                Building selectedBuilding = buildings.get(numBat - 1);
                gameManager.addWorkers(selectedBuilding.getType(), numBat - 1, numWorkers);
                actionPerfomed();
            } else {
                System.out.println("Choix non valide. Veuillez réessayer.");
            }

        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }

    }


    private void removeWorkers() {
        System.out.println("\nTypes de bâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }
        System.out.println("Choisissez le bâtiment où supprimer des travailleurs :");
        int selectedBuildingIndex = scanner.nextInt();

        BuildingType selectedBuildingType = BuildingType.values()[selectedBuildingIndex - 1];
        List<Building> buildings = gameManager.getBuildings(selectedBuildingType);

        System.out.println("Bâtiments disponibles pour " + selectedBuildingType.name() + " :");
        displayBuildings(buildings);

        if (selectedBuildingIndex >= 1 && selectedBuildingIndex <= buildings.size()) {
            System.out.println("Entrez le numéro du bâtiment " + buildings.get(0).getType().name() + " où supprimer des travailleurs  :");
            int numBat = scanner.nextInt();
            if (numBat >= 1 && numBat <= buildings.size()) {
                System.out.println("Entrez le nombre de travailleurs à supprimer :");
                int numWorkers = scanner.nextInt();

                Building selectedBuilding = buildings.get(numBat - 1);
                gameManager.removeWorkers(selectedBuilding.getType(), numBat - 1, numWorkers);
                actionPerfomed();
            } else {
                System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }


    private void displayBuildings(List<Building> buildings) {
        int index = 1;
        for (Building building : buildings) {
            System.out.println(index + ". " + building.getType().name() +
                    " (Habitants : " + building.getCurrentResidents() + "/" + building.getType().getMaxResidents() +
                    ", Travailleurs : " + building.getCurrentWorkers() + "/" + building.getType().getMaxWorkers() + ")");
            index++;
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
                        System.out.println("  - " + building.getType().name() +
                                " (Construit, Habitants : " + building.getCurrentResidents() + "/" + building.getType().getMaxResidents() +
                                ", Travailleurs : " + building.getCurrentWorkers() + "/" + building.getType().getMaxWorkers() + ")");
                    } else {
                        System.out.println("  - " + building.getType().name() +
                                " (En construction, Temps restant : " + (building.getType().getConstructionTime() - building.getConstructionTimeElapsed()) +
                                " unité(s) de temps)");
                    }
                }
            }
        }
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
