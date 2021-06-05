package main;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Random;

public class TicketCounter implements Runnable {

	private String id;
	private String groupID;
	private int ticketSold;
	private int ranVisitor;
	private int ticketLimit = Main.mainTotalTicket;
	private int ranTime;
	private int duration;
	private LinkedList<String> ticketID;

	private DecimalFormat fd = new DecimalFormat("0000");

	TimeStamp ts;

	public TicketCounter(TimeStamp time) {
		this.ticketSold = 0;
		this.ts = time;
	}

	public synchronized void saleTicket() throws InterruptedException {

		if (ticketSold >= ticketLimit) {
			ts.tickerCounter = false;
		} else {
			ticketID = new LinkedList<>();

			ranVisitor = new Random().nextInt(4) + 1;
			ranTime = new Random().nextInt(4) + 1;

			duration = new Random().nextInt(101) + 50;

			for (int i = 0; i < ranVisitor; i++) {
				ticketSold++;
				id = "T" + fd.format(ticketSold);
				ticketID.add(id);
				Main.totalTicket.getAndIncrement();
				Main.ticketCounter.setText(String.format("%d", Main.totalTicket.get()));
			}

			Main.groupTicket.add(new Ticket(ticketID, ts.timeStamp, duration));
			groupID = Main.groupTicket.getLast().ticketID().replace("[", "").replace("]", "");

			String text = ((ranVisitor == 1 ? " Ticket " : " Tickets ") + groupID + " sold");
			String msg = String.format("[%02d%02d]" + text + "\n", ts.timeStamp / 60, ts.timeStamp % 60);

			Main.theText.append(msg);
			Thread.sleep((ranTime * 300));
		}
	}

	@Override
	public void run() {

		while (ts.tickerCounter) {
			try {
				saleTicket();
			} catch (InterruptedException e) {
			}
		}
	}

}
