import java.util.Scanner;
/**
 * Running a game
 * @author RUNSHENG TIAN
 *
 */
public class StartOneGame {
	// Instance variables
	private Game newgame;
	private Deck newdeck;
	private Scanner sc;
	private int numofchosencate;  //the number of the chosen category
	private int statenumber;
	
	/**
	 * Start a game and choose a category if the current player is human
	 */
	public StartOneGame() {
		newgame = new Game(new Deck(newdeck.getCapacity(),newdeck.getCategories()));
		newgame.startGame(4); // the number of opponents
		
		if(!newgame.checkGameWon()) {      // check is anyone won
				
			if(newgame.getDraws() != 0) {
				newgame.calculateRoundResult(numofchosencate);
			}
			
			else {
				
				if(newgame.getCurrentPlayer()==newgame.getHumanPlayer()) {
			
			    // scanner the number of a category
			        sc = new Scanner(System.in);
			        numofchosencate = sc.nextInt();
		        }
		        else {
			        numofchosencate = newgame.getCurrentPlayer().chooseCategory();
		        }
			}
			
			
			    
			statenumber = newgame.calculateRoundResult(numofchosencate);
			
			if(statenumber==1) {
				newgame.transferCardsToWinner();
				
			}
			else {
				newgame.transferCardsToCommunal();
			
		    }
		
	  }
		
	  // For assessment testing when someone won 
	  System.out.println(newgame.getCurrentPlayer() + " is the winner!");
	}

}
