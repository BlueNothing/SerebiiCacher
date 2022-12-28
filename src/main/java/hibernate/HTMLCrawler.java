package hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.Query;
import org.jsoup.*;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class HTMLCrawler {
	/*
	 * Refactor this class to include ~7 methods.
	 * dexFinder: Given nothing, pull the names of the Pokemon in the Paldea Pokedex, add them to the database as stubs if not present. This method will be adapted to add any Pokemon to the database.
	 * dexFiller: Scrolls through the database, populating null fields with values where appropriate (using 0 when a value has been searched but is empty or absent).
	 * abilityFinder: Given nothing, pulls the names of the abilities in Serebii's AbilityDex, adding them to the database as stubs if not present.
	 * abilityFiller: Scrolls through the database, populating null fields and filling stubs for the AblityDex.
	 * attackFinder: Given nothing, pulls the names of the attacks in Serebii's AttackDex, adding them to the database as stubs.
	 * attackFiller: Scrolls through the database, populating null fields and filling stubs for the AttackDex.
	 * main: Calls these methods.
	 */
	

	
	public static void main(String[] args) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		session = HibernateUtil.getSessionFactory().openSession();
		/*
		 * String selection = "3";
		Scanner scan = new Scanner(System.in);
		//while(selection == null || !(selection.equals("0"))) {
			//System.out.println("Please enter the number for your selection. To update the Pokedex, type '1'. To update the AbilityDex, type '2'. To update the AttackDex, type '3'. To exit, type '0'. To initialize the whole database, type '9'. No other options are implemented at this time.");
			//selection = scan.nextLine();
			 * 
			 */
		int selection = 1;
		switch (String.valueOf(selection)){
		case "0" :
			System.out.println("Thank you for using the Serebii Cacher. Ending execution.");
			break;
			
		case "1" :
			session = HibernateUtil.getSessionFactory().openSession();
			Pokemon.dexFinder(session);
			session.close();
			break;
			
		case "2" :
			session = HibernateUtil.getSessionFactory().openSession();
			Ability.abilityFinder(session);
			session.close();
			break;
			
		case "3" :
			session = HibernateUtil.getSessionFactory().openSession();
			Move.attackFinder(session);
			session.close();
			break;
			
		case "9" :
			session = HibernateUtil.getSessionFactory().openSession();
			Pokemon.dexFinder(session);
			Ability.abilityFinder(session);
			Move.attackFinder(session);
			session.close();
			break;
			
		default :
			System.out.println("Invalid input, please try again.");
			System.out.println("Your input was: " + selection);
			System.out.println("Handy hint: Remember to just type the number, not the quotes surrounding it.");
			break;
		}
		}
	}