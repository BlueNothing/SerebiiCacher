package serebiiCacher.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pokemon")
public class Pokemon {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String pokeName;

    @Column(name = "types")
    private String pokeTypes;

    /*
    @Column(name = "classification")
    private String unknownB;
	*/
    
    /*
    @Column(name = "height")
    private String height;
    */
    
    /*
    @Column(name = "weight")
    private String weight;
    */
    
    /*
    @Column(name = "capRate")
    private double capRate;
    */
    
    /*
     @Column(name="eggSteps")
     private int eggSteps;
     */
    
    @Column(name="abilities")
    private String abilities;
    
    /*
     @Column(name="evRewardAmt")
     private int evRewardAmt
     
     @Column(name="evRewardAttr")
     private String evRewardAttr
     */
    
    /*
     * Not sure how I want to implement recording weaknesses, if at all.
     */
    
    /* Let's ignore this info for right now.
    @Column(name="eggGroups")
    private String eggGroups;
    */
    
    /*
     * Evolutions should provide a reference link to their evolved forms.
     */
    
    /*
     * Maybe best not to worry about alt forms for now?
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
    
    @Column(name="reminderMoves")
    private String reminderMoves;
    
    @Column(name="eggMoves")
    private String eggMoves;
    
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
    
    public Pokemon() {

    }

	public Pokemon(int id, String pokeName, String pokeTypes, String abilities, String levelMoves, String tmMoves,
			String reminderMoves, String eggMoves, String totalMoves, int baseHP, int baseAtk, int baseDef,
			int baseSpAtk, int baseSpDef, int baseSpd) {
		super();
		this.id = id;
		this.pokeName = pokeName;
		this.pokeTypes = pokeTypes;
		this.abilities = abilities;
		this.levelMoves = levelMoves;
		this.tmMoves = tmMoves;
		this.reminderMoves = reminderMoves;
		this.eggMoves = eggMoves;
		this.totalMoves = totalMoves;
		this.baseHP = baseHP;
		this.baseAtk = baseAtk;
		this.baseDef = baseDef;
		this.baseSpAtk = baseSpAtk;
		this.baseSpDef = baseSpDef;
		this.baseSpd = baseSpd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getReminderMoves() {
		return reminderMoves;
	}

	public void setReminderMoves(String reminderMoves) {
		this.reminderMoves = reminderMoves;
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
    
    


}
