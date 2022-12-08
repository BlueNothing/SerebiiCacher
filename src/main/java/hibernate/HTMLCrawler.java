package hibernate;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLCrawler {
	public static void main(String[] args) throws IOException {
		Document palDex = Jsoup.connect("https://www.serebii.net/pokedex-sv/").get();
		String palDexTitle = palDex.title();
		//form name ="cent", "coast", "mount", need to read off all the entries from those 'select' forms.
		
		
		
		Document gen9AtkDex = Jsoup.connect("https://www.serebii.net/attackdex-sv/").get();
		String gen9AtkDexTitle = gen9AtkDex.title();
		Elements testData = gen9AtkDex.select("#content > main > div:nth-child(3) > table > tbody > tr > td:nth-child(1) > form > div > select");
		
		for(Element x : testData) {
			
		}
		System.out.println(testData.text());
		/*
		 * #content > main > div:nth-child(3) > table > tbody > tr > td:nth-child(1) > form > div > select
		 * 
		 * #content > main > div:nth-child(3) > table > tbody > tr > td:nth-child(2) > form > div > select
		 * 
		 * #content > main > div:nth-child(3) > table > tbody > tr > td:nth-child(3) > form > div > select
		 */
		
		Document abiliDex = Jsoup.connect("https://www.serebii.net/abilitydex/").get();
		String abiliDexTitle = abiliDex.title();
	}
	
	
}