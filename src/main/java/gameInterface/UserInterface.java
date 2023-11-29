package gameInterface;

import building.BuildingType;
import manager.Manager;

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
            System.out.println("5. Quitter");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    gameManager.manageResources();
                    gameManager.manageFoodConsumption();
                    timeUnit++;
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
                    System.out.println("Merci d'avoir joué !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }
        }
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
            gameManager.addBuilding(selectedBuildingType);
            System.out.println("Bâtiment " + selectedBuildingType.name() + " en construction.");
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }

    private void addWorkers() {
        System.out.println("\nBâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }

        int buildingChoice = scanner.nextInt();

        System.out.println("Entrez le nombre de travailleurs à ajouter :");
        int numWorkers = scanner.nextInt();

        if (buildingChoice >= 1 && buildingChoice <= BuildingType.values().length) {
            BuildingType selectedBuildingType = BuildingType.values()[buildingChoice - 1];
            gameManager.addWorkers(selectedBuildingType, 0, numWorkers);
            System.out.println(numWorkers + " travailleurs ajoutés au bâtiment " + selectedBuildingType.name() + ".");
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }

    private void removeWorkers() {
        System.out.println("\nBâtiments disponibles :");
        int index = 1;
        for (BuildingType buildingType : BuildingType.values()) {
            System.out.println(index + ". " + buildingType.name());
            index++;
        }

        int buildingChoice = scanner.nextInt();

        System.out.println("Entrez le nombre de travailleurs à supprimer :");
        int numWorkers = scanner.nextInt();

        if (buildingChoice >= 1 && buildingChoice <= BuildingType.values().length) {
            BuildingType selectedBuildingType = BuildingType.values()[buildingChoice - 1];
            gameManager.removeWorkers(selectedBuildingType, 0, numWorkers);
            System.out.println(numWorkers + " travailleurs supprimés du bâtiment " + selectedBuildingType.name() + ".");
        } else {
            System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }

    private void printGameState() {
        System.out.println("\n=== État actuel du jeu (Unité de temps : " + timeUnit + ") ===");
    }
}
