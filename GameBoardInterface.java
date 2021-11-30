
public interface GameBoardInterface {
	
	public void populateBoard();
	public void displayBoard();
	public void clearBoard();
	public void takeTurn();
	public void displayWinner();
	public boolean isFull();
	public boolean isEmpty();
	public boolean isWinner();
	public boolean isWinner(String currPlayerName);
	

} 
