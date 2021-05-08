package main;


import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketCounter implements Runnable{
	
	private Integer ticketSold;
	private Integer maxTicket;
	private Integer buyTime;
	private ReentrantLock lock;
	private Integer timeSP;
	Stack<Integer> ticketId;
	TimeStamp ts; 
	
	public TicketCounter(TimeStamp time) {
		this.ticketSold = 0;
		this.maxTicket = 900;
		this.ts = time;
		this.lock = new ReentrantLock();
		this.ticketId = new Stack<Integer>();
	}
	public void saleTicket() throws InterruptedException {
		
		int ranVisitor = new Random().nextInt(4) + 1;
		int ranTime = new Random().nextInt(4);
		ts.msg(ts.timeStamp);
		System.out.printf(" Tickets ");
		
		int duration = new Random().nextInt(101) + 50;
		
		for (int i = 0; i < ranVisitor; i++) {
			ticketSold++;
			ticketId.add(ticketSold);
			System.out.printf("T%04d, " ,ticketId.get(ticketSold-1));
			Main.visitors.add(new Visitor(ticketSold,ts.timeStamp, duration));
//			System.out.println(Main.visitors.toString());
		}
		System.out.println("sold");
		Thread.sleep((ranTime*300));
	}
	
	
	@Override
	public void  run() {
		while(true) {
		if(ts.tickerCounter) {
			try {
				saleTicket();
			} catch (InterruptedException e) {}
		}
		try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
		}
	

}
