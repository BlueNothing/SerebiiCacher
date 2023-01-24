package hibernate.move;

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
@Table(name = "attacks")
public class Move {
	@Id
	@Column(name="name", length=10000)
	private String name = "None";
	
	@Column(name="movetype") 
	String moveType;
	
	@Column(name="movecategory")
	public String moveCategory;
	
	@Column(name="movepp")
	private Integer movePP;
	
	@Column(name="movebasepower")
	private Integer moveBasePower;
	
	@Column(name="moveacc")
	private Integer moveAcc;
	
	@Column(name="battleeffect", length = 10000)
	private String battleEffect;
	
	@Column(name="indeptheffect", length = 10000)
	private String inDepthEffect;
	
	@Column(name="secondaryeffect", length = 10000)
	private String secondaryEffect;
	
	@Column(name="effectrate")
	private Double effectRate;
	
	@Column(name="movebasecrit")
	private Double moveBaseCrit;
	
	@Column(name="movepriority")
	private Integer movePriority;
	
	@Column(name="movetargets")
	private String moveTargets;
	
	@Column(name="iscontact")
	private Boolean isContact;
	
	@Column(name="issound")
	private Boolean isSound;
	
	@Column(name="ispunch")
	private Boolean isPunch;
	
	@Column(name="isbite")
	private Boolean isBite;
	
	@Column(name="issnatchable")
	private Boolean isSnatchable;
	
	@Column(name="isslice")
	private Boolean isSlice;
	
	@Column(name="isbullet")
	private Boolean isBullet;
	
	@Column(name="iswind")
	private Boolean isWind;
	
	@Column(name="ispowder")
	private Boolean isPowder;
	
	@Column(name="ismetronomable")
	private Boolean isMetronomable;
	
	@Column(name="isgravityaffected")
	private Boolean isGravityAffected;
	
	@Column(name="isdefrosting")
	private Boolean isDefrosting;
	
	@Column(name="isreflectable")
	private Boolean isReflectable;
	
	@Column(name="isblockable")
	private Boolean isBlockable;
	
	@Column(name="iscopyable")
	private Boolean isCopyable;
	
	@Column(name="isdeprecated")
	private Boolean isDeprecated;
	
	@Column(name="learnset", length = 10000)
	private String learnset;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getMoveCategory() {
		return moveCategory;
	}

	public void setMoveCategory(String moveCategory) {
		this.moveCategory = moveCategory;
	}

	public Integer getMovePP() {
		return movePP;
	}

	public void setMovePP(Integer movePP) {
		this.movePP = movePP;
	}

	public Integer getMoveBasePower() {
		return moveBasePower;
	}

	public void setMoveBasePower(Integer moveBasePower) {
		this.moveBasePower = moveBasePower;
	}

	public Integer getMoveAcc() {
		return moveAcc;
	}

	public void setMoveAcc(Integer moveAcc) {
		this.moveAcc = moveAcc;
	}

	public String getBattleEffect() {
		return battleEffect;
	}

	public void setBattleEffect(String battleEffect) {
		this.battleEffect = battleEffect;
	}

	public String getInDepthEffect() {
		return inDepthEffect;
	}

	public void setInDepthEffect(String inDepthEffect) {
		this.inDepthEffect = inDepthEffect;
	}

	public String getSecondaryEffect() {
		return secondaryEffect;
	}

	public void setSecondaryEffect(String secondaryEffect) {
		this.secondaryEffect = secondaryEffect;
	}

	public Double getEffectRate() {
		return effectRate;
	}

	public void setEffectRate(Double effectRate) {
		this.effectRate = effectRate;
	}

	public Double getMoveBaseCrit() {
		return moveBaseCrit;
	}

	public void setMoveBaseCrit(Double moveBaseCrit) {
		this.moveBaseCrit = moveBaseCrit;
	}

	public Integer getMovePriority() {
		return movePriority;
	}

	public void setMovePriority(Integer movePriority) {
		this.movePriority = movePriority;
	}

	public String getMoveTargets() {
		return moveTargets;
	}

