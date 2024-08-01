package org.example;

import java.util.Scanner;

public class GameRunner {


    public static void main(String[] args) {
        Deck deck = new Deck();     // Create a new deck of cards
        Player p1 = new Player("P1");   // Create a new player

        deck.shuffle();

        // Deal a hand of 2 cards to the player
        p1.receiveCard(deck.dealCard()); // Deal the first card to the player
        p1.receiveCard(deck.dealCard()); // Deal the second card to the player

        // Show the player's hand
        p1.showHand();

        System.out.println("Starting Deck");
        System.out.println(deck);
        // play some music
        String filepath = "CasinoJazz.wav";
        PlayMusic music = new PlayMusic();
        music.playMusic(filepath);


        Scanner sc = new Scanner(System.in);



    }
}

