package com.revature;


import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import io.javalin.Javalin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


public class Driver {
	
	
	public static void main (String[] args) {
		
			
		Login newLogin = new Login();
		Employee curEmp = new Employee();
		Manager curMan = new Manager();
		
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		Javalin app = Javalin.create().start(8000);
		
		
		//Login endpoint
		app.post("/login", ctx -> {
			
			User newTry = ctx.bodyAsClass(User.class);
			boolean existingUser = newTry.isExistingUser();
			newTry = newLogin.loginAttempt(newTry);
			
			String jws = Jwts.builder()
					.setSubject("auth")
					.claim("message", "")
					.signWith(key)
					.compact();
			
			if (!curMan.isSignedIn() && !curEmp.isSignedIn()) {
			
				if (newTry == null ) {
					
					if (existingUser) {
						
						ctx.json("Invalid username or password.");
					} else {
						
						ctx.json("Username taken.");
					}
					
				} else if (newTry.getManager()) { //MANAGER CHECK
					
					curMan.addUser(newTry);	
					curMan.setSignedIn(true);
					curEmp.resetUser();
					
					if (existingUser) {
						
						ctx.json("Username "  + curMan.getUsername() + " available.");	
					}
					
					ctx.json("Logged in as " + curMan.getUsername() + ".");
					ctx.cookie("jwt", jws);
			
				} else if (!newTry.getManager()) { //EMPLOYEE CHECK
					
					curEmp.addUser(newTry);	
					curEmp.setSignedIn(true);
					curMan.resetUser();
					
					if (!existingUser) {
			
						ctx.json("Username "  + curEmp.getUsername() + " available.");
					}
					ctx.json("Logged in as " + curEmp.getUsername() + ".");
					ctx.cookie("jwt", jws);
					
				} else {
					
					ctx.json("Try again later");
				}
			} else {

				ctx.json("Please logout before logging in to another account.");
			}
			
			ctx.status(HttpStatus.CREATED_201);	
		
		});
		
		
		//Endpoint to logout employee or manager
		app.post("/logout", ctx -> {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(ctx.cookie("jwt"));

			
			if (curMan.isSignedIn() || curEmp.isSignedIn()) {
				
				if(jwsClaims.getBody() == null) {
					
					ctx.json("Invalid cookie");
					curMan.resetUser();
					curEmp.resetUser();	
					
				} else {
					
					User newTry = ctx.bodyAsClass(User.class);
					
					if (!newTry.getUsername().equals(curMan.getUsername()) && !newTry.getUsername().equals(curEmp.getUsername()) ) {
						
						ctx.json("Error logging out.");
						
					} else if (!newTry.isSignedIn()) {
						
						curMan.resetUser();
						curEmp.resetUser();	
						ctx.json(newTry.getUsername() + " has logged off.");
					}
				}
			} else {
				
				ctx.json("You are not logged in to an account.");
			}
			
			ctx.status(HttpStatus.CREATED_201);	
		});
		
		
		
		//     MANAGER ENPOINTS
		
		
			
		//Endpoint to get tickets as manager
		app.get("/allTickets", ctx -> {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(ctx.cookie("jwt"));
				
			if(curMan.isSignedIn()) {
				
				if(jwsClaims.getBody() == null) {
					
					ctx.json("Invalid cookie");
					curMan.setSignedIn(false);
					curEmp.setSignedIn(false);
					
				} else {
					
					List<Ticket> tickets = new ArrayList<>();
					tickets = curMan.getAllTickets();
					
					if(tickets.size() == 0) {
						
						ctx.json("No Pending tickets available");
						
					} else {
						
						ctx.json("Pending tickets:");
						ctx.json(tickets);
					}
				} 
			} else {
				
				ctx.json("Requires manager permissions.");	
			}
			
			ctx.status(HttpStatus.CREATED_201);	
					
		});
		
		
		//Endpoint to change ticket status as manager
		app.post("/changeTicket", ctx -> {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(ctx.cookie("jwt"));
			
			if (curMan.isSignedIn()) {
				
				if(jwsClaims.getBody() == null) {
					
					ctx.json("Invalid cookie");
					curMan.setSignedIn(false);
					curEmp.setSignedIn(false);
					
				} else {
					
					Ticket newTicket = ctx.bodyAsClass(Ticket.class);
					
					if (curMan.changeTicketStatus(newTicket)) {
						
						ctx.json("Ticket #" + newTicket.getId() + " status updated to: " + newTicket.getStatus());
						
					} else {
						
						ctx.json("Could not update ticket #" + newTicket.getId() + " status to: " + newTicket.getStatus());
					}
					
				} 
			} else {
					
				ctx.json("Requires manager permissions.");
			}
		
			ctx.status(HttpStatus.CREATED_201);
			
		});
		
		app.post("/changePermission", ctx -> {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(ctx.cookie("jwt"));
			
			if (curMan.isSignedIn()) {
				
				if(jwsClaims.getBody() == null) {
					
					ctx.json("Invalid cookie");
					curMan.setSignedIn(false);
					curEmp.setSignedIn(false);
					
				} else {
					
					User newTry = ctx.bodyAsClass(User.class);
					
					if (newTry.getUsername() != null || newTry.getUsername() != "" ) {
						
						if (!newTry.getManager()) {
							
							if (newTry.changeUserPermissions()) {
								
								ctx.json("Manager " + newTry.getUsername() + " demoted to employee.");	
							} else {
								
								ctx.json("Could not demote manager " + newTry.getUsername() + " to employee.");
							}
						} else {
							
							if (newTry.changeUserPermissions()) {
								
								ctx.json("Employee " + newTry.getUsername() + " promoted to manager.");	
							} else {
								
								ctx.json("Could not demote employee " + newTry.getUsername() + " to manager.");
							}
						}
					} else {
						
						ctx.json("Invalid username.");	
						
					}
				} 
			} else {
					
				ctx.json("Requires manager permissions.");
			}
			
		});
		
		
		
		//       USER ENDPOINTS
			
			
		//endpoint to get all tickets as user
		app.post("/allMyTickets", ctx -> {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(ctx.cookie("jwt"));
				
			if(curEmp.isSignedIn()) {
				
				if(jwsClaims.getBody() == null) {
					
					ctx.json("Invalid cookie");
					curMan.setSignedIn(false);
					curEmp.setSignedIn(false);
					
				} else {
					
					List<Ticket> tickets = new ArrayList<>();
					Ticket newTicket = ctx.bodyAsClass(Ticket.class);
					
					tickets = curEmp.getTickets(false, newTicket.getTicketType());
					
					if(tickets.size() == 0) {
						
						ctx.json("No ticket history.");
						
					} else {
						
						ctx.json("Ticket history: ");
						ctx.json(tickets);
					}
					
				} 
			} else {
					
				ctx.json("Must sign in to an employee account.");
			}
			
			ctx.status(HttpStatus.CREATED_201);
		});
		
		
		//endpoint to get only pending tickets as user
		app.get("/pendingTickets", ctx -> {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(ctx.cookie("jwt"));
			
			if(curEmp.isSignedIn()) {
			
				if(jwsClaims.getBody() == null) {
					
					ctx.json("Invalid cookie");
					curMan.setSignedIn(false);
					curEmp.setSignedIn(false);
				
				} else {
				
					List<Ticket> tickets = new ArrayList<>();
			
					tickets = curEmp.getTickets(true, null);
					
					if(tickets.size() == 0) {
						
						ctx.json("No Pending tickets");
						
					} else {
						
						ctx.json("Pending tickets: ");
						ctx.json(tickets);
					}			
				}
				
			} else {
				
				ctx.json("Must sign in to an employee account.");
			}
			
			ctx.status(HttpStatus.CREATED_201);
		});
		

		//Enpoint to submit tickets
		app.post("/addTicket", ctx -> {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(ctx.cookie("jwt"));
			
			if(curEmp.isSignedIn()) {
				
				if(jwsClaims.getBody() == null) {
					
					ctx.json("Invalid cookie");
					curMan.setSignedIn(false);
					curEmp.setSignedIn(false);
					
				} else {	
					
					Ticket newTicket = ctx.bodyAsClass(Ticket.class);
					
					if (newTicket.getDescription() != "" && Double.parseDouble(newTicket.getAmount()) > 0.00 ) {
						
						if (!curEmp.addTicket(newTicket)) {
							
							ctx.json("Can't add reimbursement ticket.");
							
						} else {
							
							ctx.json("Ticket amount of " + newTicket.getAmount() + " for \"" + newTicket.getDescription() + "\" added.");
						}
						
					} else if (newTicket.getDescription() == "") {
						
						ctx.json("Reimbursement description necessary.");	
						
					} else {
						
						ctx.json("Reimbursement amount necessary.");
					}
					
				} 
				
			} else {
					
				ctx.json("Must sign in to an employee account.");
			}	
			
			
			ctx.status(HttpStatus.CREATED_201);			
		});
			
		
		
    }
		
}
