package com.cooksys.Soa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.cooksys.Soa.connectionUtils;
import com.cooksys.Soa.entity.Interest;
import com.cooksys.Soa.entity.Location;
import com.cooksys.Soa.entity.Person;

public class LocationDao {
	connectionUtils connector = new connectionUtils();
	private Set<Location> location = new HashSet();
	Statement stmt;
	Connection conn;
	ResultSet res;


	public LocationDao() {
		try{
			conn = connector.getDBConnection();
			stmt = conn.createStatement();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public Location get(Long id){
		Location loc=null;
		try{
			ResultSet res = stmt.executeQuery("SELECT * FROM soa_assignment.\"Location\" WHERE id=" + id );
			res.next();
			loc = new Location(res.getLong("id"), res.getString("city"), res.getString("state"), res.getString("country"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return loc;
	}

	

	public void updateLocationRecord(Location location) {

		try 
		{ 
			System.out.println("Location " + location.toString() + "\n has to be updated ");
			String query = "UPDATE soa_assignment.\"Location\" SET " +
					String.format("\"city\"='%s', \"state\"='%s', \"country\"='%s'", location.getCity(), location.getState(), location.getCountry()) +
					"WHERE id = " + location.getId();
			//			stmt = getDBConnection().createStatement();
			//			String query = "UPDATE soa_assignment.\"Location\" SET city=?,state=?,country=? WHERE id=?)";
			//			PreparedStatement pstmt = conn.prepareStatement(query);
			//			pstmt.setString(1, location.getCity());
			//			pstmt.setString(2, location.getState());
			//			pstmt.setString(3, location.getCountry());
			//			pstmt.setLong(4, location.getId());
			//			
			//			
			//			pstmt.execute();
			stmt.executeUpdate(query);
			//			stmt.close();
			System.out.println("Succefully added new Location to the Location table");
			System.out.println("And updated Record is " + location.toString());		


		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void save(Location location){		
		if(location.getId() == null){
			insertIntoLocation(location);
		}
		else{
			updateLocationRecord(location);
		}
	}
	
	
public void insertIntoLocation(Location location) {
		
		try 
		{
			String query = "INSERT INTO soa_assignment.\"Location\"(city,state,country) VALUES(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, location.getCity());
			pstmt.setString(2, location.getState());
			pstmt.setString(3, location.getCountry());
			pstmt.executeUpdate();
			res = pstmt.getGeneratedKeys();
			res.next();
			location.setId(res.getLong("id"));
			stmt.close();	
			System.out.println("Location " + location.toString() + "\n is scuccessfully inserted into table");
		
		} catch (SQLException e) {
			e.printStackTrace();
			}	
	}

}
