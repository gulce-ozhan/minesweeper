package cbl.minesweeper.controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import cbl.minesweeper.model.MineFieldCell;
import cbl.minesweeper.model.MinefieldModel;
import cbl.minesweeper.view.MinesweeperBoard;
import cbl.minesweeper.view.TopControlPanel;

public class MainController {
    
        /*
     * enum for keeping the game status, which can be in ongoing, won or 
     * lost at any given time.
     */
    public enum GameStatus {ONGOING, WON, LOST};
    /*
     * enum for keeping the game difficulty level, which can be beginner
     * (9x9, 10 mines), intermediate (16x16, 40 mines) or expert (16x30,
     * 99 mines), as parametrized in the properties file.
     */
    public enum Difficulty {BEGINNER, INTERMEDIATE, EXPERT};

    // The main Model object for the minefield representation.
    private MinefieldModel minefieldModel;   

    public MinefieldModel getMinefieldModel() {
        return minefieldModel;
    }

    // Gme status instance variable
    private GameStatus gameStatus;  
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
  
    // Keeps track of how many mines are left. This value is communicated
    // with the model and view objects. It is one of the key instruments
    // in determining the end of the game
    private int minesLeft;
    public int getMinesLeft(){
        return minesLeft;
    }
    public void setMinesLeft(int minesLeft){
        this.minesLeft = minesLeft;
        if (pnlTopControl!=null){
            pnlTopControl.setMinesLeft(minesLeft);
        }
    }

    // The main frame that all the GUI elements reside in
    private JFrame frmMineSweeper;
    // The minefield game board panel
    private MinesweeperBoard pnlMineSweeperBoard;
    // The status bar at the bottom
    private JLabel lblStatusbar;
    // The control panel at the top. It conatins the mine count, restart 
    // button (smiley icon), game difficulty level buttons and the timer
    private TopControlPanel pnlTopControl;
    // The timer used to tick the timer counter every second.


    //TODO: private MinesweeperTimerTask timerTask;
    private Timer timer;
    private int elapsedSeconds = 0;
    private JLabel timerLabel;
    
    // The following configuration parameters are read/overrridden from the config.properties
    public String ICON_PATH = "src/main/resources/icons/";
    public String ICON_SUFFIX = ".png";     
    public Difficulty DIFFICULTY = Difficulty.BEGINNER;
    public int N_MINES = 10;
    public int N_ROWS = 9;
    public int N_COLS = 9;
    public int CELL_SIZE_PIX = 32;

    private int N_MINES_BEGIN = 10;
    private int N_ROWS_BEGIN = 9;
    private int N_COLS_BEGIN = 9;
    private int N_MINES_INT = 10;
    private int N_ROWS_INT = 9;
    private int N_COLS_INT = 9;
    private int N_MINES_EXP = 10;
    private int N_ROWS_EXP = 9;
    private int N_COLS_EXP = 9;

    // Reads key configuration prameters from the properties file
    private void readProperties(){
        //Read the game configuration parameters from the config.properties file
        String configPath =  "src/main/resources/config.properties";
        try (InputStream input = new FileInputStream(configPath)) {
            // Create a new Properties object
            Properties prop = new Properties();
            // Use the Properties objects to load the properties file
            prop.load(input);
            // get the property values and print them out
            N_MINES_BEGIN = Integer.parseInt(prop.getProperty("mine.count.beginner"));
            N_ROWS_BEGIN = Integer.parseInt(prop.getProperty("minefield.dim.rows.beginner"));
            N_COLS_BEGIN = Integer.parseInt(prop.getProperty("minefield.dim.cols.beginner"));
            N_MINES_INT = Integer.parseInt(prop.getProperty("mine.count.intermediate"));
            N_ROWS_INT = Integer.parseInt(prop.getProperty("minefield.dim.rows.intermediate"));
            N_COLS_INT = Integer.parseInt(prop.getProperty("minefield.dim.cols.intermediate"));
            N_MINES_EXP = Integer.parseInt(prop.getProperty("mine.count.expert"));
            N_ROWS_EXP = Integer.parseInt(prop.getProperty("minefield.dim.rows.expert"));
            N_COLS_EXP = Integer.parseInt(prop.getProperty("minefield.dim.cols.expert"));
            int difficulty =  Integer.parseInt(prop.getProperty("difficulty.level"));
            DIFFICULTY = Difficulty.values()[difficulty];
            //TODO: setParametersBasedOnDifficulty(DIFFICULTY);
            CELL_SIZE_PIX = Integer.parseInt(prop.getProperty("cell.size.pixels"));
        } catch (IOException ex) {
            System.out.println("Error occuurred while reading config.properties file. Keeping default values.");
            ex.printStackTrace();
        }
    }

