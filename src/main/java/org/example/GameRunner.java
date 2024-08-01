package org.example;

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
        Player player = new Player(playerName, 1000); // Player starts with a balance of 1000
        Dealer dealer = new Dealer();

        // Create managers
        BetManager betManager = new BetManager(sc);
        PlayerActionManager actionManager = new PlayerActionManager(sc, deck);

        while (true) {
            // Start new round
            player.resetHand();
            dealer.resetHand();
            deck.shuffle();

            System.out.println("Balance: " + player.getBalance());

            int bet = betManager.placeBet(player);
            if (bet == 0) {
                System.out.println(player.getName() + " has left the table. Remaining funds: " + player.getBalance());
                System.out.println("See you next time!");
                break;
            }

            // Deal a hand of 2 cards to the player
            player.receiveCard(deck.dealCard());
            player.receiveCard(deck.dealCard());

            // Dealer's initial hand
            dealer.receiveCard(deck.dealCard());
            dealer.receiveCard(deck.dealCard());

            // Show the player's hand
            player.showHand();
            System.out.printf(player.getName() + " has " + player.calculateHandValue());
            System.out.printf("\nDealer shows " + dealer.getHand().get(0));

            // Check if player can split
            actionManager.checkSplit(player, bet);

            // Player's turn for first hand
            boolean playerBust = actionManager.playHand(player, bet, false);

            if (playerBust) {
                promptToPlayAgain(sc, player);
                continue; // Player busts, move to the next round
            }

            // Player's turn for split hand (if any)
            if (player.getSplitHand() != null) {
                System.out.println("Playing split hand:");
                playerBust = actionManager.playHand(player, bet, true);
                if (playerBust) {
                    promptToPlayAgain(sc, player);
                    continue; // Split hand busts, move to the next round
                }
            }

            // Dealer's turn
            dealer.showHand();
            System.out.println("Dealer's hand value: " + dealer.calculateHandValue());

            while (dealer.shouldHit()) {
                dealer.receiveCard(deck.dealCard());
                dealer.showHand();
                System.out.println("Dealer's hand value: " + dealer.calculateHandValue());

                if (dealer.isBust()) {
                    System.out.println("Dealer busts! " + player.getName() + " wins.");
                    player.updateBalance(bet * 2); // Player wins, double the bet
                    break;
                }
            }

            if (dealer.isBust()) {
                promptToPlayAgain(sc, player);
                continue; // Dealer busts, move to the next round
            }

            // Determine the winner for first hand
            int playerValue = player.calculateHandValue();
            int dealerValue = dealer.calculateHandValue();

            System.out.println(player.getName() + "'s final hand value: " + playerValue);
            System.out.println("Dealer's final hand value: " + dealerValue);

            if (playerValue > dealerValue) {
                System.out.println(player.getName() + " wins!");
                player.updateBalance(bet * 2); // Player wins, double the bet
            } else if (playerValue < dealerValue) {
                System.out.println("Dealer wins!");
            } else {
                System.out.println("It's a tie!");
                player.updateBalance(bet); // Return the bet to player
            }

            // Determine the winner for split hand (if any)
            if (player.getSplitHand() != null) {
                int splitHandValue = player.calculateSplitHandValue();
                System.out.println(player.getName() + "'s split hand final value: " + splitHandValue);

                if (splitHandValue > dealerValue && !player.isSplitHandBust()) {
                    System.out.println(player.getName() + " wins with the split hand!");
                    player.updateBalance(bet * 2); // Player wins, double the bet
                } else if (splitHandValue < dealerValue || player.isSplitHandBust()) {
                    System.out.println("Dealer wins against the split hand!");
                } else {
                    System.out.println("It's a tie with the split hand!");
                    player.updateBalance(bet); // Return the bet to player
                }
            }

            System.out.println("Current Balance: " + player.getBalance());

            if (player.getBalance() <= 0) {
                System.out.println("You are out of funds, insert $$$ to play");
                break;
            }

            promptToPlayAgain(sc, player);
        }
    }

    private static void promptToPlayAgain(Scanner sc, Player player) {
        while (true) {
            System.out.println("Do you want to play another round? (y/n)");
            String anotherRound = sc.nextLine();
            if (anotherRound.equalsIgnoreCase("y")) {
                break;
            } else if (anotherRound.equalsIgnoreCase("n")) {
                System.out.println("Thanks for playing!");
                System.exit(0); // Exit the program
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
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









