package cbl.minesweeper.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.net.URL;
import cbl.minesweeper.controller.MainController;

/*
 * This class is the top control panel of the Minesweeper game.
*  It contains the mine counter, timer, restart button, and difficulty selection buttons.  
 * TopControlPanel
*/
public class TopControlPanel extends JPanel {

    MainController mainControl;

    JLabel lblMineCounter;
    JLabel lblTimer;
    JButton btnRestart;

    JButton btnBeginner;
    JButton btnIntermediate;
    JButton btnExpert;
    
    private ImageIcon ICN_SMILEY;
    private ImageIcon ICN_SMILEY_COOL;
    private ImageIcon ICN_SMILEY_SAD;

    private ImageIcon ICN_ONE;
    private ImageIcon ICN_TWO;
    private ImageIcon ICN_THREE;

    public TopControlPanel(MainController mainControl) {
        this.mainControl = mainControl;
        initIcons();
        initialize();
    }

    public void setTime(int seconds) {
        lblTimer.setText("Time: " + seconds);
    }

    public void setMinesLeft(int minesLeft) {
        lblMineCounter.setText("Mines: " + minesLeft);        
    }   
    

    /*
     * Loads the icon from the resources folder. The icon name and description are passed as parameters.
     */
    private ImageIcon loadIcon(String name, String description) {
        String path = mainControl.ICON_PATH
                    + name
                    + mainControl.ICON_SUFFIX;

        URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Icon not found: " + path);
        }
        return new ImageIcon(url, description);
    }

    /*
     * Initializes the icons for the control panel.
     */
    private void initIcons() {
        ICN_SMILEY = loadIcon("smiley", "Restart");
        ICN_SMILEY_COOL = loadIcon("smiley-cool", "You won!");
        ICN_SMILEY_SAD = loadIcon("smiley-sad", "Bad Luck!");
        ICN_ONE = loadIcon("one", "");
        ICN_TWO = loadIcon("two", "");
        ICN_THREE = loadIcon("three", "");
    }

    /**
     * Initializes the components of the top control panel.
     */
    public void initialize() {
        lblMineCounter = new JLabel();
        lblMineCounter.setText("Mines: "+mainControl.getMinesLeft());
        lblMineCounter.setMinimumSize(new Dimension(79, 36));
        
        lblTimer = new JLabel("Time: ");
        lblTimer.setMinimumSize(new Dimension(79, 36));
        lblTimer.setHorizontalAlignment(SwingConstants.RIGHT);
        
        btnRestart = new JButton(ICN_SMILEY);
        btnRestart.setToolTipText("Restart Game");        
        btnRestart.setPreferredSize(new Dimension(36,36));
        btnRestart.setMinimumSize(new Dimension(36, 36));
        btnRestart.setOpaque(false);       
        btnRestart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mainControl.restartGame();
            }
        });
    
        JRadioButton rbtnBeginner = new JRadioButton("", ICN_ONE, true);
        rbtnBeginner.setToolTipText("Beginner");
        rbtnBeginner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt){
                mainControl.restartGame(MainController.Difficulty.BEGINNER);
            }
        });
        JRadioButton rbtnIntermediate = new JRadioButton("", ICN_TWO, true);
        rbtnIntermediate.setToolTipText("Intermediate");
        rbtnIntermediate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt){
                mainControl.restartGame(MainController.Difficulty.INTERMEDIATE);
            }
        });
        JRadioButton rbtnExpert = new JRadioButton("", ICN_THREE, true);
        rbtnExpert.setToolTipText("Expert");
        rbtnExpert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt){
                mainControl.restartGame(MainController.Difficulty.EXPERT);
            }
        });

        Insets compactMargin = new Insets(1, 1, 1, 1); // Top, Left, Bottom, Right padding
        rbtnBeginner.setMargin(compactMargin);
        rbtnIntermediate.setMargin(compactMargin); 
        rbtnExpert.setMargin(compactMargin);

        ButtonGroup btgDifficulty = new ButtonGroup();        
        btgDifficulty.add(rbtnBeginner);
        btgDifficulty.add(rbtnIntermediate);
        btgDifficulty.add(rbtnExpert);        
        JPanel pnlDifficulty = new JPanel();
        pnlDifficulty.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnlDifficulty.setMinimumSize(new Dimension(102,36));
        pnlDifficulty.setMaximumSize(new Dimension(102,36));
        pnlDifficulty.setLayout(new BoxLayout(pnlDifficulty, BoxLayout.X_AXIS));
        pnlDifficulty.add(rbtnBeginner);
        pnlDifficulty.add(rbtnIntermediate);
        pnlDifficulty.add(rbtnExpert);

        setLayout(new GridBagLayout());        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4);
        gbc.gridy = 0;

        // Mine counter
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblMineCounter, gbc);

        //Restart button
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE; //HORIZONTAL;
        add(btnRestart, gbc);
          
        // Difficulty buttons
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE; //HORIZONTAL;
        add(pnlDifficulty, gbc);

        // Timer
        gbc.gridx = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblTimer, gbc);
    }

    /**
     * Updates the restart button icon based on the game status.
     */
    public void gameWon() {
        btnRestart.setIcon(ICN_SMILEY_COOL);
    }

    /**
     * Updates the restart button icon to indicate that the game is lost.
     */
    public void gameLost() {
        btnRestart.setIcon(ICN_SMILEY_SAD);
    }

    /**
     * Prepares the top control panel for a new game by resetting the restart button icon,
     * updating the mine counter, and resetting the timer.
     */
    public void prepareStart() {
        btnRestart.setIcon(ICN_SMILEY);
        setMinesLeft(mainControl.getMinesLeft());
        setTime(0);         
    }
}
