package hibernate;

import java.util.Objects;

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

	public Ability() {

    }
    
    public Ability(String abilityName) {
    	
    }
    public Ability(String abilityName, String abilityGameText, String inDepthAbilityEffect, String overworldEffect) {
    	
    }
    
}
