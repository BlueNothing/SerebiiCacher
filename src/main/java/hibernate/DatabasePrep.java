package hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;

import hibernate.ability.Ability;
import hibernate.ability.AbilityHelpers;
import hibernate.move.Move;
import hibernate.move.MoveHelpers;
import hibernate.pokemon.Pokemon;

@Configuration
public class DatabasePrep {
	
	public static void abilityFinder(Session session) throws IOException {
		ArrayList<Ability> results = AbilityHelpers.abilityListGenerator(session);
		//String validatorHQL = "FROM Ability";
		//List<Ability> results = session.createQuery(validatorHQL).list();
		for (Ability ability : results) {
			Ability localAbility = new Ability();
			localAbility.setName(ability.getName());
			String abilityName = localAbility.getName().toLowerCase().replace("%20", "").replace(" ", "");
			String URL = "https://www.serebii.net/abilitydex/" + abilityName + ".shtml";
			Document abilityDoc = Jsoup.connect((URL)).get();
			String inGameText = null, inDepthEffect = null, overworldEffect = null, affectedMoves = null, accessSet = null;
			TreeSet<String> affectedMovesTree = new TreeSet<String>();
			TreeSet<String> accessSetTree = new TreeSet<String>();
			for(int i = 0; i < abilityDoc.select("table.dextable").size(); i++) {
				Element dexTable = abilityDoc.select("table.dextable").get(0);
				try {
				dexTable = abilityDoc.select("table.dextable").get(i);
				} catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
				Elements dexTableRows = dexTable.select("tr");
				System.out.println("Number of Rows: " + dexTableRows.size());
				Element row = dexTable.selectFirst("tr");
				Element titleCol = row.selectFirst("td");
				System.out.println("TITLE COLUMN: " + titleCol.text());
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Name")) { //First row has Attack Name, Attack Type, Category.
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						if(cols.size() == 0) {
							continue;
						}
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Game's Text:")) {
							inGameText = col.text();
							localAbility.setAbilityGameText(inGameText);
							System.out.println("In-Game Text: " + inGameText);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("In-Depth Effect:")) {
							inDepthEffect = col.text();
							localAbility.setInDepthAbilityEffect(inDepthEffect);
							System.out.println("In-Depth Effect: " + inDepthEffect);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Overworld Effect:")) {
							overworldEffect = col.text();
							localAbility.setOverworldEffect(overworldEffect);
							System.out.println("Overworld Effect: " + overworldEffect);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Details & Attacks Effected")) {
							Element subTable = row.select("table").first();
							Elements subTableRows = subTable.select("tr");
							for(Element subTableRow : subTableRows) {
								if(subTableRow.select("td").size() < 1) {
									continue;
								} else {
								String moveName = subTableRow.select("td").get(0).text();
								System.out.println(moveName);
								affectedMovesTree.add(moveName);
							}
							}
							affectedMoves = affectedMovesTree.toString().replace("[", "").replace("]", "");
							localAbility.setAffectedMoves(affectedMoves);
							System.out.println("Attacks Effected: " + affectedMoves);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Attacks Effected")) {
							Element subTable = row.select("table").first();
							Elements subTableRows = subTable.select("tr");
							for(Element subTableRow : subTableRows) {
								String moveName = subTableRow.select("td").get(0).text();
								System.out.println(moveName);
								affectedMovesTree.add(moveName);
							}
							affectedMoves = affectedMovesTree.toString().replace("[", "").replace("]", "");
							localAbility.setAffectedMoves(affectedMoves);
							System.out.println("Attacks Effected: " + affectedMoves);
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Further Details")) {
							continue;
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Lv.")) {
							continue;
						}
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("")) {
							continue;
						}
						if(!(Objects.isNull(subTitle.text())) && Character.isDigit(subTitle.text().charAt(0))) {
							continue;
						}
					}
				}
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("No.")) {
					for(int j = 2; j < dexTableRows.size(); j+= 2) {
						row = dexTableRows.select("tr").get(j);
						Elements cols = row.select("td");
						Element col = cols.get(3);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(col.text());
						accessSetTree.add(col.text());
						}
						accessSet = accessSetTree.toString().replace("[", "").replace("]", "");
						
			}
			}
			localAbility.setAccessSet(accessSet);
			System.out.println("Access Set: " + accessSet);
		//The code now effectively connects to the webpage for most abilities.
			GeneralHelpers.dbPersist(localAbility, session);
		
		}
	}
	
	public static void dexFinder(Session session) throws IOException {
		/* What we see here selects each entry in the current Serebii Pokedex and adds it to the list.
		 * Notably, the current outcome relies on a few quirks in Serebii's design for the Pokedex: 
		 * Every Pokedex entry is repeated, but only one instance of each dex entry is numbered, and outside of the Paldea dex, it's numbered with its National Dex entry.
		 * Inside the Paldea dex, they're numbered with their local dex number.
		 * Overall, then, there's at least one instance of every pokemon's dex entry that's prefaced with a number.
		 * 
		 * Now, we get into the alternate forms (Origin, Alolan, Galarian, Hisuian, Therian, etc, etc).
		 * Alternate forms are characterized by a few distinct properties - 
		 * Alternate forms may have a different type, a different classification, different weaknesses, different abilities, a different moveset, different stats, and/or different EVs.
		 * 
		*/
		ArrayList<Pokemon> variantPokeList = new ArrayList<Pokemon>();
		Document palDex = Jsoup.connect("https://www.serebii.net/pokedex-sv/").get();
		Elements palDexElems = palDex.select("option");
		List<String> palDexData = palDexElems.eachText();
		ArrayList<String> palDexOverall = new ArrayList<String>();
		List<String> results = new ArrayList<String>();
		System.out.println("Inital Pokedex data:");
		/*
		 */
	for(String s : palDexData) {
		s = s.trim(); //Okay, so this works just fine.
		if(s.contains("%C3%A9")) {
			s = s.replace("%C3%A9", "e");
		}
		if(Character.isDigit(s.charAt(0))) {
			s = s.substring(4);
			s = s.trim();
			if(!(palDexOverall.contains(s))) {
			palDexOverall.add(s);
			//System.out.println(s);
			}
	}
	}
	
	System.out.println("Dex stubs uploaded to DB.");
	System.out.println("Testing DB contents!");
	
	for(String s : palDexOverall) {
		results.add(s); //The latter option may be more thorough, but keeping this one for now, because it should be *much* faster.
		
		//Pokemon dbSample = session.get(Pokemon.class, s);
		//String pokeName = dbSample.getPokeName();
		//results.add(pokeName);
	}
		//String validatorHQL = "FROM Pokemon";
		//List<Pokemon> results = session.createQuery(validatorHQL).list();
		for (String inputName : results) {
			/*if(!((inputName.equals("Meowth") || inputName.equals("Tauros") || inputName.equals("Raichu") || inputName.equals("Wooper") || inputName.equals("Giratina")))) {
				continue;
			}
			*/
			Pokemon localPoke = new Pokemon(inputName);
			Pokemon localPokeAlola = null; //Several Pokemon have Alolan regional forms.
			Pokemon localPokeGalar = null; //Several Pokemon have Galarian regional forms.
			Pokemon localPokeHisui = null; //Several Pokemon have Hisuian regional forms.
			Pokemon localPokePaldea = null; //A few Pokemon have Paldean-exclusive forms.
			Pokemon localPokeOther1 = null; //Several Pokemon have forms which are not region-specific, like the Creation Trio, the Genie Trio, Lycanroc, Indeedee, and Toxtricity.
			Pokemon localPokeOther2 = null; //Some Pokemon, including Tauros, have multiple forms that are not neatly subsumed under the above categories; most notably, Paldean Tauros has two variant breeds.
			Pokemon localPokeOther3 = null; //Oricorio has three substantively different forms.
			Pokemon localPokeOther4 = null; //Rotom has 6 forms.
			Pokemon localPokeOther5 = null; //Rotom has 6 forms.
			ArrayList<Pokemon> localPokeList = new ArrayList<Pokemon>();
			
			String levelMoves = "";
			String tmMoves = "";
			String eggMoves = "";
			String otherMoves = "";
			String overallMoves = "";
			int baseAtk = 0;
			int baseDef = 0;
			int baseHP = 0;
			int baseSpd = 0;
			int baseSpAtk = 0;
			int baseSpDef = 0;
			int bst = 0;
			String pokeName = localPoke.getName(); //Working on this part to dynamically fill the dex!
			String urlPokeName = pokeName.toLowerCase().replace("%20", "").replace(" ", "").replace("%C3%A9", "e").replace("é", "e");
			//urlPokeName = "charmander";
			//System.out.println(urlPokeName);
			String URL = "https://www.serebii.net/pokedex-sv/" + urlPokeName + "/"; //Builds the URLs correctly!
			Document dexEntry = Jsoup.connect((URL)).get();
			/*
			 * Taking a moment to explain the strategy here: 
			 * The tables on each page in Serebii's records have a consistent pattern, but not every field exists for every entry.
			 * So if a table exists, it will appear after the tables before it in the pattern.
			 * I iterate 'i' over the list of tables on the page until I find the one I'm looking for (by checking the headers, which are uniform where extant).
			 * If I find the one I'm looking for, I update anchor to start from that table, process that table, and repeat the process for the next.
			 * If I do not find the one I'm looking for before I run out of tables, I exit the loop in an unconventional way and report an issue to the debug pane, then continue to try and process the others.
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
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Name")) {
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						int k = 0;
						for(Element col : cols) {
							System.out.println(k + col.text().toString());
							k++;
						}
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(subTitle.text());
						if(j == 0) {
						Element typeData = cols.select(".cen").get(0);
						if(!Objects.isNull(typeData)) {
							//System.out.println(typeData.html());
							System.out.println(typeData.text());
							if(typeData.text().contains("Normal") || typeData.text().contains("Style") || typeData.text().contains("Hoopa") || typeData.text().contains("Calyrex") || typeData.text().contains("Form") || typeData.text().contains("Crowned") || typeData.text().contains("Rotom")) {
								Elements typeTable = typeData.select("tr");
								for(Element typeSet : typeTable) {
									Element typeCol = cols.get(cols.size() - 1);
									System.out.println("Type Column: " + typeCol.toString());
									Elements types = typeCol.select("a");
						String typeList = "";
						for(Element type : types) {
							String typeHref = type.attr("href");
							//System.out.println(typeHref);
							String storedType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
							storedType = storedType.substring(0, 1).toUpperCase() + storedType.substring(1) + ", ";
							typeList += storedType;
						}
						typeList = typeList.substring(0, (typeList.length() - 2)); //This may behave erratically for things that have conditional types like Megas or Regional Forms.
						System.out.println("Type: " + typeList);
						localPoke.setPokeTypes(typeList);
									System.out.println(types.text());
								}
								
								if(typeData.text().contains("Paldean")) {
									localPokePaldea = new Pokemon(localPoke.getName());
									localPokePaldea.setName("Paldean " + localPokePaldea.getName());
									System.out.println(localPokePaldea.getName());
									if(localPoke.getName().equals("Tauros")) {
										localPokeOther1 = new Pokemon(localPokePaldea.getName() + " - Blaze Breed");
										localPokeOther2 = new Pokemon(localPokePaldea.getName() + " - Aqua Breed");
										System.out.println(localPokeOther1.getName());
										System.out.println(localPokeOther2.getName());
									}
								}
								if(typeData.text().contains("Hisuian")) {
									localPokeHisui = new Pokemon(localPoke.getName());
									localPokeHisui.setName("Hisuian " + localPokeHisui.getName());
									System.out.println(localPokeHisui.getName());
								}
								if(typeData.text().contains("Galarian")) {
									localPokeGalar = new Pokemon(localPoke.getName());
									localPokeGalar.setName("Galarian " + localPokeGalar.getName());
									System.out.println(localPokeGalar.getName());
								}
								if(typeData.text().contains("Alolan")) {
									localPokeAlola = new Pokemon(localPoke.getName());
									localPokeAlola.setName("Alolan " + localPokeAlola.getName());
									System.out.println(localPokeAlola.getName());
								} 
								else if(typeData.text().contains("Sensu")) { //Oricorio's forms
									localPoke.setName("Oricorio - Baile Style");
									localPokeOther1 = new Pokemon("Oricorio - Pom-Pom Style");
									localPokeOther2 = new Pokemon("Oricorio - Pa'u Style");
									localPokeOther3 = new Pokemon("Oricorio - Sensu Style");
								}
								else if(typeData.text().contains("Hoopa")) { //Hoopa's 2 forms
									localPoke.setName("Hoopa Confined");
									localPokeOther1 = new Pokemon("Hoopa Unbound");
								}
								else if(typeData.text().contains("Calyrex")){ //Calyrex and 2 forms
									localPokeOther1 = new Pokemon(localPoke.getName() + " - Ice Rider");
									localPokeOther2 = new Pokemon(localPoke.getName() + " - Shadow Rider");
								}
								else if(typeData.text().contains("Crowned")) { //Zacian and Zamazenta
									localPokeOther1 = new Pokemon(localPoke.getName() + " - Crowned Form");
									localPoke.setName(localPoke.getName() + " - Hero of Many Battles");
								}
								else if(typeData.text().contains("Style")){ //Urshifu
									localPoke.setName("Urshifu - Single Strike Style");
									localPokeOther1 = new Pokemon("Urshifu - Rapid Strike Style");
								}
								else if(typeData.text().contains("Aria Forme")) {
									localPoke.setName("Meloetta - Aria Forme");
									localPokeOther1 = new Pokemon("Meloetta - Pirouette Forme");
									
								}
								else if(typeData.text().contains("Rotom")) {
									localPokeOther1 = new Pokemon("Rotom - Frost");
									localPokeOther2 = new Pokemon("Rotom - Heat");
									localPokeOther3 = new Pokemon("Rotom - Mow");
									localPokeOther4 = new Pokemon("Rotom - Fan");
									localPokeOther5 = new Pokemon("Rotom - Wash");
								}
							} else {
						Element typeCol = cols.get(cols.size() - 1);
						System.out.println("Type Column: " + typeCol.toString());
						Elements types = typeCol.select("a");
						String typeList = "";
						for(Element type : types) {
							String typeHref = type.attr("href");
							//System.out.println(typeHref);
							String storedType = typeHref.substring((typeHref.indexOf("sv/") + 3), typeHref.indexOf(".shtml"));
							storedType = storedType.substring(0, 1).toUpperCase() + storedType.substring(1) + ", ";
							typeList += storedType;
						}
						typeList = typeList.substring(0, (typeList.length() - 2)); //This may behave erratically for things that have conditional types like Megas or Regional Forms.
						System.out.println("Type: " + typeList);
						localPoke.setPokeTypes(typeList);
						}
						}
							if(subTitle.text().equals("Classification") ) {
							System.out.println("Classification sub-table");
							col = cols.get(0);
							String classification = col.text();
							System.out.println(classification);
							localPoke.setClassification(classification);
							
							col = cols.get(1);
							String height = col.text();
							height = height.substring(0, (height.indexOf("\"") + 1));
							System.out.println(height);
							localPoke.setHeight(height);
							
							col = cols.get(2);
							String weight = col.text();
							weight = weight.substring(0, weight.indexOf("lbs") + 2);
							weight = weight.replace("lb", " lbs");
							System.out.println(weight);
							localPoke.setWeight(weight);
							
							col = cols.get(3);
							double capRate = Double.parseDouble(col.text());
							System.out.println(capRate);
							localPoke.setCapRate(capRate);
							
							col = cols.get(4);
							String eggStepString = col.text().replaceAll(",", "");
							int eggSteps = Integer.parseInt(eggStepString);
							System.out.println(eggSteps);
							localPoke.setEggSteps(eggSteps);
					}
					
				}
				}
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().startsWith("Abilities:")) {
					for(int j = 0; j < (dexTableRows.size() - 1); j += 2) {
						row = dexTableRows.select("tr").get((j + 1));
						Elements cols = row.select("td");
						int k = 0;
						for(Element col : cols) {
							System.out.println(k + col.text().toString());
							k++;
						}
						Element col = cols.get(0);
						Element subTitle = dexTableRows.select("tr").get(j).select("td").first();
						System.out.println(subTitle.text());
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().contains("Abilities:")) {
							Elements abilityElements = col.select("b");
							ArrayList<String> abilityList = new ArrayList<String>();
							boolean hiddenNext = false;
							for(Element abilityElem : abilityElements) {
								if(abilityElem.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityList.add(abilityElem.text());
								} else if(hiddenNext) {
									abilityList.add("(H) " + abilityElem.text());
									hiddenNext = false;
								}
							}
							String abilities = abilityList.toString();
							abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
							localPoke.setAbilities(abilities);
						}
							
						if(!(Objects.isNull(subTitle.text())) && subTitle.text().equals("Experience Growth")) {
							col = cols.get(cols.size() - 2);
							System.out.println("EV Column: " + col.toString());
							String evString = col.text();
							System.out.println("EV String: " + evString);
							int evAmt = 0;
							if(evString.equals("")) {
								evString = "";
								localPoke.setEvRewardAmt(evAmt);
								localPoke.setEvRewardAttr(evString);
							} else {
								if(pokeName.equals("Indeedee")) {
									localPoke.setEvRewardAmt(2);
									localPoke.setEvRewardAttr("Sp. Attack [M]; Sp. Defense [F]");
									continue;
								}
								if(evString.contains("Alolan") || evString.contains("Galarian") || evString.contains("Hisuian")) {
									System.out.println("SPECIAL CASE");
								}
							evAmt = Integer.parseInt(String.valueOf(evString.charAt(0)));
							System.out.println(evString);
							evString = evString.substring(2);
							int garbageIndex = evString.indexOf("Point") - 1;
							evString = evString.substring(0, garbageIndex);
							localPoke.setEvRewardAmt(evAmt);
							localPoke.setEvRewardAttr(evString);
							}
							System.out.println(evAmt + " " + evString);
						}
					}
				}
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Weakness")) {
					System.out.println("WEAKNESS TABLE");
					Element typeRow = dexTableRows.select("tr").get((1));
					Elements typeCols = typeRow.select("td");
					Element weakRow = dexTableRows.select("tr").get(2);
					Elements weakCols = weakRow.select("td");
					String weaknesses = "";
					String neutrals = "";
					String immunities = "";
					String resistances = "";
						
					for (int k = 0; k < typeCols.size(); k++) {
						String rawType = typeCols.get(k).select("a").attr("href");
						String typeName = rawType.substring(rawType.indexOf("sv/") + 3, rawType.indexOf(".shtml"));
						typeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1);
						if(typeName.equals("Psychict")) {
							typeName = "Psychic";
						}
						String weakLine = weakCols.get(k).text().substring(1);
						if(weakLine.equals("2") || weakLine.equals("4")){
							weaknesses += typeName + ", ";
						}
						if(weakLine.equals("0.5") || weakLine.equals("0.25")) {
							resistances += typeName + ", ";
						}
						if(weakLine.equals("0")) {
							immunities += typeName + ", ";
						}
						if(weakLine.equals("1")) {
							neutrals += typeName + ", ";
						}
						System.out.println(typeName + ": " + weakCols.get(k).text().substring(1));
					}
					if(weaknesses.length() > 0) {
					weaknesses = weaknesses.substring(0, (weaknesses.length() - 2));
					} else {
						weaknesses = "None";
					}
					if(resistances.length() > 0) {
					resistances = resistances.substring(0, (resistances.length() - 2));
					} else {
						resistances = "None";
					}
					if(immunities.length() > 0) {
					immunities = immunities.substring(0, (immunities.length() - 2));
					} else {
						immunities = "None";
					}
					if(neutrals.length() > 0) {
					neutrals = neutrals.substring(0, (neutrals.length() - 2));
					} else {
						neutrals = "None";
					}
					System.out.println("Weaknesses: " + weaknesses);
					System.out.println("Resistances: " + resistances);
					System.out.println("Immunities: " + immunities);
					System.out.println("Neutral Types: " + neutrals);
					localPoke.setWeaknesses(weaknesses);
					localPoke.setResistances(resistances);
					localPoke.setImmunities(immunities);
					localPoke.setNeutrals(neutrals);
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Wild Hold Item")) {
					System.out.println("Wild Hold Item Table");
					row = dexTableRows.select("tr").get(1);
					Elements cols = row.select("td");
					System.out.println("Wild Hold Item(s): " + cols.get(0).text());
					Element innerTable = row.selectFirst("table");
					if(Objects.isNull(innerTable)) {
						String eggGroupString = localPoke.getName() + " cannot breed";
						localPoke.setEggGroups(eggGroupString);
						continue;
					}
					Elements eggGroups = innerTable.select("a");
					String eggGroupString = "";
					for(Element eggGroup : eggGroups) {
						eggGroupString += eggGroup.text() + ", ";
						//String eggGroupHref = eggGroup.attr("href");
						//String storedGroup = eggGroupHref.substring((eggGroupHref.indexOf("egg/") + 4), eggGroupHref.indexOf(".shtml"));
						//storedGroup = storedGroup.substring(0, 1).toUpperCase() + storedGroup.substring(1) + ", ";
						//eggGroupString += storedGroup;
					}
					eggGroupString = eggGroupString.substring(0, (eggGroupString.length() - 2));
					localPoke.setEggGroups(eggGroupString);
					System.out.println("Egg Groups: " + eggGroupString);
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Standard Level Up")) {
					System.out.println("Level Moves Table");
					Elements rows = dexTable.select("tr");
					levelMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						if(localRow.text().equals("Standard Level Up") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %")) //These are header rows, which are out of scope.
							continue;
							else {
							Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
							if(localCol.size() == 1) {
								continue; //These are description rows, which are useless for a simple list of moves.
							} else {
								levelMoves += localCol.get(1).text() + ",";
							}
						}
					}
					levelMoves = levelMoves.substring(0, levelMoves.length() - 1);
					while(levelMoves.contains(",,")) {
						levelMoves = levelMoves.replace(",,", ",");
					}
					while(levelMoves.startsWith(" ,")) {
						levelMoves = levelMoves.substring(2);
					}
					if(levelMoves.endsWith(",")) {
						levelMoves = levelMoves.substring(0, levelMoves.length() - 1);
					}
					System.out.println(levelMoves);
					overallMoves += levelMoves + ",";
					localPoke.setLevelMoves(levelMoves);
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Technical Machine Attacks")) {
					System.out.println("TM Moves Table");
					Elements rows = dexTable.select("tr");
					tmMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						if(localRow.text().equals("TM Moves Table") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %")) //These are header rows, which are out of scope.
							continue;
							else {
							Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
							if(localCol.size() == 1) {
								continue; //These are description rows, which are useless for a simple list of moves.
							} else if(localCol.size() > 1){
								tmMoves += localCol.get(1).text() + ",";
							} else {
								continue;
							}
						}
					}
					tmMoves = tmMoves.substring(0, tmMoves.length() - 1);
					while(tmMoves.contains(",,")) {
						tmMoves = tmMoves.replace(",,", ",");
					}
					while(tmMoves.startsWith(" ,")) {
						tmMoves = tmMoves.substring(2);
					}
					if(tmMoves.endsWith(",")) {
						tmMoves = tmMoves.substring(0, tmMoves.length() - 1);
					}
					System.out.println(tmMoves);
					localPoke.setTmMoves(tmMoves);
					overallMoves = overallMoves + "," + tmMoves;
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Egg Moves (Details)")) {
					//System.out.println("Egg Moves Table"); DEBUG USE ONLY.
					Elements rows = dexTable.select("tr");
					//System.out.println(rows.get(2).text()); DEBUG USE ONLY.
					eggMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						Elements localCol = localRow.select("td");
						if(!(localRow.text().equals("Egg Moves Table") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %") || localCol.size() < 3)) { //These are header rows, which are out of scope.
								eggMoves += localCol.get(0).text() + ",";
							} else{
								continue;
							}
						}
					eggMoves = eggMoves.substring(0, eggMoves.length() - 1);
					while(eggMoves.contains(",,")) {
						eggMoves = eggMoves.replace(",,", ",");
					}
					while(eggMoves.startsWith(" ,")) {
						eggMoves = eggMoves.substring(2);
					}
					if(eggMoves.endsWith(",")) {
						eggMoves = eggMoves.substring(0, eggMoves.length() - 1);
					}
					System.out.println(eggMoves);
					localPoke.setEggMoves(eggMoves);
					overallMoves = overallMoves + "," + eggMoves;
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().contains("Move") && !(titleCol.text().contains("Egg Moves"))) {
					System.out.println("Other Moves Table");
					Elements rows = dexTable.select("tr");
					System.out.println(rows.get(0).text());
					otherMoves = "";
					for(int k = 0; k < rows.size(); k++) {
						Element localRow = rows.get(k);
						Elements localCol = localRow.select("td"); //These are where all of the right types of data are found.
						if(localRow.text().equals("Pre-Evolution Only Moves") || localRow.text().equals("Move Reminder Only Attacks") || localRow.text().equals("Special Moves") || localRow.text().equals("Level Attack Name Type Cat. Att. Acc. PP Effect %") || localCol.size() < 3) { //These are header rows, which are out of scope.
							continue;
						}
							else {
								otherMoves += localCol.get(0).text() + ",";
							}
						}
					otherMoves = otherMoves.substring(0, otherMoves.length() - 1);
					while(otherMoves.contains(",,")) {
						otherMoves = otherMoves.replace(",,", ",");
					}
					while(otherMoves.startsWith(" ,")) {
						otherMoves = otherMoves.substring(2);
					}
					if(otherMoves.endsWith(",")) {
						otherMoves = otherMoves.substring(0, otherMoves.length() - 1);
					}
					System.out.println(otherMoves);
					localPoke.setOtherMoves(otherMoves);
					overallMoves = overallMoves + "," + otherMoves;
					/*
					for(int j = 2; j < rows.size(); j++) {
						Element move = rows.get(j);
						if(move.select("td").get(1).text() != null) {
						String moveName = move.select("td").get(1).text() + ", ";
						otherMoves += moveName;
						}
					}
					overallMoves = overallMoves + ", " + otherMoves;
					*/
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Stats")) {
					//System.out.println("STATS TABLE");
					row = dexTable.select("tr").get(2);
					baseHP = Integer.parseInt(row.select("td").get(1).text());
					baseAtk = Integer.parseInt(row.select("td").get(2).text());
					baseDef = Integer.parseInt(row.select("td").get(3).text());
					baseSpAtk = Integer.parseInt(row.select("td").get(4).text());
					baseSpDef = Integer.parseInt(row.select("td").get(5).text());
					baseSpd = Integer.parseInt(row.select("td").get(6).text());
					bst = baseHP + baseAtk + baseDef + baseSpAtk + baseSpDef + baseSpd;
					localPoke.setBaseHP(baseHP);
					localPoke.setBaseAtk(baseAtk);
					localPoke.setBaseDef(baseDef);
					localPoke.setBaseSpAtk(baseSpAtk);
					localPoke.setBaseSpDef(baseSpDef);
					localPoke.setBaseSpd(baseSpd);
					localPoke.setBst(bst);
				
				}
			}
			//System.out.println(overallMoves);
			System.out.println("Assembling moveset...");
			TreeSet<String> overallMoveset = new TreeSet<String>();
			String overallMovesetString = overallMoves;
			while(overallMovesetString.indexOf(",") != -1) {
				String moveName = overallMovesetString.substring(0, overallMovesetString.indexOf(","));
				moveName = moveName.trim();
				if (!(moveName.equals(" ")) && !(moveName.equals("")))
					overallMoveset.add(moveName);
				overallMovesetString = overallMovesetString.substring(overallMovesetString.indexOf(",") + 1);
			}
				overallMoveset.add(overallMovesetString);
				if(Objects.isNull(eggMoves) || eggMoves.isBlank() || eggMoves.equals("")) {
					eggMoves = "None";
					localPoke.setEggMoves(eggMoves);
				}
				if(Objects.isNull(otherMoves) || otherMoves.isBlank()) {
					otherMoves = "None";
					localPoke.setOtherMoves(otherMoves);
				}
				localPoke.setTotalMoves(overallMoveset.toString().trim());
			//localPoke.setotherMoves(otherMoves);
				System.out.println(localPoke.getTotalMoves());
			/*
			Pokemon dbSample = session.get(Pokemon.class, poke.getPokeName());
			 
			if (!(Objects.isNull(dbSample)) && dbSample.getPokeName().equals(poke.getPokeName()) && dbSample.equals(poke)) {
				System.out.println("Nothing to do here.");
				continue;
				}
			*/
			/*
			session.beginTransaction();
			session.merge(localPoke);
			session.getTransaction().commit();
			*/
			localPokeList.add(localPoke);
			if(!Objects.isNull(localPokeAlola)) {
			variantPokeList.add(localPokeAlola);
			}
			if(!Objects.isNull(localPokeGalar)) {
				variantPokeList.add(localPokeGalar);
				}
			if(!Objects.isNull(localPokeHisui)) {
				variantPokeList.add(localPokeHisui);
				}
			if(!Objects.isNull(localPokePaldea)) {
				variantPokeList.add(localPokePaldea);
				}
			if(!Objects.isNull(localPokeOther1)) {
				variantPokeList.add(localPokeOther1);
				}
			if(!Objects.isNull(localPokeOther2)) {
				variantPokeList.add(localPokeOther2);
				}
			if(!Objects.isNull(localPokeOther3)) {
				variantPokeList.add(localPokeOther3);
				}
			if(!Objects.isNull(localPokeOther4)) {
				variantPokeList.add(localPokeOther4);
				}
			if(!Objects.isNull(localPokeOther5)) {
				variantPokeList.add(localPokeOther5);
				}
			
			for(Pokemon poke : localPokeList) {
				if(!(Objects.isNull(poke))) {
			Pokemon dbSample = session.get(Pokemon.class, poke.getName());
			if(Objects.isNull(dbSample)) {
				System.out.println("There is a new database entry!");
				session.beginTransaction();
				session.persist(poke);
				session.getTransaction().commit();
				continue;
			} else if (!(dbSample.toString().equals(poke.toString()))){
				System.out.println("There is nothing to do.");
				continue;
				} else {
					session.beginTransaction();
					session.merge(poke);
					session.getTransaction().commit();
					continue;
				}
		}
		}
		}
		if(!Objects.isNull(variantPokeList)) {
			for(Pokemon poke : variantPokeList) {
				System.out.println(poke.toString());
			}
			System.out.println(variantPokeList.size() + " distinct variant forms catalogued.");
			}
			
			/*
			 * How do we want to handle the move lists?
			 * Current implementation calls for moves stored as Strings (for their names). Probably best to do this.
			 * Best to one-to-many relation moves to the pokemon who can learn them.
			 * 
			 * 
			 */
	}
	
	public static void attackFinder(Session session) throws IOException{
		/*
		String validatorHQL = "FROM Move";
		List<Move> results = session.createQuery(validatorHQL).list();
		*/
		ArrayList<Move> results = MoveHelpers.attackDexGenerator();
		
		for (Move move : results) {
			String learnset = "";
			TreeSet<String> learnsetTree = new TreeSet<String>();
			Move inputMove = new Move(move.getName());
			String moveName = inputMove.getName(); //Working on this part to dynamically fill the dex!
			Document dexEntry = MoveHelpers.urlHelper(moveName);
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
					inputMove = MoveHelpers.attackNameTableHelper(inputMove, dexTable);
					}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Physical Contact")) {
					inputMove = MoveHelpers.physicalContactTableHelper(inputMove, dexTable);	
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
			
			learnset = learnsetTree.toString();
			if(learnset.isEmpty() || learnset.equals("[]")) {
				learnset = "None";
			}
			System.out.println("Pokemon that can currently learn this move in Gen 9: " + learnset);
			inputMove.setLearnset(learnset);
			System.out.println(inputMove.toString());
				
				Move dbSample = session.get(Move.class, moveName);
				 
				if (!(Objects.isNull(dbSample)) && dbSample.getName().equals(inputMove.getName()) && dbSample.toString().equals(inputMove.toString())) {
					System.out.println("Nothing to do here.");
					continue;
					} 
				else if(Objects.isNull(dbSample)) {
					System.out.println("There is a new database entry!");
					session.beginTransaction();
					session.persist(inputMove);
					session.getTransaction().commit();
					continue;
				}
				else if (!(dbSample.toString().equals(inputMove.toString()))){
					System.out.println("There is new data!");
					session.beginTransaction();
					session.merge(inputMove);
					session.getTransaction().commit();
					}
	}
}
	
	public static void databaseInitializer(String id) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
			switch (id){ //This set of options should be used for populating the database.	
			case "pokemon" :
				session = HibernateUtil.getSessionFactory().openSession();
				dexFinder(session);
				session.close();
				break;
				
			case "abilities" :
				session = HibernateUtil.getSessionFactory().openSession();
				abilityFinder(session);
				session.close();
				break;
				
			case "moves" :
				session = HibernateUtil.getSessionFactory().openSession();
				attackFinder(session);
				session.close();
				break;
				
				
			case "all" :
				session = HibernateUtil.getSessionFactory().openSession();
				dexFinder(session);
				abilityFinder(session);
				attackFinder(session);
				session.close();
				break;
				
			default :
				System.out.println("Fully populating the database. This may take a while, please be patient.");
				session = HibernateUtil.getSessionFactory().openSession();
				dexFinder(session);
				abilityFinder(session);
				attackFinder(session);
				session.close();
				System.out.println("Databases populated!");
				break;
			}
	
}
	
}
