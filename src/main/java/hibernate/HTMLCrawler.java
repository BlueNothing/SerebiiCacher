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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hibernate.pokemon.Pokemon;

@SpringBootApplication(scanBasePackages = {"hibernate", "hibernate.ability", "hibernate.move", "hibernate.pokemon"})
@EnableAutoConfiguration

public class HTMLCrawler {
	/*
	 * Alright, there are a few things I can do here that are relatively important.
	 * Need to figure out an elegant way to deal with alternate forms.
	 * Need to start implementing a front-end, REST, and refactoring to better Persistence implementations.
	 */
	

	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(HTMLCrawler.class, args);
	}
	
	public static void collisionChecker() throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		session = HibernateUtil.getSessionFactory().openSession();
		String testAbility = "";
		ArrayList<Pokemon> testCollision = new ArrayList<Pokemon>();
		System.out.println("Do you want to find a moveset collision? Y/N");
		Scanner scan = new Scanner(System.in);
		String doCollide = scan.nextLine();
		if(doCollide.equalsIgnoreCase("Y")) {
		System.out.println("Do you want to include an ability in your collision check? Y/N");
		String withAbility = scan.nextLine();
		
		if(withAbility.equalsIgnoreCase("Y")) {
		System.out.println("Testing movelist collision detection for the case with a specified ability.");
		System.out.println("Please type the exact name of the ability without following punctuation (so \"Zero to Hero\" would be Zero to Hero, Adaptability would be Adaptability, etc.");
		testAbility = scan.nextLine();
		}
		ArrayList<String> testMoves = new ArrayList<String>();
		System.out.println("Do you want to add a move to check for collision with? Y/N");
		String doProceed = scan.nextLine();
		while(!(doProceed.equalsIgnoreCase("N"))) {
			if(doProceed.equalsIgnoreCase("Y")) {
				System.out.println("Please enter the exact name of one of the moves you want to find a collision for.");
				String moveName = scan.nextLine();
				if(!(moveName.isBlank()) && !(moveName.equals("Yes") && !(moveName.equals("No")))) {
					testMoves.add(moveName);
					System.out.println("Added " + moveName + ". Continue?");
					doProceed = scan.nextLine();
		}
			} else {
				System.out.println("Please type Y or N");
		}
		}
		if(withAbility.equalsIgnoreCase("Y") && !(testAbility.isBlank())) {
				testCollision = intersectionFinder(session, testAbility, testMoves);
		} else {
			testCollision = intersectionFinder(session, testMoves);
		}
		
		ArrayList<String> outputList = new ArrayList<String>();
		for(Pokemon p : testCollision) {
			outputList.add(p.getName());
		}
		
		if(withAbility.equalsIgnoreCase("Y") && !(testAbility.isBlank())) {
		System.out.println("Overall Collision Set - The following Pokemon have the specified ability " + testAbility + " and the specifed moves" + testMoves.toString() + " : "+ outputList.toString());
		} else {
			System.out.println("Overall Collision Set - The following Pokemon have the specifed move combination: " + testMoves.toString() + " : "+ outputList.toString());
		}
	}
	}
	
	public static void collisionChecker(String ability, ArrayList<String> testMoves) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		session = HibernateUtil.getSessionFactory().openSession();
		String testAbility = ability;
		ArrayList<Pokemon> testCollision = new ArrayList<Pokemon>();
		testCollision = intersectionFinder(session, testAbility, testMoves);
		ArrayList<String> outputList = new ArrayList<String>();
		for(Pokemon p : testCollision) {
			outputList.add(p.getName());
		}
		System.out.println("Overall Collision Set - The following Pokemon have the specified ability " + testAbility + " and the specifed moves" + testMoves.toString() + " : "+ outputList.toString());
	}
	
	public static void collisionChecker(ArrayList<String> testMoves) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Pokemon> testCollision = new ArrayList<Pokemon>();
		testCollision = intersectionFinder(session, testMoves);
		ArrayList<String> outputList = new ArrayList<String>();
		for(Pokemon p : testCollision) {
			outputList.add(p.getName());
		}
		System.out.println("Overall Collision Set - The following Pokemon have the specifed move combination: " + testMoves.toString() + " : "+ outputList.toString());
		
	}


public static ArrayList<Pokemon> intersectionFinder(Session session, ArrayList<String> moves){
	/*
	 * General strategy: Find all the Pokemon that can learn each move in the list.
	 * If there are any Pokemon who can learn all of the moves in the list, they're the results, return them, else "None".
	 */
	ArrayList<List<Pokemon>> moveListSet = new ArrayList<List<Pokemon>>(); //This list contains all the movelists that satisfy each component query.
	for(String moveName : moves) {
		String moveFinderHQL = "FROM Pokemon as poke WHERE poke.totalMoves LIKE '%" + moveName + "%'";
		List<Pokemon> foundPokemon = session.createQuery(moveFinderHQL).list();
		moveListSet.add(foundPokemon);
	}
	ArrayList<Pokemon> results = new ArrayList<Pokemon>();
	for(Pokemon p : moveListSet.get(0)) { //Any collision that satisfies the constraint must be in each list. As such, searching each entry in the first list is a fine strategy, because all valid collisions have to be in there.
		boolean validResult = true;
		for(int i = 0; i < moveListSet.size(); i++) {
			if(!(moveListSet.get(i).contains(p))) {
				validResult = false;
			}
		}
		if(validResult) {
			results.add(p);
		}
	}
	if(results.size() == 0) {
		results.add(new Pokemon("None"));
	}
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
	ArrayList<Pokemon> moveResults = intersectionFinder(session, moves); //Containing the simpler method improves uniformity.
	for(Pokemon p : abilityOutput) { //For each Pokemon with the specified ability...
		boolean validResult = true;
		if(!(moveResults.contains(p))){ //If none of the Pokemon in the moveset collision have the ability, this is an invalid collision. 
			validResult = false;
		}
		if(validResult) { //If at least one Pokemon in the moveset collision has the ability, this is a valid collision.
			results.add(p);
		}
	}
	if(results.size() == 0) {
		results.add(new Pokemon("None"));
	}
	
	return results;
}
}

