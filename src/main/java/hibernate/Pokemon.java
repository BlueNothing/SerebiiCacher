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
import org.jsoup.select.Elements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pokemon")
public class Pokemon {
	
    @Id
    @Column(name = "name")
    private String pokeName;

    @Column(name = "types")
    private String pokeTypes;

    @Column(name = "classification")
    private String classification;
    
    @Column(name = "height")
    private String height;
    
    @Column(name = "weight")
    private String weight;
    
    @Column(name = "capRate")
    private double capRate;
    
     @Column(name="eggSteps")
     private int eggSteps;
    
    @Column(name="abilities")
    private String abilities;
    
     @Column(name="evRewardAmt")
     private int evRewardAmt;
     
     @Column(name="evRewardAttr")
     private String evRewardAttr;
    
    /*
     * Not sure how I want to implement recording weaknesses, if at all.
     */
    
    /* Let's ignore this info for right now.
    @Column(name="eggGroups")
    private String eggGroups;
    */
    
    /*
     * Locations are interesting data, but out of scope for initial implementation.
     *
     * Flavor text for the Dex entries is out of scope for now.
     */
    
    @Column(name="levelMoves", length = 10000)
    private String levelMoves;
    
    @Column(name="tmMoves", length = 10000)
    private String tmMoves;
    
    @Column(name="eggMoves", length = 10000)
    private String eggMoves;
    
    @Column(name="otherMoves", length = 10000)
    private String otherMoves;
    
    @Column(name="totalMoves", length = 100000)
    private String totalMoves;
    
    @Column(name="baseHP")
    private int baseHP;
    
    @Column(name="baseAtk")
    private int baseAtk;
    
    @Column(name="baseDef")
    private int baseDef;
    
    @Column(name="baseSpAtk")
    private int baseSpAtk;
    
    @Column(name="baseSpDef")
    private int baseSpDef;
    
    @Column(name="baseSpd")
    private int baseSpd;
    
    @Column(name="bst")
    private int bst;
    
    
    
