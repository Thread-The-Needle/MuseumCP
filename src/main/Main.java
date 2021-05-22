package main;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	
	static LinkedList<Ticket> groupTicket = new LinkedList<>();
	static PriorityBlockingQueue<Ticket> ticketsEntered = new PriorityBlockingQueue<>();
	static volatile AtomicInteger counter = new AtomicInteger(0);
	static volatile AtomicInteger visitorMuseum = new AtomicInteger(0);
	static volatile AtomicInteger totalTicket = new AtomicInteger(0);
	
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(6);
		TimeStamp ts = new TimeStamp();
		TicketCounter tk = new TicketCounter(ts);
		Enter etn = new Enter(ts, "NE");
		Enter ets = new Enter(ts, "SE");
		Exit exe = new Exit(ts,"EE");
		Exit exw = new Exit(ts,"WE");

		executor.execute((Runnable) ts);
		executor.execute((Runnable) tk);
		executor.execute((Runnable) etn);
		executor.execute((Runnable) ets);
		executor.execute((Runnable) exe);
		executor.execute((Runnable) exw);
		executor.shutdown();
	}
}
