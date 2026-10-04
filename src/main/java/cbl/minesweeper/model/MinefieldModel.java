package cbl.minesweeper.model;
import java.util.Random;

import cbl.minesweeper.controller.MainController;

/*
 * This class is the main Model of of the mine field. It consists of an array 
 * of MineFieldCells. The model initializes the minefield with mines (the
 * number provided by the Contoller) that are randomly distributed. When the 
 * mines are created, number cells that neighbour the mine cell are calculated
 * 
 */
public class MinefieldModel {
    /*
     * Enum type for the (real) contents of a cell
     */
    public enum ContentType {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, MINE
    };
    /*
     * Enum type for the (display) icons of a cell
     */
    public enum IconType {
        TILE_COVERED, TILE_EMPTY, NUMBER, MINE, MINE_EXPL, FLAG, REVERT_FLAG
    };

    // The main data structure that models the minefield
    private MineFieldCell[] field;
    // private int allCells;
    // private int minesLeft;
    private Random random;
    // Reference to the Controller object
    private MainController mainController;

    /*
     * Constructor that takes in the main Conroller object and initializes the
     * random number generator
     */
    public MinefieldModel(MainController mainController) {
        this.mainController = mainController;
        // The random number generator that will be used in selecting cells with mines
        random = new Random();
    }

    //TODO: public static boolean isContentTypeNumber(ContentType contentType) {}
    

    /*
     * Initializes the mine field by randomly determining a new mine cell 
     * position and update its 8 neigbours' mine threat situation.
     */
    public void initializeMinefield() {
        int numberOfCells = mainController.N_ROWS * mainController.N_COLS;
        field = new MineFieldCell[numberOfCells];
        for (int i = 0; i < numberOfCells; i++) {
            field[i] = new MineFieldCell();
        }
        // TODO: Randomly determine a new mine cell position and update its 8 neigbours'
        //  mine threat situation.

    }

    /*
     * If an empty cell is clicked by the user, all empty and numbered cells
     * that are reacheable by the cell are recursively uncovered. Mine cells
     * are avoided. Each cell has 8 immediate neighbours; namely, NW,N,NE,E
     * SE,S,SW,W. 
     */
    public void discoverConnectedEmptyCells(int emptyCell) {
        int current_col = emptyCell % mainController.N_COLS;
        // TODO: Discover the 8 neighbours of the empty cell, provided that they are within the
        // minefield boundaries. For each neighbour, if it is an empty cell, we continue
        // TODO: Discover the 3 neighbours that are in the same column as the empty cell         
        // TODO: Discover the 3 neighbours that are in the same row as the empty cell

    }

    /*
     * Returns the cell that is at the given position in the minefield array.
     */
    public MineFieldCell getCell(int position) {
        if (position >= 0 && position < field.length) {
            return field[position];
        } else {
            return null;
        }
    }
}
