package com.revature;

public class Ticket {
	
	private int amount;
	private Employee ticketOwner;
	private String state;
	private String description;
	
	
	public Ticket(int amount, Employee ticketOwner, String state, String description) {
		super();
		this.amount = amount;
		this.ticketOwner = ticketOwner;
		this.state = state;
		this.description = description;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Employee getTicketOwner() {
		return ticketOwner;
	}
	public void setTicketOwner(Employee ticketOwner) {
		this.ticketOwner = ticketOwner;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDiscription(String description) {
		this.description = description;
	}
	

}
