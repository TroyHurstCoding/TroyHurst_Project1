package com.revature;

import java.util.List;

import com.revature.repo.UserRepo;

public class Manager extends User {
	

	private UserRepo repo = new UserRepo();

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager (int id, String username, String password) {
		super(id, username, password);
	}

	public boolean changeTicketStatus(Ticket toChange) {
		//check if status is Approved/Denied, of neither keep pending
		if (toChange.changeTicketStatus()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public List<Ticket> getAllTickets() {
		return repo.getAllTickets();
		
	}
	
	
}
