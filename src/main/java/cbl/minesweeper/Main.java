package cbl.minesweeper;

import java.awt.EventQueue;

import cbl.minesweeper.controller.MainController;

public class Main {

    public static void main(String[] args) {        
        EventQueue.invokeLater(() -> {
            new MainController().launchGame();       
        }); 
    }
}