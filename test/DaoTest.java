import dao.DeckDao;
import dao.DeckTextDao;
import model.Deck;

public class DaoTest {
    public static void main(String[] args){
        DeckDao dao = new DeckTextDao("StarCitizenDeck.txt");
        Deck deck;
        try{
            dao.initialize();
            deck = dao.readDeck();
            deck.print();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
