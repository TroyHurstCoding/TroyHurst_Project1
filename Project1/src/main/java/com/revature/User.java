package com.revature;

import com.revature.repo.UserRepo;

public class User {
	
	private String username;
	private int id;
	private String password;
	private boolean manager;
	private boolean existingUser;
	private boolean signedIn;
	
	private UserRepo repo = new UserRepo();
	
	public User(){    
		setSignedIn(false);
    }
    
    public User(int id, String username, String password, boolean manager, boolean existingUser){
        this.id = id;
        this.username = username;
        this.password = password;
        this.manager = manager;
        this.existingUser = existingUser;
    }
 
    public User(int id, String username, String password, boolean manager){
        this.id = id;
        this.username = username;
        this.password = password;
        this.manager = manager;
    }
    
    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    public void addUser(User user) {
    	this.setId(user.getId());
    	this.setUsername(user.getUsername());
    	this.setPassword(user.getPassword());
    	this.setManager(user.getManager());
    	this.setExistingUser(true);
    }
    
    public boolean changeUserPermissions () {
    	return repo.changePermissions(this.getManager(), this);
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getManager() {
        return manager;
    }
 
    public void setManager(boolean manager) {
        this.manager = manager;
    }
 
    public boolean isExistingUser() {
		return existingUser;
	}

	public void setExistingUser(boolean existingUser) {
		this.existingUser = existingUser;
	}

	public String toString(){
 
        return "[" + 
            this.getId() + 
            " : " +  
            this.getUsername() + 
            " : " + 
            this.getPassword() + 
            "]";
    }

	public boolean isSignedIn() {
		return signedIn;
	}

	public void setSignedIn(boolean signedIn) {
		this.signedIn = signedIn;
	}
	
	public void resetUser() {
		 this.id = 0;
        this.username = null;
        this.password = null;
        this.manager = false;
        this.existingUser = false;
        this.signedIn = false;
	}
}


