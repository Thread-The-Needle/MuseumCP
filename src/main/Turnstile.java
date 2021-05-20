package main;

import java.util.*;

public class Turnstile {

    ArrayList<String> turnstile = new ArrayList<String>();

    public Turnstile(){
        turnstile.add("1");
        turnstile.add("2");
        turnstile.add("3");
        turnstile.add("4");
    }

    public void shuffle(){
        Collections.shuffle(turnstile, new Random(10));
    }
}
