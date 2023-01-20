package hibernate.move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class MoveHelpers {
	
	public static ArrayList<Move> attackDexGenerator() throws IOException{
		ArrayList<Move> results = new ArrayList<Move>();
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
		Move move = new Move();
		move.setName(s);
		results.add(move);
		}
	return results;
	}
	
	public static Document urlHelper(String moveName) throws IOException {
		String urlMoveName = moveName.toLowerCase().replace("%20", "").replace(" ", "").replace("%C3%A9", "e").replace("é", "e");
		System.out.println("Move Name: " + moveName);
		String URL = "https://www.serebii.net/attackdex-sv/" + urlMoveName + ".shtml"; //Builds the URLs correctly!
		Document dexEntry = Jsoup.connect((URL)).get();
		return dexEntry;
	}
	
	public static Move attackNameTableHelper(Move inputMove, Element dexTable) {
		Elements dexTableRows = dexTable.select("tr");
		Element row = dexTable.selectFirst("tr");
		
		String battleEffect = "";
		boolean isDeprecated = false;
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
		
		for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
			row = dexTableRows.select("tr").get((j + 1));
			Elements cols = row.select("td");
			Element col = cols.get(0);
			Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
			System.out.println(subTitle.text());
			
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Attack Name")) { //moveType handling.
				col = cols.get(1);
				Element hrefSource = col.select("a").first();
				String typeHref = hrefSource.attr("href"); //This isn't processing for some reason.
				moveType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
				moveType = moveType.substring(0,1).toUpperCase() + moveType.substring(1);
				System.out.println("Move Type: " + moveType);
				inputMove.setMoveType(moveType);
				
				//moveCategory handling.
				col = cols.get(2);
				if(!(Objects.isNull(col.select("a").first()))) {
					String categoryHref = col.select("a").first().attr("href");
					moveCategory = categoryHref.substring((categoryHref.indexOf("sv/") + 3), categoryHref.indexOf(".shtml"));
					moveCategory = moveCategory.substring(0,1).toUpperCase() + moveCategory.substring(1);
					System.out.println("Move Category: " + inputMove.moveCategory);
					inputMove.setMoveCategory(moveCategory);
				} else {
					moveCategory = "";
					System.out.println(moveCategory);
					inputMove.setMoveCategory(moveCategory);
				}
				
				}
			
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Power Points")) {
				movePP = Integer.parseInt(cols.get(0).text());
				moveBasePower = Integer.parseInt(cols.get(1).text());
				moveAcc = Integer.parseInt(cols.get(2).text());
				System.out.println("Move PP: " + movePP);
				System.out.println("Move Base Power: " + moveBasePower);
				System.out.println("Move Accuracy: " + moveAcc);
				inputMove.setMovePP(movePP);
				inputMove.setMoveBasePower(moveBasePower);
				inputMove.setMoveAcc(moveAcc);
			}
			
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Battle Effect:")) {
				col = cols.get(0);
				battleEffect = col.text();
				if(battleEffect.contains("This move can't be used.")) {
					isDeprecated = true;
				}
				System.out.println("Battle Effect: " + battleEffect);
				inputMove.setBattleEffect(battleEffect);
				inputMove.setDeprecated(isDeprecated);
			}
			
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("In-Depth Effect:")) {
					col = cols.get(0);
					inDepthEffect = col.text();
					System.out.println("In-Depth Effect: " + inDepthEffect);
					inputMove.setInDepthEffect(inDepthEffect);
				}
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Secondary Effect:")) {
				secondaryEffect = col.text();
				System.out.println("Secondary Effect: " + secondaryEffect);
				inputMove.setSecondaryEffect(secondaryEffect);
				
				Element effRteCol = cols.select(".cen").first();
				String effRateString = effRteCol.text();
				System.out.println(effRteCol.text().toString());
				effectRate = 0;
				if(effRateString.equals("-- %")) {
					effectRate = 0; //This corresponds to no effect chance under normal circumstances.
				} else {
					effRateString = effRateString.replace("%", "").replace(" ", "");
					effectRate = Double.parseDouble(effRateString);
				}
				System.out.println("Effect Rate: " + effRateString);
				System.out.println("Extracted value: " + effectRate);
				inputMove.setEffectRate(effectRate);
			}
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Base Critical Hit Rate")) {
				moveBaseCrit = 0;
				if(cols.get(0).text().equals("None")) {
					moveBaseCrit = 0;
				} else {
					moveBaseCrit = Double.parseDouble(cols.get(0).text().substring(0, cols.get(0).text().length() - 1));
				}
				System.out.println("Base Crit: " + moveBaseCrit);
				inputMove.setMoveBaseCrit(moveBaseCrit);
				
				movePriority = Integer.parseInt(cols.get(1).text());
				System.out.println("Move Priority: " + movePriority);
				inputMove.setMovePriority(movePriority);
				
				moveTargets = cols.get(2).text();
				System.out.println("Move Targets: " + moveTargets);
				inputMove.setMoveTargets(moveTargets);
			}
			}
		return inputMove;
			
		
	}
	
	public static Move physicalContactTableHelper(Move inputMove, Element dexTable) {
		Elements dexTableRows = dexTable.select("tr");
		Element row = dexTable.selectFirst("tr");
		boolean isBite = false, isBlockable = false, isBullet = false, isContact = false, isCopyable = false;
		boolean isGravityAffected = false, isMetronomable = false, isPowder = false, isPunch = false, isReflectable = false, isSlice = false;
		boolean isSnatchable = false, isDefrosting = false, isSound = false, isWind = false;
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
						isContact = true;
						break;
					case "No":
						isContact = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Makes Physical Contact?: " + isContact);
				inputMove.setContact(isContact);
			}
				
				col = cols.get(1);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isSound = true;
						break;
					case "No":
						isSound = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Sound Move?: " + isSound);
				inputMove.setSound(isSound);
			}
				
				col = cols.get(2);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isPunch = true;
						break;
					case "No":
						isPunch = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Punch Move?: " + isPunch);
				inputMove.setPunch(isPunch);
			}
				
				col = cols.get(3);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isBite = true;
						break;
					case "No":
						isBite = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Bite Move?: " + isBite);
				inputMove.setBite(isBite);
			}
				col = cols.get(4);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isSnatchable = true;
						break;
					case "No":
						isSnatchable = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Snatchable?: " + isSnatchable);
				inputMove.setSnatchable(isSnatchable);
			}
		}
		
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().contains("Slicing Move")) {
				col = cols.get(0);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isSlice = true;
						break;
					case "No":
						isSlice = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Slicing Move?: " + isSlice);
				inputMove.setSlice(isSlice);
			}
				
				col = cols.get(1);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isBullet = true;
						break;
					case "No":
						isBullet = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Bullet Move?: " + isBullet);
				inputMove.setBullet(isBullet);
			}
				
				col = cols.get(2);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isWind = true;
						break;
					case "No":
						isWind = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Wind Move?: " + isWind);
				inputMove.setWind(isWind);
			}
				
				col = cols.get(3);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isPowder = true;
						break;
					case "No":
						isPowder = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Powder Move: " + isPowder);
				inputMove.setPowder(isPowder);
			}
				col = cols.get(4);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isMetronomable = true;
						break;
					case "No":
						isMetronomable = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Metronome?: " + isMetronomable);
				inputMove.setMetronomable(isMetronomable);
			}
		}
			if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Affected by Gravity")) {
				col = cols.get(0);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isGravityAffected = true;
						break;
					case "No":
						isGravityAffected = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Affected by Gravity?: " + isGravityAffected);
				inputMove.setGravityAffected(isGravityAffected);
			}
				
				col = cols.get(1);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isDefrosting = true;
						break;
					case "No":
						isDefrosting = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Defrosts on use?: " + isDefrosting);
				inputMove.setDefrosting(isDefrosting);
			}
				
				col = cols.get(2);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isReflectable = true;
						break;
					case "No":
						isReflectable = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Reflected by Magic Coat/Bounce?: " + isReflectable);
				inputMove.setReflectable(isReflectable);
			}
				
				col = cols.get(3);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isBlockable = true;
						break;
					case "No":
						isBlockable = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Blocked by moves like Protect?: " + isBlockable);
				inputMove.setBlockable(isBlockable);
			}
				col = cols.get(4);
				if(!(Objects.isNull(col.text()))) {
					switch (col.text()) {
					case "Yes":
						isCopyable = true;
						break;
					case "No":
						isCopyable = false;
						break;
					default:
						System.out.println("This is an invalid state!");
						throw new IllegalArgumentException("Answer does not map to boolean.");
				}
				System.out.println("Copyable by Mirror Move?: " + isCopyable);
				inputMove.setCopyable(isCopyable);
				
			}
		}
	}
		return inputMove;
	}

}