    @Override
	public int hashCode() {
		return Objects.hash(abilities, baseAtk, baseDef, baseHP, baseSpAtk, baseSpDef, baseSpd, bst, capRate,
				classification, eggMoves, eggSteps, evRewardAmt, evRewardAttr, height, levelMoves, otherMoves, pokeName,
				pokeTypes, tmMoves, totalMoves, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pokemon other = (Pokemon) obj;
		return Objects.equals(abilities, other.abilities) && baseAtk == other.baseAtk && baseDef == other.baseDef
				&& baseHP == other.baseHP && baseSpAtk == other.baseSpAtk && baseSpDef == other.baseSpDef
				&& baseSpd == other.baseSpd && bst == other.bst
				&& Double.doubleToLongBits(capRate) == Double.doubleToLongBits(other.capRate)
				&& Objects.equals(classification, other.classification) && Objects.equals(eggMoves, other.eggMoves)
				&& eggSteps == other.eggSteps && evRewardAmt == other.evRewardAmt
				&& Objects.equals(evRewardAttr, other.evRewardAttr) && Objects.equals(height, other.height)
				&& Objects.equals(levelMoves, other.levelMoves) && Objects.equals(otherMoves, other.otherMoves)
				&& Objects.equals(pokeName, other.pokeName) && Objects.equals(pokeTypes, other.pokeTypes)
				&& Objects.equals(tmMoves, other.tmMoves) && Objects.equals(totalMoves, other.totalMoves)
				&& Objects.equals(weight, other.weight);
	}



	public Pokemon() {
		this.pokeName = "MissingNo";
		this.pokeTypes = null;
		this.classification = null;
		this.height = null;
		this.weight = null;
		this.capRate = 0;
		this.eggSteps = 0;
		this.abilities = null;
		this.evRewardAmt = 0;
		this.evRewardAttr = null;
		this.levelMoves = null;
		this.tmMoves = null;
		this.otherMoves = null;
		this.eggMoves = null;
		this.totalMoves = null;
		this.baseHP = 0;
		this.baseAtk = 0;
		this.baseDef = 0;
		this.baseSpAtk = 0;
		this.baseSpDef = 0;
		this.baseSpd = 0;
		this.bst = 0;
    }
    
    public Pokemon(String pokeName) {
    	this.pokeName = pokeName;
    	this.pokeTypes = null;
		this.classification = null;
		this.height = null;
		this.weight = null;
		this.capRate = 0;
		this.eggSteps = 0;
		this.abilities = null;
		this.evRewardAmt = 0;
		this.evRewardAttr = null;
		this.levelMoves = null;
		this.tmMoves = null;
		this.otherMoves = null;
		this.eggMoves = null;
		this.totalMoves = null;
		this.baseHP = 0;
		this.baseAtk = 0;
		this.baseDef = 0;
		this.baseSpAtk = 0;
		this.baseSpDef = 0;
		this.baseSpd = 0;
		this.bst = 0;
    }

	
    public Pokemon(String pokeName, String pokeTypes, String classification, String height, String weight, double capRate,
			int eggSteps, String abilities, int evRewardAmt, String evRewardAttr, String levelMoves, String tmMoves,
			String otherMoves, String eggMoves, String totalMoves, int baseHP, int baseAtk, int baseDef,
			int baseSpAtk, int baseSpDef, int baseSpd, int bst) {
		super();
		this.pokeName = pokeName;
		this.pokeTypes = pokeTypes;
		this.classification = classification;
		this.height = height;
		this.weight = weight;
		this.capRate = capRate;
		this.eggSteps = eggSteps;
		this.abilities = abilities;
		this.evRewardAmt = evRewardAmt;
		this.evRewardAttr = evRewardAttr;
		this.levelMoves = levelMoves;
		this.tmMoves = tmMoves;
		this.otherMoves = otherMoves;
		this.eggMoves = eggMoves;
		this.totalMoves = totalMoves;
		this.baseHP = baseHP;
		this.baseAtk = baseAtk;
		this.baseDef = baseDef;
		this.baseSpAtk = baseSpAtk;
		this.baseSpDef = baseSpDef;
		this.baseSpd = baseSpd;
		this.bst = bst;
	}

	public String getPokeName() {
		return pokeName;
	}

	public void setPokeName(String pokeName) {
		this.pokeName = pokeName;
	}

	public String getPokeTypes() {
		return pokeTypes;
	}

	public void setPokeTypes(String pokeTypes) {
		this.pokeTypes = pokeTypes;
	}

	public String getAbilities() {
		return abilities;
	}

	public void setAbilities(String abilities) {
		this.abilities = abilities;
	}

	public String getLevelMoves() {
		return levelMoves;
	}

	public void setLevelMoves(String levelMoves) {
		this.levelMoves = levelMoves;
	}

	public String getTmMoves() {
		return tmMoves;
	}

	public void setTmMoves(String tmMoves) {
		this.tmMoves = tmMoves;
	}

	public String getotherMoves() {
		return otherMoves;
	}

	public void setotherMoves(String otherMoves) {
		this.otherMoves = otherMoves;
	}

	public String getEggMoves() {
		return eggMoves;
	}

	public void setEggMoves(String eggMoves) {
		this.eggMoves = eggMoves;
	}

	public String getTotalMoves() {
		return totalMoves;
	}

	public void setTotalMoves(String totalMoves) {
		this.totalMoves = totalMoves;
	}

	public int getBaseHP() {
		return baseHP;
	}

	public void setBaseHP(int baseHP) {
		this.baseHP = baseHP;
	}

	public int getBaseAtk() {
		return baseAtk;
	}

	public void setBaseAtk(int baseAtk) {
		this.baseAtk = baseAtk;
	}

	public int getBaseDef() {
		return baseDef;
	}

	public void setBaseDef(int baseDef) {
		this.baseDef = baseDef;
	}

	public int getBaseSpAtk() {
		return baseSpAtk;
	}

	public void setBaseSpAtk(int baseSpAtk) {
		this.baseSpAtk = baseSpAtk;
	}

	public int getBaseSpDef() {
		return baseSpDef;
	}

	public void setBaseSpDef(int baseSpDef) {
		this.baseSpDef = baseSpDef;
	}

	public int getBaseSpd() {
		return baseSpd;
	}

	public void setBaseSpd(int baseSpd) {
		this.baseSpd = baseSpd;
	}

	public int getBst() {
		return bst;
	}

	public void setBst(int bst) {
		this.bst = bst;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public double getCapRate() {
		return capRate;
	}

	public void setCapRate(double capRate) {
		this.capRate = capRate;
	}

	public int getEggSteps() {
		return eggSteps;
	}

	public void setEggSteps(int eggSteps) {
		this.eggSteps = eggSteps;
	}

	public int getEvRewardAmt() {
		return evRewardAmt;
	}

	public void setEvRewardAmt(int evRewardAmt) {
		this.evRewardAmt = evRewardAmt;
	}

	public String getEvRewardAttr() {
		return evRewardAttr;
	}

	public void setEvRewardAttr(String evRewardAttr) {
		this.evRewardAttr = evRewardAttr;
	}

	public String getOtherMoves() {
		return otherMoves;
	}

	public void setOtherMoves(String otherMoves) {
		this.otherMoves = otherMoves;
	}

	@Override
	public String toString() {
		return "Pokemon [pokeName=" + pokeName + ", pokeTypes=" + pokeTypes + ", classification=" + classification + ", height="
				+ height + ", weight=" + weight + ", capRate=" + capRate + ", eggSteps=" + eggSteps + ", abilities="
				+ abilities + ", evRewardAmt=" + evRewardAmt + ", evRewardAttr=" + evRewardAttr + ", levelMoves="
				+ levelMoves + ", tmMoves=" + tmMoves + ", otherMoves=" + otherMoves + ", eggMoves=" + eggMoves
				+ ", totalMoves=" + totalMoves + ", baseHP=" + baseHP + ", baseAtk=" + baseAtk + ", baseDef=" + baseDef
				+ ", baseSpAtk=" + baseSpAtk + ", baseSpDef=" + baseSpDef + ", baseSpd=" + baseSpd + ", bst=" + bst
				+ "]";
	}
    
	public static void dexFinder(Session session) throws IOException {
		 /* What we see here selects each entry in the current Serebii Pokedex and adds it to the list.
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
		if(s.contains("%C3%A9")) {
			s = s.replace("%C3%A9", "e");
		}
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
	//results.forEach(outcome -> System.out.println(outcome)); This prints the dex data, but isn't needed right now.
	dexFiller(session);
	}
	
	public static void dexFiller(Session session) throws IOException {
		String validatorHQL = "FROM Pokemon";
		List<Pokemon> results = session.createQuery(validatorHQL).list();
		for (Pokemon poke : results) {
			System.out.println(poke.toString());
			String levelMoves = "";
			String tmMoves = "";
			String eggMoves = "";
			String otherMoves = "";
			String overallMoves = "";
			int baseAtk = 0;
			int baseDef = 0;
			int baseHP = 0;
			int baseSpd = 0;
			int baseSpAtk = 0;
			int baseSpDef = 0;
			int bst = 0;
			String pokeName = poke.getPokeName(); //Working on this part to dynamically fill the dex!
			String urlPokeName = pokeName.toLowerCase().replace("%20", "").replace(" ", "").replace("%C3%A9", "e").replace("é", "e");
			urlPokeName = "charmander";
			System.out.println(urlPokeName);
			String URL = "https://www.serebii.net/pokedex-sv/" + urlPokeName + "/"; //Builds the URLs correctly!
			Document dexEntry = Jsoup.connect((URL)).get();
			/*
			 * Taking a moment to explain the strategy here: 
			 * The tables on each page in Serebii's records have a consistent pattern, but not every field exists for every entry.
			 * So if a table exists, it will appear after the tables before it in the pattern.
			 * I iterate 'i' over the list of tables on the page until I find the one I'm looking for (by checking the headers, which are uniform where extant).
			 * If I find the one I'm looking for, I update anchor to start from that table, process that table, and repeat the process for the next.
			 * If I do not find the one I'm looking for before I run out of tables, I exit the loop in an unconventional way and report an issue to the debug pane, then continue to try and process the others.
			 * 
			 * TODO: Remember that the gen 9 new pokemon have a different data structure! Need to fix this.
			 * Maybe set a boolean value for whether it's a new Pokemon, then adapt the xPath variables accordingly.
			 */
			boolean typeTableFound = false, abilityTableFound = false, levelMovesTableFound = false, statsTableFound = false;
			boolean enabled = false; //Used for 'experimental' or unimplemented features.
			for(int i = 0; i < dexEntry.select("table.dextable").size(); i++) {
				Element dexTable = dexEntry.select("table.dextable").get(0);
				try {
				dexTable = dexEntry.select("table.dextable").get(i);
				} catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
				Elements dexTableRows = dexTable.select("tr");
				Element row = dexTable.selectFirst("tr");
				Element titleCol = row.selectFirst("td");
				System.out.println("TITLE COLUMN: " + titleCol.text()); //Useful for debugging.
				Element sortRow = dexTable.selectXpath("//*[@id=\"content\"]/main/div[2]/table[4]/tbody/tr[4]/td[1]").first();
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Name")) { //Means the type table has been found.
					System.out.println("TYPE TABLE:");
					row = dexTableRows.select("tr").get(1);
					//This should work for dual-types, will need to test.
					Element typeCol = row.selectFirst(".cen");
					Elements types = typeCol.select("a");
					String typeList = "";
					for(Element type : types) {
						String typeHref = type.attr("href");
						System.out.println(typeHref);
						String storedType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
						storedType = storedType.substring(0, 1).toUpperCase() + storedType.substring(1) + ", ";
						typeList += storedType;
					}
					typeList = typeList.substring(0, (typeList.length() - 2));
					System.out.println("Type: " + typeList);
					poke.setPokeTypes(typeList);
					typeTableFound = true;
				
					//System.out.println("CLASSIFICATION TABLE"); Useful for debugging.
					row = dexTable.selectXpath("//*[@id=\"content\"]/main/div[2]/table[4]/tbody/tr[4]/td[1]").first();
					Element classificationCol = row.select("td").get(0);
					String classification = classificationCol.text();
					poke.setClassification(classification);
					
					row = dexTable.selectXpath("//*[@id=\"content\"]/main/div[2]/table[4]/tbody/tr[4]/td[2]").first();
					Element heightCol = row.select("td").get(0);
					String height = heightCol.text();
					height = height.substring(0, (height.indexOf("\"") + 1));
					poke.setHeight(height);
					
					row = dexTable.selectXpath("//*[@id=\"content\"]/main/div[2]/table[4]/tbody/tr[4]/td[3]").first();
					Element weightCol = row.select("td").get(0);
					String weight = weightCol.text();
					weight = weight.substring(0, weight.indexOf("lbs") + 2);
					weight = weight.replace("lb", " lbs");
					poke.setWeight(weight);
					
					row = dexTable.selectXpath("//*[@id=\"content\"]/main/div[2]/table[4]/tbody/tr[4]/td[4]").first();
					Element capRateCol = row.select("td").get(0);
					double capRate = Double.parseDouble(capRateCol.text());
					poke.setCapRate(capRate);
					
					row = dexTable.selectXpath("//*[@id=\"content\"]/main/div[2]/table[4]/tbody/tr[4]/td[5]").first();
					Element eggStepsCol = row.select("td").get(0);
					String eggStepString = eggStepsCol.text().replaceAll(",", "");
					int eggSteps = Integer.parseInt(eggStepString);
					poke.setEggSteps(eggSteps);
				}
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().startsWith("Abilities:")) { //Means the ability table has been found.
					//System.out.println("ABILITIES TABLE"); Useful for debugging.
					row = dexTable.selectXpath("//*[@id=\"content\"]/main/div[2]/table[5]/tbody/tr[2]/td").first();
					Elements abilityElements = row.select("b");
					String abilityList = "";
					boolean hiddenNext = false;
					for(Element ability : abilityElements) {
						if(ability.text().equals("Hidden Ability")) {
							hiddenNext = true;
							continue;
						}
						else if(!hiddenNext) {
							abilityList += (ability.text() + ", ");
						} else if(hiddenNext) {
							abilityList += ("(H) " + ability.text());
						}
					}
					System.out.println(abilityList);
					poke.setAbilities(abilityList);
					
					row = dexTable.select("tr").get(3);
					Element evCol = row.select("td").get(2);
					String evString = evCol.text();
					if(evString.equals("")) {
						int evAmt = 0;
						evString = "";
						poke.setEvRewardAmt(evAmt);
						poke.setEvRewardAttr(evString);
					} else {
						if(poke.getPokeName().equals("Indeedee")) {
							System.out.println(evString);
							continue;
						}
					int evAmt = Integer.parseInt(String.valueOf(evString.charAt(0)));
					System.out.println(evString);
					evString = evString.substring(2);
					int garbageIndex = evString.indexOf("Point") - 1;
					evString = evString.substring(0, garbageIndex);
					poke.setEvRewardAmt(evAmt);
					poke.setEvRewardAttr(evString);
					}
					
					abilityTableFound = true;
				}
				if(enabled && !(Objects.isNull(titleCol.text())) && titleCol.text().equals("Weakness")) {
					System.out.println("WEAKNESS TABLE");
					//row = dexTable.select("tr").get(1);
					System.out.println("NYI");
				}
				
				if(enabled && !(Objects.isNull(titleCol.text())) && titleCol.text().equals("Wild Hold Item")) {
					System.out.println("Wild Hold Item Table");
					//row = dexTable.select("tr").get(1);
					System.out.println("NYI");
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Standard Level Up")) {
					System.out.println("Level Moves Table");
					Elements rows = dexTable.select("tr");
					levelMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						if(localRow.text().equals("Standard Level Up") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %")) //These are header rows, which are out of scope.
							continue;
							else {
							Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
							if(localCol.size() == 1) {
								continue; //These are description rows, which are useless for a simple list of moves.
							} else {
								levelMoves += localCol.get(1).text() + ",";
							}
						}
					}
					levelMoves = levelMoves.substring(0, levelMoves.length() - 1);
					System.out.println(levelMoves);
					overallMoves += levelMoves + ",";
					poke.setLevelMoves(levelMoves);
					levelMovesTableFound = true;
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Technical Machine Attacks")) {
					System.out.println("TM Moves Table");
					Elements rows = dexTable.select("tr");
					tmMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						if(localRow.text().equals("TM Moves Table") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %")) //These are header rows, which are out of scope.
							continue;
							else {
							Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
							if(localCol.size() == 1) {
								continue; //These are description rows, which are useless for a simple list of moves.
							} else if(localCol.size() > 1){
								tmMoves += localCol.get(1).text() + ",";
							} else {
								continue;
							}
						}
					}
					tmMoves = tmMoves.substring(0, tmMoves.length() - 1);
					System.out.println(tmMoves);
					poke.setTmMoves(tmMoves);
					overallMoves = overallMoves + "," + tmMoves;
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Egg Moves (Details)")) {
					//System.out.println("Egg Moves Table"); DEBUG USE ONLY.
					Elements rows = dexTable.select("tr");
					//System.out.println(rows.get(2).text()); DEBUG USE ONLY.
					eggMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						if(localRow.text().equals("Egg Moves Table") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %")) //These are header rows, which are out of scope.
							continue;
							else {
							Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
							if(localCol.size() == 1) {
								continue; //These are description rows, which are useless for a simple list of moves.
							} else if (localCol.size() > 1){
								eggMoves += localCol.get(0).text() + ",";
							} else{
								continue;
							}
						}
					}
					eggMoves = eggMoves.substring(0, eggMoves.length() - 1);
					System.out.println(eggMoves);
					poke.setEggMoves(eggMoves);
					overallMoves = overallMoves + "," + eggMoves;
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().contains("Move") && !(titleCol.text().contains("Egg Moves"))) {
					System.out.println("Other Moves Table");
					Elements rows = dexTable.select("tr");
					System.out.println(rows.get(0).text());
					otherMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						if(localRow.text().equals("Pre-Evolution Only Moves") || localRow.text().equals("Move Reminder Only Attacks") || localRow.text().equals("Special Moves") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %")) //These are header rows, which are out of scope.
							continue;
							else {
							Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
							if(localCol.size() == 1) {
								continue; //These are description rows, which are useless for a simple list of moves.
							} else if (localCol.size() > 1){
								eggMoves += localCol.get(0).text() + ",";
							} else{
								continue;
							}
						}
					}
					eggMoves = eggMoves.substring(0, eggMoves.length() - 1);
					System.out.println(eggMoves);
					poke.setEggMoves(eggMoves);
					overallMoves = overallMoves + "," + eggMoves;
					/*
					for(int j = 2; j < rows.size(); j++) {
						Element move = rows.get(j);
						if(move.select("td").get(1).text() != null) {
						String moveName = move.select("td").get(1).text() + ", ";
						otherMoves += moveName;
						}
					}
					overallMoves = overallMoves + ", " + otherMoves;
					*/
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Stats")) {
					//System.out.println("STATS TABLE");
					row = dexTable.select("tr").get(2);
					baseHP = Integer.parseInt(row.select("td").get(1).text());
					baseAtk = Integer.parseInt(row.select("td").get(2).text());
					baseDef = Integer.parseInt(row.select("td").get(3).text());
					baseSpAtk = Integer.parseInt(row.select("td").get(4).text());
					baseSpDef = Integer.parseInt(row.select("td").get(5).text());
					baseSpd = Integer.parseInt(row.select("td").get(6).text());
					bst = baseHP + baseAtk + baseDef + baseSpAtk + baseSpDef + baseSpd;
					poke.setBaseHP(baseHP);
					poke.setBaseAtk(baseAtk);
					poke.setBaseDef(baseDef);
					poke.setBaseSpAtk(baseSpAtk);
					poke.setBaseSpDef(baseSpDef);
					poke.setBaseSpd(baseSpd);
					poke.setBst(bst);
				
				}
			}
			//System.out.println(overallMoves);
			System.out.println("Assembling moveset...");
			TreeSet<String> overallMoveset = new TreeSet<String>();
			String overallMovesetString = overallMoves;
			while(overallMovesetString.indexOf(",") != -1) {
				String moveName = overallMovesetString.substring(0, overallMovesetString.indexOf(","));
				moveName = moveName.trim();
				if (!(moveName.equals(" ")) && !(moveName.equals("")))
					overallMoveset.add(moveName);
				overallMovesetString = overallMovesetString.substring(overallMovesetString.indexOf(",") + 1);
			}
				overallMoveset.add(overallMovesetString);
			System.out.println(overallMoveset.toString().trim());
			//poke.setotherMoves(otherMoves);
			poke.setTotalMoves(overallMoveset.toString().trim());
			System.out.println(poke.toString());
			/*
			Pokemon dbSample = session.get(Pokemon.class, poke.getPokeName());
			 
			if (!(Objects.isNull(dbSample)) && dbSample.getPokeName().equals(poke.getPokeName()) && dbSample.equals(poke)) {
				System.out.println("Nothing to do here.");
				continue;
				}
			*/
			session.beginTransaction();
			session.update(poke);
			session.getTransaction().commit();
		}
			
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
		
	}
    
	public static void natDexFinder() throws IOException {
		Document natDex = Jsoup.connect("https://www.serebii.net/pokemon/nationalpokedex.shtml").get();
		Element natDexTable = natDex.select("table.dextable").get(0);
		Elements rows = natDexTable.select("tr");
		ArrayList<String> natDexOverall = new ArrayList<String>();
		for(int i = 2; i < 5/*rows.size()*/; i++) {
			Element row = rows.get(i);
			Elements cols = row.select("td");
			System.out.println(cols.text().toString());
			for(int j = 0; j < cols.size(); j++) {
				Element col= cols.get(j);
				if(!(col.text().toString() == null)) {
				System.out.println(j + col.text().toString());
			}
			//System.out.println(cols.get(2).text());
			//natDexOverall.add(cols.get(2).text());
		//Columns 3, 5, and 6-11 are all useful data, but most important are 3 and 5.
			
		}
	}
	}

}
