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

	public boolean addTicket (Ticket tick) {

		tick.setStatus("Pending");
		tick.setId(this.getId());
		return tick.addTicket();
	}
	
	public List<Ticket>  getTickets(boolean pending, String ticketType) {
		return repo.getAllEmpTickets(this, pending, ticketType);
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
