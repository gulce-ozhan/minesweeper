package cbl.minesweeper.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cbl.minesweeper.controller.MainController;

public class TopControlPanel extends JPanel {

    MainController mainControl;

    JLabel lblMineCounter;
    JLabel lblTimer;
    JButton btnRestart;

    JButton btnBeginner;
    JButton btnIntermediate;
    JButton btnExpert;
    
    // TODO: Declare ImageIcon variables for the smiley faces and numbers. 
    //private ImageIcon ICN_SMILEY;

    public TopControlPanel(MainController mainControl){
        this.mainControl = mainControl;
        initIcons();
        initialize();
    }

    public void setTime(int seconds){
        // TODO: Update the timer label with the given seconds.
    }

    public void setMinesLeft(int minesLeft){
        // TODO: Update the mine counter label with the given mines left.
    }   
    
    private void initIcons(){
        // TODO: Initialize the icons for the smiley faces and numbers using the ICON_PATH and ICON_SUFFIX from the main controller.
    }

    public void initialize(){
        // TODO: Initialize the top control panel with the mine counter, timer, restart button, and difficulty level buttons. Set up the layout and add action listeners for the buttons.
    }

    public void gameWon(){
        // TODO: Update the restart button icon to the cool smiley face when the game is won.
    }
    public void gameLost(){
        //TODO: Update the restart button icon to the sad smiley face when the game is lost.
    }

    public void prepareStart(){
        // TODO: Reset the restart button icon to the default smiley face, update the mine counter and timer to their initial values.
    }
}
