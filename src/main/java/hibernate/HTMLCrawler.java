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
	//Might be cool to extend the functionality of this interface to do more stuff.
	

	
	public static void main(String[] args) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		session = HibernateUtil.getSessionFactory().openSession();
		//String selection = "3";
		Scanner scan = new Scanner(System.in);
		//while(selection == null || !(selection.equals("0"))) {
			//System.out.println("Please enter the number for your selection. To update the Pokedex, type '1'. To update the AbilityDex, type '2'. To update the AttackDex, type '3'. To exit, type '0'. To initialize the whole database, type '9'. No other options are implemented at this time.");
			//selection = scan.nextLine();
		int selection = 0;
		switch (String.valueOf(selection)){ //This set of options should be used for populating the database.
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
		System.out.println("Testing movelist collision detection for the case with a specified ability.");
		System.out.println("Please type the exact name of the ability without following punctuation (so \"Zero to Hero\" would be Zero to Hero, Adaptability would be Adaptability, etc.");
		String testAbility = scan.nextLine();
		ArrayList<String> testMoves = new ArrayList<String>();
		System.out.println("Do you want to add a move to check for collision with?");
		String doProceed = scan.nextLine();
		while(!(doProceed.equals("No"))) {
			if(doProceed.equals("Yes")) {
				System.out.println("Please enter the exact name of one of the moves you want to find a collision for.");
				String moveName = scan.nextLine();
				if(!(moveName.isBlank()) && !(moveName.equals("Yes") && !(moveName.equals("No")))) {
					testMoves.add(moveName);
					System.out.println("Added " + moveName + ". Continue?");
					doProceed = scan.nextLine();
		}
		}
		}
		//testMoves = new ArrayList<String>();
		//testMoves.add("Ancient Power");
		//testMoves.add("Belly Drum");
		ArrayList<Pokemon> testCollision = intersectionFinder(session, testAbility, testMoves);
		ArrayList<String> outputList = new ArrayList<String>();
		for(Pokemon p : testCollision) {
			outputList.add(p.getPokeName());
		}
		System.out.println("Overall Collision Set - The following Pokemon have the specified ability " + testAbility + " and the specifed moves" + testMoves.toString() + " : "+ outputList.toString());
		
		//Options should also be provided for working with the *cached* database using certain prebuilt forms of queries.
		}


public static ArrayList<Pokemon> intersectionFinder(Session session, ArrayList<String> moves){
	/*
	 * General strategy: Find all the Pokemon that can learn each move in the list.
	 * If there are any Pokemon who can learn all of the moves in the list, they're the results, return them, else "None".
	 */
	ArrayList<List<Pokemon>> moveListSet = new ArrayList<List<Pokemon>>();
	for(String moveName : moves) {
		String validatorHQL = "FROM Pokemon WHERE OVERALLMOVES CONTAINS '%" + moveName + "%';";
		List<Pokemon> results = session.createQuery(validatorHQL).list();
	}
	ArrayList<Pokemon> results = new ArrayList<Pokemon>();
	
	return results;
}

public static ArrayList<Pokemon> intersectionFinder(Session session, String abilityName, ArrayList<String> moves){
	/*
	 * General strategy: Find all the Pokemon that can learn each move in the list.
	 * If there are any Pokemon who can learn all of the moves in the list, they're the results, return them, else "None".
	 */
	ArrayList<Pokemon> results = new ArrayList<Pokemon>(); //Here's where all the valid collisions go.
	String abilitiesHQL = "FROM Pokemon as poke WHERE poke.abilities LIKE '%" + abilityName + "%'";
	List<Pokemon> abilityOutput = session.createQuery(abilitiesHQL).list(); //Find the Pokemon with the chosen ability.
	//System.out.println(abilityOutput.toString()); TESTING USE ONLY.
	ArrayList<List<Pokemon>> moveOutputsList = new ArrayList<List<Pokemon>>();
	for(String moveName : moves) {
		String moveFinderHQL = "FROM Pokemon as poke WHERE poke.totalMoves LIKE '%" + moveName + "%'";
		List<Pokemon> moveOutput = session.createQuery(moveFinderHQL).list(); //Add all Pokemon who can have the chosen move to their own list.
		//System.out.println(moveOutput.toString());
		moveOutputsList.add(moveOutput);
	}
	for(Pokemon p : abilityOutput) {
		boolean validResult = true;
		for(int i = 0; i < moveOutputsList.size(); i++) {
			if(!(moveOutputsList.get(i).contains(p))) {
				validResult = false;
			}
		}
		if(validResult) {
			results.add(p);
		}
	}
	
	return results;
}
}

