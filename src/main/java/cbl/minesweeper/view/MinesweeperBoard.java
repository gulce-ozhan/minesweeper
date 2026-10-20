package cbl.minesweeper.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.net.URL;

import cbl.minesweeper.controller.MainController;
import cbl.minesweeper.model.MineFieldCell;
import cbl.minesweeper.model.MinefieldModel;

/*
 * The main View component of the mine sweeper game application. It maintains
 * the mine field. Although the model data structure is a single array, the 
 * view is rendered as a two dimensional mine field. Each cell kind has its
 * own icon. The constructor parameter is the main Control object. Besides 
 * the main minefield panel, there is also a small status panel at the bottom.
 */
public class MinesweeperBoard extends JPanel {
    private ImageIcon ICN_TILE_EMPTY;
    private ImageIcon ICN_TILE_COVERED;
    private ImageIcon ICN_MINE;
    private ImageIcon ICN_MINE_EXPL;
    private ImageIcon ICN_FLAG;
    private ImageIcon ICN_FLAG_CROSSED;
    private ImageIcon ICN_ONE;
    private ImageIcon ICN_TWO;
    private ImageIcon ICN_THREE;
    private ImageIcon ICN_FOUR;
    private ImageIcon ICN_FIVE;
    private ImageIcon ICN_SIX;
    private ImageIcon ICN_SEVEN;
    private ImageIcon ICN_EIGHT;

    private int BOARD_WIDTH = 0;
    private int BOARD_HEIGHT = 0;    
    private int minesLeft;
    private final JLabel statusbar;

    private MainController mainControl;
    private int falseFlaggedCells = 0;

    /*
     * The constructor that takes in the main Control object and the status bat
     */
    public MinesweeperBoard(JLabel statusbar, MainController mainControl) {
        this.statusbar = statusbar;
        this.mainControl = mainControl;
        addMouseListener(new MinesAdapter());
        initIcons();
        initBoard();
        initGUI();
    }

