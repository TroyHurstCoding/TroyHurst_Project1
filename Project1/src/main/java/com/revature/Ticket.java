package com.revature;


import com.revature.repo.UserRepo;

public class Ticket {
	
	private String amount;
	private int id;
	private String status;
	private String description;
	private int uniqueId;
	private String ticketType;

	private UserRepo repo = new UserRepo();
	
	public Ticket() {
		
	}
	
	public Ticket(int id, String description, String status, String amount, int uniqueId, String ticketType) {
		super();
		this.amount = amount;
		this.id = id;
		this.status = status;
		this.description = description;
		this.uniqueId = uniqueId;
		this.ticketType = ticketType;	
	}
	
	public boolean changeTicketStatus() {
		return repo.updateTicket(this.getId(), this.getStatus(), this.getUniqueId());
	}

	public boolean addTicket() {
		return repo.addTicket(this);
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDiscription(String description) {
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	
}