	public void setMoveTargets(String moveTargets) {
		this.moveTargets = moveTargets;
	}

	public Boolean getIsContact() {
		return isContact;
	}

	public void setIsContact(Boolean isContact) {
		this.isContact = isContact;
	}

	public Boolean getIsSound() {
		return isSound;
	}

	public void setIsSound(Boolean isSound) {
		this.isSound = isSound;
	}

	public Boolean getIsPunch() {
		return isPunch;
	}

	public void setIsPunch(Boolean isPunch) {
		this.isPunch = isPunch;
	}

	public Boolean getIsBite() {
		return isBite;
	}

	public void setIsBite(Boolean isBite) {
		this.isBite = isBite;
	}

	public Boolean getIsSnatchable() {
		return isSnatchable;
	}

	public void setIsSnatchable(Boolean isSnatchable) {
		this.isSnatchable = isSnatchable;
	}

	public Boolean getIsSlice() {
		return isSlice;
	}

	public void setIsSlice(Boolean isSlice) {
		this.isSlice = isSlice;
	}

	public Boolean getIsBullet() {
		return isBullet;
	}

	public void setIsBullet(Boolean isBullet) {
		this.isBullet = isBullet;
	}

	public Boolean getIsWind() {
		return isWind;
	}

	public void setIsWind(Boolean isWind) {
		this.isWind = isWind;
	}

	public Boolean getIsPowder() {
		return isPowder;
	}

	public void setIsPowder(Boolean isPowder) {
		this.isPowder = isPowder;
	}

	public Boolean getIsMetronomable() {
		return isMetronomable;
	}

	public void setIsMetronomable(Boolean isMetronomable) {
		this.isMetronomable = isMetronomable;
	}

	public Boolean getIsGravityAffected() {
		return isGravityAffected;
	}

	public void setIsGravityAffected(Boolean isGravityAffected) {
		this.isGravityAffected = isGravityAffected;
	}

	public Boolean getIsDefrosting() {
		return isDefrosting;
	}

	public void setIsDefrosting(Boolean isDefrosting) {
		this.isDefrosting = isDefrosting;
	}

	public Boolean getIsReflectable() {
		return isReflectable;
	}

	public void setIsReflectable(Boolean isReflectable) {
		this.isReflectable = isReflectable;
	}

	public Boolean getIsBlockable() {
		return isBlockable;
	}

	public void setIsBlockable(Boolean isBlockable) {
		this.isBlockable = isBlockable;
	}

	public Boolean getIsCopyable() {
		return isCopyable;
	}

	public void setIsCopyable(Boolean isCopyable) {
		this.isCopyable = isCopyable;
	}

	public Boolean getIsDeprecated() {
		return isDeprecated;
	}

	public void setIsDeprecated(Boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}

	public String getLearnset() {
		return learnset;
	}

	public void setLearnset(String learnset) {
		this.learnset = learnset;
	}

