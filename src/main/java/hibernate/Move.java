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
	@Column(name="moveName", length = 10000)
	private String moveName;
	
	@Column(name="moveType") 
	String moveType;
	
	@Column(name="moveCategory") 
	String moveCategory;
	
	@Column(name="movePP")
	private int movePP;
	
	@Column(name="moveBasePower")
	private int moveBasePower;
	
	@Column(name="moveAcc")
	private int moveAcc;
	
	@Column(name="battleEffect", length = 10000)
	private String battleEffect;
	
	@Column(name="inDepthEffect", length = 10000)
	private String inDepthEffect;
	
	@Column(name="secondaryEffect", length = 10000)
	private String secondaryEffect;
	
	@Column(name="effectRate")
	private double effectRate;
	
	@Column(name="moveBaseCrit")
	private double moveBaseCrit;
	
	@Column(name="movePriority")
	private int movePriority;
	
	@Column(name="moveTargets")
	private String moveTargets;
	
	@Column(name="isContact")
	private boolean isContact;
	
	@Column(name="isSound")
	private boolean isSound;
	
	@Column(name="isPunch")
	private boolean isPunch;
	
	@Column(name="isBite")
	private boolean isBite;
	
	@Column(name="isSnatchable")
	private boolean isSnatchable;
	
	@Column(name="isSlice")
	private boolean isSlice;
	
	@Column(name="isBullet")
	private boolean isBullet;
	
	@Column(name="isWind")
	private boolean isWind;
	
	@Column(name="isPowder")
	private boolean isPowder;
	
	@Column(name="isMetronomable")
	private boolean isMetronomable;
	
	@Column(name="isGravityAffected")
	private boolean isGravityAffected;
	
	@Column(name="isDefrosting")
	private boolean isDefrosting;
	
	@Column(name="isReflectable")
	private boolean isReflectable;
	
	@Column(name="isBlockable")
	private boolean isBlockable;
	
	@Column(name="isCopyable")
	private boolean isCopyable;
	
	@Column(name="isDeprecated")
	private boolean isDeprecated;
	
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

	public int getMovePP() {
		return movePP;
	}

	public void setMovePP(int movePP) {
		this.movePP = movePP;
	}

	public int getMoveBasePower() {
		return moveBasePower;
	}

	public void setMoveBasePower(int moveBasePower) {
		this.moveBasePower = moveBasePower;
	}

	public int getMoveAcc() {
		return moveAcc;
	}

	public void setMoveAcc(int moveAcc) {
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

	public double getEffectRate() {
		return effectRate;
	}

	public void setEffectRate(double effectRate) {
		this.effectRate = effectRate;
	}

	public double getMoveBaseCrit() {
		return moveBaseCrit;
	}

	public void setMoveBaseCrit(double moveBaseCrit) {
		this.moveBaseCrit = moveBaseCrit;
	}

	public int getMovePriority() {
		return movePriority;
	}

	public void setMovePriority(int movePriority) {
		this.movePriority = movePriority;
	}

	public String getMoveTargets() {
		return moveTargets;
	}

	public void setMoveTargets(String moveTargets) {
		this.moveTargets = moveTargets;
	}

	public boolean isContact() {
		return isContact;
	}

	public void setContact(boolean isContact) {
		this.isContact = isContact;
	}

	public boolean isSound() {
		return isSound;
	}

	public void setSound(boolean isSound) {
		this.isSound = isSound;
	}

	public boolean isPunch() {
		return isPunch;
	}

	public void setPunch(boolean isPunch) {
		this.isPunch = isPunch;
	}

	public boolean isBite() {
		return isBite;
	}

	public void setBite(boolean isBite) {
		this.isBite = isBite;
	}

	public boolean isSnatchable() {
		return isSnatchable;
	}

	public void setSnatchable(boolean isSnatchable) {
		this.isSnatchable = isSnatchable;
	}

	public boolean isSlice() {
		return isSlice;
	}

	public void setSlice(boolean isSlice) {
		this.isSlice = isSlice;
	}

	public boolean isBullet() {
		return isBullet;
	}

	public void setBullet(boolean isBullet) {
		this.isBullet = isBullet;
	}

	public boolean isWind() {
		return isWind;
	}

	public void setWind(boolean isWind) {
		this.isWind = isWind;
	}

	public boolean isPowder() {
		return isPowder;
	}

	public void setPowder(boolean isPowder) {
		this.isPowder = isPowder;
	}

	public boolean isMetronomable() {
		return isMetronomable;
	}

	public void setMetronomable(boolean isMetronomable) {
		this.isMetronomable = isMetronomable;
	}

	public boolean isGravityAffected() {
		return isGravityAffected;
	}

	public void setGravityAffected(boolean isGravityAffected) {
		this.isGravityAffected = isGravityAffected;
	}
	
	public boolean isDefrosting() {
		return isDefrosting;
	}

	public void setDefrosting(boolean isDefrosting) {
		this.isDefrosting = isDefrosting;
	}

	public boolean isReflectable() {
		return isReflectable;
	}

	public void setReflectable(boolean isReflectable) {
		this.isReflectable = isReflectable;
	}

	public boolean isBlockable() {
		return isBlockable;
	}

	public void setBlockable(boolean isBlockable) {
		this.isBlockable = isBlockable;
	}

	public boolean isCopyable() {
		return isCopyable;
	}

	public void setCopyable(boolean isCopyable) {
		this.isCopyable = isCopyable;
	}

	public String getLearnset() {
		return learnset;
	}

	public void setLearnset(String learnset) {
		this.learnset = learnset;
	}

	public boolean isDeprecated() {
		return isDeprecated;
	}

	public void setDeprecated(boolean isDeprecated) {
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
}