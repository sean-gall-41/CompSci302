/**
 * This class serves to define a new player for the New Years game.
 * It also serves to give that player methods related to that player's
 * in-game score, how many strikes they have, etc.
 * 
 * Bugs: (Do not know of any except have not called the record method oops) 
 * 
 * @author (Sean Gallogly) */
public class Player {
	private String name; //will be the name of the initialized player
	private int currStrikes, gamesWon; //currStrikes is the number of strikes that the player has at the moment ; gamesWon is the number of games won by this player
	private boolean isHuman; //a flag to determine whether the player is human or not 
	private Date[] moves; //An array containing all of the valid moves the player has input 
	private int numMoves; //The number of moves the player has taken in a game 
	
	public Player(String inName){
		this.name = inName;
		this.currStrikes = 0;
		this.gamesWon = 0;
		moves = new Date[22];
		numMoves = 0;
		if(inName.length()<2){ //avoids substring error
			isHuman = true;
		}
		else{
			this.isHuman = !inName.substring(0,2).equals("AI");
		}
	}
	/**
	 *obtains the moves array (which contains all legal moves made in a game) of the player calling it
	 * @return (the calling player's moves array)
	 */
	public Date[] getmoves() {
		return this.moves;
	}
	/**
	 *Obtains the number of moves this player has made in an arbitrary game
	 * @return (returns the integer representation of the number of moves this player has made in this game)
	 */
	public int getnumMoves() {
		return this.numMoves;
	}
	/**
	 *Determines whether the calling instance is a human or an AI
	 *
	 * @return (returns true or false whether player is human or not)
	 */
	public boolean getIsHuman() {
	return this.isHuman;
	}
	/**
	 *Obtains the number of wins that the calling player has
	 *
	 * @return (returns the integer representation of games won by this player)
	 */
	public int getWins(){
		return this.gamesWon;
	}
	/**
	 *Obtains the number of strikes accrued by the calling player (Note: if the player is an
	 *AI, this method is never called)
	 *
	 * @return (returns an integer representation of the number of strikes the current player has)
	 */
	public int getStrikes(){
		return this.currStrikes;
	}
	/**
	 *Increments the number of strikes this player has and prints a println statement
	 *declaring how many strikes the current player now has   
	 */
	public void incStrikes(){
		System.out.println(this.name+" now has "+ ++this.currStrikes +" strikes");
	}
	/**
	 *Resets the number of strikes a player has as well as the number of moves that player has
	 *played in a given game
	 */
	public void reset(){
		this.currStrikes = 0;
		this.numMoves    = 0;
	}
	/**
	 *Increments the number of games won by the calling instance
	 */
	public void won(){
		this.gamesWon++;
	}
	/**
	 * A getter for the calling instance's assigned name
	 * @return (Returns the string representation of this player's name)
	 */
	public String getName(){
		return this.name;
	}
	/**
	 *populates the calling player's moves array with moves the player has made
	 *
	 * @param (inDate) (The date that will be assigned a location in the moves array)
	 */
	public void recordMove(Date inDate){
		moves[numMoves]=inDate;
		numMoves++;
	}
}