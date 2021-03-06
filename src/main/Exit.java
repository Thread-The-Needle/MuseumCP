package main;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exit implements Runnable {

	private Lock lock;
	private String gate;

	Ticket ticket;
	Turnstile turnstile = new Turnstile();

	TimeStamp ts;

	public Exit(TimeStamp ts, String gate) {
		this.lock = new ReentrantLock();
		this.ts = ts;
		this.gate = gate;
	}

	public synchronized void exit() {

		//if 30 min before museum close, will force the visitors to exit
		if (ts.timeStamp >= (ts.closedMuseum - 30)) {

			//get and delete the first data from list ticketEntered
			ticket = Main.ticketsEntered.poll();

			for (int i = 0; i < ticket.ticketSize(); i++) {

				//decrement the counter visitor in museum
				Main.visitorInMuseum.getAndDecrement();

				String text = (" " + ticket.ticketId().get(i) + " exited through Turnstile " + gate
				+ turnstile.turnstile.get(i%4));
				String msg = String.format("[%02d:%02d]" + text + "\n", ts.timeStamp / 60, ts.timeStamp % 60);
				//output gui
				Main.theText.append(msg);

			}


		} 
		//normal exit for visitor according to their stay time
		else if (ts.timeStamp >= Main.ticketsEntered.peek().retriveExitTime()
				&& ts.timeStamp < (ts.closedMuseum - 30)) {

			//get and delete the first data from list ticketEntered
			ticket = Main.ticketsEntered.poll();

			//shuffle the turnstile so visitor can enter at random
			turnstile.shuffle();

			for (int i = 0; i < ticket.ticketSize(); i++) {

				//decrement the counter visitor in museum
				Main.visitorInMuseum.getAndDecrement();

				String text = (" " + ticket.ticketId().get(i) + " exited through Turnstile " + gate
				+ turnstile.turnstile.get(i%4));
				String msg = String.format("[%02d%02d]" + text + "\n", ts.timeStamp / 60, ts.timeStamp % 60);

				//output gui
				Main.theText.append(msg);

			}
			
		}

	}

	@Override
	public void run() {
		while (ts.museumCounter) {
			lock.lock();
			try {
				if (ts.timeStamp >= ts.openMuseum && !Main.ticketsEntered.isEmpty()) {
					exit();
				}
				int ran = new Random().nextInt(3) + 1;
				int ran2 = new Random().nextInt(200) + 1;
				Thread.sleep((100 * ran + ran2));
			} catch (InterruptedException e) {
			} finally {
				lock.unlock();
			}
		}
		//output to gui terminal to say museum close
		String closemsg = String.format("[%02d%02d]  Museum exit " + gate + " counter closed" + "\n", ts.timeStamp / 60, ts.timeStamp % 60);
		Main.theText.append(closemsg);
	}

}
