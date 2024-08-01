package org.example;

public class Card {
    private String cardSuit; //Suit of card
    private String cardRank;    //Rank of card

        //Constructor to initialize the cards suit and rank
    public Card(String cardSuit, String cardRank) {
        this.cardSuit = cardSuit;
        this.cardRank = cardRank;

    }

        //Getter Method retrieving card suit
    public String getCardSuit() {
        return cardSuit;
    }
        //getter Method retrieving card rank
    public String getCardRank() {
        return cardRank;
    }

    // Overriding the toString method to provide a string representation of the card
    @Override
    public String toString() {
        return cardRank + " of " + cardSuit;
    }



}