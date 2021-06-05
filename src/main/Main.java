package main;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class Main {

	static LinkedList<Ticket> groupTicket = new LinkedList<>();
	static PriorityBlockingQueue<Ticket> ticketsEntered = new PriorityBlockingQueue<>();
	static volatile AtomicInteger counter = new AtomicInteger(0);
	static volatile AtomicInteger visitorInMuseum = new AtomicInteger(0);
	static volatile AtomicInteger totalTicket = new AtomicInteger(0);
	static int mainTotalTicket, mainOpenTime, mainCloseTime, mainVisitorInMuseum;
	static boolean startnStop;
	static ExecutorService executor = Executors.newFixedThreadPool(6);;

	static JScrollPane theScroller;
	static JFrame theFrame, qFrame;
	static JButton startButton, stopButton, okButton;
	static JLabel timeS, ticket, totalVis, visMuseum, visitorInMuseumC, totalTicketC, openTimeC, closeTimeC;
	static JTextField timeText, ticketCounter, totVisText, visMuseumText, visitorInMuseumCT, totalTicketCT, openTimeCT,
			closeTimeCT;
	static JTextArea theText;
	static JPanel fPanel, q1Panel, q2Panel;
	static DefaultCaret caret;
	static Border border = BorderFactory.createLoweredBevelBorder();
	static GridBagConstraints constraints;

	JTextField makeText() {
		JTextField t = new JTextField(20);
		t.setEditable(false);
		t.setHorizontalAlignment(JTextField.CENTER);
		t.setBorder(border);
		theFrame.getContentPane().add(t, constraints);
		return t;
	}

	public void caseGUI() {
		qFrame = new JFrame("Museum Input test Case");
		q1Panel = new JPanel();
		q2Panel = new JPanel();
		visitorInMuseumC = new JLabel("Total Visitor in Museum");
		totalTicketC = new JLabel("Total ticket");
		openTimeC = new JLabel("Open Time (24Hours)");
		closeTimeC = new JLabel("Close Time (24Hours)");
		visitorInMuseumCT = new JTextField("100");
		totalTicketCT = new JTextField("900");
		openTimeCT = new JTextField("8");
		closeTimeCT = new JTextField("18");
		okButton = new JButton("OK");

		counter = new AtomicInteger(0);
		visitorInMuseum = new AtomicInteger(0);
		totalTicket = new AtomicInteger(0);

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mainOpenTime = Integer.parseInt(openTimeCT.getText());
				mainCloseTime = Integer.parseInt(closeTimeCT.getText());
				mainTotalTicket = Integer.parseInt(totalTicketCT.getText());
				mainVisitorInMuseum = Integer.parseInt(visitorInMuseumCT.getText());
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				startnStop = true;
				theText.setText(null);
				qFrame.dispose();

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						executor = Executors.newFixedThreadPool(6);
						TimeStamp ts = new TimeStamp();
						TicketCounter tk = new TicketCounter(ts);
						Enter etn = new Enter(ts, "NE");
						Enter ets = new Enter(ts, "SE");
						Exit exe = new Exit(ts, "EE");
						Exit exw = new Exit(ts, "WE");

						executor.execute((Runnable) ts);
						executor.execute((Runnable) tk);
						executor.execute((Runnable) etn);
						executor.execute((Runnable) ets);
						executor.execute((Runnable) exe);
						executor.execute((Runnable) exw);
						executor.shutdown();
					}
				});
			}

		});

		q1Panel.setLayout(new BoxLayout(q1Panel, BoxLayout.PAGE_AXIS));
		q1Panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		q2Panel.setLayout(new FlowLayout());

		q1Panel.add(openTimeC);
		q1Panel.add(openTimeCT);
		q1Panel.add(closeTimeC);
		q1Panel.add(closeTimeCT);
		q1Panel.add(totalTicketC);
		q1Panel.add(totalTicketCT);
		q1Panel.add(visitorInMuseumC);
		q1Panel.add(visitorInMuseumCT);
		q2Panel.add(okButton);

		qFrame.add(q1Panel, BorderLayout.PAGE_START);
		qFrame.add(q2Panel, BorderLayout.PAGE_END);

		qFrame.pack();
		qFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		qFrame.setLocationRelativeTo(null);
		qFrame.setVisible(true);
	}

	public void museumGUI() {

		theFrame = new JFrame("Museum Simulator");
		fPanel = new JPanel();
		startButton = new JButton("Start Museum");
		stopButton = new JButton("Stop");
		timeS = new JLabel("TimeStamp", SwingConstants.CENTER);
		timeText = makeText();
		ticket = new JLabel("Ticket Sold", SwingConstants.CENTER);
		ticketCounter = makeText();
		totalVis = new JLabel("Total Visitors", SwingConstants.CENTER);
		totVisText = makeText();
		visMuseum = new JLabel("Visitors in Museum", SwingConstants.CENTER);
		visMuseumText = makeText();
		theText = new JTextArea("");
		theScroller = new JScrollPane(theText);
		caret = (DefaultCaret) theText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(30, 10, 30, 10);
		stopButton.setEnabled(false);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startnStop = false;
				new Main().caseGUI();
			}
		});

		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startnStop = false;
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				theText.append("Museum simulator closed");
			}
		});

		theFrame.add(fPanel);
		fPanel.setLayout(new GridLayout(5, 2));
		fPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		fPanel.add(startButton);
		fPanel.add(stopButton);
		fPanel.add(timeS);
		fPanel.add(timeText);
		fPanel.add(ticket);
		fPanel.add(ticketCounter);
		fPanel.add(totalVis);
		fPanel.add(totVisText);
		fPanel.add(visMuseum);
		fPanel.add(visMuseumText);
		theFrame.add(theScroller);

		theFrame.setLayout(new GridLayout(1, 2));
		theFrame.pack();

		theFrame.setLocationRelativeTo(null);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new Main().museumGUI();
	}
}
