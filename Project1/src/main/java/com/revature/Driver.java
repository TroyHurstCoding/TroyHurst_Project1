package com.revature;


import org.eclipse.jetty.http.HttpStatus;
import io.javalin.Javalin;
//import io.javalin.http.Context;

public class Driver {

	public static void main (String[] args) {
		
		//int maxAttempts = 5;
		//boolean signIn = false;
		
		Login newLogin = new Login();
		
		User e1 = new User(0,"Troy", "trizzle", "69");
		newLogin.addUser(e1);
		User e2 = new User(0,"mom", "mom", "69");
		newLogin.addUser(e2);
		User e3 = new User(0,"Troy", "tri", "69", true, false);
		newLogin.addUser(e3);
		
		//newLogin.getUsers();
		
		Javalin app = Javalin.create().start(8000);
		
		app.post("/login", ctx -> {
			User newTry = ctx.bodyAsClass(User.class);
			newLogin.setUser(newTry);
			newLogin.loginAttempt(newTry);
			System.out.println("Hello " + newLogin.getUsername());
			ctx.status(HttpStatus.CREATED_201);
			
				
		});
		
//		while (!signIn) {
//			cnt++;
//			System.out.println(maxAttempts - cnt + " attempts remaining");
//			//newLog = ctx.bodyAsClass(Login.class);
//		
//		
//			app.post("/login", ctx -> {
//				User newTry = ctx.bodyAsClass(User.class);
//				newLogin.setUser(e1);
//				//System.out.println(newLog.getUsername());
//				ctx.status(HttpStatus.CREATED_201);
//				
//					
//			});
//			
//			System.out.println(maxAttempts - cnt + " attempts remaining");
//		}
//		app.get("/person/{id}", (Context ctx) -> {
//			
//			
//			//ctx.json();
//		});
//		
//		app.post("/new-item", ctx -> {
//			
//			//Person receivedPerson = ctx.bodyAsClass(Person.class);
//			//System.out.println(receivedPerson);
//			//ctx.status(HttpStatus.CREATED_201);
//			String jsonBody = ctx.body();
//			System.out.println(jsonBody);
//			
//		});
		
//		
//		((Employee) newEmp).addTicket();
//		((Employee) newEmp).getCurrentTickets();
		
		
    }
}
