package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;    // List to hold the player's hand of cards
    private List<Card> splitHand; // List to hold the player's split hand of cards (if any)
    private int balance;

    // Constructor to initialize the player with a name and starting balance
    public Player(String name, int startingBalance) {
        this.name = name;
        this.hand = new ArrayList<>();  // Initialize the hand as an empty list
        this.splitHand = null; // Initialize split hand as null
        this.balance = startingBalance; // Initialize the balance
    }

    // Method for the player to receive a card
    public void receiveCard(Card card) {
        hand.add(card); // Add the card to the player's hand
    }

    // Method for the player to receive a card to their split hand
    public void receiveCardToSplitHand(Card card) {
        if (splitHand == null) {
            splitHand = new ArrayList<>();
        }
        splitHand.add(card); // Add the card to the player's split hand
    }

    // Method to display the player's hand
    public void showHand() {
        System.out.println(name + " has " + hand.size() + " cards"); // Print the player's name and number of cards
        for (Card card : hand) {
            System.out.println(card); // Print each card in the player's hand
        }
    }

    // Method to display the player's split hand
    public void showSplitHand() {
        if (splitHand != null) {
            System.out.println(name + " has " + splitHand.size() + " cards in split hand"); // Print the player's name and number of cards in split hand
            for (Card card : splitHand) {
                System.out.println(card); // Print each card in the player's split hand
            }
        }
    }

    // Method to calculate the total value of the player's hand
    public int calculateHandValue() {
        return calculateHandValue(hand);
    }

    // Method to calculate the total value of the player's split hand
    public int calculateSplitHandValue() {
        if (splitHand == null) return 0;
        return calculateHandValue(splitHand);
    }

    // Helper method to calculate the total value of a hand
    private int calculateHandValue(List<Card> hand) {
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

    // Method to check if the player is bust in split hand
    public boolean isSplitHandBust() {
        return calculateSplitHandValue() > 21;
    }

    // Method to check if the player can split
    public boolean canSplit() {
        if (hand.size() == 2) {
            return hand.get(0).getCardRank().equals(hand.get(1).getCardRank());
        }
        return false;
    }

    // Getter method to retrieve the player's name
    public String getName() {
        return name;
    }

    // Getter method to retrieve the player's hand
    public List<Card> getHand() {
        return hand;
    }

    // Getter method to retrieve the player's split hand
    public List<Card> getSplitHand() {
        return splitHand;
    }

    // Method for placing a bet
    public boolean placeBet(int bet) {
        if (bet > balance) {
            System.out.println("Not enough money to place bet");
            return false;
        }
        balance -= bet;
        return true;
    }

    // Method to update balance based on results
    public void updateBalance(int amount) {
        balance += amount;
    }

    // Getter method to retrieve the player's balance
    public int getBalance() {
        return balance;
    }

    // Method to reset the player's hand
    public void resetHand() {
        hand.clear();
        splitHand = null;
    }
}
