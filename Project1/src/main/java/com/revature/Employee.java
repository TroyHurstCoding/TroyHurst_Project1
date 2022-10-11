package com.revature;
import java.util.ArrayDeque;
import java.util.Deque;


public class Employee extends User {
	
	private Deque<Ticket> tickets = new ArrayDeque<Ticket>();  
	private Deque<Ticket> pastTickets = new ArrayDeque<Ticket>();
	
	

	public Employee() {
		super();
		
	}

	public Employee(int id, String name, String username, String password) {
		super(id, name, username, password);
		
	}

	public void addTicket () {
//		Scanner in = new Scanner(System.in);
//		System.out.print("Reimbursement amount:");
//		String a = in.nextLine();
//		System.out.print("Description:");
//		String d = in.nextLine();
//		Ticket newTicket = new Ticket(Integer.parseInt(a), this, "pending", d);
//		tickets.add(newTicket);
		
	}
	
	public void oldTicket (Ticket finished) {
		pastTickets.add(finished);
	}
	
	public void getCurrentTickets() {
		for(Ticket tix : tickets) {
			System.out.println(toString(tix));
		}
	}
	
	public void getPastTickets() {
		for(Ticket tix : pastTickets) {
			System.out.println(toString(tix));
		}
	}
	
	public String toString(Ticket tick) {
		return "[" + 
	            tick.getAmount() + 
	            " : " + 
	            tick.getState() + 
	            " : "  +
	            tick.getDescription() +
 	            "]";
	}
}
