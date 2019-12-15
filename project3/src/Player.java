//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            (Project3)
// Files:            (Project3.java , Player,java , StratTable.java)
// Semester:         (course) Summer 2017
//
// Author:           (Sean Gallogly)
// Email:            (sgallogly@wisc.edu)
// CS Login:         (sgallogly)
// Lecturer's Name:  (Steve Earth)
// Lab Section:      (Summer 2017)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     (Wei Lin Tsai)
// Partner Email:    (wtsai9@wisc.edu)
// Partner CS Login: (wtsai9)
// Lecturer's Name:  (Steve Earth)
// Lab Section:      (summer 2017)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    _x_ Write-up states that Pair Programming is allowed for this assignment.
//    _x_ We have both read the CS302 Pair Programming policy.
//    _x_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.
//
// Persons:          (identify each person and describe their help in detail)
// Online Sources:   (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 *This class serves to help define fields and methods relevant to the
 *players of the hangman game in Project3. For milestone1, there are 
 *only 3 fields and 7 methods, but more will be added to facilitate
 *the looping of games, the AI, and more for milestone 2
 * 
 * Bugs: (cannot think of any at the moment) 
 * 
 * @author (Sean Gallogly) */

public class Player {

	private int numStrikes; //declare number of Strikes that Player has
	private char guess;   //declare letter of word that player guess
	private boolean isHuman; //declare whether player is human or AI
	
	
	/**
	 * 
	 * @return number of strikes player has so far
	 */
	public int getNumStrikes() {
		return this.numStrikes;
	}
	
	/**
	 * 
	 * @param newStrikeNum setter for numStrikes
	 */
	public void setNumStrikes(int newStrikeNum) {
		this.numStrikes = newStrikeNum;
	}
	
	/**
	 * method which take no argument and decrease total of 10 strikes by 1
	 */
	public void decrementStrikes() {
		this.numStrikes--;
	}
	
	/**
	 * 
	 * @return getter for letter of word player Guess 
	 */
	public char getGuess() {
		return this.guess;
	}
	
	/**
	 * 
	 * @param newGuess Setter for letter of word player Guess
	 */
	public void setGuess(char newGuess) {
		this.guess = newGuess;
	}
	
	/**
	 * 
	 * @return getter which return true or false whether player is human or not
	 */
	public boolean getIsHuman() {
		return this.isHuman;
	}
	
	/**
	 * 
	 * @param trueOrFalse Setter which take true or false argument and return whether player is human or not 
	 */
	public void setIsHuman(boolean trueOrFalse) {
		this.isHuman = trueOrFalse;
	}
	
}
