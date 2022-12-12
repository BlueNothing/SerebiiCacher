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
	
	public static void dexFinder(Session session) throws IOException {
		/*
		 * Populating the list from this table will ultimately be preferable, but for now, this is a job for a refactor.
		Document natDex = Jsoup.connect("https://www.serebii.net/pokemon/nationalpokedex.shtml").get();
		Elements natDexTable = natDex.select("content > main > table");
		Elements rows = natDexTable.select("tr");
		ArrayList<String> natDexOverall = new ArrayList<String>();
		for(int i = 2; i < rows.size(); i++) {
			Element row = rows.get(i);
			Elements cols = row.select("td");
			System.out.println(cols.get(2).text());
			natDexOverall.add(cols.get(2).text());
			
		}
		
		//The solution implemented below works, but could be more efficient - 
		 * What we see here selects each entry in the current Serebii Pokedex and adds it to the list.
		 * Notably, the current outcome relies on a few quirks in Serebii's design for the Pokedex: 
		 * Every Pokedex entry is repeated, but only one instance of each dex entry is numbered, and outside of the Paldea dex, it's numbered with its National Dex entry.
		 * Inside the Paldea dex, they're numbered with their local dex number.
		 * Overall, then, there's at least one instance of every pokemon's dex entry that's prefaced with a number.
		*/
		
		Document palDex = Jsoup.connect("https://www.serebii.net/pokedex-sv/").get();
		Elements palDexElems = palDex.select("option");
		List<String> palDexData = palDexElems.eachText();
		ArrayList<String> palDexOverall = new ArrayList<String>();
		System.out.println("Inital Pokedex data:");
		/*
		 */
	for(String s : palDexData) {
		s = s.trim(); //Okay, so this works just fine.
		if(Character.isDigit(s.charAt(0))) {
			s = s.substring(4);
			s = s.trim();
			if(!(palDexOverall.contains(s))) {
			palDexOverall.add(s);
			//System.out.println(s);
			}
	}
	}
	System.out.println("Dex stubs uploaded to DB.");
	System.out.println("Testing DB contents!");
	for(String s : palDexOverall) {
		
		Pokemon dbSample = session.get(Pokemon.class, s);
		Pokemon pokemon = new Pokemon();
		pokemon.setPokeName(s);
		if (!(Objects.isNull(dbSample)) && dbSample.getPokeName().equals(pokemon.getPokeName()) && dbSample.equals(pokemon)) {
			System.out.println("Nothing to do here.");
			continue;
		} else {
		session.beginTransaction();
		session.persist(pokemon);
		session.getTransaction().commit();
	}
	}
	String validatorHQL = "FROM Pokemon";
	List<Pokemon> results = session.createQuery(validatorHQL).list();
	results.forEach(outcome -> System.out.println(outcome));
	dexFiller(session);
	}
	
	public static void dexFiller(Session session) throws IOException {
		//String validatorHQL = "FROM Pokemon";
		//List<Pokemon> results = session.createQuery(validatorHQL).list();
		//for (Pokemon poke : results) {
		//	String pokeName = poke.getPokeName(); //Working on this part to dynamically fill the dex!
		//	String URL = "https://www.serebii.net/pokedex-sv/" + pokeName.toLowerCase().replace("%20", "").replace(" ", "") + "/"; //Builds the URLs correctly!
		//	Document dexEntry = Jsoup.connect((URL)).get();
			//TYPES in HREFS here: #content > main > div:nth-child(2) > table:nth-child(4) > tbody > tr:nth-child(2) > td.cen
			//Row contains Classification, Height, Weight, Capture Rate, Base Egg Steps: #content > main > div:nth-child(2) > table:nth-child(4) > tbody > tr:nth-child(4)
			//Abilities here: #content > main > div:nth-child(2) > table:nth-child(5) > tbody > tr:nth-child(2)
			//EV data here: #content > main > div:nth-child(2) > table:nth-child(5) > tbody > tr:nth-child(4) > td:nth-child(3)
			//BST and Stats here: #content > main > div:nth-child(2) > table:nth-child(23) > tbody > tr:nth-child(3)
			/*
			 * This table contains Level-Up Moves: #content > main > div:nth-child(2) > table:nth-child(18) > tbody
			 * TM Moves: #content > main > div:nth-child(2) > table:nth-child(20) > tbody
			 * Egg Moves: #content > main > div:nth-child(2) > table:nth-child(21)
			 * OTHER Moves: 
			 * Search for things structured like Moves (Things where the second row, first column of the table is "Attack Name");
			 * Once found, add every instance.
			 * How do we want to handle the move lists?
			 * Current implementation calls for moves stored as Strings (for their names). Probably best to do this.
			 * Best to one-to-many relation moves to the pokemon who can learn them.
			 * 
			 * 
			 */
		//}
		session.close();
		System.out.println("/n /n /n");
		/*
		 * Implementation pending!
		 */
	}
	
	public static void abilityFinder(Session session) throws IOException{
		Document abiliDex = Jsoup.connect("https://www.serebii.net/abilitydex/").get();
		Elements abilitiesDex = abiliDex.select("option");
		List<TextNode> abilities = abilitiesDex.textNodes();
		System.out.println(abilitiesDex.eachText());
		ArrayList<String> abilitiesDexOverall = new ArrayList<String>();
		System.out.println("Inital ability dex data:");
		
		for(TextNode x : abilities) {
				if(!(x.text().startsWith("AbilityDex"))) {
					abilitiesDexOverall.add(x.text());
					System.out.println(x.text());
					Ability dbSample = session.get(Ability.class, x.text());
					Ability localAbility = new Ability();
					localAbility.setAbilityName(x.text());
					if (!(Objects.isNull(dbSample)) && dbSample.getAbilityName().equals(localAbility.getAbilityName())) {
						System.out.println("Nothing to do here.");
						continue;
					} else {
					session.beginTransaction();
					session.persist(localAbility);
					session.getTransaction().commit();
		}
		} else {
			continue;
		}
	
	}
		abilityFiller(session);
	}
	
	public static void abilityFiller(Session session) throws IOException {
		/* NYI but proposed - 
		 * If "content > main > table:nth-child(5) > tbody > tr:nth-child(7) > td" holds "Attacks Effected"...
		 * Add list of named moves as new table element for relevant abilities.
		 */
		
		System.out.println("Starting abilityFiller");
		Ability localAbility = new Ability();
		String validatorHQL = "FROM Ability";
		List<Ability> results = session.createQuery(validatorHQL).list();
		for (Ability ability : results) {
			localAbility.setAbilityName(ability.getAbilityName()); //Working on this part to dynamically fill the dex!
			String abilityName = localAbility.getAbilityName().toLowerCase().replace("%20", "").replace(" ", "");
			String URL = "https://www.serebii.net/abilitydex/" + abilityName + ".shtml";
			Document abilityDoc = Jsoup.connect((URL)).get();
		//The code now effectively connects to the webpage for most abilities.
		 
			
		String inGameText, inDepthEffect, overworldEffect = null;
		System.out.println(abilityName);
		//System.out.println(abilityDoc.selectFirst("content > main > table:nth-child(5) > tbody > tr:nth-child(3)").hasText());
		if(abilityDoc.selectXpath("//*[@id=\"content\"]/main/table[3]/tbody/tr[3]/td").text().toString().startsWith("Game's Text")) {
			inGameText = abilityDoc.selectXpath("//*[@id=\"content\"]/main/table[3]/tbody/tr[4]/td").text().toString();
			localAbility.setAbilityGameText(inGameText);
		} else {
			inGameText = "0";
		}
		if(abilityDoc.selectXpath("//*[@id=\"content\"]/main/table[3]/tbody/tr[5]/td").text().toString().startsWith("In-Depth Effect")) {
			inDepthEffect = abilityDoc.selectXpath("//*[@id=\"content\"]/main/table[3]/tbody/tr[6]/td").text().toString();
			localAbility.setInDepthAbilityEffect(inDepthEffect);
		} else {
			inDepthEffect = "0";
		}
		if(abilityDoc.selectXpath("//*[@id=\"content\"]/main/table[3]/tbody/tr[7]/td").text().toString().startsWith("Overworld Effect")) {
			overworldEffect = abilityDoc.selectXpath("//*[@id=\"content\"]/main/table[3]/tbody/tr[8]/td").text().toString();
			localAbility.setOverworldEffect(overworldEffect);
		} else {
			overworldEffect = "0";
		}
		
		if(!(ability.equals(localAbility)) && !(Objects.isNull(ability))) {
			session.beginTransaction();
			ability.setAbilityName(localAbility.getAbilityName());
			ability.setAbilityGameText(inGameText);
			ability.setInDepthAbilityEffect(inDepthEffect);
			ability.setOverworldEffect(overworldEffect);
			session.update(ability);
			session.getTransaction().commit();
		} else if (!(ability.equals(localAbility))){
			session.beginTransaction();
			session.persist(localAbility);
			session.getTransaction().commit();
		} else if(ability.equals(localAbility)) {
			System.out.println("Ability already in database. There is nothing to do here.");
			continue;
		}
		}
		session.close();
		System.out.println("/n /n /n");
	}
	
	public static void attackFinder(Session session) throws IOException{
		Document gen9AtkDex = Jsoup.connect("https://www.serebii.net/attackdex-sv/").get();
		Elements AtkDex = gen9AtkDex.select("option");
		List<TextNode> attacks = AtkDex.textNodes();
		System.out.println(AtkDex.eachText());
		ArrayList<String> AtkDexOverall = new ArrayList<String>();
		System.out.println("Inital attack dex data:");
		
		for(TextNode x : attacks) {
				if(!(x.text().startsWith("AttackDex"))) {
					AtkDexOverall.add(x.text());
		}
		}
	for(String s : AtkDexOverall) {
		Move moveSample = session.get(Move.class, s);
		Move move = new Move();
		move.setMoveName(s);
		if(!(Objects.isNull(moveSample)) && moveSample.getMoveName().equals(move.getMoveName()) && moveSample.equals(move)) {
			System.out.println("Nothing to do here.");
			continue;
		} else {
			session.beginTransaction();
			session.persist(move);
			session.getTransaction().commit();
			System.out.println(s);
		}
	}
	String validatorHQL = "FROM Move";
	List<Move> results = session.createQuery(validatorHQL).list();
	results.forEach(outcome -> System.out.println(outcome));
	attackFiller(session);
	}
	
	public static void attackFiller(Session session) throws IOException{
		/*
		 * Implementation pending.
		 */
		session.close();
		System.out.println("/n /n /n");
	}
	
	public static void main(String[] args) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		/*
		 * Prefer to take user input to request activation of a subroutine.
		 */
		String selection = null;
		Scanner scan = new Scanner(System.in);
		while(selection == null || !(selection.equals("0"))) {
			System.out.println("Please enter the number for your selection. To update the Pokedex, type '1'. To update the AbilityDex, type '2'. To update the AttackDex, type '3'. To exit, type '0'. No other options are implemented at this time.");
			selection = scan.nextLine();
		switch (selection){
		case "0" :
			System.out.println("Thank you for using the Serebii Cacher. Ending execution.");
			break;
			
		case "1" :
			session = HibernateUtil.getSessionFactory().openSession();
			dexFinder(session);
			break;
			
		case "2" :
			session = HibernateUtil.getSessionFactory().openSession();
			abilityFinder(session);
			break;
			
		case "3" :
			session = HibernateUtil.getSessionFactory().openSession();
			//attackFinder(session);
			break;
			
		default :
			System.out.println("Invalid input, please try again.");
			System.out.println("Your input was: " + selection);
			System.out.println("Handy hint: Remember to just type the number, not the quotes surrounding it.");
			break;
		}
		
	}
	}
}