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

@Configuration
public class DatabasePrep {
	
	public static void abilityFinder(Session session) throws IOException {
		Document abiliDex = Jsoup.connect("https://www.serebii.net/abilitydex/").get();
		Elements abilitiesDex = abiliDex.select("option");
		List<TextNode> abilities = abilitiesDex.textNodes();
		List<Ability> results = new ArrayList<Ability>();
		System.out.println(abilitiesDex.eachText());
		ArrayList<String> abilitiesDexOverall = new ArrayList<String>();
		System.out.println("Inital ability dex data:");
		
		for(TextNode x : abilities) {
				if(!(x.text().startsWith("AbilityDex"))) {
					abilitiesDexOverall.add(x.text());
					System.out.println(x.text());
					Ability dbSample = session.get(Ability.class, x.text());
					Ability localAbility = new Ability();
					localAbility.setAbilityName(x.text());
					results.add(localAbility);
					if (!(Objects.isNull(dbSample)) && dbSample.getAbilityName().equals(localAbility.getAbilityName())) {
						System.out.println("Nothing to do here.");
						continue;
					} else {
		}
		} else {
			continue;
		}
	
	}
		System.out.println(results.toString());
		System.out.println("Starting abilityFiller");
		//String validatorHQL = "FROM Ability";
		//List<Ability> results = session.createQuery(validatorHQL).list();
		for (Ability ability : results) {
			Ability localAbility = new Ability();
			localAbility.setAbilityName(ability.getAbilityName());
			String abilityName = localAbility.getAbilityName().toLowerCase().replace("%20", "").replace(" ", "");
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
			Ability dbSample = session.get(Ability.class, localAbility.getAbilityName());
			System.out.println(localAbility.toString());
		if(!(Objects.isNull(dbSample)) && dbSample.toString().equals(localAbility.toString())) {
			System.out.println(localAbility.getAbilityName() + ": already in database. There is nothing to do here.");
			continue;
		} else if (Objects.isNull(dbSample)){
			session.beginTransaction();
			session.persist(localAbility);
			session.getTransaction().commit();
			continue;
		}
		else if(!(Objects.isNull(dbSample)) && !(dbSample.toString().equals(localAbility.toString()))) {
			session.beginTransaction();
			session.merge(localAbility);
			session.getTransaction().commit();
			continue;
		} 
		
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
			String pokeName = localPoke.getPokeName(); //Working on this part to dynamically fill the dex!
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
								for(Element types : typeTable) {
									/*
									 * Element typeCol = cols.get(cols.size() - 1);
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
									 */
									System.out.println(types.text());
								}
								
								if(typeData.text().contains("Paldean")) {
									localPokePaldea = new Pokemon(localPoke.getPokeName());
									localPokePaldea.setPokeName("Paldean " + localPokePaldea.getPokeName());
									System.out.println(localPokePaldea.getPokeName());
									if(localPoke.getPokeName().equals("Tauros")) {
										localPokeOther1 = new Pokemon(localPokePaldea.getPokeName() + " - Blaze Breed");
										localPokeOther2 = new Pokemon(localPokePaldea.getPokeName() + " - Aqua Breed");
										System.out.println(localPokeOther1.getPokeName());
										System.out.println(localPokeOther2.getPokeName());
									}
								}
								if(typeData.text().contains("Hisuian")) {
									localPokeHisui = new Pokemon(localPoke.getPokeName());
									localPokeHisui.setPokeName("Hisuian " + localPokeHisui.getPokeName());
									System.out.println(localPokeHisui.getPokeName());
								}
								if(typeData.text().contains("Galarian")) {
									localPokeGalar = new Pokemon(localPoke.getPokeName());
									localPokeGalar.setPokeName("Galarian " + localPokeGalar.getPokeName());
									System.out.println(localPokeGalar.getPokeName());
								}
								if(typeData.text().contains("Alolan")) {
									localPokeAlola = new Pokemon(localPoke.getPokeName());
									localPokeAlola.setPokeName("Alolan " + localPokeAlola.getPokeName());
									System.out.println(localPokeAlola.getPokeName());
								} 
								else if(typeData.text().contains("Sensu")) { //Oricorio's forms
									localPoke.setPokeName("Oricorio - Baile Style");
									localPokeOther1 = new Pokemon("Oricorio - Pom-Pom Style");
									localPokeOther2 = new Pokemon("Oricorio - Pa'u Style");
									localPokeOther3 = new Pokemon("Oricorio - Sensu Style");
								}
								else if(typeData.text().contains("Hoopa")) { //Hoopa's 2 forms
									localPoke.setPokeName("Hoopa Confined");
									localPokeOther1 = new Pokemon("Hoopa Unbound");
								}
								else if(typeData.text().contains("Calyrex")){ //Calyrex and 2 forms
									localPokeOther1 = new Pokemon(localPoke.getPokeName() + " - Ice Rider");
									localPokeOther2 = new Pokemon(localPoke.getPokeName() + " - Shadow Rider");
								}
								else if(typeData.text().contains("Crowned")) { //Zacian and Zamazenta
									localPokeOther1 = new Pokemon(localPoke.getPokeName() + " - Crowned Form");
									localPoke.setPokeName(localPoke.getPokeName() + " - Hero of Many Battles");
								}
								else if(typeData.text().contains("Style")){ //Urshifu
									localPoke.setPokeName("Urshifu - Single Strike Style");
									localPokeOther1 = new Pokemon("Urshifu - Rapid Strike Style");
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
						String eggGroupString = localPoke.getPokeName() + " cannot breed";
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
			/*localPokeList.add(localPokeAlola);
			localPokeList.add(localPokeGalar);
			localPokeList.add(localPokeHisui);
			localPokeList.add(localPokePaldea);
			localPokeList.add(localPokeOther1);
			localPokeList.add(localPokeOther2);
			localPokeList.add(localPokeOther3);
			localPokeList.add(localPokeOther4);
			localPokeList.add(localPokeOther5);
			*/
			for(Pokemon poke : localPokeList) {
				if(!(Objects.isNull(poke))) {
			Pokemon dbSample = session.get(Pokemon.class, poke.getPokeName());
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
		Move move = new Move();
		move.setMoveName(s);
		results.add(move);
		}
		for (Move move : results) {
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
				 
				if (!(Objects.isNull(dbSample)) && dbSample.getMoveName().equals(inputMove.getMoveName()) && dbSample.toString().equals(inputMove.toString())) {
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
