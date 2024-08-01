package org.example;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Deck implements DeckActions{

    private Objects/* you can change this to any type you want*/ myCards;
    private int numCards;


    @Override
    public void shuffle() {

    }

    @Override
    public Card dealNextCard() {
        return null;
    }

    @Override
    public void printDeck(int numToPrint) {

    }
}
