package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BetManager {
    private final Scanner sc;

    public BetManager(Scanner sc) {
        this.sc = sc;
    }

    public int placeBet(Player player) {
        int bet = 0;
        while (true) {
            System.out.printf("Place Bet (increments of 5): ");
            try {
                bet = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume the invalid input
                continue;
            }
            sc.nextLine(); // Consume the newline

            if (bet == 0 || (bet % 5 == 0 && player.placeBet(bet))) {
                break;
            } else if (bet % 5 != 0) {
                System.out.println("Bet must be in increments of 5. Try again.");
            } else {
                System.out.println("Not enough money to place bet. Try again or enter 0 to leave the table.");
            }
        }
        return bet;
    }
}
