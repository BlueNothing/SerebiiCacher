package hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		session.beginTransaction();
		Pokemon pokemon = new Pokemon();
		pokemon.setPokeName(s);
		System.out.println(pokemon.toString());
		session.persist(pokemon);
		session.getTransaction().commit();
	}
	String validatorHQL = "FROM Pokemon";
	List results = session.createQuery(validatorHQL).list();
	session.close();
	}
	
	public static void dexFiller(Session session) throws IOException {
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
					//TODO: Add to Ability DB!
		}
		}
	
	}
	
	public static void abilityFiller(Session session) throws IOException {
		System.out.println("Outputting sample ability test.");
		Ability testAbility = new Ability("Adaptability");
		String abilityName = "Zen Mode";
		abilityName = abilityName.toLowerCase().replace("%20", "").replace(" ", "");
		//The code now effectively connects to the webpage for most abilities.
		System.out.println(("https://www.serebii.net/abilitydex/" + abilityName + ".shtml"));
		/*
		 * https://www.serebii.net/abilitydex/adaptability.shtml
		 * https://www.serebii.net/abilitydex/adaptability.shtml
		 */
		Document abilityDoc = Jsoup.connect(("https://www.serebii.net/abilitydex/" + abilityName.toLowerCase() + ".shtml")).get();
		Elements abilityData = abilityDoc.select(".fooinfo");
		//System.out.println(abilityData.text().toString());
		List<String> abilityDataText = abilityData.eachText();
		String inGameText, inDepthEffect, overworldEffect = null;
		/*
		 * The following strategy looks like it should be fine, but runs into unexpected edge cases like Palafin in current implementation.
		 */
		if((!abilityDataText.get(0).startsWith("#"))) {
			inGameText = abilityDataText.get(0);
		}
		else {
			inGameText = "0";
		}
		if((!abilityDataText.get(1).startsWith("#"))) {
			inDepthEffect = abilityDataText.get(1);
		}
		else {
			inDepthEffect = "0";
		}
		if((!abilityDataText.get(2).startsWith("#"))) {
			overworldEffect = abilityDataText.get(2);
		}
		else {
			overworldEffect = "0";
		}
		System.out.println(inGameText);
		System.out.println(inDepthEffect);
		System.out.println(overworldEffect);
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
		System.out.println(s);
		//TODO: Add to Move DB!
	}
	}
	
	public static void attackFiller(Session session) throws IOException{
		/*
		 * Implementation pending.
		 */
	}
	
	public static void main(String[] args) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		session = HibernateUtil.getSessionFactory().openSession();
		dexFinder(session);
		session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("\n \n \n");
		dexFiller(session);
		session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("\n \n \n");
		abilityFinder(session);
		session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("\n \n \n");
		//abilityFiller(session);
		//session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("\n \n \n");
		attackFinder(session);
		session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("\n \n \n");
		//attackFiller(session);
	}
}