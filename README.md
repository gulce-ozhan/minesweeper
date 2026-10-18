#2IP90 CBL PROJECT - MINESWEEPER GAME

By Gulce Ozhan (2457288) and Oney Khambhadia (2422301)


##Description
This CBL project implements the minesweeper game of Windows in a more modern GUI with extended features. The goal is to reveal all the cells that do not contain mines without clicking on a mine. When a safe cell is revealed, it displays a number indicating how many mines are in the surrounding cells; if there are no nearby mines, neighbouring empty cells are revealed automatically. Players can flag cells that they believe contain mines to help keep track of dangerous locations. The player wins when all non-mine cells have been revealed and loses if a mine is revealed (i.e., stepped on). The main challenge is using the numbered clues and logical reasoning to determine where the mines are located. The project is hosted on GitHub and can be accessed at https://github.com/gulce-ozhan/minesweeper. 

 For Minesweeper, the main mechanics can be as follows:
•	Revealing cells: the player clicks a hidden cell to reveal it.
•	Mines: revealing a mine causes the player to lose.
•	Number clues: a revealed safe cell shows how many mines are in its surrounding cells (between 1–8).
•	Empty-cell expansion: revealing a cell with no adjacent mines automatically reveals nearby safe cells.
•	Flagging: players can flag cells they suspect contain mines.
•	Winning:  the player wins when all non-mine cells are revealed.
Visual ideas from existing versions of game can be seen at https://minesweeperonline.com/:

##Advanced Topics
This project demonstrates the use of two specific advanced topics, namely, Java Properties for external configuration and Git for version control and collaboration.  Java Properties taught us how to make software configurable without modifying its implementation, while Git enabled us to collaborate effectively and maintain a structured development history. Most importantly, we gained hands-on experience with practices commonly used in larger software projects.  

Besides these, the project uses advanced Swing constructs (JFrame, JPanel, JButton, JRadioButton, GridbagLayout, BoxLayout), event handling (ActionListener, MouseAdapter), and follows the most widely used GUI interaction design pattern, Model-View-Controller. Maven build automation tool is used for managing the build lifecycle, dependencies, and documentation. Project settings and dependency configurations is specified in the pom.xml file. 

##Main Flow
The game starts by taking into account the difficulty level and the related parameters in the properties file. There are 3 dfficulty levels:beginner (9x9, 40 mines), intermediate (16x16, 40 mines) and expert (16x30, 99 mines). The user can restart the game any time by clicking the restart button seen as the smiley icon. As the game unfolds, status on the remaining mines (flagging assumes mine cells) and the time elapsed are displayed. If the user wins, the smiley icon turns to 'cool smiley' and if the user loses by stepping on a mine, the icon turns to 'sad smiley'. In either case of win or lose, the entire minefield is displayed to the user in open form. The user can any time switch to a different difficulty level by clicking on the 1,2,3, buttons on the top control panel. The same difficulty level is kept in subsequent restarts.

##Input/Output Specifications
The user specifies configuration parameters in the config.properties file and launches the main application that starts the Minesweeper game GUI. The user inputs are provided by mouse clicks on the mine field and on the top control panel. Output responses are instantly provided in the GUI. The game ends by user clicking on the close (x) button at the top right corner of the main frame.
 
##Buiding and Running
- Compile and run in VSCode IDE: the application can be compiled and launched by running Main.java.
- Build automation with maven: in a terminal, run *mvn clean package* to build the project and generata an executable JAR file.
- Launchh executable JAR: to directly launch the application, in a terminal go to where the jar file is and run *java -jar minesweeper-1.0.jar*

##Draft Backlog Ideas:
No	Name	How to Demo	Notes
1	Display game board	Launch the app; a grid of clearly distinguishable covered cells is displayed in the GUI.	Display a grid of clickable cells.
2	Start a new game	Launch the app and click New Game; a fresh covered board is created and the game is ready.	Reset the board and randomly place mines.
3	Reveal a cell	Click a covered cell; the cell is revealed and shows the number of mines in the surrounding cells.	Calculate adjacent mines and display a number from 0–8.
4	Reveal empty areas	Click a cell with no adjacent mines; nearby empty cells are automatically revealed.	Recursively reveal connected empty cells and their boundaries.
5	Flag a suspected mine	Right-click a covered cell; the cell is marked with a flag. Right-click again to remove it.	Flagged cells should not be revealed by a normal click.
6	Detect a mine	Click a cell containing a mine; the game reports a loss and reveals the mines.	Stop normal gameplay after a mine is selected.
7	Detect a win	Reveal all cells that do not contain mines; the application reports that the player has won.	Check whether all non-mine cells have been revealed.
8	End the game	Win or lose a game; the result is displayed and further moves are prevented.	Handle both win and loss states.
9	Track game time	Start a game; a timer begins and stops when the player wins or loses.	Display elapsed playing time.
10	Display remaining mines	Place or remove flags; the displayed mine counter updates accordingly.	Can show total mines − placed flags.
11	Configure game settings	Change settings in a .properties file and launch the game; the configured values are used.	Could configure board dimensions or number of mines.
