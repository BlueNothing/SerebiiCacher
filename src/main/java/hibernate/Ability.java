package hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "abilities")
public class Ability {
	@Id
    @Column(name = "name")
    private String abilityName;

    @Column(name = "gameText", length = 1020)
    private String abilityGameText;
    
    @Column(name = "inDepthAbilityEffect", length = 1020)
    private String inDepthAbilityEffect;
    
    @Column(name="overworldEffect", length = 1020)
    private String overworldEffect;
    
    @Column(name="affectedMoves", length = 3000)
    private String affectedMoves;
    
    @Column(name="accessSet", length = 1020)
    private String accessSet;
    
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
	

	public String getAffectedMoves() {
		return affectedMoves;
	}

	public void setAffectedMoves(String affectedMoves) {
		this.affectedMoves = affectedMoves;
	}

	public String getAccessSet() {
		return accessSet;
	}

	public void setAccessSet(String accessSet) {
		this.accessSet = accessSet;
	}

	@Override
	public String toString() {
		return "Ability [abilityName=" + abilityName + ", abilityGameText=" + abilityGameText
				+ ", inDepthAbilityEffect=" + inDepthAbilityEffect + ", overworldEffect=" + overworldEffect
				+ ", affectedMoves=" + affectedMoves + ", accessSet=" + accessSet + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(abilityGameText, abilityName, accessSet, affectedMoves, inDepthAbilityEffect,
				overworldEffect);
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
				&& Objects.equals(accessSet, other.accessSet) && Objects.equals(affectedMoves, other.affectedMoves)
				&& Objects.equals(inDepthAbilityEffect, other.inDepthAbilityEffect)
				&& Objects.equals(overworldEffect, other.overworldEffect);
	}
	
