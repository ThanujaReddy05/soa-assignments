package com.cooksys.Soa;

import java.util.HashSet;
import java.util.Set;

import com.cooksys.Soa.dao.InterestDao;
import com.cooksys.Soa.dao.LocationDao;
import com.cooksys.Soa.dao.PersonDao;
import com.cooksys.Soa.entity.Interest;
import com.cooksys.Soa.entity.Location;
import com.cooksys.Soa.entity.Person;



public class App 
{
	public static void main( String[] args ) throws Exception
	{
		Set<Interest> intSet1 = new HashSet();
		Set<Interest> intSet2 = new HashSet();
		LocationDao locationDao = new LocationDao();
		InterestDao interestDao = new InterestDao();
		PersonDao personDao = new PersonDao();
		intSet1.add(interestDao.get(1L));
		intSet1.add(interestDao.get(2L));

		intSet2.add(interestDao.get(2L));
		intSet2.add(interestDao.get(3L));

		System.out.println("\n\nWelocome to DAO\n");

		
		System.out.println("-------------------------");
		System.out.println("------Test cases---------");
		System.out.println("-------------------------");

		System.out.println("\n------------------------");		
		System.out.println("-----Person Test Cases---");		
		System.out.println("------------------------\n\n\n");

		//New Person object can be stored    
		System.out.println("\n------------------------------");
		System.out.println("-----New Person object can be stored---");
		System.out.println("---------------------------\n");
		Person p1 = new Person("Tom", "Hokins", new Integer(35) , locationDao.get(1L), intSet1);
		personDao.save(p1);

		//Person object can be retrieved
		System.out.println("\n------------------------------");
		System.out.println("-----Person object can be retrieved----");
		System.out.println("---------------------------\n");
		PersonDao personDao2 = new PersonDao();
		Person retrievePerson = personDao2.get(5L);
		System.out.println(retrievePerson.toString());

		//Existing Person can be updated
		System.out.println("\n------------------------------");
		System.out.println("-----Existing Person can be updated----");
		System.out.println("--------------------------\n");
		Person ePerson = new Person(1L, " Thanuja", "Reddy", 30, locationDao.get(1L), intSet2);
		personDao.save(ePerson);


		System.out.println("\n\n\n------------------------------");
		System.out.println("------------------------------");
		System.out.println("-----Location Test Cases------");
		System.out.println("------------------------------");
		System.out.println("------------------------------\n\n\n");

		//New Location can be stored
		System.out.println("\n------------------------------");
		System.out.println("-----New Location can be stored------");
		System.out.println("------------------------\n");
		Location l1 = new Location(4L,"SFO", "CA","USA");
		locationDao.save(l1);


		//Location object can be retrieved
		System.out.println("\n------------------------------");
		System.out.println("-----Location object can be retrieved-----");
		System.out.println("------------------------\n");
		Location retrieveLocation = locationDao.get(4L);
		System.out.println(retrieveLocation.toString());

		//Existing Location can be updated
		Location updateocation = new Location(2L, "Nashville", "TN", "US");
		locationDao.save(updateocation);
		System.out.println("Updated location = " + locationDao.get(2L));


		System.out.println("\n\n\n------------------------------");
		System.out.println("------------------------------");
		System.out.println("-----Interest Test Cases------");
		System.out.println("------------------------------");
		System.out.println("------------------------------\n\n\n");

		//New Interest can be stored
		Interest i1 = new Interest("Dance");		
		interestDao.save(i1);

		//Exiting Interest can be updated
		Interest i2 = new Interest(2L, "Cooking");
		interestDao.save(i2);

		// Interest object can be retrieved
		//		InterestDao i2D = new InterestDao();
		System.out.println(interestDao.get(3L));   


	}
}
