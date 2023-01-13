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
@Table(name = "pokemon")
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
    private Double capRate;
    
     @Column(name="eggSteps")
     private Integer eggSteps;
    
    @Column(name="abilities")
    private String abilities;
    
     @Column(name="evRewardAmt")
     private Integer evRewardAmt;
     
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
    private Integer baseHP;
    
    @Column(name="baseAtk")
    private Integer baseAtk;
    
    @Column(name="baseDef")
    private Integer baseDef;
    
    @Column(name="baseSpAtk")
    private Integer baseSpAtk;
    
    @Column(name="baseSpDef")
    private Integer baseSpDef;
    
    @Column(name="baseSpd")
    private Integer baseSpd;
    
    @Column(name="bst")
    private Integer bst;
    
    
    
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
		this.capRate = 0.0;
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
		this.capRate = 0.0;
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

	
    public Pokemon(String pokeName, String pokeTypes, String classification, String height, String weight, Double capRate,
			Integer eggSteps, String abilities, Integer evRewardAmt, String evRewardAttr, String levelMoves, String tmMoves,
			String otherMoves, String eggMoves, String totalMoves, Integer baseHP, Integer baseAtk, Integer baseDef,
			Integer baseSpAtk, Integer baseSpDef, Integer baseSpd, Integer bst) {
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

	public Integer getBaseHP() {
		return baseHP;
	}

	public void setBaseHP(Integer baseHP) {
		this.baseHP = baseHP;
	}

	public Integer getBaseAtk() {
		return baseAtk;
	}

	public void setBaseAtk(Integer baseAtk) {
		this.baseAtk = baseAtk;
	}

	public Integer getBaseDef() {
		return baseDef;
	}

	public void setBaseDef(Integer baseDef) {
		this.baseDef = baseDef;
	}

	public Integer getBaseSpAtk() {
		return baseSpAtk;
	}

	public void setBaseSpAtk(Integer baseSpAtk) {
		this.baseSpAtk = baseSpAtk;
	}

	public Integer getBaseSpDef() {
		return baseSpDef;
	}

	public void setBaseSpDef(Integer baseSpDef) {
		this.baseSpDef = baseSpDef;
	}

	public Integer getBaseSpd() {
		return baseSpd;
	}

	public void setBaseSpd(Integer baseSpd) {
		this.baseSpd = baseSpd;
	}

	public Integer getBst() {
		return bst;
	}

	public void setBst(Integer bst) {
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

	public Double getCapRate() {
		return capRate;
	}

	public void setCapRate(Double capRate) {
		this.capRate = capRate;
	}

	public Integer getEggSteps() {
		return eggSteps;
	}

	public void setEggSteps(Integer eggSteps) {
		this.eggSteps = eggSteps;
	}

	public Integer getEvRewardAmt() {
		return evRewardAmt;
	}

	public void setEvRewardAmt(Integer evRewardAmt) {
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
    
	public static void natDexFinder() throws IOException {
		Document natDex = Jsoup.connect("https://www.serebii.net/pokemon/nationalpokedex.shtml").get();
		Element natDexTable = natDex.select("table.dextable").get(0);
		Elements rows = natDexTable.select("tr");
		ArrayList<String> natDexOverall = new ArrayList<String>();
		for(Integer i = 2; i < 5/*rows.size()*/; i++) {
			Element row = rows.get(i);
			Elements cols = row.select("td");
			System.out.println(cols.text().toString());
			for(Integer j = 0; j < cols.size(); j++) {
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
