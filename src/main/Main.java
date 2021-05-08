package main;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {
	
	static LinkedList<Visitor> visitors = new LinkedList<>();
	static PriorityBlockingQueue<Visitor> ticketsEntered = new PriorityBlockingQueue<>();
	
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(6);
		TimeStamp ts = new TimeStamp();
		TicketCounter tk = new TicketCounter(ts);
		Enter et = new Enter(ts, "NE");
		Exit ex = new Exit(ts,"EE");

		executor.execute((Runnable) ts);
		executor.execute((Runnable) tk);
		executor.execute((Runnable) et);
		executor.execute((Runnable) ex);
		executor.shutdown();
	}
}
