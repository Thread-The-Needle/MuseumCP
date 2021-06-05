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

			ticket = Main.groupTicket.pollFirst();

			turnstile.shuffle();

			for (int i = 0; i < ticket.ticketSize(); i++) {

				Main.visitorInMuseum.getAndIncrement();
				Main.counter.getAndIncrement();

				String text = (" " + ticket.ticketId().get(i) + " entered through Turnstile " + gate
				+ turnstile.turnstile.get(i % 4) + ". Stay for " + ticket.duration() + " minutes");
				String msg = String.format("[%02d%02d]" + text + "\n", ts.timeStamp / 60, ts.timeStamp % 60);

				Main.theText.append(msg);
			}
			ticket.exitTime(ts.timeStamp + ticket.duration());
			ticket.timeEnter(ts.timeStamp);
			Main.ticketsEntered.add(ticket);

		}
		visitorMuseum = Main.visitorInMuseum.get();
	}

	@Override
	public void run() {
		while (ts.museumCounter) {
			lock.lock();
			try {

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
		ts.msg(ts.timeStamp, " Museum entrance " + gate + " counter closed");
	}

}
