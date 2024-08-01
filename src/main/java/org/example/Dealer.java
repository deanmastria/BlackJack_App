package org.example;

public class Dealer extends Player {

    public Dealer() {
        super("Dealer", 0);
    }

    public boolean shouldHit() {
        return calculateHandValue() < 17;
    }
}


