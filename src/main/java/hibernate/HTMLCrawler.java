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
	public static void main(String[] args) throws IOException {
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
		}
		}
	for(String s : abilitiesDexOverall) {
		System.out.println(s);
	}
	}
	
	
}