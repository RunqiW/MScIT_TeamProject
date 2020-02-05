package dao;

import model.Deck;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DeckSQLDao implements DeckDao {
    String url;
    public DeckSQLDao() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url);
    }

    @Override
    public Deck readDeck() {
        return null;
    }

    @Override
    public void close() throws FileNotFoundException {

    }

    @Override
    public void initialize() throws IOException {

    }
}
