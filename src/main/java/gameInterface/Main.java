package gameInterface;

import manager.Manager;

public class Main {
    public static void main(String[] args) {
        Manager gameManager = new Manager();
        UserInterface userInterface = new UserInterface(gameManager);
        userInterface.startGame();
    }
}
