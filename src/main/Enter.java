package main;

import java.util.Random;

public class Enter implements Runnable{
	String gate;
	Integer maxVisitor;

	TimeStamp ts;
	public Enter(TimeStamp ts, String gate) {
		this.maxVisitor = 100;
		this.ts = ts;
		this.gate = gate;
	}
	
	public boolean enter() throws InterruptedException {
		
		if(Main.ticketsEntered.size() >= maxVisitor) {
			return false;
		}
		
		int turnstile = new Random().nextInt(4) + 1;
		Visitor visitor = Main.visitors.pollFirst();
		
		if (visitor == null) {
			return false;
		}
		
		String id = ts.fd.format(visitor.id);
		String hr = ts.ft.format(ts.timeStamp/60);
		String min = ts.ft.format(ts.timeStamp%60);
		
		System.out.println("["+hr+":"+min+"]"+" T" + id + " entered through Turnstile "+ 
		gate + turnstile + ". Stay for " + visitor.duration + " minutes");
		
		visitor.exitTime = ts.timeStamp + visitor.duration;
		Main.ticketsEntered.add(visitor);
		return true;
	}
	@Override
	public void run() {
		while(true) {
			if(ts.museumCounter) {
				try {
					enter();
				} catch (InterruptedException e) {}
			}
			try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {}
		}
	}

}
