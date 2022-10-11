package com.revature;

public class User {
	
	private String name;
	private String username;
	private int id;
	private String password;
	private boolean manager;
	private boolean existingUser;
	
	public User(){        
    }
    
    public User(int id, String name, String username, String password, boolean manager, boolean existingUser){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.manager = manager;
        this.existingUser = existingUser;
    }
 
    public User(int id, String name, String username, String password){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
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
            this.getName() + 
            " : " + 
            this.getUsername() + 
            " : " + 
            this.getPassword() + 
            "]";
    }
}


