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
import com.cooksys.Soa.entity.Person;

public class InterestDao {
	
	connectionUtils connector = new connectionUtils();
	private Set<Interest> interest = new HashSet();
	Statement stmt;
	Connection conn;
	ResultSet res;	
	
	
	public InterestDao() {
		try{
			conn = connector.getDBConnection();
			stmt = conn.createStatement();
		}catch (SQLException e) {
			e.printStackTrace();
			}
	}
	

	public Interest get(Long id){
		Interest intr = null;
		try 
		{			
			ResultSet res = stmt.executeQuery("SELECT * FROM soa_assignment.\"Interest\" WHERE id=" + id );
			res.next();				
			intr = new Interest(res.getLong("id"), res.getString("title"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			}	
		 return intr;
	}
	
	
	public Set<Interest> getPersonInterest(Person person){		
		HashSet<Interest> interests = new HashSet<Interest>();		
		try {
			String query = "SELECT * FROM soa_assignment.\"Interest\" " + 
							"JOIN soa_assignment.\"Person_Interest\" " +
							"ON soa_assignment.\"Person_Interest\".interest_id = soa_assignment.\"Interest\".id " +
							"JOIN soa_assignment.\"Person\" " +
							"ON soa_assignment.\"Person_Interest\".person_id = soa_assignment.\"Person\".id " +
							"WHERE soa_assignment.\"Person\".id = " + person.getId();
			res = stmt.executeQuery(query);
			while(res.next()){
				interests.add(new Interest(res.getLong("interest_id"), res.getString("title")));			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interests;
	}
	
	
	

public void updateInterestRecord(Interest interest) {		
		try 
		{  
			System.out.println("Interest " + interest.toString() + "\n has to be updated ");
			String query = "UPDATE soa_assignment.\"Interest\" SET " +
					String.format("\"title\"='%s'", interest.getTitle()) +
					"WHERE id = " + interest.getId();
//			String query = "UPDATE soa_assignment.\"Interest\" SET title=? WHERE id=?)";
//			PreparedStatement pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, interest.getTitle());
//			pstmt.setLong(2, interest.getId());			
			 stmt.executeUpdate(query);
//			pstmt.execute();
//			stmt.close();
			 System.out.println("Succefully added new Interest to the Interest table");		
			 System.out.println("And updated Record is " + interest.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			}		
	}



	public void save(Interest interest){		
		if(interest.getId() == null)
		{
			insertIntoInterest(interest);
		}
		else
		{
			updateInterestRecord(interest);
		}
	}

	
	
	public Interest insertIntoInterest(Interest interest) {
		try 
		{			
			String query = "INSERT INTO soa_assignment.\"Interest\"(title) VALUES(?)";
			PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, interest.getTitle());			
			pstmt.executeUpdate();			
			res = pstmt.getGeneratedKeys();
		    res.next();		  
		    interest.setId(res.getLong("id"));
		    stmt.close();
		    System.out.println("Interest " + interest.toString() + "\n is scuccessfully inserted into table");
		    
		} catch (SQLException e) {
			e.printStackTrace();
			}			
		return interest;
	};
}
