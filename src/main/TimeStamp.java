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
		this.openSale = 480;
		this.closedSale = 1020;
		this.openMuseum = 540;
		this.closedMuseum =1080;
		this.timeStamp =0;
		this.tickerCounter = true;
		this.museumCounter = true;
	}
	
	@Override
	public void run() {
		for(int i = openSale; i<=closedMuseum; i++) {
			timeStamp = i;

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
		
	}
	
	public void msg(int m, String t) {
		System.out.printf("[%02d:%02d]"+t+"\n", m/60, m%60 );
	}

}
