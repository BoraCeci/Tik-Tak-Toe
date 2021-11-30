
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToe_GUI extends JFrame{

	public static final int NUM_ROWS = 3;
	public static final int NUM_COLS = 3;
	public static final int NUM_MATCHES_NEEDED_TO_WIN = 3;
	
	private JPanel jpMain;
	private TicTacToeBoard jpTTTBoard ;
	private ScoreBoard jpScoreBoard;

	private Player currPlayer;
	private Player player1;
	private Player player2;
	
	
	public TicTacToe_GUI(){//overall constructor
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		player1 = new Player("W", "Wonderwoman");
		player2 = new Player("S", "Superman");
		currPlayer = player1;
		
		
		jpTTTBoard = new TicTacToeBoard();//initialize TTTBoard
		jpScoreBoard = new ScoreBoard();//initialize ScoreBoard
		jpScoreBoard.jlDisplayCurrPlayer.setText(currPlayer.getPlayerName());//display curr player on Scoreboard's label
		
		jpMain.add(jpTTTBoard, BorderLayout.CENTER);
		jpMain.add(jpScoreBoard, BorderLayout.NORTH);
		
		add(jpMain);
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class TicTacToeBoard extends JPanel implements GameBoardInterface, ActionListener{
		private JButton [][] board;
		
		private TicTacToeBoard(){
			setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
			displayBoard();
		}

		@Override
		public void actionPerformed(ActionEvent e) { //get's triggered when a button is clicked
			JButton btnClicked = (JButton) e.getSource();
			if(btnClicked.getText().equals("")){
				btnClicked.setText(currPlayer.getPlayerSymbol());
//				btnClicked.setEnabled(false);
				
				if(isWinner()){//is the curr player winner ?
					currPlayer.addNumWins();
					displayWinner();
					askPlayAgain();
				}
				else if(isFull()){   //not winner, but board full.... draw
					displayDraw();
					askPlayAgain();
				}
				takeTurn();
				jpScoreBoard.jlDisplayCurrPlayer.setText(currPlayer.getPlayerName());//update label on Scoreboard to display user
				
				
				
				
			}
			
		}
		
		private void askPlayAgain(){
			int yesNo = JOptionPane.showConfirmDialog(null, "Play Again?", "Yes or No", JOptionPane.YES_NO_OPTION);
			if(yesNo == JOptionPane.YES_OPTION){
				clearBoard();
			}
			else{
				System.exit(EXIT_ON_CLOSE);
			}
		}

		@Override
		public void populateBoard() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void displayBoard() {
			board = new JButton[NUM_ROWS][NUM_COLS];
			for(int row=0; row<NUM_ROWS; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col] = new JButton();
					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 36);
					board[row][col].setForeground(Color.BLUE);
					board[row][col].setFont(bigFont);
					board[row][col].addActionListener(this); //add actionListenter to button so it can trigger actionPerformed
					board[row][col].setEnabled(true); //make sure button is enabled
					this.add(board[row][col]); //add to TicTacToeBoard JPanel
				}
			}
			
		}

		@Override
		public void clearBoard() {
			for(int row=0; row<NUM_ROWS; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col].setText(""); //clear out each button so it doesn't display any symbols
					board[row][col].setEnabled(true); //be sure the button is enabled for clicks
				}
			}
			
		}

		@Override
		public void takeTurn() {//alternate players
			if(currPlayer.equals(player1)){
				currPlayer = player2;
			}
			else{
				currPlayer = player1;
			}
			
		}

		@Override
		public void displayWinner() {
			JOptionPane.showMessageDialog(null, "Winner! " + currPlayer.getPlayerName());
			//display wins / losses... etc
			
		}

		public void displayDraw() {
			JOptionPane.showMessageDialog(null, "DRAW! ");
			//displaydraw
			
		}

		@Override
		public boolean isFull() {
			for(int row=0; row<NUM_ROWS; row++){
				for(int col=0; col<board[row].length; col++){
					if(board[row][col].isEnabled()){
						return false;
					}
//					if(board[row][col].getText().trim().isEmpty()){
//						return false;
//					}
				}
			}
			return true;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isWinner() {
			if(isWinnerInRow()){return true;}
			else if(isWinnerInCol()){ return true;}
			else if(isWinnerInMainDiagonal()){ return true;}
			else if(isWinnerInSecondaryDiagonal()){ return true;}
			else{return false;}
			//check rows
			//check cols
			//check main diag
			//check sec diag
		}

		private boolean isWinnerInSecondaryDiagonal() {
			int sumOfMatches = 0;
			int row = 2;
			int col = 0;
			while(row >=0 && col<NUM_COLS){
				if(board[row][col].getText().trim().equalsIgnoreCase(currPlayer.getPlayerSymbol())){
					sumOfMatches++;
				}
				row--;
				col++;
			}
			if(sumOfMatches == NUM_ROWS){//winner 
				return true;
			}return false;
		}

		private boolean isWinnerInMainDiagonal() {
			int sumOfMatches = 0;
			for(int rowCol=0; rowCol< NUM_ROWS; rowCol++ ){// 00 11 22
				if(board[rowCol][rowCol].getText().trim().equalsIgnoreCase(currPlayer.getPlayerSymbol())){
					sumOfMatches++;
				}
			}
			if(sumOfMatches == NUM_MATCHES_NEEDED_TO_WIN){//winner 
				return true;
			}
	
			return false;
		}

		private boolean isWinnerInCol() {
			for(int col=0; col< NUM_COLS; col++ ){
				int sumOfMatches = 0;
				for(int row=0; row < NUM_ROWS; row++){
					if(board[row][col].getText().trim().equalsIgnoreCase(currPlayer.getPlayerSymbol())){
						sumOfMatches++;
					}
				}
				if(sumOfMatches == NUM_MATCHES_NEEDED_TO_WIN){
					return true;//winner in the column we just finished
				}
			}return false;
		}

		private boolean isWinnerInRow() {
			for(int row=0; row < NUM_ROWS; row++){
				int sumOfMatches = 0;
				for(int col=0; col< board[row].length; col++ ){
					if(board[row][col].getText().trim().equalsIgnoreCase(currPlayer.getPlayerSymbol())){
						sumOfMatches++;
					}
				}
				if(sumOfMatches == NUM_MATCHES_NEEDED_TO_WIN){
					return true;//winner in the row we just finished
				}
			}return false;
		}

		@Override
		public boolean isWinner(String currPlayerName) {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		
	}

	
	private class ScoreBoard extends JPanel{
		private JLabel jlForCurrPlayer, jlDisplayCurrPlayer;
		
		private ScoreBoard(){
			setLayout(new GridLayout(1,2)); //1 row, 2 column layout
			setBackground(Color.YELLOW);
			jlForCurrPlayer = new JLabel("current player: ");  //label to let the user know what it's for
			jlDisplayCurrPlayer = new JLabel(); //label to be updated to display alternating user
			
			add(jlForCurrPlayer);
			add(jlDisplayCurrPlayer);
			
		}
		
	}
}
	
	
	
