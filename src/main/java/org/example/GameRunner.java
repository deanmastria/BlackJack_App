package org.example;

import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Deck deck = new Deck();     // Create a new deck of cards
        deck.shuffle();

        // Create Player and Dealer
        Player p1 = new Player("P1", 1000); // Player starts with a balance of 1000
        Dealer dealer = new Dealer();

        while (true) {
            // Start new round
            p1.resetHand();
            dealer.resetHand();
            deck.shuffle();

            System.out.println("Balance: " + p1.getBalance());

            int bet = 0;
            while (true) {
                System.out.printf("Place Bet: ");
                bet = sc.nextInt();
                sc.nextLine(); // Consume the newline

                if (!p1.placeBet(bet)) {
                    System.out.printf("Not enough $ in wallet, try again or enter 0 to leave the table");
                } else {
                    break;
                }

                if (bet == 0) {
                    System.out.println("p1 has left the table, remaining funds: " + p1.getBalance());
                    System.out.printf("See you next time!");
                    return;
                }
            }
            // Deal a hand of 2 cards to the player
            p1.receiveCard(deck.dealCard()); // Deal the first card to the player
            p1.receiveCard(deck.dealCard()); // Deal the second card to the player

            // Dealer's initial hand
            dealer.receiveCard(deck.dealCard());
            dealer.receiveCard(deck.dealCard());

            // Show the player's hand
            p1.showHand();
            System.out.printf("P1 has " + p1.calculateHandValue());

            System.out.printf("\nDealer shows " + dealer.getHand().get(0));

            // Player's turn
            while (true) {
                System.out.println("\nHit or Stand? (h/s)");
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("h")) {
                    p1.receiveCard(deck.dealCard());
                    p1.showHand();
                    System.out.println("Player's hand value: " + p1.calculateHandValue());

                    if (p1.isBust()) {
                        System.out.println("Bust! Dealer wins.");
                        break;
                    }
                } else if (input.equalsIgnoreCase("s")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'h' to hit or 's' to stand.");
                }
            }

            if (p1.isBust()) {
                continue;
            }

            // Dealer's turn
            dealer.showHand();
            System.out.println("Dealer's hand value: " + dealer.calculateHandValue());

            while (dealer.shouldHit()) {
                dealer.receiveCard(deck.dealCard());
                dealer.showHand();
                System.out.println("Dealer's hand value: " + dealer.calculateHandValue());

                if (dealer.isBust()) {
                    System.out.println("Dealer busts! Player wins.");
                    p1.updateBalance(bet * 2); // Player wins, double the bet
                    break;
                }
            }

            if (dealer.isBust()) {
                continue;
            }

            // Determine the winner
            int playerValue = p1.calculateHandValue();
            int dealerValue = dealer.calculateHandValue();

            System.out.println("Player's final hand value: " + playerValue);
            System.out.println("Dealer's final hand value: " + dealerValue);

            if (playerValue > dealerValue) {
                System.out.println("Player wins!");
                p1.updateBalance(bet * 2); // Player wins, double the bet
            } else if (playerValue < dealerValue) {
                System.out.println("Dealer wins!");
            } else {
                System.out.println("It's a tie!");
                p1.updateBalance(bet); // Return the bet to player
            }

            System.out.println("Current Balance: " + p1.getBalance());

            if (p1.getBalance() <= 0) {
                System.out.println("You are out of funds, insert $$$ to play");
                break;
            }

            System.out.println("Do you want to play another round? (y/n)");
            String anotherRound = sc.nextLine();

            if (!anotherRound.equalsIgnoreCase("y")) {
                break;
            }
        }

        System.out.println("Thanks for playing!");
    }
}
//        System.out.println("Starting Deck");
//        System.out.println(deck);
//        // play some music
//        String filepath = "CasinoJazz.wav";
//        PlayMusic music = new PlayMusic();
//        music.playMusic(filepath);









