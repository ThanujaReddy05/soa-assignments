package com.cooksys.Soa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cooksys.Soa.entity.Interest;
import com.cooksys.Soa.entity.Location;
import com.cooksys.Soa.entity.Person;

public class connectionUtils {

	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	static final String USER = "postgres";	
	static final String PASS = "bondstone"; 
	Connection conn = null;
	 Statement stmt = null;
	 ResultSet res;
		 
	public connectionUtils() {
		super();
		
	}

	public Connection getDBConnection()
	{      
      try{      	
      	Class.forName(JDBC_DRIVER);      	
      	System.out.println("Connectiong to a selected database");
      	conn = DriverManager.getConnection(DB_URL, USER, PASS);
          System.out.println("Connected database successfully...");
	
	 } catch (SQLException se) {
         //Handle errors for JDBC
         se.printStackTrace();
      }catch(Exception e){
         //Handle errors for Class.forName
         e.printStackTrace();
}
	return conn;
}

	
}
