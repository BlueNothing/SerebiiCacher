package hibernate.ability;

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
    @Column(name = "name", length=255)
    private String name = "None";

    @Column(name = "gameText", length = 1020)
    private String abilityGameText;
    
    @Column(name = "inDepthAbilityEffect", length = 1020)
    private String inDepthAbilityEffect = "None";
    
    @Column(name="overworldEffect", length = 1020)
    private String overworldEffect = "None";
    
    @Column(name="affectedMoves", length = 3000)
    private String affectedMoves = "None";
    
    @Column(name="accessSet", length = 1020)
    private String accessSet;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "Ability [name=" + name + ", abilityGameText=" + abilityGameText
				+ ", inDepthAbilityEffect=" + inDepthAbilityEffect + ", overworldEffect=" + overworldEffect
				+ ", affectedMoves=" + affectedMoves + ", accessSet=" + accessSet + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(abilityGameText, name, accessSet, affectedMoves, inDepthAbilityEffect,
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
		return Objects.equals(abilityGameText, other.abilityGameText) && Objects.equals(name, other.name)
				&& Objects.equals(accessSet, other.accessSet) && Objects.equals(affectedMoves, other.affectedMoves)
				&& Objects.equals(inDepthAbilityEffect, other.inDepthAbilityEffect)
				&& Objects.equals(overworldEffect, other.overworldEffect);
	}

	public Ability() {
		this.name = "None";
    	this.abilityGameText = "None";
    	this.inDepthAbilityEffect = "None";
    	this.overworldEffect = "None";
    }
    
    public Ability(String name) {
    	this.name = name;
    	this.abilityGameText = "None";
    	this.inDepthAbilityEffect = "None";
    	this.overworldEffect = "None";
    }

	public Ability(String name, String abilityGameText, String inDepthAbilityEffect, String overworldEffect,
			String affectedMoves, String accessSet) {
		super();
		this.name = name;
		this.abilityGameText = abilityGameText;
		this.inDepthAbilityEffect = inDepthAbilityEffect;
		this.overworldEffect = overworldEffect;
		this.affectedMoves = affectedMoves;
		this.accessSet = accessSet;
	}
   
    
}
