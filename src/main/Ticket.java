package main;

import java.util.LinkedList;

public class Ticket implements Comparable<Ticket>{
    // public LinkedList<Visitor> visitor;
    private LinkedList<String> id;
    private int time;
    private int duration;
    private int exitTime;

    // public Ticket(LinkedList<Visitor> visitor){
    //     this.visitor = visitor;
    // }
    public Ticket(LinkedList<String> id, int time, int duration){
        this.duration = duration;
		this.id = id;
        this.time = time;
    }

    @Override
    public String toString() {
        return this.id + "-" + this.time + "-" + this.exitTime;
    }

    public String ticketID() {
        return this.id.toString();
    }
    
    public String stringVisitor() {
        return "ID:"+this.id.toString() + " Enter:" + this.time + " Exit:" + this.exitTime + " Duration:" + this.duration;
    }

    public int ticketSize() {
        return this.id.size();
    }

    public int exitTime(int et) {
        return this.exitTime = et;
    }

    public int duration() {
        return this.duration;
    }

    public int retriveExitTime() {
        return this.exitTime;
    }

    public int timeEnter(int te) {
        return (this.time = te);
    }

    public LinkedList<String> ticketId() {
        return this.id;
    }

    @Override
    public int compareTo(Ticket o) {
        return exitTime - o.exitTime;
    }
}
