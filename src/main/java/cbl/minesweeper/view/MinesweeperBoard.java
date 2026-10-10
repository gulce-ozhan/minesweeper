package cbl.minesweeper.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    // private boolean inGame;
    private int minesLeft;
    private final JLabel statusbar;

    private MainController mainControl;

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
     * Creates the icons for the cells
     */
    private void initIcons(){
        String iconPath = mainControl.ICON_PATH;
        String suffix = mainControl.ICON_SUFFIX;
        ICN_TILE_EMPTY = new ImageIcon(iconPath + "tile-empty" + suffix);
        ICN_TILE_COVERED = new ImageIcon(iconPath + "tile-covered" + suffix);
        ICN_MINE = new ImageIcon(iconPath + "mine" + suffix);        
        ICN_MINE_EXPL = new ImageIcon(iconPath + "mine-exploded" + suffix);
        ICN_FLAG = new ImageIcon(iconPath + "flag" + suffix);
        ICN_FLAG_CROSSED = new ImageIcon(iconPath + "flag-crossed" + suffix);
        ICN_ONE = new ImageIcon(iconPath + "one" + suffix);
        ICN_TWO = new ImageIcon(iconPath + "two" + suffix);
        ICN_THREE = new ImageIcon(iconPath + "three" + suffix);
        ICN_FOUR = new ImageIcon(iconPath + "four" + suffix);
        ICN_FIVE = new ImageIcon(iconPath + "five" + suffix);
        ICN_SIX = new ImageIcon(iconPath + "six" + suffix);
        ICN_SEVEN = new ImageIcon(iconPath + "seven" + suffix);
        ICN_EIGHT = new ImageIcon(iconPath + "eight" + suffix);
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
    public void initGUI(){
        BOARD_WIDTH = mainControl.N_COLS * this.mainControl.CELL_SIZE_PIX + 1;
        BOARD_HEIGHT = mainControl.N_ROWS * this.mainControl.CELL_SIZE_PIX + 1;
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /*
     * Restarts the game through board and GUI initializations. This method is 
     * called by the Controller object
     */
    public void  restartGame(){
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
                //TODO: If the game is not ongoing (i.e., won or lost), we can reveal the 
                //underlying mines and flags. If the game is ongoing, we can only show the
                //covered cells and the uncovered cells.   
                
                g.drawImage(imageToDraw, (j * mainControl.CELL_SIZE_PIX), (i * mainControl.CELL_SIZE_PIX), this);
            }
        }
        // TODO: If the game is on, but there are no more cells left to undiscover,
        // then inform the Controller that the game is won by the user. 
        // Update the status bar accordingly
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

            System.out.println("Mouse pressed at: " + x + ", " + y + " corresponding to cell: " + cRow + ", " + cCol);

            if ((x < mainControl.N_COLS * mainControl.CELL_SIZE_PIX) && (y < mainControl.N_ROWS * mainControl.CELL_SIZE_PIX)) {
                MineFieldCell cell = mainControl.getCell((cRow * mainControl.N_COLS) + cCol);
                if (e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) {
                    //TODO: Handle the right mouse click event to mark a cell as flagged or
                    // unflagged. Update the mines left counter and the status bar accordingly. 
                    // If the user has flagged all mines correctly, inform the Controller that 
                    // the game is won.   
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
                            break;
                        default:
                            cell.setIconType(MinefieldModel.IconType.NUMBER);
                            break;
                    }
                }
            }
            repaint();
        }
    }
}
