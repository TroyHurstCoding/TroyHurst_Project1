package com.revature;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import io.javalin.Javalin;
//import io.javalin.http.Context;

public class Driver {
	
	
	public static void main (String[] args) {
		
		//int maxAttempts = 5;	
		Login newLogin = new Login();
		Employee curEmp = new Employee();
		Manager curMan = new Manager();

		
//		User e1 = new User(0, "trizzle", "69");
//		newLogin.addUser(e1);
//		User e2 = new User(0, "mom", "69");
//		newLogin.addUser(e2);
//		User e3 = new User(0, "tri", "69", true, false);
//		newLogin.addUser(e3);
		
		
		Javalin app = Javalin.create().start(8000);
		
			
		//Login endpoint
		app.post("/login", ctx -> {
			
			User newTry = ctx.bodyAsClass(User.class);
			newTry = newLogin.loginAttempt(newTry);
			
			if (newTry != null && newTry.getManager()) {
				curMan.addUser(newTry);	
				curMan.setSignedIn(true);
		
			} else if (newTry != null && !newTry.getManager()) {
				curEmp.addUser(newTry);	
				curEmp.setSignedIn(true);
				
			} else {
				System.out.println("Try again later");
			}
			
			ctx.status(HttpStatus.CREATED_201);	
		
		});
		
		
			
		//Endpoint to get tickets as manager
		app.get("/allTickets", ctx -> {
			
			List<Ticket> tickets = new ArrayList<>();
			
			if(curMan.isSignedIn()) {
					tickets = curMan.getAllTickets();
					ctx.json(tickets);
			}
			
			ctx.status(HttpStatus.CREATED_201);	
					
		});
		
		
		//Endpoint to change ticket status as manager
		app.post("/changeTicket", ctx -> {
			 
			if (curMan.isSignedIn()) {
				Ticket newTicket = ctx.bodyAsClass(Ticket.class);
				if (newTicket.changeTicketStatus()) {
					System.out.println("Ticket status updated to: " + newTicket.getStatus());
				} else {
					System.out.println("Could not update ticket status to: " + newTicket.getStatus());
				}
			}
		
			
			ctx.status(HttpStatus.CREATED_201);
		});
			
		
			
		//endpoint to get all tickets as user
		app.get("/allMyTickets", ctx -> {
			
			List<Ticket> tickets = new ArrayList<>();
			
			if(curEmp.isSignedIn()) {
				tickets = curEmp.getTickets(false);
				ctx.json(tickets);
			}
			
			ctx.status(HttpStatus.CREATED_201);
		});
		
		
		//endpoint to get only pending tickets as user
		app.get("/pendingTickets", ctx -> {
			
			List<Ticket> tickets = new ArrayList<>();
			
			if(curEmp.isSignedIn()) {
				tickets = curEmp.getTickets(true);
				ctx.json(tickets);
			}
			
			ctx.status(HttpStatus.CREATED_201);
		});
		

		//Enpoint to submit tickets
		app.post("/addTicket", ctx -> {
			
			if(curEmp.isSignedIn()) {
				
				Ticket newTicket = ctx.bodyAsClass(Ticket.class);
				newTicket.setStatus("Pending");
				
				newTicket.setId(curEmp.getId());
				newTicket.addTicket();
				System.out.println("Ticket Added");
			}
			
			ctx.status(HttpStatus.CREATED_201);			
		});
			
		
		
		
    }
		
}
