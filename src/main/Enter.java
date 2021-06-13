package main;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Enter implements Runnable {

	private String gate;
	private Lock lock;
	private int maxVisitor = Main.mainVisitorInMuseum - 6;
	private int visitorMuseum;

	Turnstile turnstile = new Turnstile();
	Ticket ticket;
	TimeStamp ts;

	public Enter(TimeStamp ts, String gate) {
		this.lock = new ReentrantLock();
		this.ts = ts;
		this.gate = gate;
	}

	public synchronized void enter() {

		if (Main.groupTicket.peek() != null) {
			//get the data from the list
			//example of the data [[T0001,T0002]-520-120-0,[T0003]-523-100-0 ]
			//pollFirst meaning they will get [T0001,T0002]-520-120-0
			ticket = Main.groupTicket.pollFirst();

			//shuffle the turnstile so visitor can enter at random
			turnstile.shuffle();

			for (int i = 0; i < ticket.ticketSize(); i++) {
				//increment on visitor in museum counter
				Main.visitorInMuseum.getAndIncrement();
				//increment on total visitor visit museum 
				Main.counter.getAndIncrement();

				String text = (" " + ticket.ticketId().get(i) + " entered through Turnstile " + gate
				+ turnstile.turnstile.get(i % 4) + ". Stay for " + ticket.duration() + " minutes");
				String msg = String.format("[%02d%02d]" + text + "\n", ts.timeStamp / 60, ts.timeStamp % 60);
				//output to gui terminal
				Main.theText.append(msg);
			}
			//take the timestamp they enter the museum and add the duration time stay
			//to get the exit time
			ticket.exitTime(ts.timeStamp + ticket.duration());
			ticket.timeEnter(ts.timeStamp);
			//add the data to list ticketEntered
			Main.ticketsEntered.add(ticket);

		}
		//get the counter of visitor in museum to make sure below the limit of visitor in museum
		visitorMuseum = Main.visitorInMuseum.get();
	}

	@Override
	public void run() {
		while (ts.museumCounter) {
			lock.lock();
			try {
				//output to gui visitor in museum and total visitor
				Main.visMuseumText.setText(String.format("%d", Main.visitorInMuseum.get()));
				Main.totVisText.setText(String.format("%d", Main.counter.get()));

				if (visitorMuseum >= maxVisitor) {
					visitorMuseum = Main.visitorInMuseum.get();
				} else if (ts.timeStamp >= ts.openMuseum && !Main.groupTicket.isEmpty()) {
					enter();
				}
				int ran = new Random().nextInt(4) + 1;
				int ran2 = new Random().nextInt(100) + 1;
				Thread.sleep((100 * ran + ran2));
			} catch (InterruptedException e) {
			} finally {
				lock.unlock();
			}
		}
		
		//output to gui terminal to say museum close
		String closemsg = String.format("[%02d%02d]  Museum entrance " + gate + " counter closed" + "\n", ts.timeStamp / 60, ts.timeStamp % 60);
		Main.theText.append(closemsg);
	}

}
