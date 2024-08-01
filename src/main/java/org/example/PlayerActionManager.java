package org.example;

import java.util.Scanner;

public class PlayerActionManager {
    private final Scanner sc;
    private final Deck deck;

    public PlayerActionManager(Scanner sc, Deck deck) {
        this.sc = sc;
        this.deck = deck;
    }

    public boolean playHand(Player player, int bet, boolean isSplitHand) {
        boolean playerDoubleDown = false;
        while (true) {
            System.out.println("\nHit, Stand, or Double Down? (h/s/d)");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("d")) {
                if (player.placeBet(bet)) {
                    bet *= 2;
                    playerDoubleDown = true;
                    System.out.println("Bet doubled. New bet: " + bet);
                    if (isSplitHand) {
                        player.receiveCardToSplitHand(deck.dealCard());
                        player.showSplitHand();
                        System.out.println("Player's split hand value: " + player.calculateSplitHandValue());
                    } else {
                        player.receiveCard(deck.dealCard());
                        player.showHand();
                        System.out.println("Player's hand value: " + player.calculateHandValue());
                    }
                    break;
                } else {
                    System.out.println("Not enough balance to double down.");
                }
            } else if (input.equalsIgnoreCase("h")) {
                if (isSplitHand) {
                    player.receiveCardToSplitHand(deck.dealCard());
                    player.showSplitHand();
                    System.out.println("Player's split hand value: " + player.calculateSplitHandValue());
                } else {
                    player.receiveCard(deck.dealCard());
                    player.showHand();
                    System.out.println("Player's hand value: " + player.calculateHandValue());
                }

                if ((isSplitHand && player.isSplitHandBust()) || (!isSplitHand && player.isBust())) {
                    System.out.println("Bust! Dealer wins.");
                    return true; // Indicate that the player has busted
                }
            } else if (input.equalsIgnoreCase("s")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'h' to hit, 's' to stand, or 'd' to double down.");
            }

            if (playerDoubleDown) {
                break;
            }
        }
        return false; // Indicate that the player has not busted
    }

    public void checkSplit(Player player, int bet) {
        if (player.canSplit()) {
            System.out.println("\nDo you want to split your hand? (y/n)");
            String splitInput = sc.nextLine();
            if (splitInput.equalsIgnoreCase("y")) {
                if (player.placeBet(bet)) {
                    player.receiveCardToSplitHand(player.getHand().remove(1));
                    player.receiveCard(deck.dealCard());
                    player.receiveCardToSplitHand(deck.dealCard());
                    System.out.println("Hand split into two hands.");
                    player.showHand();
                    player.showSplitHand();
                } else {
                    System.out.println("Not enough balance to split.");
                }
            }
        }
    }
}

