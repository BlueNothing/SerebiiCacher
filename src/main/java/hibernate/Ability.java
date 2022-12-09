package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "abilities")
public class Ability {
	/*
	 * NAME, GAME TEXT, IN-DEPTH EFFECT;
	 */

	@Id
    @Column(name = "name")
    private String abilityName;

    @Column(name = "gameText")
    private String abilityGameText;
    
    @Column(name = "inDepthAbilityEffect")
    private String inDepthAbilityEffect;
    
    @Column(name="overworldEffect")
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

	public Ability() {

    }
    
    public Ability(String abilityName) {
    	
    }
    public Ability(String abilityName, String abilityGameText, String inDepthAbilityEffect, String overworldEffect) {
    	
    }
    
}
