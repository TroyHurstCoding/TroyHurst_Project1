package com.revature;
import java.util.ArrayDeque;
import java.util.Deque;

public class Manager extends User {
		
	private Deque<Ticket> allTickets = new ArrayDeque<Ticket>(); 
	

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager (int id, String name, String username, String password) {
		super(id, name, username, password);
		// TODO Auto-generated constructor stub
	}

	public void changeTicketStatus( Ticket toChange, String status) {
		//check if status is Approved/Denied, of neither keep pending
		if (status == "Approved" || status == "Denied") {
			toChange.setState(status);
		} else {
			System.out.println("Invalid Ticket Status");
		}
		
	}
	
	public void getCurrentTickets(String status) {
		for(Ticket tix : allTickets) {
			if (tix.getState() == status) {
				System.out.println(tix);
			}
		}
	}
	
	
}
