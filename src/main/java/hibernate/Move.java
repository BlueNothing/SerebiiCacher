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
	
	@Column(name="secondaryEffect", length = 10000)
	private String secondaryEffect;
	
	@Column(name="moveBaseCrit")
	private int moveBaseCrit;
	
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
	
	@Column(name="isReflectable")
	private boolean isReflectable;
	
	@Column(name="isBlockable")
	private boolean isBlockable;
	
	@Column(name="isCopyable")
	private boolean isCopyable;
	
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

	public String getSecondaryEffect() {
		return secondaryEffect;
	}

	public void setSecondaryEffect(String secondaryEffect) {
		this.secondaryEffect = secondaryEffect;
	}

	public int getMoveBaseCrit() {
		return moveBaseCrit;
	}

	public void setMoveBaseCrit(int moveBaseCrit) {
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

	@Override
	public int hashCode() {
		return Objects.hash(battleEffect, isBite, isBlockable, isBullet, isContact, isCopyable, isGravityAffected,
				isMetronomable, isPowder, isPunch, isReflectable, isSlice, isSnatchable, isSound, isWind, learnset,
				moveAcc, moveBaseCrit, moveBasePower, moveCategory, moveName, movePP, movePriority, moveTargets,
				moveType, secondaryEffect);
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
		return Objects.equals(battleEffect, other.battleEffect) && isBite == other.isBite
				&& isBlockable == other.isBlockable && isBullet == other.isBullet && isContact == other.isContact
				&& isCopyable == other.isCopyable && isGravityAffected == other.isGravityAffected
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
				+ battleEffect + ", secondaryEffect=" + secondaryEffect + ", moveBaseCrit=" + moveBaseCrit
				+ ", movePriority=" + movePriority + ", moveTargets=" + moveTargets + ", isContact=" + isContact
				+ ", isSound=" + isSound + ", isPunch=" + isPunch + ", isBite=" + isBite + ", isSnatchable="
				+ isSnatchable + ", isSlice=" + isSlice + ", isBullet=" + isBullet + ", isWind=" + isWind
				+ ", isPowder=" + isPowder + ", isMetronomable=" + isMetronomable + ", isGravityAffected="
				+ isGravityAffected + ", isReflectable=" + isReflectable + ", isBlockable=" + isBlockable
				+ ", isCopyable=" + isCopyable + ", learnset=" + learnset + "]";
	}

	public static void attackFinder(Session session) throws IOException{
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
		if(!(Objects.isNull(moveSample)) && moveSample.getMoveName().equals(move.getMoveName()) && moveSample.equals(move)) {
			System.out.println("Nothing to do here.");
			continue;
		} else {
			session.beginTransaction();
			session.persist(move);
			session.getTransaction().commit();
			System.out.println(s);
		}
	}
	String validatorHQL = "FROM Move";
	List<Move> results = session.createQuery(validatorHQL).list();
	results.forEach(outcome -> System.out.println(outcome));
	attackFiller(session);
	}
	
	public static void attackFiller(Session session) throws IOException{
		String validatorHQL = "FROM Move";
		List<Move> results = session.createQuery(validatorHQL).list();
		for (Move move : results) {
			System.out.println(move.toString());
			String moveName = move.getMoveName(); //Working on this part to dynamically fill the dex!
			String urlMoveName = moveName.toLowerCase().replace("%20", "").replace(" ", "").replace("%C3%A9", "e").replace("é", "e");
			System.out.println(urlMoveName);
			String URL = "https://www.serebii.net/attackdex-sv/" + urlMoveName + ".shtml"; //Builds the URLs correctly!
			Document dexEntry = Jsoup.connect((URL)).get();
			/*
			 * Taking a moment to explain the strategy here: 
			 * The tables on each page in Serebii's records have a consistent pattern, but not every field exists for every entry.
			 * So if a table exists, it will appear after the tables before it in the pattern.
			 * I iterate 'i' over the list of tables on the page until I find the one I'm looking for (by checking the headers, which are uniform where extant).
			 * If I find the one I'm looking for, I update anchor to start from that table, process that table, and repeat the process for the next.
			 * If I do not find the one I'm looking for before I run out of tables, I exit the loop in an unconventional way and report an issue to the debug pane, then continue to try and process the others.
			 * 
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
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Attack Name")) {
					row = dexTableRows.select("tr").get(5);
					Elements cols = row.select("td");
					Element col = cols.get(0);
					String battleEffect = col.text();
					if(battleEffect.contains("This move can't be used.")) {
						System.out.println("DEPRECATED MOVE");
						continue;
					}
					row = dexTableRows.select("tr").get(1);
					cols = row.select("td");
					col = cols.get(1);
					System.out.println(col);
					Element hrefSource = col.select("a").first();
					String typeHref = hrefSource.attr("href"); //This isn't processing for some reason.
					String moveType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
					System.out.println(moveType);
					col = cols.get(2);
					String categoryHref = col.select("a").first().attr("href");
					String moveCategory = categoryHref.substring((categoryHref.indexOf("sv/") + 3), categoryHref.indexOf(".shtml"));
					System.out.println(moveCategory);
					
					row = dexTableRows.select("tr").get(3);
					cols = row.select("td");
					int powerPoints = Integer.parseInt(cols.get(0).text());
					int basePower = Integer.parseInt(cols.get(1).text());
					int accuracy = Integer.parseInt(cols.get(2).text());
					System.out.println(powerPoints + " " + basePower + " " + accuracy);
					
					row = dexTableRows.select("tr").get(7);
					cols = row.select("td");
					String inDepthEffect = cols.get(0).text();
					System.out.println("In-Depth Effect: " + inDepthEffect);
					
					row = dexTableRows.select("tr").get(9);
					cols = row.select("td");
					String secondaryEffect = cols.get(0).text(); //Where's this redundancy?
					System.out.println("Secondary Effect: " + secondaryEffect);
					
					double effectRate = Double.parseDouble(cols.get(1).text());
					System.out.println("Effect Rate: " + effectRate);
					
					double baseCrit = 0;
					if(cols.get(0).text().equals("None")) {
						baseCrit = 0;
					} else {
						baseCrit = Double.parseDouble(cols.get(0).text().substring(0, cols.get(0).text().length() - 1));
					}
					System.out.println("Base Crit: " + baseCrit);
					int movePriority = Integer.parseInt(cols.get(1).text());
					System.out.println("Move Priority: " + movePriority);
					String moveTargets = cols.get(2).text();
					System.out.println("Move Targets: " + moveTargets);
				}
				
				
				/*
				Pokemon dbSample = session.get(Pokemon.class, poke.getPokeName());
				 
				if (!(Objects.isNull(dbSample)) && dbSample.getPokeName().equals(poke.getPokeName()) && dbSample.equals(poke)) {
					System.out.println("Nothing to do here.");
					continue;
					}
				*/
				//session.beginTransaction();
				//session.persist(move);
				//session.getTransaction().commit();
		}
			
	}
		session.close();
		System.out.println("/n /n /n");
	}
	
}