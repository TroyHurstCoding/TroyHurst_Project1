package com.revature;

import java.util.List;

import com.revature.repo.UserRepo;

public class Employee extends User {
	
	private UserRepo repo = new UserRepo();
	

	public Employee() {
		super();
		
	}

	public Employee(int id, String username, String password) {
		super(id, username, password);
		
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
	
	public List<Ticket>  getTickets(boolean pending) {
		return repo.getAllEmpTickets(this, pending);
	}
	
	
	public String toString(Ticket tick) {
		return "[" + 
	            tick.getAmount() + 
	            " : " + 
	            tick.getStatus() + 
	            " : "  +
	            tick.getDescription() +
 	            "]";
	}
}
