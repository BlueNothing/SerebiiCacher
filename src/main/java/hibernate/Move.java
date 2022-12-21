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
	private String moveType;
	
	@Column(name="moveCategory")
	private String moveCategory;
	
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
	
	

	public static void attackFinder(Session session) throws IOException{
		List<Move> results = new ArrayList<Move>();
		Document gen9AtkDex = Jsoup.connect("https://www.serebii.net/attackdex-sv/").get();
		Elements AtkDex = gen9AtkDex.select("option");
		List<TextNode> attacks = AtkDex.textNodes();
		System.out.println(AtkDex.eachText());
		ArrayList<String> AtkDexOverall = new ArrayList<String>();
		System.out.println("Inital attack dex data:");
		
		for(TextNode x : attacks) {
				if(!(x.text().startsWith("AttackDex"))) {
					AtkDexOverall.add(x.text());
		}
		}
	for(String s : AtkDexOverall) {
		Move moveSample = session.get(Move.class, s);
		Move move = new Move();
		move.setMoveName(s);
		results.add(move);
		}
	attackFiller(session, results);
	}
	
	public static void attackFiller(Session session, List<Move> attackDex) throws IOException{
		/*
		String validatorHQL = "FROM Move";
		List<Move> results = session.createQuery(validatorHQL).list();
		*/
		for (Move move : attackDex) {
			TreeSet<String> learnsetTree = new TreeSet<String>();
			Move inputMove = new Move(move.getMoveName());
			String moveName = inputMove.getMoveName(); //Working on this part to dynamically fill the dex!
			String urlMoveName = moveName.toLowerCase().replace("%20", "").replace(" ", "").replace("%C3%A9", "e").replace("é", "e");
			System.out.println("Move Name: " + moveName);
			String URL = "https://www.serebii.net/attackdex-sv/" + urlMoveName + ".shtml"; //Builds the URLs correctly!
			Document dexEntry = Jsoup.connect((URL)).get();
			/*
			 * Taking a moment to explain the strategy here: 
			 * The tables on each page in Serebii's records have a consistent pattern, but not every field exists for every entry.
			 * So if a table exists, it will appear after the tables before it in the pattern.
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
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Attack Name")) { //First row has Attack Name, Attack Type, Category.
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(subTitle.text());
						
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Attack Name")) {
							//moveType handling.
							col = cols.get(1);
							Element hrefSource = col.select("a").first();
							String typeHref = hrefSource.attr("href"); //This isn't processing for some reason.
							inputMove.moveType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
							inputMove.moveType = inputMove.moveType.substring(0,1).toUpperCase() + inputMove.moveType.substring(1);
							System.out.println("Move Type: " + inputMove.moveType);
							inputMove.setMoveType(inputMove.moveType);
							
							//moveCategory handling.
							col = cols.get(2);
							if(!(Objects.isNull(col.select("a").first()))) {
								String categoryHref = col.select("a").first().attr("href");
								inputMove.moveCategory = categoryHref.substring((categoryHref.indexOf("sv/") + 3), categoryHref.indexOf(".shtml"));
								inputMove.moveCategory = inputMove.moveCategory.substring(0,1).toUpperCase() + inputMove.moveCategory.substring(1);
								System.out.println("Move Category: " + inputMove.moveCategory);
								inputMove.setMoveCategory(inputMove.moveCategory);
							} else {
								inputMove.moveCategory = "";
								System.out.println(inputMove.moveCategory);
								inputMove.setMoveCategory(inputMove.moveCategory);
							}
							
							}
						
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Power Points")) {
							inputMove.movePP = Integer.parseInt(cols.get(0).text());
							inputMove.moveBasePower = Integer.parseInt(cols.get(1).text());
							inputMove.moveAcc = Integer.parseInt(cols.get(2).text());
							System.out.println("Move PP: " + inputMove.movePP);
							System.out.println("Move Base Power: " + inputMove.moveBasePower);
							System.out.println("Move Accuracy: " + inputMove.moveAcc);
							inputMove.setMovePP(inputMove.movePP);
							inputMove.setMoveBasePower(inputMove.moveBasePower);
							inputMove.setMoveAcc(inputMove.moveAcc);
						}
						
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Battle Effect:")) {
							col = cols.get(0);
							inputMove.battleEffect = col.text();
							if(inputMove.battleEffect.contains("This move can't be used.")) {
								inputMove.isDeprecated = true;
							}
							System.out.println("Battle Effect: " + inputMove.battleEffect);
							inputMove.setBattleEffect(inputMove.battleEffect);
							inputMove.setDeprecated(inputMove.isDeprecated);
						}
						
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("In-Depth Effect:")) {
								col = cols.get(0);
								inputMove.inDepthEffect = col.text();
								System.out.println("In-Depth Effect: " + inputMove.inDepthEffect);
								inputMove.setInDepthEffect(inputMove.inDepthEffect);
							}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Secondary Effect:")) {
							inputMove.secondaryEffect = col.text();
							System.out.println("Secondary Effect: " + inputMove.secondaryEffect);
							inputMove.setSecondaryEffect(inputMove.secondaryEffect);
							
							Element effRteCol = cols.select(".cen").first();
							String effRateString = effRteCol.text();
							System.out.println(effRteCol.text().toString());
							inputMove.effectRate = 0;
							if(effRateString.equals("-- %")) {
								inputMove.effectRate = 0; //This corresponds to no effect chance under normal circumstances.
							} else {
								effRateString = effRateString.replace("%", "").replace(" ", "");
								inputMove.effectRate = Double.parseDouble(effRateString);
							}
							System.out.println("Effect Rate: " + effRateString);
							System.out.println("Extracted value: " + inputMove.effectRate);
							inputMove.setEffectRate(inputMove.effectRate);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Base Critical Hit Rate")) {
							inputMove.moveBaseCrit = 0;
							if(cols.get(0).text().equals("None")) {
								inputMove.moveBaseCrit = 0;
							} else {
								inputMove.moveBaseCrit = Double.parseDouble(cols.get(0).text().substring(0, cols.get(0).text().length() - 1));
							}
							System.out.println("Base Crit: " + inputMove.moveBaseCrit);
							inputMove.setMoveBaseCrit(inputMove.moveBaseCrit);
							
							inputMove.movePriority = Integer.parseInt(cols.get(1).text());
							System.out.println("Move Priority: " + inputMove.movePriority);
							inputMove.setMovePriority(inputMove.movePriority);
							
							inputMove.moveTargets = cols.get(2).text();
							System.out.println("Move Targets: " + inputMove.moveTargets);
							inputMove.setMoveTargets(inputMove.moveTargets);
						}
						}
						
					}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Physical Contact")) {
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(subTitle.text());
						
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Physical Contact")) {
							col = cols.get(0);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isContact = true;
									break;
								case "No":
									inputMove.isContact = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Makes Physical Contact?: " + inputMove.isContact);
							inputMove.setContact(inputMove.isContact);
						}
							
							col = cols.get(1);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isSound = true;
									break;
								case "No":
									inputMove.isSound = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Sound Move?: " + inputMove.isSound);
							inputMove.setSound(inputMove.isSound);
						}
							
							col = cols.get(2);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isPunch = true;
									break;
								case "No":
									inputMove.isPunch = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Punch Move?: " + inputMove.isPunch);
							inputMove.setPunch(inputMove.isPunch);
						}
							
							col = cols.get(3);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isBite = true;
									break;
								case "No":
									inputMove.isBite = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Bite Move?: " + inputMove.isBite);
							inputMove.setBite(inputMove.isBite);
						}
							col = cols.get(4);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isSnatchable = true;
									break;
								case "No":
									inputMove.isSnatchable = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Snatchable?: " + inputMove.isSnatchable);
							inputMove.setSnatchable(inputMove.isSnatchable);
						}
					}
					
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().contains("Slicing Move")) {
							col = cols.get(0);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isSlice = true;
									break;
								case "No":
									inputMove.isSlice = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Slicing Move?: " + inputMove.isSlice);
							inputMove.setSlice(inputMove.isSlice);
						}
							
							col = cols.get(1);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isBullet = true;
									break;
								case "No":
									inputMove.isBullet = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Bullet Move?: " + inputMove.isBullet);
							inputMove.setBullet(inputMove.isBullet);
						}
							
							col = cols.get(2);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isWind = true;
									break;
								case "No":
									inputMove.isWind = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Wind Move?: " + inputMove.isWind);
							inputMove.setWind(inputMove.isWind);
						}
							
							col = cols.get(3);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isPowder = true;
									break;
								case "No":
									inputMove.isPowder = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Powder Move: " + inputMove.isPowder);
							inputMove.setPowder(inputMove.isPowder);
						}
							col = cols.get(4);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isMetronomable = true;
									break;
								case "No":
									inputMove.isMetronomable = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Metronome?: " + inputMove.isMetronomable);
							inputMove.setMetronomable(inputMove.isMetronomable);
						}
					}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Affected by Gravity")) {
							col = cols.get(0);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isGravityAffected = true;
									break;
								case "No":
									inputMove.isGravityAffected = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Affected by Gravity?: " + inputMove.isGravityAffected);
							inputMove.setGravityAffected(inputMove.isGravityAffected);
						}
							
							col = cols.get(1);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isDefrosting = true;
									break;
								case "No":
									inputMove.isDefrosting = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Defrosts on use?: " + inputMove.isDefrosting);
							inputMove.setDefrosting(inputMove.isDefrosting);
						}
							
							col = cols.get(2);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isReflectable = true;
									break;
								case "No":
									inputMove.isReflectable = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Reflected by Magic Coat/Bounce?: " + inputMove.isReflectable);
							inputMove.setReflectable(inputMove.isReflectable);
						}
							
							col = cols.get(3);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isBlockable = true;
									break;
								case "No":
									inputMove.isBlockable = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Blocked by moves like Protect?: ");
							inputMove.setBlockable(inputMove.isBlockable);
						}
							col = cols.get(4);
							if(!(Objects.isNull(col.text()))) {
								switch (col.text()) {
								case "Yes":
									inputMove.isCopyable = true;
									break;
								case "No":
									inputMove.isCopyable = false;
									break;
								default:
									System.out.println("This is an invalid state!");
									throw new IllegalArgumentException("Answer does not map to boolean.");
							}
							System.out.println("Copyable by Mirror Move?: " + inputMove.isCopyable);
							inputMove.setCopyable(inputMove.isCopyable);
							
						}
					}
				}	
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("No.")) {
					if(dexTableRows.size() < 2) {
						System.out.println("This learnset component is empty.");
						continue;
					}
					for(int j = 2; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get(j);
						Elements cols = row.select("td");
						String pokeName = cols.get(3).text();
						learnsetTree.add(pokeName);
						System.out.println(pokeName);
					}
					}
				}
			inputMove.learnset = learnsetTree.toString();
			if(inputMove.learnset.isEmpty() || inputMove.learnset.equals("[]")) {
				inputMove.learnset = "None";
			}
			System.out.println("Pokemon that can currently learn this move in Gen 9: " + inputMove.learnset);
			inputMove.setLearnset(inputMove.learnset);
			System.out.println(inputMove.toString());
				
				Move dbSample = session.get(Move.class, moveName);
				 
				if (!(Objects.isNull(dbSample)) && dbSample.getMoveName().equals(inputMove.getMoveName()) && dbSample.toString().equals(inputMove.toString())) {
					System.out.println("Nothing to do here.");
					continue;
					} else {
					System.out.println("There is new data!");
					}
				
				session.beginTransaction();
				session.persist(inputMove);
				session.getTransaction().commit();
	}
		session.close();
	
}
	
}