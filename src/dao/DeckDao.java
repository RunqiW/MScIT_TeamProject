package dao;

import model.Deck;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DeckDao {
    public Deck readDeck();
    public void close() throws IOException;
    public void initialize() throws FileNotFoundException, IOException;
}
