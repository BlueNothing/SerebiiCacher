package hibernate;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;

public class HTMLCrawler {
	public static void main(String[] args) throws IOException {
		Document palDex = Jsoup.connect("").get();
		String palDexTitle = palDex.title();
		
		
		
		Document gen9AtkDex = Jsoup.connect("").get();
		String gen9AtkDexTitle = gen9AtkDex.title();
		
		Document abiliDex = Jsoup.connect("").get();
		String abiliDexTitle = abiliDex.title();
	}
	
	
}