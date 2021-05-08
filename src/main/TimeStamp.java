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
		this.tickerCounter = false;
		this.museumCounter = false;
	}
	
	@Override
	public void run() {
		for(int i = openSale; i<=closedMuseum; i++) {
			timeStamp = i;
			if(timeStamp>=openSale && timeStamp<=closedSale ) {
				tickerCounter = true;
			}else {
				tickerCounter = false;
			}
			if(timeStamp>=openMuseum && timeStamp<=closedMuseum ) {
				museumCounter = true;
			}else {
				museumCounter = false;
			}

			try {
				Thread.sleep(300);
			}catch (InterruptedException ex) {}
		}
		
	}
	
	public void msg(int m) {
		System.out.printf("[%02d:%02d]", m/60, m%60);
	}

	
}
