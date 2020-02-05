package model;

import java.util.Iterator;
import java.util.LinkedList;

public class Deck {
    LinkedList<Card> deck;
    Description description;
    public Deck(Description description){
        this.description = description;
        deck =  new LinkedList<Card>();
    }

    public void addCard(Card card){
        deck.push(card);
    }
    public Card removeCard(){
        return deck.poll();
    }
    public void print(){
        Iterator<Card> it = deck.iterator();
        while(it.hasNext()){
            it.next().print();
        }
    }
}
