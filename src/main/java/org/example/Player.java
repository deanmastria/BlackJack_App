package org.example;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


//implementation of blackjack player
public class Player {
    private String name;
    private List<Card> hand;    // List to hold the player's hand of cards

    // Constructor to initialize the player with a name
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();  // Initialize the hand as an empty list
    }
    // Method for the player to receive a card
    public void receiveCard(Card card) {
        hand.add(card);     // Add the card to the player's hand

    }
    // Method to display the player's hand
    public void showHand() {
        System.out.println(name + " has " + hand.size() + " cards");        // Print the player's name
        for (Card card : hand) {
            System.out.println(card);           // Print each card in the player's hand
        }
    }
    // Getter method to retrieve the player's name
    public String getName() {
        return name;
    }




}