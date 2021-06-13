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

    //shuffle the turnstile so visitor can enter at random
    public void shuffle(){
        Collections.shuffle(turnstile, new Random(10));
    }
}
