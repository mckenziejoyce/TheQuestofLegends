 /* McKenzie Joyce 
  * Feb 2020 
  * BoardGame.java : An interface for BoardGames 
  * 		createBoard(int height, int width) : Must create a board 
  * 		checkMoveInput(int row, int col) : Must have a method to check if the desired move is legal/possible in the game
  * 		makeMove(BoardTile token, int row, int col) : Must have a method that places and/or moves the desired game piece to the desired board cell
  */
public interface BoardGame {
	public abstract Board createBoard(int height, int width);
	public abstract boolean checkMoveInput(int row, int col);
	public abstract boolean move();
}
