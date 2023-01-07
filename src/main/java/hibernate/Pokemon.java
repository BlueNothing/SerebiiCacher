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
     
     @Column(name="weaknesses")
     private String weaknesses;
     
     @Column(name="neutrals")
     private String neutrals; //Neutral types.
     
     @Column(name="resistances")
     private String resistances;
     
     @Column(name="immunities")
     private String immunities;
    
    @Column(name="eggGroups")
    private String eggGroups;
    
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
				classification, eggGroups, eggMoves, eggSteps, evRewardAmt, evRewardAttr, height, immunities,
				levelMoves, neutrals, otherMoves, pokeName, pokeTypes, resistances, tmMoves, totalMoves, weaknesses,
				weight);
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
				&& Objects.equals(classification, other.classification) && Objects.equals(eggGroups, other.eggGroups)
				&& Objects.equals(eggMoves, other.eggMoves) && eggSteps == other.eggSteps
				&& evRewardAmt == other.evRewardAmt && Objects.equals(evRewardAttr, other.evRewardAttr)
				&& Objects.equals(height, other.height) && Objects.equals(immunities, other.immunities)
				&& Objects.equals(levelMoves, other.levelMoves) && Objects.equals(neutrals, other.neutrals)
				&& Objects.equals(otherMoves, other.otherMoves) && Objects.equals(pokeName, other.pokeName)
				&& Objects.equals(pokeTypes, other.pokeTypes) && Objects.equals(resistances, other.resistances)
				&& Objects.equals(tmMoves, other.tmMoves) && Objects.equals(totalMoves, other.totalMoves)
				&& Objects.equals(weaknesses, other.weaknesses) && Objects.equals(weight, other.weight);
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

	public String getWeaknesses() {
		return weaknesses;
	}

	public void setWeaknesses(String weaknesses) {
		this.weaknesses = weaknesses;
	}

	public String getNeutrals() {
		return neutrals;
	}

	public void setNeutrals(String neutrals) {
		this.neutrals = neutrals;
	}

	public String getResistances() {
		return resistances;
	}

	public void setResistances(String resistances) {
		this.resistances = resistances;
	}

	public String getImmunities() {
		return immunities;
	}

	public void setImmunities(String immunities) {
		this.immunities = immunities;
	}

	public String getEggGroups() {
		return eggGroups;
	}

	public void setEggGroups(String eggGroups) {
		this.eggGroups = eggGroups;
	}

	@Override
	public String toString() {
		return "Pokemon [pokeName=" + pokeName + ", pokeTypes=" + pokeTypes + ", classification=" + classification
				+ ", height=" + height + ", weight=" + weight + ", capRate=" + capRate + ", eggSteps=" + eggSteps
				+ ", abilities=" + abilities + ", evRewardAmt=" + evRewardAmt + ", evRewardAttr=" + evRewardAttr
				+ ", weaknesses=" + weaknesses + ", neutrals=" + neutrals + ", resistances=" + resistances
				+ ", immunities=" + immunities + ", eggGroups=" + eggGroups + ", levelMoves=" + levelMoves
				+ ", tmMoves=" + tmMoves + ", eggMoves=" + eggMoves + ", otherMoves=" + otherMoves + ", totalMoves="
				+ totalMoves + ", baseHP=" + baseHP + ", baseAtk=" + baseAtk + ", baseDef=" + baseDef + ", baseSpAtk="
				+ baseSpAtk + ", baseSpDef=" + baseSpDef + ", baseSpd=" + baseSpd + ", bst=" + bst + "]";
	}
    
	public static void dexFinder(Session session) throws IOException {
		 /* What we see here selects each entry in the current Serebii Pokedex and adds it to the list.
		 * Notably, the current outcome relies on a few quirks in Serebii's design for the Pokedex: 
		 * Every Pokedex entry is repeated, but only one instance of each dex entry is numbered, and outside of the Paldea dex, it's numbered with its National Dex entry.
		 * Inside the Paldea dex, they're numbered with their local dex number.
		 * Overall, then, there's at least one instance of every pokemon's dex entry that's prefaced with a number.
		 * 
		 * Now, we get into the alternate forms (Origin, Alolan, Galarian, Hisuian, Therian, etc, etc).
		 * Alternate forms are characterized by a few distinct properties - 
		 * Alternate forms may have a different type, a different classification, different weaknesses, different abilities, a different moveset, different stats, and/or different EVs.
		 * 
		*/
		
		Document palDex = Jsoup.connect("https://www.serebii.net/pokedex-sv/").get();
		Elements palDexElems = palDex.select("option");
		List<String> palDexData = palDexElems.eachText();
		ArrayList<String> palDexOverall = new ArrayList<String>();
		List<String> results = new ArrayList<String>();
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
		results.add(s); //The latter option may be more thorough, but keeping this one for now, because it should be *much* faster.
		
		//Pokemon dbSample = session.get(Pokemon.class, s);
		//String pokeName = dbSample.getPokeName();
		//results.add(pokeName);
	}
	//results.forEach(outcome -> System.out.println(outcome)); This prints the dex data, but isn't needed right now.
	dexFiller(session, results);
	}
	
	public static void dexFiller(Session session, List<String> results) throws IOException {
		//String validatorHQL = "FROM Pokemon";
		//List<Pokemon> results = session.createQuery(validatorHQL).list();
		for (String inputName : results) {
			/*if(!((inputName.equals("Meowth") || inputName.equals("Tauros") || inputName.equals("Raichu") || inputName.equals("Wooper") || inputName.equals("Giratina")))) {
				continue;
			}
			*/
			Pokemon localPoke = new Pokemon(inputName);
			Pokemon localPokeAlola = null; //Several Pokemon have Alolan regional forms.
			Pokemon localPokeGalar = null; //Several Pokemon have Galarian regional forms.
			Pokemon localPokeHisui = null; //Several Pokemon have Hisuian regional forms.
			Pokemon localPokePaldea = null; //A few Pokemon have Paldean-exclusive forms.
			Pokemon localPokeOther1 = null; //Several Pokemon have forms which are not region-specific, like the Creation Trio, the Genie Trio, Lycanroc, Indeedee, and Toxtricity.
			Pokemon localPokeOther2 = null; //Some Pokemon, including Tauros, have multiple forms that are not neatly subsumed under the above categories; most notably, Paldean Tauros has two variant breeds.
			ArrayList<Pokemon> localPokeList = new ArrayList<Pokemon>();
			
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
			String pokeName = localPoke.getPokeName(); //Working on this part to dynamically fill the dex!
			String urlPokeName = pokeName.toLowerCase().replace("%20", "").replace(" ", "").replace("%C3%A9", "e").replace("é", "e");
			//urlPokeName = "charmander";
			//System.out.println(urlPokeName);
			String URL = "https://www.serebii.net/pokedex-sv/" + urlPokeName + "/"; //Builds the URLs correctly!
			Document dexEntry = Jsoup.connect((URL)).get();
			/*
			 * Taking a moment to explain the strategy here: 
			 * The tables on each page in Serebii's records have a consistent pattern, but not every field exists for every entry.
			 * So if a table exists, it will appear after the tables before it in the pattern.
			 * I iterate 'i' over the list of tables on the page until I find the one I'm looking for (by checking the headers, which are uniform where extant).
			 * If I find the one I'm looking for, I update anchor to start from that table, process that table, and repeat the process for the next.
			 * If I do not find the one I'm looking for before I run out of tables, I exit the loop in an unconventional way and report an issue to the debug pane, then continue to try and process the others.
			*/
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
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Name")) {
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						int k = 0;
						for(Element col : cols) {
							System.out.println(k + col.text().toString());
							k++;
						}
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(subTitle.text());
						if(j == 0) {
						Element typeData = cols.select(".cen").get(0);
						if(!Objects.isNull(typeData)) {
							//System.out.println(typeData.html());
							System.out.println(typeData.text());
							if(typeData.text().contains("Normal")) {
								Elements typeTable = typeData.select("tr");
								for(Element types : typeTable) {
									/*
									 * Element typeCol = cols.get(cols.size() - 1);
						System.out.println("Type Column: " + typeCol.toString());
						Elements types = typeCol.select("a");
						String typeList = "";
						for(Element type : types) {
							String typeHref = type.attr("href");
							//System.out.println(typeHref);
							String storedType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
							storedType = storedType.substring(0, 1).toUpperCase() + storedType.substring(1) + ", ";
							typeList += storedType;
						}
						typeList = typeList.substring(0, (typeList.length() - 2)); //This may behave erratically for things that have conditional types like Megas or Regional Forms.
						System.out.println("Type: " + typeList);
						localPoke.setPokeTypes(typeList);
									 */
									System.out.println(types.text());
								}
								
								if(typeData.text().contains("Paldean")) {
									localPokePaldea = new Pokemon(localPoke.getPokeName());
									localPokePaldea.setPokeName("Paldean " + localPokePaldea.getPokeName());
									System.out.println(localPokePaldea.getPokeName());
								}
								if(typeData.text().contains("Hisuian")) {
									localPokeHisui = new Pokemon(localPoke.getPokeName());
									localPokeHisui.setPokeName("Hisuian " + localPokeHisui.getPokeName());
									System.out.println(localPokeHisui.getPokeName());
								}
								if(typeData.text().contains("Galarian")) {
									localPokeGalar = new Pokemon(localPoke.getPokeName());
									localPokeGalar.setPokeName("Galarian " + localPokeGalar.getPokeName());
									System.out.println(localPokeGalar.getPokeName());
								}
								if(typeData.text().contains("Alolan")) {
									localPokeAlola = new Pokemon(localPoke.getPokeName());
									localPokeAlola.setPokeName("Alolan " + localPokeAlola.getPokeName());
									System.out.println(localPokeAlola.getPokeName());
								}
							} else {
						Element typeCol = cols.get(cols.size() - 1);
						System.out.println("Type Column: " + typeCol.toString());
						Elements types = typeCol.select("a");
						String typeList = "";
						for(Element type : types) {
							String typeHref = type.attr("href");
							//System.out.println(typeHref);
							String storedType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
							storedType = storedType.substring(0, 1).toUpperCase() + storedType.substring(1) + ", ";
							typeList += storedType;
						}
						typeList = typeList.substring(0, (typeList.length() - 2)); //This may behave erratically for things that have conditional types like Megas or Regional Forms.
						System.out.println("Type: " + typeList);
						localPoke.setPokeTypes(typeList);
						}
						}
							if(subTitle.text().equals("Classification") ) {
							System.out.println("Classification sub-table");
							col = cols.get(0);
							String classification = col.text();
							System.out.println(classification);
							localPoke.setClassification(classification);
							
							col = cols.get(1);
							String height = col.text();
							height = height.substring(0, (height.indexOf("\"") + 1));
							System.out.println(height);
							localPoke.setHeight(height);
							
							col = cols.get(2);
							String weight = col.text();
							weight = weight.substring(0, weight.indexOf("lbs") + 2);
							weight = weight.replace("lb", " lbs");
							System.out.println(weight);
							localPoke.setWeight(weight);
							
							col = cols.get(3);
							double capRate = Double.parseDouble(col.text());
							System.out.println(capRate);
							localPoke.setCapRate(capRate);
							
							col = cols.get(4);
							String eggStepString = col.text().replaceAll(",", "");
							int eggSteps = Integer.parseInt(eggStepString);
							System.out.println(eggSteps);
							localPoke.setEggSteps(eggSteps);
					}
					
				}
				}
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().startsWith("Abilities:")) {
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						int k = 0;
						for(Element col : cols) {
							System.out.println(k + col.text().toString());
							k++;
						}
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(subTitle.text());
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().contains("Abilities:")) {
							Elements abilityElements = col.select("b");
							ArrayList<String> abilityList = new ArrayList<String>();
							boolean hiddenNext = false;
							for(Element abilityElem : abilityElements) {
								if(abilityElem.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityList.add(abilityElem.text());
								} else if(hiddenNext) {
									abilityList.add("(H) " + abilityElem.text());
									hiddenNext = false;
								}
							}
							String abilities = abilityList.toString();
							abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
							localPoke.setAbilities(abilities);
						}
							
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Experience Growth")) {
							col = cols.get(cols.size() - 2);
							System.out.println("EV Column: " + col.toString());
							String evString = col.text();
							System.out.println("EV String: " + evString);
							int evAmt = 0;
							if(evString.equals("")) {
								evString = "";
								localPoke.setEvRewardAmt(evAmt);
								localPoke.setEvRewardAttr(evString);
							} else {
								if(pokeName.equals("Indeedee")) {
									localPoke.setEvRewardAmt(2);
									localPoke.setEvRewardAttr("Sp. Attack [M]; Sp. Defense [F]");
									continue;
								}
								if(evString.contains("Alolan") || evString.contains("Galarian") || evString.contains("Hisuian")) {
									System.out.println("SPECIAL CASE");
								}
							evAmt = Integer.parseInt(String.valueOf(evString.charAt(0)));
							System.out.println(evString);
							evString = evString.substring(2);
							int garbageIndex = evString.indexOf("Point") - 1;
							evString = evString.substring(0, garbageIndex);
							localPoke.setEvRewardAmt(evAmt);
							localPoke.setEvRewardAttr(evString);
							}
							System.out.println(evAmt + " " + evString);
						}
					}
				}
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Weakness")) {
					System.out.println("WEAKNESS TABLE");
					Element typeRow = dexTableRows.select("tr").get((1));
					Elements typeCols = typeRow.select("td");
					Element weakRow = dexTableRows.select("tr").get(2);
					Elements weakCols = weakRow.select("td");
					String weaknesses = "";
					String neutrals = "";
					String immunities = "";
					String resistances = "";
						
					for (int k = 0; k < typeCols.size(); k++) {
						String rawType = typeCols.get(k).select("a").attr("href");
						String typeName = rawType.substring(rawType.indexOf("sv/") + 3, rawType.indexOf(".shtml"));
						typeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1);
						if(typeName.equals("Psychict")) {
							typeName = "Psychic";
						}
						String weakLine = weakCols.get(k).text().substring(1);
						if(weakLine.equals("2") || weakLine.equals("4")){
							weaknesses += typeName + ", ";
						}
						if(weakLine.equals("0.5") || weakLine.equals("0.25")) {
							resistances += typeName + ", ";
						}
						if(weakLine.equals("0")) {
							immunities += typeName + ", ";
						}
						if(weakLine.equals("1")) {
							neutrals += typeName + ", ";
						}
						System.out.println(typeName + ": " + weakCols.get(k).text().substring(1));
					}
					if(weaknesses.length() > 0) {
					weaknesses = weaknesses.substring(0, (weaknesses.length() - 2));
					} else {
						weaknesses = "None";
					}
					if(resistances.length() > 0) {
					resistances = resistances.substring(0, (resistances.length() - 2));
					} else {
						resistances = "None";
					}
					if(immunities.length() > 0) {
					immunities = immunities.substring(0, (immunities.length() - 2));
					} else {
						immunities = "None";
					}
					if(neutrals.length() > 0) {
					neutrals = neutrals.substring(0, (neutrals.length() - 2));
					} else {
						neutrals = "None";
					}
					System.out.println("Weaknesses: " + weaknesses);
					System.out.println("Resistances: " + resistances);
					System.out.println("Immunities: " + immunities);
					System.out.println("Neutral Types: " + neutrals);
					localPoke.setWeaknesses(weaknesses);
					localPoke.setResistances(resistances);
					localPoke.setImmunities(immunities);
					localPoke.setNeutrals(neutrals);
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Wild Hold Item")) {
					System.out.println("Wild Hold Item Table");
					row = dexTableRows.select("tr").get(1);
					Elements cols = row.select("td");
					System.out.println("Wild Hold Item(s): " + cols.get(0).text());
					Element innerTable = row.selectFirst("table");
					if(Objects.isNull(innerTable)) {
						String eggGroupString = localPoke.getPokeName() + " cannot breed";
						localPoke.setEggGroups(eggGroupString);
						continue;
					}
					Elements eggGroups = innerTable.select("a");
					String eggGroupString = "";
					for(Element eggGroup : eggGroups) {
						eggGroupString += eggGroup.text() + ", ";
						//String eggGroupHref = eggGroup.attr("href");
						//String storedGroup = eggGroupHref.substring((eggGroupHref.indexOf("egg/") + 4), eggGroupHref.indexOf(".shtml"));
						//storedGroup = storedGroup.substring(0, 1).toUpperCase() + storedGroup.substring(1) + ", ";
						//eggGroupString += storedGroup;
					}
					eggGroupString = eggGroupString.substring(0, (eggGroupString.length() - 2));
					localPoke.setEggGroups(eggGroupString);
					System.out.println("Egg Groups: " + eggGroupString);
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
					while(levelMoves.contains(",,")) {
						levelMoves = levelMoves.replace(",,", ",");
					}
					while(levelMoves.startsWith(" ,")) {
						levelMoves = levelMoves.substring(2);
					}
					if(levelMoves.endsWith(",")) {
						levelMoves = levelMoves.substring(0, levelMoves.length() - 1);
					}
					System.out.println(levelMoves);
					overallMoves += levelMoves + ",";
					localPoke.setLevelMoves(levelMoves);
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
					while(tmMoves.contains(",,")) {
						tmMoves = tmMoves.replace(",,", ",");
					}
					while(tmMoves.startsWith(" ,")) {
						tmMoves = tmMoves.substring(2);
					}
					if(tmMoves.endsWith(",")) {
						tmMoves = tmMoves.substring(0, tmMoves.length() - 1);
					}
					System.out.println(tmMoves);
					localPoke.setTmMoves(tmMoves);
					overallMoves = overallMoves + "," + tmMoves;
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Egg Moves (Details)")) {
					//System.out.println("Egg Moves Table"); DEBUG USE ONLY.
					Elements rows = dexTable.select("tr");
					//System.out.println(rows.get(2).text()); DEBUG USE ONLY.
					eggMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						Elements localCol = localRow.select("td");
						if(!(localRow.text().equals("Egg Moves Table") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %") || localCol.size() < 3)) { //These are header rows, which are out of scope.
								eggMoves += localCol.get(0).text() + ",";
							} else{
								continue;
							}
						}
					eggMoves = eggMoves.substring(0, eggMoves.length() - 1);
					while(eggMoves.contains(",,")) {
						eggMoves = eggMoves.replace(",,", ",");
					}
					while(eggMoves.startsWith(" ,")) {
						eggMoves = eggMoves.substring(2);
					}
					if(eggMoves.endsWith(",")) {
						eggMoves = eggMoves.substring(0, eggMoves.length() - 1);
					}
					System.out.println(eggMoves);
					localPoke.setEggMoves(eggMoves);
					overallMoves = overallMoves + "," + eggMoves;
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().contains("Move") && !(titleCol.text().contains("Egg Moves"))) {
					System.out.println("Other Moves Table");
					Elements rows = dexTable.select("tr");
					System.out.println(rows.get(0).text());
					otherMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
						if(localRow.text().equals("Pre-Evolution Only Moves") || localRow.text().equals("Move Reminder Only Attacks") || localRow.text().equals("Special Moves") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %") || localCol.size() < 3) { //These are header rows, which are out of scope.
							continue;
						}
							else {
								otherMoves += localCol.get(0).text() + ",";
							}
						}
					otherMoves = otherMoves.substring(0, otherMoves.length() - 1);
					while(otherMoves.contains(",,")) {
						otherMoves = otherMoves.replace(",,", ",");
					}
					while(otherMoves.startsWith(" ,")) {
						otherMoves = otherMoves.substring(2);
					}
					if(otherMoves.endsWith(",")) {
						otherMoves = otherMoves.substring(0, otherMoves.length() - 1);
					}
					System.out.println(otherMoves);
					localPoke.setOtherMoves(otherMoves);
					overallMoves = overallMoves + "," + otherMoves;
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
					localPoke.setBaseHP(baseHP);
					localPoke.setBaseAtk(baseAtk);
					localPoke.setBaseDef(baseDef);
					localPoke.setBaseSpAtk(baseSpAtk);
					localPoke.setBaseSpDef(baseSpDef);
					localPoke.setBaseSpd(baseSpd);
					localPoke.setBst(bst);
				
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
				if(Objects.isNull(eggMoves) || eggMoves.isBlank() || eggMoves.equals("")) {
					eggMoves = "None";
					localPoke.setEggMoves(eggMoves);
				}
				if(Objects.isNull(otherMoves) || otherMoves.isBlank()) {
					otherMoves = "None";
					localPoke.setOtherMoves(otherMoves);
				}
				localPoke.setTotalMoves(overallMoveset.toString().trim());
			//localPoke.setotherMoves(otherMoves);
				System.out.println(localPoke.getTotalMoves());
			/*
			Pokemon dbSample = session.get(Pokemon.class, poke.getPokeName());
			 
			if (!(Objects.isNull(dbSample)) && dbSample.getPokeName().equals(poke.getPokeName()) && dbSample.equals(poke)) {
				System.out.println("Nothing to do here.");
				continue;
				}
			*/
			/*
			session.beginTransaction();
			session.merge(localPoke);
			session.getTransaction().commit();
			*/
			localPokeList.add(localPoke);
			localPokeList.add(localPokeAlola);
			localPokeList.add(localPokeGalar);
			localPokeList.add(localPokeHisui);
			localPokeList.add(localPokePaldea);
			localPokeList.add(localPokeOther1);
			localPokeList.add(localPokeOther2);
			for(Pokemon poke : localPokeList) {
				if(!(Objects.isNull(poke))) {
			Pokemon dbSample = session.get(Pokemon.class, poke.getPokeName());
			if(Objects.isNull(dbSample)) {
				System.out.println("There is a new database entry!");
				session.beginTransaction();
				session.persist(poke);
				session.getTransaction().commit();
				continue;
			} else if (!(dbSample.toString().equals(poke.toString()))){
				System.out.println("There is nothing to do.");
				continue;
				} else {
					session.beginTransaction();
					session.merge(poke);
					session.getTransaction().commit();
					continue;
				}
		}
		}
		}
			
			/*
			 * How do we want to handle the move lists?
			 * Current implementation calls for moves stored as Strings (for their names). Probably best to do this.
			 * Best to one-to-many relation moves to the pokemon who can learn them.
			 * 
			 * 
			 */
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


/*
 * Thinking out loud - What are the major limitations of this program at present?
 * The program handles convergent species fine, because they're essentially different in the ways the database scans for.
 * The program does *not* handle Pokemon well when they have substantial differences (subspecies like Alolan/Galarian/Hisuian forms, certain legendaries like the Creation Trio, the Genies, Meloetta, Hoopa, Calyrex, Squawkabilly, Indeedee, and possibly others.
 * In the absence of subspecies, the code provided *MOSTLY* works well for processing Serebii's tables into a database.
 * Formatting on the movelist tables is screwy for reasons I don't entirely grasp as well, but it's probably an easy fix. 
 * 
 * More Thorough Analysis - 
 * Note - Meowth and Calyrex have no classification.
 * Charmeleon's Egg Moves have one that isn't separated from the rest by a comma as expected. Is this the only column where this issue happens? Unsure, but Sunflora and Slowking have this issue in Egg Moves, too.
 * Definitely not the only column, the Legendary Birds have this issue in the TM section.
 * 
 * The moves table needs extensive reformatting.
*/
