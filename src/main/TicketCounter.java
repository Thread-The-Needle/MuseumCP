package main;


import java.util.Random;

public class TicketCounter implements Runnable{
	
	private Integer ticketSold;
	TimeStamp ts; 
	
	public TicketCounter(TimeStamp time) {
		this.ticketSold = 0;
		this.ts = time;
	}
	public void saleTicket() throws InterruptedException {
		
		int ranVisitor = new Random().nextInt(4) + 1;
		int ranTime = new Random().nextInt(4);
		ts.msg(ts.timeStamp);
		System.out.printf(" Tickets ");
		
		int duration = new Random().nextInt(101) + 50;
		
		for (int i = 0; i < ranVisitor; i++) {
			ticketSold++;
			System.out.printf("T%04d, " ,ticketSold);
			Main.visitors.add(new Visitor(ticketSold,ts.timeStamp, duration));
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
