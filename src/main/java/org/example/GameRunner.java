package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Deck deck = new Deck();     // Create a new deck of cards
        deck.shuffle();

        // Prompt the user for their name
        System.out.println("Enter your name: ");
        String playerName = sc.nextLine();

        // Create Player and Dealer
        Player p1 = new Player(playerName, 1000); // Player starts with a balance of 1000
        Dealer dealer = new Dealer();

        while (true) {
            // Start new round
            p1.resetHand();
            dealer.resetHand();
            deck.shuffle();

            System.out.println("Balance: " + p1.getBalance());

            int bet = 0;
            while (true) { // Start of bet validation loop
                System.out.printf("Place Bet (increments of 5): ");
                try {
                    bet = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); // Consume the invalid input
                    continue;
                }
                sc.nextLine(); // Consume the newline

                if (bet == 0) {
                    System.out.println(p1.getName() + " has left the table. Remaining funds: " + p1.getBalance());
                    System.out.println("See you next time!");
                    return;
                }

                if (bet % 5 != 0) {
                    System.out.println("Bet must be in increments of 5. Try again.");
                    continue;
                }

                if (!p1.placeBet(bet)) {
                    System.out.println("Not enough money to place bet. Try again or enter 0 to leave the table.");
                } else {
                    break;
                }
            } // End of bet validation loop

            // Deal a hand of 2 cards to the player
            p1.receiveCard(deck.dealCard()); // Deal the first card to the player
            p1.receiveCard(deck.dealCard()); // Deal the second card to the player

            // Dealer's initial hand
            dealer.receiveCard(deck.dealCard());
            dealer.receiveCard(deck.dealCard());

            // Show the player's hand
            p1.showHand();
            System.out.printf(p1.getName() + " has " + p1.calculateHandValue());

            System.out.printf("\nDealer shows " + dealer.getHand().get(0));

            // Check if player can split
            if (p1.canSplit()) {
                System.out.println("\nDo you want to split your hand? (y/n)");
                String splitInput = sc.nextLine();
                if (splitInput.equalsIgnoreCase("y")) {
                    if (p1.placeBet(bet)) {
                        p1.receiveCardToSplitHand(p1.getHand().remove(1));
                        p1.receiveCard(deck.dealCard());
                        p1.receiveCardToSplitHand(deck.dealCard());
                        System.out.println("Hand split into two hands.");
                        p1.showHand();
                        p1.showSplitHand();
                    } else {
                        System.out.println("Not enough balance to split.");
                    }
                }
            }

            // Player's turn for first hand
            playHand(p1, dealer, sc, deck, bet, false);

            if (p1.isBust() && (p1.getSplitHand() == null || p1.isSplitHandBust())) {
                continue; // Player busts, move to the next round
            }

            // Player's turn for split hand (if any)
            if (p1.getSplitHand() != null) {
                System.out.println("Playing split hand:");
                playHand(p1, dealer, sc, deck, bet, true);
            }

            // Dealer's turn
            dealer.showHand();
            System.out.println("Dealer's hand value: " + dealer.calculateHandValue());

            while (dealer.shouldHit()) {
                dealer.receiveCard(deck.dealCard());
                dealer.showHand();
                System.out.println("Dealer's hand value: " + dealer.calculateHandValue());

                if (dealer.isBust()) {
                    System.out.println("Dealer busts! " + p1.getName() + " wins.");
                    p1.updateBalance(bet * 2); // Player wins, double the bet
                    break;
                }
            }

            if (dealer.isBust()) {
                continue; // Dealer busts, move to the next round
            }

            // Determine the winner for first hand
            int playerValue = p1.calculateHandValue();
            int dealerValue = dealer.calculateHandValue();

            System.out.println(p1.getName() + "'s final hand value: " + playerValue);
            System.out.println("Dealer's final hand value: " + dealerValue);

            if (playerValue > dealerValue) {
                System.out.println(p1.getName() + " wins!");
                p1.updateBalance(bet * 2); // Player wins, double the bet
            } else if (playerValue < dealerValue) {
                System.out.println("Dealer wins!");
            } else {
                System.out.println("It's a tie!");
                p1.updateBalance(bet); // Return the bet to player
            }

            // Determine the winner for split hand (if any)
            if (p1.getSplitHand() != null) {
                int splitHandValue = p1.calculateSplitHandValue();
                System.out.println(p1.getName() + "'s split hand final value: " + splitHandValue);

                if (splitHandValue > dealerValue && !p1.isSplitHandBust()) {
                    System.out.println(p1.getName() + " wins with the split hand!");
                    p1.updateBalance(bet * 2); // Player wins, double the bet
                } else if (splitHandValue < dealerValue || p1.isSplitHandBust()) {
                    System.out.println("Dealer wins against the split hand!");
                } else {
                    System.out.println("It's a tie with the split hand!");
                    p1.updateBalance(bet); // Return the bet to player
                }
            }

            System.out.println("Current Balance: " + p1.getBalance());

            if (p1.getBalance() <= 0) {
                System.out.println("You are out of funds, insert $$$ to play");
                break;
            }

            while (true) {
                System.out.println("Do you want to play another round? (y/n)");
                String anotherRound = sc.nextLine();
                if (anotherRound.equalsIgnoreCase("y")) {
                    break;
                } else if (anotherRound.equalsIgnoreCase("n")) {
                    System.out.println("Thanks for playing!");
                    return;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            }
        }
    }

    private static void playHand(Player p1, Dealer dealer, Scanner sc, Deck deck, int bet, boolean isSplitHand) {
        boolean playerDoubleDown = false;
        while (true) {
            System.out.println("\nHit, Stand, or Double Down? (h/s/d)");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("d")) {
                if (p1.placeBet(bet)) {
                    bet *= 2;
                    playerDoubleDown = true;
                    System.out.println("Bet doubled. New bet: " + bet);
                    if (isSplitHand) {
                        p1.receiveCardToSplitHand(deck.dealCard());
                        p1.showSplitHand();
                        System.out.println("Player's split hand value: " + p1.calculateSplitHandValue());
                    } else {
                        p1.receiveCard(deck.dealCard());
                        p1.showHand();
                        System.out.println("Player's hand value: " + p1.calculateHandValue());
                    }
                    break;
                } else {
                    System.out.println("Not enough balance to double down.");
                }
            } else if (input.equalsIgnoreCase("h")) {
                if (isSplitHand) {
                    p1.receiveCardToSplitHand(deck.dealCard());
                    p1.showSplitHand();
                    System.out.println("Player's split hand value: " + p1.calculateSplitHandValue());
                } else {
                    p1.receiveCard(deck.dealCard());
                    p1.showHand();
                    System.out.println("Player's hand value: " + p1.calculateHandValue());
                }

                if ((isSplitHand && p1.isSplitHandBust()) || (!isSplitHand && p1.isBust())) {
                    System.out.println("Bust! Dealer wins.");
                    break;
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
    }
}




//        System.out.println("Starting Deck");
//        System.out.println(deck);
//        // play some music
//        String filepath = "CasinoJazz.wav";
//        PlayMusic music = new PlayMusic();
//        music.playMusic(filepath);









