package online.dwResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//import javax.ws.rs.Consumes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;

import dao.DaoFactory;
import dao.DeckTextDao;
import dao.Statistic;
import dao.StatisticSQLDao;
import game.*;
import io.dropwizard.jersey.sessions.Session;
import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import online.dwViews.GameScreenView;
import online.dwViews.StatisticsView;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps

/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controlled from a Web page.
 */
public class TopTrumpsRESTAPI {
	TopTrumpsJSONConfiguration conf;
	/**
	 * Constructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// Add relevant initialization here
		this.conf = conf;
	}

	// Add relevant API methods here
    @GET
	@Path("/newGame")
	public GameScreenView newGame(@Session HttpSession session) {
		//initial deck & game
		DaoFactory daoFactory =conf.getDao();
		DeckTextDao deckDao =daoFactory.getDeckTextDao();
		deckDao.initialize();
		Deck deck = new Deck();
		deckDao.readDeck(deck);
		deckDao.close();
		deck.shuffle();
		Game game = new Game(deck, (conf.getNumAIPlayers()+1));
		//initial game screen view.
		GameScreenView gameScreenView = new GameScreenView();
		LinkedList<Player> players = new LinkedList<Player>();
		gameScreenView.setDropBtn(deck.getCategory());
		//start the first round.
		game.startRound();
		//set data in game screen view.
		for(int j=0;j<5;j++){ gameScreenView.setBtnDisplay(j,false); }
		players.clear(); players.add(game.getHumanPlayer());
		gameScreenView.setPlayers(players);

		if (game.getCurrentPlayer().isHuman()) {
			//views when human choose category
			gameScreenView.setRoundProgress("Round: "+game.getRounds()+". Waiting on you to select a category");
			gameScreenView.setBtnDisplay(1,true);
		}else{
            //views when AI choose category
			gameScreenView.setRoundProgress("Round: "+game.getRounds()+". Player have drawn their cards");
			gameScreenView.setBtnDisplay(0,true);
		}
		gameScreenView.setCurrentPlayer("Active player is "+game.getCurrentPlayer().getPlayerName());
		gameScreenView.setCategorySelection("");
		// write http session.
		session.setAttribute("game",game);
		session.setAttribute("gameView",gameScreenView);
		session.setAttribute("players",players);

		return gameScreenView;
	}

	@GET
	@Path("/displayAISelection")
	public GameScreenView displayAISelection(@Session HttpSession session){
        //read http session
		Game game = (Game)session.getAttribute("game");
		LinkedList<Player> players = (LinkedList<Player>)session.getAttribute("players");
		GameScreenView gameScreenView = (GameScreenView)session.getAttribute("gameView");
        // let AI choose
		game.chooseCategory();

		//set view
		for(int j=0;j<5;j++){ gameScreenView.setBtnDisplay(j,false); }
		players.clear();
		players.addAll(game.getPlayers());
		gameScreenView.setPlayers(players);
		gameScreenView.setRoundProgress("Round: "+game.getRounds()+". "+game.getCurrentPlayer().getPlayerName()+" have made the selection");
		gameScreenView.setCurrentPlayer("Active player is "+game.getCurrentPlayer().getPlayerName());
		gameScreenView.setCategorySelection(game.getCurrentPlayer().getPlayerName()+" selected "+game.getCurrentPlayer().getDeck().getCategory()[game.getCurrentCategory()]);
		gameScreenView.setBtnDisplay(2,true);

		return gameScreenView;
	}
	@GET
	@Path("/toSelectCategory")
	public GameScreenView toSelectCategory(@Session HttpSession session,@QueryParam("dropBtn") int index){
        //read session
		Game game = (Game)session.getAttribute("game");
		LinkedList<Player> players = (LinkedList<Player>)session.getAttribute("players");
		GameScreenView gameScreenView = (GameScreenView)session.getAttribute("gameView");
        //read drop button form
		game.setCurrentCategory(index);
        //view
		for(int j=0;j<5;j++){ gameScreenView.setBtnDisplay(j,false); }
		players.clear();
		players.addAll(game.getPlayers());
		gameScreenView.setPlayers(players);
		gameScreenView.setRoundProgress("Round: "+game.getRounds()+". "+game.getCurrentPlayer().getPlayerName()+" have made the selection");
		gameScreenView.setCurrentPlayer("Active player is "+game.getCurrentPlayer().getPlayerName());
		gameScreenView.setCategorySelection(game.getCurrentPlayer().getPlayerName()+" selected "+game.getCurrentPlayer().getDeck().getCategory()[game.getCurrentCategory()]);
		gameScreenView.setBtnDisplay(2,true);

		return gameScreenView;
	}

	@GET
	@Path("/showWinner")
	public GameScreenView showWinner(@Session HttpSession session){
        //read http session
		Game game = (Game)session.getAttribute("game");
		LinkedList<Player> players = (LinkedList<Player>)session.getAttribute("players");
		GameScreenView gameScreenView = (GameScreenView)session.getAttribute("gameView");
        // end round
		game.checkRoundResult();

		gameScreenView.setCategorySelection("");
		for(int j=0;j<5;j++){ gameScreenView.setBtnDisplay(j,false); }
		//check if game end/ if human lose/ if this round draw
		if(game.checkGameEnd()){
			// if game end (players<=1)
			gameScreenView.setRoundProgress("Round: " + game.getRounds() + ". " + game.getPlayers().getFirst().getPlayerName() + " wins this round.");
			gameScreenView.setCurrentPlayer("Game Over."+game.getPlayers().getFirst().getPlayerName() + " wins the game.");
			gameScreenView.setBtnDisplay(4,true);
			String stat = "";
			for(int i =0;i<conf.getNumAIPlayers()+1;i++){
				Player p = game.getAllPlayer().get(i);
				stat = stat + p.getPlayerName()+" wins "+p.getRoundsWon()+"rounds.</br>";
			}
			gameScreenView.setCategorySelection(game.getRounds()+" rounds played, include"+game.getDraws()+ " draws.</br>"+stat);

			players.clear();
			players.addAll(game.getPlayers());
			gameScreenView.setPlayers(players);
			//write statistics
			StatisticSQLDao statisticSQLDao = conf.getDao().getStaticDao();
			statisticSQLDao.initialize();
			statisticSQLDao.writeGame(game);
			Iterator<Player> it = game.getAllPlayer().iterator();
			while(it.hasNext()){
				statisticSQLDao.writePlayer(it.next());
			}
			statisticSQLDao.close();
		}else{
			if(game.isHumanFailed()){
				// if human lose & game continues,auto play till end
				while(!game.checkGameEnd()){
					game.startRound();
					game.chooseCategory();
					game.checkRoundResult();
				}
                // show statistics in this game
				String stat = "";
				for(int i =0;i<conf.getNumAIPlayers()+1;i++){
					Player p = game.getAllPlayer().get(i);
					stat = stat + p.getPlayerName()+" wins "+p.getRoundsWon()+" rounds.</br>";
				}
				gameScreenView.setRoundProgress("Round: " + game.getRounds() + ". "+ game.getPlayers().getFirst().getPlayerName()+" wins the game.");
				gameScreenView.setCurrentPlayer("You Lose. Game is auto-played till end.");
				gameScreenView.setCategorySelection(game.getRounds()+" rounds played, include"+game.getDraws()+ " draws.</br>"+stat);
				gameScreenView.setBtnDisplay(4,true);

				players.clear();
				players.addAll(game.getPlayers());
				gameScreenView.setPlayers(players);
                //write statistics
				StatisticSQLDao statisticSQLDao = conf.getDao().getStaticDao();
				statisticSQLDao.initialize();
				statisticSQLDao.writeGame(game);
				Iterator<Player> it = game.getAllPlayer().iterator();
				while(it.hasNext()){
					statisticSQLDao.writePlayer(it.next());
				}
				statisticSQLDao.close();
			}else{
				if(game.getWinner()==null){
					//if round draw
					gameScreenView.setRoundProgress("Round: "+game.getRounds()+". Draw - cards sent to communal pile("+game.getCommunalPile().getSize() +" cards).");
					gameScreenView.setCurrentPlayer("");
					gameScreenView.setCategorySelection("");
					gameScreenView.setBtnDisplay(3,true);

					players.clear();
					gameScreenView.setPlayers(players);
				}else{
					//if round ends normally
					gameScreenView.setRoundProgress("Round: "+game.getRounds()+". "+game.getWinner().getPlayerName()+" wins this round.");
					gameScreenView.setCurrentPlayer("");
					gameScreenView.setCategorySelection("");
					gameScreenView.setBtnDisplay(3,true);

					players.clear();
					gameScreenView.setPlayers(players);
				}
			}
		}
		return gameScreenView;
	}

	@GET
	@Path("/nextRound")
	public GameScreenView nextRound(@Session HttpSession session){
		// read http session
		Game game = (Game)session.getAttribute("game");
		LinkedList<Player> players = (LinkedList<Player>)session.getAttribute("players");
		GameScreenView gameScreenView = (GameScreenView)session.getAttribute("gameView");
        // start new round, same as start round part in newGame
		game.startRound();

		for(int j=0;j<5;j++){ gameScreenView.setBtnDisplay(j,false); }
		players.clear(); players.add(game.getHumanPlayer());
		gameScreenView.setPlayers(players);
		if (game.getCurrentPlayer().isHuman()) {
			gameScreenView.setRoundProgress("Round: "+game.getRounds()+". Waiting on you to a category");
			gameScreenView.setBtnDisplay(1,true);
		}else{
			gameScreenView.setRoundProgress("Round: "+game.getRounds()+". Player have drawn their cards");
			gameScreenView.setBtnDisplay(0,true);
		}
		gameScreenView.setCurrentPlayer("Active player is "+game.getCurrentPlayer().getPlayerName());
		gameScreenView.setCategorySelection("");
		return gameScreenView;
	}

	@GET
	@Path("/stats")
	public StatisticsView statisticsView(){
		StatisticSQLDao statisticSQLDao = conf.getDao().getStaticDao();
		statisticSQLDao.initialize();
		Statistic statistic = statisticSQLDao.readStatistic();
		statisticSQLDao.close();
		return new StatisticsView(statistic);
	}
}
