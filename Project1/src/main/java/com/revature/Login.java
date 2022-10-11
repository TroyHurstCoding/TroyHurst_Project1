package com.revature;
import java.util.HashMap;

public class Login {
	
	
//	private boolean existingUser = false;
//	private String name;
//	private String username;
//	private String password;
	private User tempUser;
	private int userCnt = 0;
	//private Deque<User> users = new ArrayDeque<User>();  
	private HashMap<Integer, User> users = new HashMap<Integer, User>();
	private HashMap<String, Integer> usernameId = new HashMap<String, Integer>();
	
	public Login() {}
	
//	public Login(boolean existingUser, boolean manager, String name,  String username, String password) {
//		super();
//		this.existingUser = existingUser;
//		this.tempUser = new User(0, name, username, password, manager, existingUser);
//	}

	public void newUser () {
		if (tempUser.getManager()) {
			//User newUser = new Manager(++employeeCnt, name, username, password);
			User newUser = this.tempUser;
			Manager newMan = new Manager(++userCnt, newUser.getName(), newUser.getUsername(), newUser.getPassword() );
			users.put(userCnt, newMan);
			usernameId.put(newMan.getUsername(), userCnt);
		} else {
			//User newUser = new Employee(++employeeCnt, name, username, password);
			User newUser = this.tempUser;
			Employee newEmp = new Employee(++userCnt, newUser.getName(), newUser.getUsername(), newUser.getPassword() );
			users.put(userCnt, newEmp);
			usernameId.put(newEmp.getUsername(), userCnt);
			//System.out.println("added username: " + username + " password " + password);
		}
		
		//Send new user to database
	}
	
	public User loginAttempt(User newUser) {

		
		if (!this.tempUser.isExistingUser()) {
	        
	        if (this.usernameTaken(this.getUsername())) {
	        	System.out.println("Username unavailable ");
	        	return null;
		        //u = in.nextLine();
	        } else {
			
	        	System.out.println("Username available.");
	        	this.newUser();
	        	return this.getUser();
	        }
	        
		} else {
			
			this.tempUser = newUser;
			
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
		
		int key = usernameId.get(this.getUsername());
		
		if(users.get(key).getPassword().hashCode() == this.getPassword() ) {
			return true;
		}
		
		return false;
	}
	
	//Returns user ID given a username
	public User getUser() {
		return users.get(usernameId.get(this.getUsername()));
	}
	
	
	//Prints all users 
	public void getUsers() {
		System.out.println("Printing users");
		for( User thisUser : users.values()) {
			System.out.println(thisUser.getUsername());
		}
	}
	
	public boolean usernameTaken (String username) {
		if(usernameId.get(username) == null) {
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
