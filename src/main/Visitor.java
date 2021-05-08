package main;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Visitor implements Comparable<Visitor>{
	public int id;
    public int time;
    public int duration;
    public int exitTime;
    
	public Visitor(int id, int time, int duration) {
		this.duration = duration;
		this.id = id;
        this.time = time;
	}

    @Override
    public String toString() {
        return this.id + "-" + this.time + "-" + this.exitTime;
    }

	@Override
	public int compareTo(Visitor o) {
		return exitTime - o.exitTime;
	}
}
