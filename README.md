Minesweeper
By Gulce Ozhan and Oney Khambhadia
For the CBL project we plan to develop Minesweeper game. Minesweeper is a game played on a grid of hidden cells, some of which contain mines. The goal is to reveal all cells that do not contain mines without clicking on a mine. When a safe cell is revealed, it displays a number indicating how many mines are in the surrounding cells; if there are no nearby mines, neighbouring empty cells are usually revealed automatically. Players can flag cells that they believe contain mines to help keep track of dangerous locations. The player wins when all non-mine cells have been revealed and loses if a mine is revealed. The main challenge is using the numbered clues and logical reasoning to determine where the mines are located.
 For Minesweeper, the main mechanics can be as follows:
•	Revealing cells: the player clicks a hidden cell to reveal it.
•	Mines: revealing a mine causes the player to lose.
•	Number clues: a revealed safe cell shows how many mines are in its surrounding cells (between 1–8).
•	Empty-cell expansion: revealing a cell with no adjacent mines automatically reveals nearby safe cells.
•	Flagging: players can flag cells they suspect contain mines.
•	Winning:  the player wins when all non-mine cells are revealed.
Visual ideas from existing versions of game (https://minesweeperonline.com/):
 	 
Draft Backlog Ideas:
#	Name	How to Demo	Notes
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

Possible Advanced topics of choice:  
-	Version Control Workflows (Git / Branching Strategies)
-	Build Systems (Gradle / Maven)
-	Model-View Controller Design Pattern
-	Program Configuration: Java .properties file 