    // Initializes the minefield Model object
    private void initModel() {
        setMinesLeft(N_MINES);
        minefieldModel = new MinefieldModel(this);
        minefieldModel.initializeMinefield();
    }

    // Initializes the GUI View objects
    private void initGUI() {
        frmMineSweeper = new JFrame();
        
        pnlTopControl = new TopControlPanel(this);
        pnlTopControl.setMinesLeft(minesLeft);
        pnlTopControl.setLayout(new BorderLayout());

        JLabel lblMineCounter = new JLabel("Mines: 10");
        pnlTopControl.add(lblMineCounter, BorderLayout.WEST);
        
        frmMineSweeper.add(pnlTopControl, BorderLayout.NORTH);
        //smiley button
        ImageIcon smileyicon = new ImageIcon(ICON_PATH + "smiley" + ICON_SUFFIX);
        JButton btnRestart = new JButton(smileyicon);

        JPanel pnlRestart = new JPanel();
        pnlRestart.add(btnRestart);



        //numbers png
        ImageIcon oneicon = new ImageIcon(ICON_PATH + "one" + ICON_SUFFIX);
        ImageIcon twoicon = new ImageIcon(ICON_PATH + "two" + ICON_SUFFIX);
        ImageIcon threeicon = new ImageIcon(ICON_PATH + "three" + ICON_SUFFIX);

        JLabel lblOne = new JLabel(oneicon);
        JLabel lblTwo = new JLabel(twoicon);
        JLabel lblThree = new JLabel(threeicon);


        JPanel pnlCenter = new JPanel();

        // center controls

        pnlCenter.add(btnRestart);
        pnlCenter.add(lblOne);
        pnlCenter.add(lblTwo);
        pnlCenter.add(lblThree);

        pnlTopControl.add(pnlCenter, BorderLayout.CENTER);

        //timer
        timerLabel = new JLabel("Time: 00");
        pnlTopControl.add(timerLabel, BorderLayout.EAST);

        //bottom status bar
        lblStatusbar = new JLabel("");
        frmMineSweeper.add(lblStatusbar, BorderLayout.SOUTH);

        //minesweeper board
        pnlMineSweeperBoard = new MinesweeperBoard(lblStatusbar, this);
        frmMineSweeper.add(pnlMineSweeperBoard);

        //frame settings
        frmMineSweeper.setResizable(false);
        frmMineSweeper.pack();
        frmMineSweeper.setTitle("Minesweeper");
        frmMineSweeper.setLocationRelativeTo(null);
        frmMineSweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    // Returns the cell at the given index position in the minefield Model
    public MineFieldCell getCell(int position){
        return getMinefieldModel().getCell(position);
    }

    // Triggered by the View to initiate the discovery of the empty cells
    // in the Model, upon user click
    public void discoverConnectedEmptyCells(int position){
        getMinefieldModel().discoverConnectedEmptyCells(position);
    }

    // Handles the chores for winning the game.
    public void gameWon(){
        setGameStatus(GameStatus.WON);
        timer.stop();
    }

    // Handles the chores for loss of the game.
    public void gameLost(){
        setGameStatus(GameStatus.LOST);
        timer.stop();
        getMinefieldModel().revealAllMines();
    }

    // Restarts the game with the selected difficulty.
    public void restartGame(Difficulty level){
        // TODO:
    }

    // Forces a redraw of the top-level container. Used multiple times 
    // in response to changes of the game status and user actions/clicks
    private void redrawFrame(){
        frmMineSweeper.pack();
        frmMineSweeper.repaint(); 
    }


    // Launches the game for the first time. Used by the main method.
    public void launchGame(){
        readProperties();
        initModel();
        initGUI();

        // Set the game status to ongoing
        gameStatus = GameStatus.ONGOING;
        //TODO: Start the timer
        timer = new Timer(1000, e -> {
            elapsedSeconds++;
            timerLabel.setText("Time: " + elapsedSeconds); // Update the timer label every second
        });

        EventQueue.invokeLater(() -> {
            var ex = frmMineSweeper; //new MinesweeperFrame(minefieldModel);
            ex.setVisible(true);

            timer.start(); // Start the timer when the frame is visible     
        });
    }

    //TODO: Implement the timer task to update the timer label on the top control panel every second.

}
