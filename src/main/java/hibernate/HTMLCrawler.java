package hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	public static void dexFinder() throws IOException {
		Document palDex = Jsoup.connect("https://www.serebii.net/pokedex-sv/").get();
		String palDexTitle = palDex.title();
		Elements palDexElems1 = palDex.select("content > main > div:nth-child(4) > table:nth-child(2) > tbody > tr > td:nth-child(1) > form");
		palDexElems1 = palDexElems1.select("option");
		List<TextNode> pokemon1 = palDexElems1.textNodes();
		System.out.println(palDexElems1.eachText());
		Elements palDexElems2 = palDex.select("content > main > div:nth-child(4) > table:nth-child(2) > tbody > tr > td:nth-child(2) > form");
		List<TextNode> pokemon2 = palDexElems2.textNodes();
		System.out.println(palDexElems2.eachText());
		Elements palDexElems3 = palDex.select("content > main > div:nth-child(4) > table:nth-child(2) > tbody > tr > td:nth-child(3) > form");
		List<TextNode> pokemon3 = palDexElems3.textNodes();
		System.out.println(palDexElems3.eachText());
		
		ArrayList<String> palDexOverall = new ArrayList<String>();
		System.out.println("Inital Pokedex data:");
		
		for(TextNode x : pokemon1) {
				if(!(x.text().startsWith("Poké"))) {
					palDexOverall.add(x.text());
		}
		}
		for(TextNode x : pokemon2) {
			if(!(x.text().startsWith("Poké"))) {
				palDexOverall.add(x.text());
	}
	}
		for(TextNode x : pokemon3) {
			if(!(x.text().startsWith("Poké"))) {
				palDexOverall.add(x.text());
	}
	}
	for(String s : palDexOverall) {
		System.out.println(s);
	}
		//form name ="cent", "coast", "mount", need to read off all the entries from those 'select' forms.
	}
	
	public static void dexFiller() throws IOException {
		/*
		 * Implementation pending!
		 */
	}
	
	public static void abilityFinder() throws IOException{
		Document abiliDex = Jsoup.connect("https://www.serebii.net/abilitydex/").get();
		String abiliDexTitle = abiliDex.title();
		Elements abilitiesDex = abiliDex.select("option");
		List<TextNode> abilities = abilitiesDex.textNodes();
		System.out.println(abilitiesDex.eachText());
		ArrayList<String> abilitiesDexOverall = new ArrayList<String>();
		System.out.println("Inital ability dex data:");
		
		for(TextNode x : abilities) {
				if(!(x.text().startsWith("AbilityDex"))) {
					abilitiesDexOverall.add(x.text());
					System.out.println(x.text());
		}
		}
	
	}
	
	public static void abilityFiller() throws IOException {
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
	
	public static void attackFinder() throws IOException{
		Document gen9AtkDex = Jsoup.connect("https://www.serebii.net/attackdex-sv/").get();
		String gen9AtkDexTitle = gen9AtkDex.title();
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
	}
	}
	
	public static void attackFiller() throws IOException{
		/*
		 * Implementation pending.
		 */
	}
	
	public static void main(String[] args) throws IOException {
		dexFinder();
		dexFiller();
		abilityFinder();
		abilityFiller();
		attackFinder();
		attackFiller();
	}
}