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
@Table(name = "moves")
public class Move {
	@Id
	@Column(name="name", length = 10000)
	private String moveName;
	
	@Column(name="movetype") 
	String moveType;
	
	@Column(name="movecategory") 
	String moveCategory;
	
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

	public String getMoveName() {
		return moveName;
	}

	public void setMoveName(String moveName) {
		this.moveName = moveName;
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

	public Boolean isContact() {
		return isContact;
	}

	public void setContact(Boolean isContact) {
		this.isContact = isContact;
	}

	public Boolean isSound() {
		return isSound;
	}

	public void setSound(Boolean isSound) {
		this.isSound = isSound;
	}

	public Boolean isPunch() {
		return isPunch;
	}

	public void setPunch(Boolean isPunch) {
		this.isPunch = isPunch;
	}

	public Boolean isBite() {
		return isBite;
	}

	public void setBite(Boolean isBite) {
		this.isBite = isBite;
	}

	public Boolean isSnatchable() {
		return isSnatchable;
	}

	public void setSnatchable(Boolean isSnatchable) {
		this.isSnatchable = isSnatchable;
	}

	public Boolean isSlice() {
		return isSlice;
	}

	public void setSlice(Boolean isSlice) {
		this.isSlice = isSlice;
	}

	public Boolean isBullet() {
		return isBullet;
	}

	public void setBullet(Boolean isBullet) {
		this.isBullet = isBullet;
	}

	public Boolean isWind() {
		return isWind;
	}

	public void setWind(Boolean isWind) {
		this.isWind = isWind;
	}

	public Boolean isPowder() {
		return isPowder;
	}

	public void setPowder(Boolean isPowder) {
		this.isPowder = isPowder;
	}

	public Boolean isMetronomable() {
		return isMetronomable;
	}

	public void setMetronomable(Boolean isMetronomable) {
		this.isMetronomable = isMetronomable;
	}

	public Boolean isGravityAffected() {
		return isGravityAffected;
	}

	public void setGravityAffected(Boolean isGravityAffected) {
		this.isGravityAffected = isGravityAffected;
	}
	
	public Boolean isDefrosting() {
		return isDefrosting;
	}

	public void setDefrosting(Boolean isDefrosting) {
		this.isDefrosting = isDefrosting;
	}

	public Boolean isReflectable() {
		return isReflectable;
	}

	public void setReflectable(Boolean isReflectable) {
		this.isReflectable = isReflectable;
	}

	public Boolean isBlockable() {
		return isBlockable;
	}

	public void setBlockable(Boolean isBlockable) {
		this.isBlockable = isBlockable;
	}

	public Boolean isCopyable() {
		return isCopyable;
	}

	public void setCopyable(Boolean isCopyable) {
		this.isCopyable = isCopyable;
	}

	public String getLearnset() {
		return learnset;
	}

	public void setLearnset(String learnset) {
		this.learnset = learnset;
	}

	public Boolean isDeprecated() {
		return isDeprecated;
	}

	public void setDeprecated(Boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(battleEffect, effectRate, inDepthEffect, isBite, isBlockable, isBullet, isContact,
				isCopyable, isDefrosting, isDeprecated, isGravityAffected, isMetronomable, isPowder, isPunch,
				isReflectable, isSlice, isSnatchable, isSound, isWind, learnset, moveAcc, moveBaseCrit, moveBasePower,
				moveCategory, moveName, movePP, movePriority, moveTargets, moveType, secondaryEffect);
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
		return Objects.equals(battleEffect, other.battleEffect)
				&& Double.doubleToLongBits(effectRate) == Double.doubleToLongBits(other.effectRate)
				&& Objects.equals(inDepthEffect, other.inDepthEffect) && isBite == other.isBite
				&& isBlockable == other.isBlockable && isBullet == other.isBullet && isContact == other.isContact
				&& isCopyable == other.isCopyable && isDefrosting == other.isDefrosting
				&& isDeprecated == other.isDeprecated && isGravityAffected == other.isGravityAffected
				&& isMetronomable == other.isMetronomable && isPowder == other.isPowder && isPunch == other.isPunch
				&& isReflectable == other.isReflectable && isSlice == other.isSlice
				&& isSnatchable == other.isSnatchable && isSound == other.isSound && isWind == other.isWind
				&& Objects.equals(learnset, other.learnset) && moveAcc == other.moveAcc
				&& moveBaseCrit == other.moveBaseCrit && moveBasePower == other.moveBasePower
				&& Objects.equals(moveCategory, other.moveCategory) && Objects.equals(moveName, other.moveName)
				&& movePP == other.movePP && movePriority == other.movePriority
				&& Objects.equals(moveTargets, other.moveTargets) && Objects.equals(moveType, other.moveType)
				&& Objects.equals(secondaryEffect, other.secondaryEffect);
	}

	@Override
	public String toString() {
		return "Move [moveName=" + moveName + ", moveType=" + moveType + ", moveCategory=" + moveCategory + ", movePP="
				+ movePP + ", moveBasePower=" + moveBasePower + ", moveAcc=" + moveAcc + ", battleEffect="
				+ battleEffect + ", inDepthEffect=" + inDepthEffect + ", secondaryEffect=" + secondaryEffect
				+ ", effectRate=" + effectRate + ", moveBaseCrit=" + moveBaseCrit + ", movePriority=" + movePriority
				+ ", moveTargets=" + moveTargets + ", isContact=" + isContact + ", isSound=" + isSound + ", isPunch="
				+ isPunch + ", isBite=" + isBite + ", isSnatchable=" + isSnatchable + ", isSlice=" + isSlice
				+ ", isBullet=" + isBullet + ", isWind=" + isWind + ", isPowder=" + isPowder + ", isMetronomable="
				+ isMetronomable + ", isGravityAffected=" + isGravityAffected + ", isDefrosting=" + isDefrosting
				+ ", isReflectable=" + isReflectable + ", isBlockable=" + isBlockable + ", isCopyable=" + isCopyable
				+ ", isDeprecated=" + isDeprecated + ", learnset=" + learnset + "]";
	}

	public Move() {
		super();
	}

	public Move(String moveName) {
		super();
		this.moveName = moveName;
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