
public class Player implements Comparable<Player>{

	private String playerSymbol;
	private String playerName;
	private int numGames, numWins, numLosses;

	public Player(){
		playerSymbol = " * ";
		playerName = "Play Doe";
		numGames = 0;
		numWins = 0;
		numLosses = 0;
	}
	public Player(String symbol, String name){
		playerSymbol = symbol;
		playerName = name;
		numGames = 0;
		numWins = 0;
		numLosses = 0;
	}

	protected void addNumWins(){
		numWins++;
		numGames++;
	}
	protected void addNumLosses(){
		numLosses++;
		numGames++;
	}
	protected void addDraw(){
		numGames++;
	}

	public int getNumWins(){
		return numWins;
	}
	public int getNumLosses(){
		return numLosses;
	}
	public int getNumGames(){
		return numGames;
	}
	public int getNumDraws(){
		return ( numGames - (numWins + numLosses) );
	}

	public String getPlayerName(){
		return playerName;
	}
	public String getPlayerSymbol(){
		return playerSymbol;
	}
	
	@Override
	public String toString(){
		return String.format("Player [name=%20s, symbol=%20s, number of games=%05d, number of wins=%05d, number of losses=%05d, number of draws=%05d]", 
				playerName, playerSymbol, numGames, numWins,numLosses,getNumDraws());
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Player){
			Player otherP = (Player)o;
			if(this.playerName.equalsIgnoreCase(otherP.playerName)){
				if(this.playerSymbol.equalsIgnoreCase(otherP.playerSymbol)){
					if(this.numGames == otherP.numGames){
						if(this.numWins == otherP.numWins){
							if(this.numLosses == otherP.numLosses){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int compareTo(Player otherP) {
		if(otherP != null){
			if(this.numWins < otherP.numWins){
				return -1;
			}
			else if(this.numWins > otherP.numWins){
				return 1;
			}
		}
		return 0;
	}
}
	
