package org.example;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


//implementation of blackjack player
public class Player {
    private String name;
    private List<Card> hand;    // List to hold the player's hand of cards
    private int balance;

    // Constructor to initialize the player with a name
    public Player(String name, int startingBalance) {
        this.name = name;
        this.hand = new ArrayList<>();  // Initialize the hand as an empty list
        this.balance = startingBalance;
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

    // Method to calculate the total value of the player's hand
    public int calculateHandValue() {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            String rank = card.getCardRank();
            if ("Jack".equals(rank) || "Queen".equals(rank) || "King".equals(rank)) {
                value += 10;
            } else if ("Ace".equals(rank)) {
                value += 11;
                aceCount++;
            } else {
                value += Integer.parseInt(rank);
            }
        }

        // Adjust for Aces if value is over 21
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    // Method to check if the player is bust
    public boolean isBust() {
        return calculateHandValue() > 21;
    }

    // Getter method to retrieve the player's name
    public String getName() {
        return name;
    }
     //Getter to retreive players hand
    public List<Card> getHand() {
        return hand;
    }

    //Method for placing bet
    public boolean placeBet(int bet) {
        if (bet > balance) {
            System.out.println("Not enough money to place bet");
            return false;
        }
        balance -= bet;
        return true;
    }

    //method: update balance based on reults
    public void updateBalance(int amount) {
        balance += amount;
    }
    public int getBalance() {
        return balance;
    }

    public void resetHand() {
        hand.clear();
    }
}