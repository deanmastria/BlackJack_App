package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements DeckActions{
    private List<Card> cards;  // List to hold the cards in the deck


    // Loop through each suit and rank to create the full deck of cards
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"}; //Array of suits
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; //Array of ranks

        // Method to shuffle the deck of cards
        for (String suit : suits) {
            for(String rank : ranks) {
                cards.add(new Card(suit, rank));  // Add a new card to the deck
            }
        }
    }
    // Method to shuffle the deck of cards
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Method to deal a card from the deck (removes and returns the top card)
    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;    // Return null if the deck is empty (or throw an exception)
        }
        return cards.remove(cards.size() -1);       // Remove and return the last card in the list
    }

    // Overriding the toString method to provide a string representation of the deck
    @Override
    public String toString() {
        StringBuilder deckString = new StringBuilder(); // Use StringBuilder for efficient string concatenation
        for (Card card : cards) {
            deckString.append(card.toString()).append("\n");    // Append each card to the string
        }
        return deckString.toString();
    }



    @Override
    public Card dealNextCard() {
        return null;
    }

    @Override
    public void printDeck(int numToPrint) {

    }
}
