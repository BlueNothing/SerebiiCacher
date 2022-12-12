package hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "abilities")
public class Ability {
	/*
	 * NAME, GAME TEXT, IN-DEPTH EFFECT;
	 * Possibly add an element for affectedMoves, but this one is more of a 'later development' feature.
	 */

	@Id
    @Column(name = "name")
    private String abilityName;

    @Column(name = "gameText", length = 1020)
    private String abilityGameText;
    
    @Column(name = "inDepthAbilityEffect", length = 1020)
    private String inDepthAbilityEffect;
    
    @Column(name="overworldEffect", length = 1020)
    private String overworldEffect;
    
    public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	public String getAbilityGameText() {
		return abilityGameText;
	}

	public void setAbilityGameText(String abilityGameText) {
		this.abilityGameText = abilityGameText;
	}

	public String getInDepthAbilityEffect() {
		return inDepthAbilityEffect;
	}

	public void setInDepthAbilityEffect(String inDepthAbilityEffect) {
		this.inDepthAbilityEffect = inDepthAbilityEffect;
	}

	public String getOverworldEffect() {
		return overworldEffect;
	}

	public void setOverworldEffect(String overworldEffect) {
		this.overworldEffect = overworldEffect;
	}

	@Override
	public String toString() {
		return "Ability [abilityName=" + abilityName + ", abilityGameText=" + abilityGameText
				+ ", inDepthAbilityEffect=" + inDepthAbilityEffect + ", overworldEffect=" + overworldEffect + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(abilityGameText, abilityName, inDepthAbilityEffect, overworldEffect);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ability other = (Ability) obj;
		return Objects.equals(abilityGameText, other.abilityGameText) && Objects.equals(abilityName, other.abilityName)
				&& Objects.equals(inDepthAbilityEffect, other.inDepthAbilityEffect)
				&& Objects.equals(overworldEffect, other.overworldEffect);
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
			localAbility.setAbilityName(ability.getAbilityName());
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

	public Ability() {

    }
    
    public Ability(String abilityName) {
    	
    }
    public Ability(String abilityName, String abilityGameText, String inDepthAbilityEffect, String overworldEffect) {
    	
    }
    
}
