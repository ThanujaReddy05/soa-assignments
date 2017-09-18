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

public class PersonDao {
	connectionUtils connector = new connectionUtils();
	private Set<Person> people = new HashSet();
	Statement stmt;
	Connection conn;
	ResultSet res;
	private LocationDao locationDao;
	private InterestDao interestDao;	

	public PersonDao() {
		try
		{
			conn = connector.getDBConnection();
			stmt = conn.createStatement();
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}



	public Person get(Long id){	
		Person p=null;
		try 
		{locationDao = new LocationDao();
		interestDao = new InterestDao();
			res = stmt.executeQuery("SELECT * FROM soa_assignment.\"Person\" WHERE id=" + id);
			res.next();

			Location location = locationDao.get(res.getLong("loc_id"));
			p = new Person(res.getLong("id"), res.getString("firstname"), res.getString("lastname"), res.getInt("age"),location);
			Set<Interest> interests = interestDao.getPersonInterest(p);
			p.setInterests(interests);

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return p;
	}


	public void save(Person person) throws Exception {

		if(person.getId() == null)
		{
			insertIntoPerson(person);
		}
		else
		{
			updatePersonRecord(person);
		}


	}

	public Person insertIntoPerson(Person person) {

		try 
		{
			String query = "INSERT INTO soa_assignment.\"Person\"(firstname,lastname,age,loc_id) VALUES(?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);			
			pstmt.setString(1, person.getFirstName());
			pstmt.setString(2, person.getLastName());
			pstmt.setInt(3, person.getAge());
			pstmt.setLong(4, person.getLocation().getId());
			pstmt.execute();
			res = pstmt.getGeneratedKeys();
			res.next();
			person.setId(res.getLong("id"));
			updatePerson_Interest(person);
			pstmt.close();
			System.out.println("Person " + person.toString() + "\n is scuccessfully inserted into table");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;

	}


	public Person updatePersonRecord(Person person) {
		try 
		{
			System.out.println("Person " + person.toString() + "\n has to be updated ");
			String query = "UPDATE soa_assignment.\"Person\" SET " +
					String.format("\"firstname\"='%s', \"lastname\"='%s', \"age\"='%d', \"loc_id\"='%d'", person.getFirstName(), person.getLastName(), person.getAge(), person.getLocationId()) +
					"WHERE id = " + person.getId();
			//		String query = "UPDATE soa_assignment.\"Person\" SET firstname=?,lastname=?,age=?, loc_id=? WHERE id=?)";
			//		PreparedStatement pstmt = conn.prepareStatement(query);
			//		pstmt.setString(1, person.getFirstName());
			//		pstmt.setString(2, person.getLastName());
			//		pstmt.setInt(3, person.getAge());
			//		pstmt.setLong(4, person.getLocation().getId());
			//		pstmt.setLong(5, person.getId());
			//								
			//		pstmt.execute();
			stmt.executeUpdate(query);
			updatePerson_Interest(person);
			System.out.println("And updated Record is " + person.toString());			
			return person;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}



	private void updatePerson_Interest(Person person){
		try {
			System.out.println("----- Relationship between a Person and Interests  ------");						
			for (Interest i : person.getInterests()){
				String query = "INSERT INTO soa_assignment.\"Person_Interest\" (person_id, interest_id) VALUES(?,?) ";

				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setLong(1, person.getId());
				pstmt.setLong(2, i.getId());
				pstmt.executeUpdate();
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public Set<Person> findInterestGroup(Interest interest, Location location){
		people.clear();
		try 
		{
			String query = "SELECT * from soa_assignment.\"Person\" " +
					"join soa_assignment.\"Location\" " + 
					"on soa_assignmen.\"Person\".\"loc_id\" = soa_assignmen.\"Location\".id" + 
					"join soa_assignment.\"Person_Interest\"" +
					"on soa_assignment.\"Person_Interest\".\"person_id\"  = soa_assignment.\"Person\".id" +
					"join soa_assignment.\"Interest\" " +
					"on soa_assignment.\"Interest_Location\".\"i_id\"  = soa_assignment.\"Interest\".id" +
					"where soa_assignment.\"loc_id  = " + location.getId() + "and soa_assignmen.\"interest_id\" = " + interest.getId() ;
			res = stmt.executeQuery(query);
			while(res.next())
			{
				Location locationp = locationDao.get(res.getLong("loc_id"));
				Person p = new Person(res.getLong("id"), res.getString("firstname"), res.getString("lastname"), res.getInt("age"),locationp);
				people.add(p);
			}
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return people;
	}



}
