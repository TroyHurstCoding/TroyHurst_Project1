package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.Ticket;
import com.revature.User;
import com.revature.util.ConnectionFactory;

public class UserRepo {

	public void addUser(User user) {

		PreparedStatement stmt = null;
		final String SQL = "INSERT INTO users values(default, ?, ?, ?)";

		try (Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			//stmt.setInt(1, user.getId());
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setBoolean(3, user.getManager());
			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public User findUser(String username) {
		
		User newUser = new User();
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		final String SQL = "SELECT * FROM users WHERE username = ?";

		try(Connection conn = ConnectionFactory.getConnection()) {

			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, username);
			set = stmt.executeQuery();
			if (set.next()) {
				newUser = new User(set.getInt(1), set.getString(2), set.getString(3), set.getBoolean(4));
			} else {
				return null;
			}
			
		}

		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		// Cleanup
		finally {

			try {
				set.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return newUser;
	}
	
	
	public boolean changePermissions(String status, User user) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		final String SQL = "UPDATE users SET is_manager = ? WHERE user_id = ?";
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, status);
			stmt.setInt(2, user.getId());
			stmt.execute(SQL);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	
	public List<User> findAll() {

		// Make necessary Objects
		List<User> users = new ArrayList<>();
		ResultSet set = null;
		Statement stmt = null;

		try (Connection conn = ConnectionFactory.getConnection()){

		
			stmt = conn.createStatement();

			set = stmt.executeQuery("SELECT * FROM users");

			while (set.next()) {
				users.add(
						new User(set.getInt(1), set.getString(2), set.getString(3), set.getBoolean(4)));
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		// Cleanup
		finally {

			try {
				set.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return users;
	}
	
	
	public void addTicket(Ticket tick) {

		PreparedStatement stmt = null;
		final String SQL = "INSERT INTO tickets values( ?, ?, ?, ?, default)";

		try (Connection conn = ConnectionFactory.getConnection()) {
			
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, tick.getId());
			stmt.setString(2, tick.getDescription());
			stmt.setString(3, tick.getStatus());
			stmt.setInt(4, tick.getAmount());
			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	public boolean updateTicket(int id, String status, int uniqueId) {

		PreparedStatement stmt = null;
		final String SQL = "UPDATE tickets SET status = ? WHERE user_id = ? and unique_id = ? and status = 'Pending'";
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			stmt = conn.prepareStatement(SQL);
			
			stmt.setString(1, status);
			stmt.setInt(2, id);
			stmt.setInt(3, uniqueId);
			if(!stmt.execute()) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	
	public List<Ticket> getAllTickets() {

		// Make necessary Objects
		List<Ticket> tickets = new ArrayList<>();
		ResultSet set = null;
		Statement stmt = null;

		try (Connection conn = ConnectionFactory.getConnection()) {

		
			stmt = conn.createStatement();
			//WHERE status = 'pending' ORDER BY date_added 
			set = stmt.executeQuery("SELECT * FROM tickets WHERE status = 'Pending' ORDER BY unique_id");

			while (set.next()) {
				tickets.add(
						new Ticket(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getInt(5)) );
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		// Cleanup
		finally {

			try {
				set.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tickets;
	}
	
	
	public List<Ticket> getAllEmpTickets(User user, boolean pending) {

		// Make necessary Objects
		List<Ticket> tickets = new ArrayList<>();
		ResultSet set = null;
		PreparedStatement stmt = null;
		final String SQL;
		
		if (pending) {
			SQL = "SELECT * FROM tickets WHERE user_id = ? AND status = 'pending'";
		} else {
			SQL = "SELECT * FROM tickets WHERE user_id = ? ";
		}

		try (Connection conn = ConnectionFactory.getConnection()) {

		
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, user.getId());
			
			set = stmt.executeQuery();
			

			while (set.next()) {
				tickets.add(
						new Ticket(set.getInt(1), set.getString(2), set.getString(3), set.getInt(4), set.getInt(5)));
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		// Cleanup
		finally {

			try {
				set.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tickets;
	}
	
	
	public void deleteById(int id)
    {

        String SQL = "DELETE FROM users WHERE user_id = ?";

        try(Connection conn = ConnectionFactory.getConnection();
        	PreparedStatement stmt = conn.prepareStatement(SQL);) {
           
        	stmt.setInt(1, id);
            stmt.executeUpdate();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
