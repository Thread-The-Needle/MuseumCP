package main;

import java.util.Random;

public class Exit implements Runnable{
	String gate;
	
	TimeStamp ts;
	public Exit(TimeStamp ts, String gate) {
		this.ts = ts;
		this.gate = gate;
	}
	
	public void exit(){
		while(true){
		int turnstile = new Random().nextInt(4) + 1;
		Visitor visitor = Main.ticketsEntered.peek();
		if (visitor == null) {
			break;
		}
		if(visitor.exitTime < ts.timeStamp) {
			break;
		}
		if(ts.timeStamp >= visitor.exitTime) {
			ts.msg(ts.timeStamp);
			
			String id = ts.fd.format(visitor.id);
			System.out.println(" T"+ id + " exited  through Turnstile "+ gate+turnstile);
			Main.ticketsEntered.poll();
		}
		}
	}
	
	@Override
	public void run() {
		while(true) {
			exit();
		}
	}

}
