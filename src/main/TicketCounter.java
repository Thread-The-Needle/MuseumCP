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
			//ticketID is to store the ticket as a group in list 
			//and by declaring here it will always renew
			ticketID = new LinkedList<>();

			ranVisitor = new Random().nextInt(4) + 1;
			ranTime = new Random().nextInt(4) + 1;

			duration = new Random().nextInt(101) + 50;

			for (int i = 0; i < ranVisitor; i++) {
				ticketSold++;
				id = "T" + fd.format(ticketSold);
				//store ticket id
				ticketID.add(id);
				//increment on the total ticket
				Main.totalTicket.getAndIncrement();
				//output in gui terminal
				Main.ticketCounter.setText(String.format("%d", Main.totalTicket.get()));
			}

			//after ticketID store the data in a list
			//then that list will be store in the groupTicket list including timestamp and duration
			//so that they can enter the same entrance and get the same stay time
			Main.groupTicket.add(new Ticket(ticketID, ts.timeStamp, duration));
			//string output to gui terminal
			groupID = Main.groupTicket.getLast().ticketID().replace("[", "").replace("]", "");

			//if  random ticket is 1 then "ticket" else "tickets"
			String text = ((ranVisitor == 1 ? " Ticket " : " Tickets ") + groupID + " sold");
			//combine all the string
			String msg = String.format("[%02d%02d]" + text + "\n", ts.timeStamp / 60, ts.timeStamp % 60);

			//output to gui terminal
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
		//output to gui to say counter ticket is close
		String closemsg = String.format("[%02d%02d]  Ticket Counter closed\n", ts.timeStamp / 60, ts.timeStamp % 60);
		Main.theText.append(closemsg);
	}

}
