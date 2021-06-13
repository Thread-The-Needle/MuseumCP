package main;

import java.text.DecimalFormat;

public class TimeStamp implements Runnable{
	
	public int openSale;
	public int closedSale;
	public int openMuseum;
	public int closedMuseum;
	public int timeStamp;
	public boolean tickerCounter;
	public boolean museumCounter;
	public DecimalFormat fd;

	
	public TimeStamp() {
		this.fd = new DecimalFormat("0000");
		this.openSale = Main.mainOpenTime * 60;
		this.closedSale = (Main.mainCloseTime - 1) * 60;
		this.openMuseum = (Main.mainOpenTime + 1) * 60;
		this.closedMuseum =Main.mainCloseTime * 60;
		this.timeStamp =0;
		this.tickerCounter = true;
		this.museumCounter = true;
	}
	
	@Override
	public void run() {
		int i = openSale;
		while(Main.startnStop && i<=closedMuseum){
			//this timestamp will set the state to close counter ticket and museum
			timeStamp = i;
			i++;
			Main.timeText.setText(String.format("%02d%02d", timeStamp/60, timeStamp%60));

			if(timeStamp>=closedSale){
				tickerCounter = false;
			}
			
			if(timeStamp>=closedMuseum){
				museumCounter = false;
			}

			try {
				Thread.sleep(300);
			}catch (InterruptedException ex) {}
	}
	tickerCounter = false;
	museumCounter = false;
	}
	
	public void msg(int m, String t) {
		System.out.printf("[%02d:%02d]"+t+"\n", m/60, m%60 );
	}

}