	@Override
	public int hashCode() {
		return Objects.hash(battleEffect, effectRate, inDepthEffect, isBite, isBlockable, isBullet, isContact,
				isCopyable, isDefrosting, isDeprecated, isGravityAffected, isMetronomable, isPowder, isPunch,
				isReflectable, isSlice, isSnatchable, isSound, isWind, learnset, moveAcc, moveBaseCrit, moveBasePower,
				moveCategory, movePP, movePriority, moveTargets, moveType, name, secondaryEffect);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		return Objects.equals(battleEffect, other.battleEffect) && Objects.equals(effectRate, other.effectRate)
				&& Objects.equals(inDepthEffect, other.inDepthEffect) && Objects.equals(isBite, other.isBite)
				&& Objects.equals(isBlockable, other.isBlockable) && Objects.equals(isBullet, other.isBullet)
				&& Objects.equals(isContact, other.isContact) && Objects.equals(isCopyable, other.isCopyable)
				&& Objects.equals(isDefrosting, other.isDefrosting) && Objects.equals(isDeprecated, other.isDeprecated)
				&& Objects.equals(isGravityAffected, other.isGravityAffected)
				&& Objects.equals(isMetronomable, other.isMetronomable) && Objects.equals(isPowder, other.isPowder)
				&& Objects.equals(isPunch, other.isPunch) && Objects.equals(isReflectable, other.isReflectable)
				&& Objects.equals(isSlice, other.isSlice) && Objects.equals(isSnatchable, other.isSnatchable)
				&& Objects.equals(isSound, other.isSound) && Objects.equals(isWind, other.isWind)
				&& Objects.equals(learnset, other.learnset) && Objects.equals(moveAcc, other.moveAcc)
				&& Objects.equals(moveBaseCrit, other.moveBaseCrit)
				&& Objects.equals(moveBasePower, other.moveBasePower)
				&& Objects.equals(moveCategory, other.moveCategory) && Objects.equals(movePP, other.movePP)
				&& Objects.equals(movePriority, other.movePriority) && Objects.equals(moveTargets, other.moveTargets)
				&& Objects.equals(moveType, other.moveType) && Objects.equals(name, other.name)
				&& Objects.equals(secondaryEffect, other.secondaryEffect);
	}

	@Override
	public String toString() {
		return "Move [name=" + name + ", moveType=" + moveType + ", moveCategory=" + moveCategory + ", movePP=" + movePP
				+ ", moveBasePower=" + moveBasePower + ", moveAcc=" + moveAcc + ", battleEffect=" + battleEffect
				+ ", inDepthEffect=" + inDepthEffect + ", secondaryEffect=" + secondaryEffect + ", effectRate="
				+ effectRate + ", moveBaseCrit=" + moveBaseCrit + ", movePriority=" + movePriority + ", moveTargets="
				+ moveTargets + ", isContact=" + isContact + ", isSound=" + isSound + ", isPunch=" + isPunch
				+ ", isBite=" + isBite + ", isSnatchable=" + isSnatchable + ", isSlice=" + isSlice + ", isBullet="
				+ isBullet + ", isWind=" + isWind + ", isPowder=" + isPowder + ", isMetronomable=" + isMetronomable
				+ ", isGravityAffected=" + isGravityAffected + ", isDefrosting=" + isDefrosting + ", isReflectable="
				+ isReflectable + ", isBlockable=" + isBlockable + ", isCopyable=" + isCopyable + ", isDeprecated="
				+ isDeprecated + ", learnset=" + learnset + "]";
	}

	public Move() {
		super();
		String name = "";
		String battleEffect = "";
		boolean isBite = false, isBlockable = false, isBullet = false, isContact = false, isCopyable = false, isDeprecated = false;
		boolean isGravityAffected = false, isMetronomable = false, isPowder = false, isPunch = false, isReflectable = false, isSlice = false;
		boolean isSnatchable = false, isDefrosting = false, isSound = false, isWind = false;
		String learnset = "";
		int moveAcc = 0;
		double moveBaseCrit = 0;
		int moveBasePower = 0;
		String moveCategory = "";
		int movePP = 0;
		int movePriority = 0;
		double effectRate = 0;
		String moveTargets = "";
		String moveType = "";
		String secondaryEffect = "";
		String inDepthEffect = "";
	}

	public Move(String name) {
		super();
		this.name = name;
		String battleEffect = "";
		Boolean isBite = false, isBlockable = false, isBullet = false, isContact = false, isCopyable = false, isDeprecated = false;
		Boolean isGravityAffected = false, isMetronomable = false, isPowder = false, isPunch = false, isReflectable = false, isSlice = false;
		Boolean isSnatchable = false, isDefrosting = false, isSound = false, isWind = false;
		String learnset = "";
		Integer moveAcc = 0;
		Double moveBaseCrit = 0.0;
		Integer moveBasePower = 0;
		String moveCategory = "";
		Integer movePP = 0;
		Integer movePriority = 0;
		Double effectRate = 0.0;
		String moveTargets = "";
		String moveType = "";
		String secondaryEffect = "";
		String inDepthEffect = "";
	}
}