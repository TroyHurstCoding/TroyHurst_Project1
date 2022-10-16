package com.revature;

import com.revature.repo.UserRepo;

public class Login {
	
	private User tempUser = new User();
	private UserRepo repo = new UserRepo();
	
	public Login() {}
	
//	public Login(boolean existingUser, boolean manager, String name,  String username, String password) {
//		super();
//		this.existingUser = existingUser;
//		this.tempUser = new User(0, name, username, password, manager, existingUser);
//	}

	
	//If user is new, check username for availability, create account, return user object. 
	//If existing, check username password combo and return user object
	public User loginAttempt(User newUser) {
		
		this.setUser(newUser);
		
		if (!this.tempUser.isExistingUser()) {
	        
	        if (this.usernameTaken(this.getUsername())) { //Check if username is in database already
	        	//return null if username taken
	        	System.out.println("Username taken");
	        	return null;
	        } else {
	        	//Otherwise add username and password to DB
	        	System.out.println("Username available");
	        	this.newUser();
	        	return this.getUser();
	        }
	        
		} else {
				
	        if(!this.checkCredentials()) {
	        	System.out.println("Invalid username or password");
	        	return null;
	        } else {
	        	System.out.println("Access granted");
	        	return this.getUser();
	        }
	    }
		
	}
	
	public boolean checkCredentials() {
		
		User temp = repo.findUser(this.getUsername());
		
		if(temp.getPassword().hashCode() == this.getPassword() ) {
			return true;
		}
		
		return false;
	}
	
	public void newUser () {
		if (tempUser.getManager()) {
			User newUser = this.tempUser;
			Manager newMan = new Manager(0, newUser.getUsername(), newUser.getPassword() );
			repo.addUser(newMan);
		} else {
			User newUser = this.tempUser;
			Employee newEmp = new Employee(0, newUser.getUsername(), newUser.getPassword() );
			repo.addUser(newEmp);
		}
		
		//Send new user to database
	}
	
	//Returns user given a username
	public User getUser() {
		return repo.findUser(this.getUsername());
	}
	
	public boolean usernameTaken (String username) {
		User temp = repo.findUser(username);
		
		if(temp == null) {
			return false;
		}
		return true;
	}

	public boolean isManager() {
		return tempUser.getManager();
	}

	public void setManager(boolean manager) {
		this.tempUser.setManager(manager); 
	}

	public String getUsername() {
		return this.tempUser.getUsername();
	}

	public void setUsername(String username) {
		this.tempUser.setUsername(username);
	}

	public int getPassword() {
		return this.tempUser.getPassword().hashCode();
	}

	public void setPassword(String password) {
		this.tempUser.setPassword(password);
	}
	
	public void setUser (User newUser) {
		this.tempUser = newUser;
	}

	public void addUser (User newUser) {
		this.tempUser = newUser;
		this.newUser();
	}
	

}