    /*
     * Loads the icon from the resources folder. The icon name is passed as a 
     * parameter. The path and suffix are read from the config.properties file.
     */
    private ImageIcon loadIcon(String name) {
        String path = mainControl.ICON_PATH
                    + name
                    + mainControl.ICON_SUFFIX;
        URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Icon not found: " + path);
        }
        return new ImageIcon(url);
    }

    /*
     * Creates the icons for the cells
     */
    private void initIcons() {
        ICN_TILE_EMPTY = loadIcon("tile-empty");
        ICN_TILE_COVERED = loadIcon("tile-covered");
        ICN_MINE = loadIcon("mine");
        ICN_MINE_EXPL = loadIcon("mine-exploded");
        ICN_FLAG = loadIcon("flag");
        ICN_FLAG_CROSSED = loadIcon("flag-crossed");
        ICN_ONE = loadIcon("one");
        ICN_TWO = loadIcon("two");
        ICN_THREE = loadIcon("three");
        ICN_FOUR = loadIcon("four");
        ICN_FIVE = loadIcon("five");
        ICN_SIX = loadIcon("six");
        ICN_SEVEN = loadIcon("seven");
        ICN_EIGHT = loadIcon("eight");
    }
        /*
     * Initializes the game board control variables
     */
    public void initBoard() { 
        minesLeft = mainControl.getMinesLeft();
        statusbar.setText(Integer.toString(minesLeft));            
    }

    /*
     * Initializes the GUI of the minefield panel. 
     */
    public void initGUI() {
        BOARD_WIDTH = mainControl.N_COLS * this.mainControl.CELL_SIZE_PIX + 1;
        BOARD_HEIGHT = mainControl.N_ROWS * this.mainControl.CELL_SIZE_PIX + 1;
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /*
     * Restarts the game through board and GUI initializations. This method is 
     * called by the Controller object
     */
    public void  restartGame() {
        initBoard();
        initGUI();
        repaint();
    }

    // @Override
    /*
     * The overriding paintComponent method that performa one of the most 
     * critical tasks in the game. Traverses the cells one by one and renders
     * them on the the mine field GUI. If there are no more cells left to 
     * undiscover, then inform the Controller that the game is won .
     */
    public void paintComponent(Graphics g) {
        int cellsYetToDiscover = 0;
        Image imageToDraw = null;
        for (int i = 0; i < mainControl.N_ROWS; i++) {
            for (int j = 0; j < mainControl.N_COLS; j++) {
                MineFieldCell cell = mainControl.getCell((i * mainControl.N_COLS) + j);
                switch (cell.getIconType()) {
                    case TILE_EMPTY:
                        imageToDraw = ICN_TILE_EMPTY.getImage();
                        break;
                    case NUMBER: 
                        imageToDraw = getIconForNumberCell(cell);            
                        break;
                    case TILE_COVERED:
                        cellsYetToDiscover++;
                        imageToDraw = ICN_TILE_COVERED.getImage();;
                        break;
                    case MINE:
                        imageToDraw = ICN_MINE.getImage();
                        break;
                    case MINE_EXPL:
                        imageToDraw = ICN_MINE_EXPL.getImage();;
                        break;                        
                    case FLAG:
                        imageToDraw = ICN_FLAG.getImage();;
                        break;
                    case REVERT_FLAG:
                        imageToDraw = ICN_TILE_COVERED.getImage();
                        break;

                }
                //If the game is not ongoing (i.e., won or lost) we can reveal the underlying mines
                if (mainControl.getGameStatus() != MainController.GameStatus.ONGOING) {
                    if (cell.getContentType() == MinefieldModel.ContentType.MINE){
                        if (cell.getIconType() != MinefieldModel.IconType.MINE_EXPL) {
                            imageToDraw = ICN_MINE.getImage();
                        }
                    }else if (cell.getContentType() != MinefieldModel.ContentType.MINE && 
                                cell.getIconType() == MinefieldModel.IconType.FLAG) {
                        imageToDraw = ICN_FLAG_CROSSED.getImage();
                    }else if (cell.getIconType() == MinefieldModel.IconType.TILE_COVERED) {
                            imageToDraw = getIconForNumberCell(cell);
                    }
                }                
                g.drawImage(imageToDraw, 
                    (j * mainControl.CELL_SIZE_PIX), 
                    (i * mainControl.CELL_SIZE_PIX), this);
            }
        }
        // If the game is on, but there are no more cells left to undiscover,
        // then inform the Controller that the game is won by the user. The 
        // status bar is further updated with the outcome of the game.
        if (mainControl.getGameStatus()==MainController.GameStatus.ONGOING) {
            if (cellsYetToDiscover == 0){
                mainControl.gameWon();
                statusbar.setText("Game won");
            }
        }else if (mainControl.getGameStatus()==MainController.GameStatus.LOST) {
            statusbar.setText("Game lost");
        }else if (mainControl.getGameStatus()==MainController.GameStatus.WON) {
            statusbar.setText("Game won");
        }
    }


    // This method is called to return the icon for a number cell.
    // If the cell was not a number (unlkely case), it return empty tile
    private Image getIconForNumberCell(MineFieldCell cell) {
        switch (cell.getContentType()) {
            case ONE:
                return ICN_ONE.getImage();
            case TWO:
                return ICN_TWO.getImage();
            case THREE:
                return ICN_THREE.getImage();
            case FOUR:
                return ICN_FOUR.getImage();
            case FIVE:
                return ICN_FIVE.getImage();
            case SIX:
                return ICN_SIX.getImage();
            case SEVEN:
                return ICN_SEVEN.getImage();
            case EIGHT:
                return ICN_EIGHT.getImage();
            default:
                return ICN_TILE_EMPTY.getImage();
        }
    }   

    /*
     * Together with the paintComponent(), this MouseAdapter is one of the most
     * important methods of the game. It handles the left mouse click and
     * right mouse click events on the mine cells. The clicked cell is 
     * determined by dividing the coordinates to the cell pixel sizes.
     */
    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int cCol = x / mainControl.CELL_SIZE_PIX;
            int cRow = y / mainControl.CELL_SIZE_PIX;

            //System.out.println("Mouse pressed at: " + x + ", " + y + " corresponding to cell: " + cRow + ", " + cCol);
            if ((x < mainControl.N_COLS * mainControl.CELL_SIZE_PIX) && 
                (y < mainControl.N_ROWS * mainControl.CELL_SIZE_PIX)) {
                MineFieldCell cell = mainControl.getCell((cRow * mainControl.N_COLS) + cCol);
                if (e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) {
                    // Handle the right mouse click event to mark a cell as flagged or
                    // unflagged. Update the mines left counter and the status bar accordingly. 
                    // If the user has flagged all mines correctly, inform the Controller that 
                    // the game is won.                      
                    if (cell.getContentType() == MinefieldModel.ContentType.MINE) {
                        if (cell.getIconType() == MinefieldModel.IconType.TILE_COVERED) {
                            if (minesLeft > 0) {
                                cell.setIconType(MinefieldModel.IconType.FLAG);
                                minesLeft--;
                                statusbar.setText(minesLeft + "");
                                mainControl.setMinesLeft(minesLeft);
                                checkIfGameWon();
                            } else {
                                // statusbar.setText("Game won!");
                                // mainControl.gameWon();
                                checkIfGameWon();
                            }
                        } else if (cell.getIconType() == MinefieldModel.IconType.FLAG) {
                            cell.setIconType(MinefieldModel.IconType.TILE_COVERED);
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText(msg);
                            mainControl.setMinesLeft(minesLeft);
                        }
                    } else {
                        // The user has right clicked to mark a cell that is not mine in reality
                        // The same behavour as above. But check it
                        if (cell.getIconType() == MinefieldModel.IconType.TILE_COVERED) {
                            if (minesLeft > 0) {                                
                                cell.setIconType(MinefieldModel.IconType.FLAG);
                                minesLeft--;
                                statusbar.setText(minesLeft + "");
                                mainControl.setMinesLeft(minesLeft);
                                falseFlaggedCells++;
                            } else {// Normally this case should not happen                                
                                statusbar.setText("No mines left");
                            }
                            checkIfGameWon();
                        } else if (cell.getIconType() == MinefieldModel.IconType.FLAG) {
                            cell.setIconType(MinefieldModel.IconType.TILE_COVERED);
                            minesLeft++;
                            statusbar.setText(minesLeft + "");
                            mainControl.setMinesLeft(minesLeft);
                            falseFlaggedCells--;
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON1
                    && mainControl.getGameStatus() == MainController.GameStatus.ONGOING
                    && cell.getIconType() == MinefieldModel.IconType.TILE_COVERED) {
                    int position = (cRow * mainControl.N_COLS) + cCol;
                    switch (cell.getContentType()) {
                        case MINE:
                            cell.setIconType(MinefieldModel.IconType.MINE_EXPL);
                            mainControl.gameLost();
                            statusbar.setText("Game over! You hit a mine.");
                            break;
                        case ZERO:
                            cell.setIconType(MinefieldModel.IconType.TILE_EMPTY);
                            mainControl.discoverConnectedEmptyCells(position);
                            checkIfGameWon();
                            break;
                        default:
                            cell.setIconType(MinefieldModel.IconType.NUMBER);
                            checkIfGameWon();
                            break;
                    }
                }
            }
            repaint();
        }

        private void checkIfGameWon() {
            if (minesLeft == 0) {
                if (falseFlaggedCells == 0) {
                    mainControl.gameWon();
                    statusbar.setText("Game won.");
                } else {                    
                    mainControl.gameLost();
                }
            }
        }
    }
}
