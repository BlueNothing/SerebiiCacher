package hibernate;

import java.util.Objects;

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
    private String unknownB;
    
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
     * Evolutions should provide a reference link to their evolved forms.
     * This is out of scope for now.
     */
    
    /*
     * Maybe best not to worry about alt forms for now?
     * Regional forms will get recorded as their own Pokemon, but alt forms rarely get different movesets or abilities.
     * Rockruff will be an odd one for record purposes, though.
     */
    
    /*
     * Locations are interesting data, but out of scope for initial implementation.
     */
    
    /*
     * Flavor text for the Dex entries is out of scope for now.
     */
    
    @Column(name="levelMoves")
    private String levelMoves;
    
    @Column(name="tmMoves")
    private String tmMoves;
    
    @Column(name="eggMoves")
    private String eggMoves;
    
    @Column(name="otherMoves")
    private String otherMoves;
    
    @Column(name="totalMoves")
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
		return Objects.hash(abilities, baseAtk, baseDef, baseHP, baseSpAtk, baseSpDef, baseSpd, bst, eggMoves,
				levelMoves, pokeName, pokeTypes, otherMoves, tmMoves, totalMoves);
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
				&& baseSpd == other.baseSpd && bst == other.bst && Objects.equals(eggMoves, other.eggMoves)
				&& Objects.equals(levelMoves, other.levelMoves) && Objects.equals(pokeName, other.pokeName)
				&& Objects.equals(pokeTypes, other.pokeTypes) && Objects.equals(otherMoves, other.otherMoves)
				&& Objects.equals(tmMoves, other.tmMoves) && Objects.equals(totalMoves, other.totalMoves);
	}

	public Pokemon() {

    }
    
    public Pokemon(String pokeName) {
    	this.pokeName = pokeName;
    }

	
    public Pokemon(String pokeName, String pokeTypes, String unknownB, String height, String weight, double capRate,
			int eggSteps, String abilities, int evRewardAmt, String evRewardAttr, String levelMoves, String tmMoves,
			String otherMoves, String eggMoves, String totalMoves, int baseHP, int baseAtk, int baseDef,
			int baseSpAtk, int baseSpDef, int baseSpd, int bst) {
		super();
		this.pokeName = pokeName;
		this.pokeTypes = pokeTypes;
		this.unknownB = unknownB;
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

	@Override
	public String toString() {
		return "Pokemon [pokeName=" + pokeName + ", pokeTypes=" + pokeTypes + ", unknownB=" + unknownB + ", height="
				+ height + ", weight=" + weight + ", capRate=" + capRate + ", eggSteps=" + eggSteps + ", abilities="
				+ abilities + ", evRewardAmt=" + evRewardAmt + ", evRewardAttr=" + evRewardAttr + ", levelMoves="
				+ levelMoves + ", tmMoves=" + tmMoves + ", otherMoves=" + otherMoves + ", eggMoves=" + eggMoves
				+ ", totalMoves=" + totalMoves + ", baseHP=" + baseHP + ", baseAtk=" + baseAtk + ", baseDef=" + baseDef
				+ ", baseSpAtk=" + baseSpAtk + ", baseSpDef=" + baseSpDef + ", baseSpd=" + baseSpd + ", bst=" + bst
				+ "]";
	}
    
    


}
