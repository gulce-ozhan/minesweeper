package cbl.minesweeper.model;
import java.util.ArrayList;
import java.util.List;
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

    /*
     * Returns true if the content type is a number from ONE to EIGHT
     */
    public static boolean isContentTypeNumber(ContentType contentType) {
        return contentType != ContentType.ZERO && contentType != ContentType.MINE;
    }

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
        // Never try to place more mines than there are cells, otherwise the
        // loop below would never end
        int minesToPlace = Math.min(mainController.N_MINES, numberOfCells);
        int minesPlaced = 0;
        while (minesPlaced < minesToPlace) {
            int position = random.nextInt(numberOfCells);
            // Skip cells that already have a mine, so no mine is placed twice
            if (field[position].getContentType() == ContentType.MINE) {
                continue;
            }
            field[position].setContentType(ContentType.MINE);
            minesPlaced++;
            // Every non-mine neighbour now has one more mine around it
            for (int neighbour : getNeighbours(position)) {
                ContentType contentType = field[neighbour].getContentType();
                if (contentType != ContentType.MINE) {
                    field[neighbour].setContentType(
                        ContentType.values()[contentType.ordinal() + 1]);
                }
            }
        }
    }

    /*
     * Returns the positions of the (up to 8) neighbours of the given cell that
     * lie within the minefield boundaries. Rows and columns are checked
     * separately, because in the single array the cell left of column 0 would
     * otherwise wrap around to the end of the previous row.
     */
    private List<Integer> getNeighbours(int position) {
        int row = position / mainController.N_COLS;
        int col = position % mainController.N_COLS;
        List<Integer> neighbours = new ArrayList<>();
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                int nRow = row + dRow;
                int nCol = col + dCol;
                if ((dRow == 0 && dCol == 0)
                    || nRow < 0 || nRow >= mainController.N_ROWS
                    || nCol < 0 || nCol >= mainController.N_COLS) {
                    continue;
                }
                neighbours.add(nRow * mainController.N_COLS + nCol);
            }
        }
        return neighbours;
    }

    /*
     * If an empty cell is clicked by the user, all empty and numbered cells
     * that are reacheable by the cell are recursively uncovered. Mine cells
     * are avoided. Each cell has 8 immediate neighbours; namely, NW,N,NE,E
     * SE,S,SW,W.
     */
    public void discoverConnectedEmptyCells(int emptyCell) {
        for (int neighbour : getNeighbours(emptyCell)) {
            MineFieldCell cell = field[neighbour];
            // Only uncover covered cells; flagged cells and already
            // uncovered cells are left alone
            if (cell.getIconType() != IconType.TILE_COVERED
                || cell.getContentType() == ContentType.MINE) {
                continue;
            }
            if (cell.getContentType() == ContentType.ZERO) {
                cell.setIconType(IconType.TILE_EMPTY);
                discoverConnectedEmptyCells(neighbour);
            } else {
                cell.setIconType(IconType.NUMBER);
            }
        }
    }

    /*
     * Uncovers every mine that is still covered. Used when the game is lost.
     */
    public void revealAllMines() {
        for (MineFieldCell cell : field) {
            if (cell.getContentType() == ContentType.MINE
                && cell.getIconType() == IconType.TILE_COVERED) {
                cell.setIconType(IconType.MINE);
            }
        }
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
