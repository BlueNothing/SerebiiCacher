package hibernate.ability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class AbilityHelpers {
	
	public static ArrayList<Ability> abilityListGenerator(Session session) throws IOException{
		Document abiliDex = Jsoup.connect("https://www.serebii.net/abilitydex/").get();
		Elements abilitiesDex = abiliDex.select("option");
		List<TextNode> abilities = abilitiesDex.textNodes();
		ArrayList<Ability> results = new ArrayList<Ability>();
		System.out.println(abilitiesDex.eachText());
		ArrayList<String> abilitiesDexOverall = new ArrayList<String>();
		System.out.println("Inital ability dex data:");
		
		for(TextNode x : abilities) {
				if(!(x.text().startsWith("AbilityDex"))) {
					abilitiesDexOverall.add(x.text());
					System.out.println(x.text());
					Ability dbSample = session.get(Ability.class, x.text());
					Ability localAbility = new Ability();
					localAbility.setName(x.text());
					results.add(localAbility);
					if (!(Objects.isNull(dbSample)) && dbSample.getName().equals(localAbility.getName())) {
						System.out.println("Nothing to do here.");
						continue;
					} else {
		}
		} else {
			continue;
		}
	
	}
		System.out.println(results.toString());
		System.out.println("Starting abilityFiller");
		return results;
	}

}