	public static void abilityFinder(Session session) throws IOException{
		Document abiliDex = Jsoup.connect("https://www.serebii.net/abilitydex/").get();
		Elements abilitiesDex = abiliDex.select("option");
		List<TextNode> abilities = abilitiesDex.textNodes();
		List<Ability> results = new ArrayList<Ability>();
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
					results.add(localAbility);
					if (!(Objects.isNull(dbSample)) && dbSample.getAbilityName().equals(localAbility.getAbilityName())) {
						System.out.println("Nothing to do here.");
						continue;
					} else {
		}
		} else {
			continue;
		}
	
	}
		System.out.println(results.toString());
		abilityFiller(session, results);
	}
	
	public static void abilityFiller(Session session, List<Ability> results) throws IOException {
		
		System.out.println("Starting abilityFiller");
		//String validatorHQL = "FROM Ability";
		//List<Ability> results = session.createQuery(validatorHQL).list();
		for (Ability ability : results) {
			Ability localAbility = new Ability();
			localAbility.setAbilityName(ability.getAbilityName());
			String abilityName = localAbility.getAbilityName().toLowerCase().replace("%20", "").replace(" ", "");
			String URL = "https://www.serebii.net/abilitydex/" + abilityName + ".shtml";
			Document abilityDoc = Jsoup.connect((URL)).get();
			String inGameText = null, inDepthEffect = null, overworldEffect = null, affectedMoves = null, accessSet = null;
			TreeSet<String> affectedMovesTree = new TreeSet<String>();
			TreeSet<String> accessSetTree = new TreeSet<String>();
			for(int i = 0; i < abilityDoc.select("table.dextable").size(); i++) {
				Element dexTable = abilityDoc.select("table.dextable").get(0);
				try {
				dexTable = abilityDoc.select("table.dextable").get(i);
				} catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
				Elements dexTableRows = dexTable.select("tr");
				System.out.println("Number of Rows: " + dexTableRows.size());
				Element row = dexTable.selectFirst("tr");
				Element titleCol = row.selectFirst("td");
				System.out.println("TITLE COLUMN: " + titleCol.text());
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Name")) { //First row has Attack Name, Attack Type, Category.
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						if(cols.size() == 0) {
							continue;
						}
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Game's Text:")) {
							inGameText = col.text();
							localAbility.setAbilityGameText(inGameText);
							System.out.println("In-Game Text: " + inGameText);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("In-Depth Effect:")) {
							inDepthEffect = col.text();
							localAbility.setInDepthAbilityEffect(inDepthEffect);
							System.out.println("In-Depth Effect: " + inDepthEffect);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Overworld Effect:")) {
							overworldEffect = col.text();
							localAbility.setOverworldEffect(overworldEffect);
							System.out.println("Overworld Effect: " + overworldEffect);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Details & Attacks Effected")) {
							Element subTable = row.select("table").first();
							Elements subTableRows = subTable.select("tr");
							for(Element subTableRow : subTableRows) {
								if(subTableRow.select("td").size() < 1) {
									continue;
								} else {
								String moveName = subTableRow.select("td").get(0).text();
								System.out.println(moveName);
								affectedMovesTree.add(moveName);
							}
							}
							affectedMoves = affectedMovesTree.toString().replace("[", "").replace("]", "");
							localAbility.setAffectedMoves(affectedMoves);
							System.out.println("Attacks Effected: " + affectedMoves);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Attacks Effected")) {
							Element subTable = row.select("table").first();
							Elements subTableRows = subTable.select("tr");
							for(Element subTableRow : subTableRows) {
								String moveName = subTableRow.select("td").get(0).text();
								System.out.println(moveName);
								affectedMovesTree.add(moveName);
							}
							affectedMoves = affectedMovesTree.toString().replace("[", "").replace("]", "");
							localAbility.setAffectedMoves(affectedMoves);
							System.out.println("Attacks Effected: " + affectedMoves);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Further Details")) {
							continue;
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Lv.")) {
							continue;
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("")) {
							continue;
						}
						if(!(Objects.isNull(subTitle.text())) && Character.isDigit(subTitle.text().charAt(0))) {
							continue;
						}
					}
				}
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("No.")) {
					for(int j = 2; j < dexTableRows.size(); j+= 2) {
						row = dexTableRows.select("tr").get(j);
						Elements cols = row.select("td");
						Element col = cols.get(3);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(col.text());
						accessSetTree.add(col.text());
						}
						accessSet = accessSetTree.toString().replace("[", "").replace("]", "");
						
			}
			}
			localAbility.setAccessSet(accessSet);
			System.out.println("Access Set: " + accessSet);
		//The code now effectively connects to the webpage for most abilities.
			Ability dbSample = session.get(Ability.class, localAbility.getAbilityName());
			System.out.println(localAbility.toString());
		if(!(Objects.isNull(dbSample)) && dbSample.toString().equals(localAbility.toString())) {
			System.out.println(localAbility.getAbilityName() + ": already in database. There is nothing to do here.");
			continue;
		} else if (Objects.isNull(dbSample)){
			session.beginTransaction();
			session.persist(localAbility);
			session.getTransaction().commit();
			continue;
		}
		else if(!(Objects.isNull(dbSample)) && !(dbSample.toString().equals(localAbility.toString()))) {
			session.beginTransaction();
			session.merge(localAbility);
			session.getTransaction().commit();
			continue;
		} 
		
		}
	}

	public Ability() {
		this.abilityName = null;
    	this.abilityGameText = null;
    	this.inDepthAbilityEffect = null;
    	this.overworldEffect = null;
    }
    
    public Ability(String abilityName) {
    	this.abilityName = abilityName;
    	this.abilityGameText = null;
    	this.inDepthAbilityEffect = null;
    	this.overworldEffect = null;
    }

	public Ability(String abilityName, String abilityGameText, String inDepthAbilityEffect, String overworldEffect,
			String affectedMoves, String accessSet) {
		super();
		this.abilityName = abilityName;
		this.abilityGameText = abilityGameText;
		this.inDepthAbilityEffect = inDepthAbilityEffect;
		this.overworldEffect = overworldEffect;
		this.affectedMoves = affectedMoves;
		this.accessSet = accessSet;
	}
   
    
}
