package main;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Random;

public class TicketCounter implements Runnable {

	private Integer ticketSold;
	private String id;
	private int ranVisitor;
	private int ranTime;
	private int duration;
	// private LinkedList<Visitor> ticket;
	private LinkedList<String> ticketID;
	private String groupID;

	private DecimalFormat fd = new DecimalFormat("0000");

	TimeStamp ts;

	public TicketCounter(TimeStamp time) {
		this.ticketSold = 0;
		this.ts = time;
	}

	public synchronized void saleTicket() throws InterruptedException {

		// ticket = new LinkedList<>();
		ticketID = new LinkedList<>();

		ranVisitor = new Random().nextInt(4) + 1;
		ranTime = new Random().nextInt(4) + 1;

		duration = new Random().nextInt(101) + 50;

		// if (ts.timeStamp >= 930) {
		// 	if (duration >= (ts.closedMuseum - ts.timeStamp)) {
		// 		duration = ts.closedMuseum - ts.timeStamp;
		// 	} else
		// 		duration = new Random().nextInt(ts.closedMuseum - ts.timeStamp - 10);
		// }

		for (int i = 0; i < ranVisitor; i++) {
			ticketSold++;
			id = "T" + fd.format(ticketSold);
			ticketID.add(id);
			// ticket.add(new Visitor(id, ts.timeStamp, duration));
		}

		Main.groupTicket.add(new Ticket(ticketID, ts.timeStamp, duration));
		groupID = Main.groupTicket.getLast().ticketID().replace("[", "").replace("]", "");

		ts.msg(ts.timeStamp, (ranVisitor == 1 ? " Ticket " : " Tickets ") + groupID + " sold");
		// System.out.println(Main.groupTicket.getLast().stringVisitor());
		Thread.sleep((ranTime * 300));
	}

	@Override
	public void run() {

		while (ts.tickerCounter) {
			try {
				saleTicket();
			} catch (InterruptedException e) {
			}
		}
		ts.msg(ts.timeStamp, " Ticket Counter closed");
	}

}
