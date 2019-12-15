import java.lang.Math;
/**
 *This class serves to create a strategy table for an AI to use once it has played 
 *with itself for TRIALS amount of games, in which case it will choose the move
 *for each turn according to the strategy table
 * 
 * Bugs: (The only problem that I have seen so far is how "intelligent" the AI is, even after 100000 games:
 * On average, the win/loss ratio of a "smart" AI vs the control AI is only 1.38 compared to the approximately
 * 2500 ratio of the screenshot example) 
 * 
 * @author (Sean Gallogly) */
public class StratTable {
	private static int[][] gamesData = new int[365][2];
	
	StratTable() {
		for (int i = 0 ; i < 365 ; i++) {
			for (int j = 0 ; j < 2 ; j++) {
				if (!((i == 364) && (j == 0))) {
					gamesData[i][j] = 1;
				}
				else {
					gamesData[i][j] = 0;
				}
			}
		}
	}
	/**
	 *The update method serves to enter data points into the gamesData array for each player
	 *after each game during the learning stage
	 *
	 * @param (preGameMoves) (The array of moves that have been recorded for this player in the previous game)
	 * @param (numMovesMade) (The number of moves that this player has made in the previous game)
	 * @param (winLose) (is either 0 or 1 depending on whether the player lost or won, respectively)
	 */
	public static void update(Date[] prevGameMoves , int numMovesMade , int winLose) {
		for (int i = 0 ; i < numMovesMade ; i++) {
			if (winLose == 0) {
				gamesData[Date.dayNum(prevGameMoves[i])][0]++;
			}
			else if (winLose == 1) {
				gamesData[Date.dayNum(prevGameMoves[i])][1]++;
			}
		}
	}
	/**
	 *This function calculates the winRate (W/(W+L)) of the move that is passed to it as an argument
	 *
	 * @param (inLegalMove) (The legal move who's winrate is to be calcuated)
	 * @return (returns the double representation of the winRate so far for this particular date)
	 */
	public static double winRate(Date inLegalMove) {
		Double winRate = ((double)gamesData[Date.dayNum(inLegalMove)][1]) / ((double)((gamesData[Date.dayNum(inLegalMove)][0]) + (gamesData[Date.dayNum(inLegalMove)][1])));
		return winRate;
	}
	/**
	 *This method chooses the most likely move in a random way, proprotional to the 
	 *winRates of the dates in the rate passed to it as an argument
	 *
	 * @param (inLegalMoves) (the array of legal moves after a given game)
	 * @return (returns the move that is chosen based on a random double and whether that double
	 * is within the range of the normalized winrate of this move vs. the next index date's normalized winrate  )
	 */
	public static Date likelyMove(Date[] inLegalMoves) {
		double[] percs = new double[inLegalMoves.length];
		double sum1 = 0;
		double sum2 = 0;
		for (int i = 0 ; i < inLegalMoves.length ; i++) {
			percs[i] = winRate(inLegalMoves[i]);
			sum1 += percs[i];
		}
		for (int i = 0 ; i < inLegalMoves.length ; i++) {
			percs[i] = winRate(inLegalMoves[i]) / sum1;
			sum2 +=percs[i];
			percs[i] = sum2;
		}
		int i = 0;
		while (Project1.rangen.nextDouble() > percs[i]) {
			i++;
		}
		return inLegalMoves[i];
	}
	/**
	 *This method is called after the learning phase and directly chooses, of all possible
	 *legal dates, the date with the best winrate
	 *
	 * @param (inLegalMoves) (the legalMoves array (now for each date in this array there is a unique winrate associated
	 * with it) for the previous move)
	 * @return (returns the move within the legal moves array of the previous move that has the highest winrate)
	 */
	public static Date bestMove(Date[] inLegalMoves) { 
		Date bestMove;
		bestMove = inLegalMoves[0];
		double winRate = winRate(inLegalMoves[0]);
		for (int i = 1 ; i < inLegalMoves.length ; i++) {
			if (Math.abs(winRate - winRate(inLegalMoves[i])) < 0.000001 ) {
				if (Date.dayNum(inLegalMoves[i]) > Date.dayNum(bestMove)) {
					bestMove = inLegalMoves[i];
					winRate = winRate(inLegalMoves[i]);
				}
			}
			else if ((winRate(inLegalMoves[i])) > winRate) {
				bestMove = inLegalMoves[i];
				winRate = winRate(inLegalMoves[i]);
			}
		}
		return bestMove;
	}
}
