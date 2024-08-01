package org.example;

public interface DeckActions {
    public void shuffle() ;

    public Card dealNextCard();

    public void printDeck(int numToPrint) ;
}
