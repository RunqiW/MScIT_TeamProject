package dao;

import model.Card;
import model.Deck;
import model.Description;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DeckTextDao implements DeckDao{
    String filename;
    FileReader fileReader;
    public DeckTextDao(String filename){
        this.filename = filename;
    }

    public void initialize() throws FileNotFoundException {
        this.fileReader = new FileReader(filename);
    }


    @Override
    public Deck readDeck() {
        Scanner scanner = new Scanner(fileReader);
        Description description = readDescription(scanner);
        Deck deck = new Deck(description);
        while(scanner.hasNextLine()){
            deck.addCard(readCard(scanner, description));
        }
        return deck;
    }
    private Description readDescription(Scanner scanner){
        return new Description(scanner.nextLine());
    }
    private Card readCard(Scanner scanner, Description description){
        String name = scanner.next();
        int[] values = new int[5];
        values[0] = scanner.nextInt();
        values[1] = scanner.nextInt();
        values[2] = scanner.nextInt();
        values[3] = scanner.nextInt();
        values[4] = scanner.nextInt();
        if(scanner.hasNextLine()){
            scanner.nextLine();
        }
        return new Card(name ,description, values);
    }
    public void close() throws IOException {
        fileReader.close();
    }
}
