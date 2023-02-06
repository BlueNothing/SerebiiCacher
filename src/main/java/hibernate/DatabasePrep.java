package hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

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
			AbilityHelpers.dbPersist(localAbility, session);
		
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
		 * 
		 * 
		 * TODO: Figure out why CapRate, Classification, EggSteps, Height, Weight are not evaluating properly.
		*/
		ArrayList<Pokemon> variantPokeList = new ArrayList<Pokemon>();
		Document palDex = Jsoup.connect("https://www.serebii.net/pokedex-sv/").get();
		Elements palDexElems = palDex.select("option");
		List<String> palDexData = palDexElems.eachText();
		ArrayList<String> palDexOverall = new ArrayList<String>();
		List<String> results = new ArrayList<String>();
		ArrayList<String> debug1 = new ArrayList<String>();
		ArrayList<String> debug2 = new ArrayList<String>();
		ArrayList<String> debug3 = new ArrayList<String>();
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
			String imgUrl = "serebii.net";
			
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
				//System.out.println("TITLE COLUMN: " + titleCol.text()); //Useful for debugging.
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Picture")) {
					row = dexTableRows.select("tr").get(1);
					Element imgTable = row.selectFirst("td");
					Element img = imgTable.selectFirst("img");
					System.out.println(img.toString());
					System.out.println(img.attributes().toString());
					if(!img.attr("src").toString().isBlank()) {
						//System.out.println(img.attr("src"));
						imgUrl = imgUrl + img.attr("src");
					} else if(!img.attr("id").toString().isBlank()) {
						//System.out.println(img.attr("id").toString());
						imgUrl = imgUrl + img.attr("id");
					}
					localPoke.setImgURL(imgUrl);
					
				}
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
						System.out.println("SubTitle: " + subTitle.text());
						if(j == 0) {
						Element typeData = cols.select(".cen").get(0);
						Element typeTableFrame = typeData.selectFirst("tbody");
						if(Objects.isNull(typeTableFrame) && !Objects.isNull(typeData)) {
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
						else if(!Objects.isNull(typeTableFrame) && !Objects.isNull(typeData)) {
							//System.out.println(typeData.html());
							System.out.println(typeData.text());
							Elements typeTable = typeTableFrame.select("tr");
							ArrayList<String> descriptors = new ArrayList<String>();
							ArrayList<String> typeSets = new ArrayList<String>();
							for(Element typeSet : typeTable) {
								String descriptor = typeSet.select("td").get(0).text();
								descriptors.add(descriptor);
								System.out.println("Descriptor: " + descriptor);
								Element typeCol = typeSet.select("td").get(1);
								System.out.println("Type Column: " + typeCol.toString());
								Elements types = typeCol.select("a");
								System.out.println("Type text: " + types.text());
								System.out.println("Type String: " + types.toString());
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
								typeSets.add(typeList);
								System.out.println("Base Form Type: " + typeSets.get(0));
								for(int z = 0; z < typeSets.size(); z++) {
										String check = descriptors.get(z);
										switch(check) {
										case "Normal":
											localPoke.setPokeTypes(typeSets.get(z));
											break;
										
										case "Paldean":
											localPokePaldea = new Pokemon(localPoke.getName());
											localPokePaldea.setName("Paldean " + localPokePaldea.getName());
											System.out.println(localPokePaldea.getName());
											localPokePaldea.setPokeTypes(typeSets.get(z));
											break;
										
										case "Blaze Breed":
											localPokeOther1 = new Pokemon(localPokePaldea.getName() + " - Blaze Breed");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
											
										case "Aqua Breed":
											localPokeOther2 = new Pokemon(localPokePaldea.getName() + " - Aqua Breed");
											localPokeOther2.setPokeTypes(typeSets.get(z));
											break;
											
										case "Hisuian":
											localPokeHisui = new Pokemon(localPoke.getName());
											localPokeHisui.setName("Hisuian " + localPokeHisui.getName());
											System.out.println(localPokeHisui.getName());
											localPokeHisui.setPokeTypes(typeSets.get(z));
											break;
										
										case "Galarian":
											localPokeGalar = new Pokemon(localPoke.getName());
											localPokeGalar.setName("Galarian " + localPokeGalar.getName());
											System.out.println(localPokeGalar.getName());
											localPokeGalar.setPokeTypes(typeSets.get(z));
											break;
										
										case "Alolan":
											localPokeAlola = new Pokemon(localPoke.getName());
											localPokeAlola.setName("Alolan " + localPokeAlola.getName());
											System.out.println(localPokeAlola.getName());
											localPokeAlola.setPokeTypes(typeSets.get(z));
											break;
											
										case "Baile Style":
											localPoke.setName("Oricorio - Baile Style");
											localPoke.setPokeTypes(typeSets.get(z));
											break;
											
										case "Pom-Pom Style":
											localPokeOther1 = new Pokemon("Oricorio - Pom-Pom Style");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
											
										case "Pa'u Style":
											localPokeOther2 = new Pokemon("Oricorio - Pa'u Style");
											localPokeOther2.setPokeTypes(typeSets.get(z));
											break;
											
										case "Sensu Style":
											localPokeOther3 = new Pokemon("Oricorio - Sensu Style");
											localPokeOther3.setPokeTypes(typeSets.get(z));
											break;
										
										case "Hoopa Confined":
											localPoke.setName("Hoopa Confined");
											localPoke.setPokeTypes(typeSets.get(z));
											break;
										
										case "Hoopa Unbound":
											localPokeOther1 = new Pokemon("Hoopa Unbound");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											
										case "Calyrex":
											localPoke.setPokeTypes(typeSets.get(z));
											break;
											
										case "Ice Rider":
											localPokeOther1 = new Pokemon(localPoke.getName() + " - Ice Rider");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
										
										case "Shadow Rider":
											localPokeOther2 = new Pokemon(localPoke.getName() + " - Shadow Rider");
											localPokeOther2.setPokeTypes(typeSets.get(z));
											break;
											
										case "Hero of Many Battles":
											localPoke.setName(localPoke.getName());
											localPoke.setPokeTypes(typeSets.get(z));
											break;
											
										case "Crowned Sword":
											localPokeOther1 = new Pokemon(localPoke.getName() + " - Crowned Sword");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
										
										case "Crowned Shield":
											localPokeOther1 = new Pokemon(localPoke.getName() + " - Crowned Shield");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
											
										case "Single Strike Style":
											localPoke.setName(localPoke.getName() + " - Single Strike Style");
											localPoke.setPokeTypes(typeSets.get(z));
											break;
											
										case "Rapid Strike Style":
											localPokeOther1 = new Pokemon("Urshifu - Rapid Strike Style");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
											
										case "Aria Forme":
											localPoke.setName(localPoke.getName() + " - " + descriptors.get(z));
											localPoke.setPokeTypes(typeSets.get(z));
											break;
											
										case "Pirouette Forme":
											localPokeOther1 = new Pokemon("Meloetta - Pirouette Forme");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
											
										case "Rotom":
											localPoke.setPokeTypes(typeSets.get(z));
											break;
										
										case "Frost Rotom":
											localPokeOther1 = new Pokemon(localPoke.getName() + " - " + "Frost");
											localPokeOther1.setPokeTypes(typeSets.get(z));
											break;
										
										case "Heat Rotom":
											localPokeOther2 = new Pokemon(localPoke.getName() + " - " + "Heat");
											localPokeOther2.setPokeTypes(typeSets.get(z));
											break;
											
										case "Mow Rotom":
											localPokeOther3 = new Pokemon(localPoke.getName() + " - " + "Mow");
											localPokeOther3.setPokeTypes(typeSets.get(z));
											break;
											
										case "Fan Rotom":
											localPokeOther4 = new Pokemon(localPoke.getName() + " - " + "Fan");
											localPokeOther4.setPokeTypes(typeSets.get(z));
											break;
											
										case "Wash Rotom":
											localPokeOther5 = new Pokemon(localPoke.getName() + " - " + "Wash");
											localPokeOther5.setPokeTypes(typeSets.get(z));
											break;
											
										default:
											debug1.add(localPoke.getName());
										}
										}
								}
								}
								
						}
						
						if((localPoke.getName().contains("Meowth") || localPoke.getName().contains("Calyrex")) && false) { //Haven't figured out how to select for these cases just yet.
							debug1.add("Found Meowth Classification table.");
							System.out.println("Classification sub-table");
							col = cols.get(0);
							String classification = col.text();
							if(localPoke.getName().contains("Meowth")) {
								debug1.add(cols.text());
							}
							if(localPoke.getName().contains("Calyrex")) {
								debug2.add(cols.text());
							}
						}
						else if(subTitle.text().contains("Classification") ) {
							System.out.println("Classification sub-table");
							col = cols.get(0);
							String classification = col.text();
							if(col.text().contains(")")) {
							ArrayList<String> classifications = new ArrayList<String>();
							ArrayList<String> regionData = new ArrayList<String>();
							while(classification.contains(")")) {
								int endex = classification.indexOf(")");
								classifications.add(classification.substring(0, classification.indexOf("(")));
								regionData.add(classification.substring(classification.indexOf("(") + 1, classification.indexOf(")")));
								if(classification.length() > endex) {
								classification = classification.substring(endex + 1);
								} else {
									classification = "";
								}
							}
							localPoke.setClassification(classifications.get(0));
							switch(regionData.get(1)) {
							case "Hisuian":
								localPokeHisui.setClassification(classifications.get(1));
								break;
							
							case "Alolan":
								localPokeAlola.setClassification(classifications.get(1));
								break;
			
							case "Galarian":
								localPokeGalar.setClassification(classifications.get(1));
								break;
							
							case "Paldean":
								localPokePaldea.setClassification(classifications.get(1));
								break;
							
							default:
								if(!Objects.isNull(localPokeOther1)) {
								localPokeOther1.setClassification(classifications.get(1));
								} else {
								localPoke.setClassification(col.text());
								}
						}
							//System.out.println("Classifications: " + classifications.toString());
							//debug2.add(classifications.toString());
							} 
							else{
							System.out.println("Classification: " + classification);
							localPoke.setClassification(classification);
							if(!Objects.isNull(localPokeAlola) && localPokeAlola.getClassification() == null) {
								localPokeAlola.setClassification(classification);
							}
							if(!Objects.isNull(localPokeGalar) && localPokeGalar.getClassification() == null) {
								localPokeGalar.setClassification(classification);
							}
							if(!Objects.isNull(localPokeHisui) && localPokeHisui.getClassification() == null) {
								localPokeHisui.setClassification(classification);
							}
							if(!Objects.isNull(localPokePaldea) && localPokePaldea.getClassification() == null) {
								localPokePaldea.setClassification(classification);
							}
							if(!Objects.isNull(localPokeOther1) && localPokeOther1.getClassification() == null) {
								localPokeOther1.setClassification(classification);
							}
							if(!Objects.isNull(localPokeOther2) && localPokeOther2.getClassification() == null) {
								localPokeOther2.setClassification(classification);
							}
							if(!Objects.isNull(localPokeOther3) && localPokeOther3.getClassification() == null) {
								localPokeOther3.setClassification(classification);
							}
							if(!Objects.isNull(localPokeOther4) && localPokeOther4.getClassification() == null) {
								localPokeOther4.setClassification(classification);
							}
							if(!Objects.isNull(localPokeOther5) && localPokeOther5.getClassification() == null) {
								localPokeOther5.setClassification(classification);
							}
							System.out.println("Classification set!");
							}
							col = cols.get(1);
							String height = col.text();
							height = height.substring(0, (height.indexOf("\"") + 1));
							System.out.println("Height: " + height);
							localPoke.setHeight(height);
							
							col = cols.get(2);
							String weight = col.text();
							weight = weight.substring(0, weight.indexOf("lbs") + 2);
							weight = weight.replace("lb", " lbs");
							System.out.println("Weight: " + weight);
							localPoke.setWeight(weight);
							
							col = cols.get(3);
							double capRate = Double.parseDouble(col.text());
							System.out.println("Capture Rate: " + capRate);
							localPoke.setCapRate(capRate);
							
							col = cols.get(4);
							String eggStepString = col.text().replaceAll(",", "");
							int eggSteps = Integer.parseInt(eggStepString);
							System.out.println("Egg Steps: " + eggSteps);
							localPoke.setEggSteps(eggSteps);
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
							ArrayList<String> abilityListAlola = new ArrayList<String>();
							ArrayList<String> abilityListGalar = new ArrayList<String>();
							ArrayList<String> abilityListHisui = new ArrayList<String>();
							ArrayList<String> abilityListPaldea = new ArrayList<String>();
							ArrayList<String> abilityListOther1 = new ArrayList<String>();
							ArrayList<String> abilityListOther2 = new ArrayList<String>();
							System.out.println("Ability Elements - " + abilityElements.text());
							
							/*Example for Lycanroc:
							 * Raw data: "Abilities: Keen Eye - Sand Rush - Steadfast (Hidden) (Midday Form) - Keen Eye- Vital Spirit - No Guard (Hidden) (Midnight Form) - Tough Claws (Dusk Form)"
							 * Ability Elements Output - "Ability Elements - Keen Eye Sand Rush Hidden Ability Steadfast Midnight Form Abilities Keen Eye Vital Spirit Hidden Ability No Guard Dusk Form Abilities Tough Claws"
							As elements: <b>Keen Eye</b> <b>Sand Rush</b> <b>Hidden Ability</b> <b>Steadfast</b> <b>Midnight Form Abilities</b> <b>Keen Eye</b> <b>Vital Spirit</b> <b>Hidden Ability</b> <b>No Guard</b> <b>Dusk Form Abilities</b> <b>Tough Claws</b>
							 */
							String form = "Normal";
							boolean hiddenNext = false;
							for(int eleInd = 0; eleInd < abilityElements.size(); eleInd++) {
								Element ele = abilityElements.get(eleInd);
								System.out.println(ele.toString());
								if(ele.text().equals("Alola Form Abilities")) {
									form = "Alola";
									continue;
								}
								if(ele.text().equals("Galarian Form Abilities")) {
									form = "Galar";
									continue;
								}
								if(ele.text().equals("Hisuian Form Abilities")) {
									form = "Hisui";
									continue;
								}
								if(ele.text().equals("Paldean Form Abilities")) {
									form = "Paldea";
									continue;
								}
								if(!ele.text().equals("Hidden Ability") && (ele.text().equals("Therian Forme Ability") || ele.text().contains("Abilities") || (ele.text().contains("Ability") && !ele.text().equals("Adaptability")))) {
									form = "Other";
									continue;
								}
								
								if(form.equals("Normal")) {	
								if(ele.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityList.add(ele.text());
								} else if(hiddenNext) {
									abilityList.add("(H) " + ele.text());
									hiddenNext = false;
								}
							} else if(form.equals("Alola")) {
								if(ele.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityListAlola.add(ele.text());
								} else if(hiddenNext) {
									abilityListAlola.add("(H) " + ele.text());
									hiddenNext = false;
								}
							} else if(form.equals("Galar")) {
								if(ele.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityListGalar.add(ele.text());
								} else if(hiddenNext) {
									abilityListGalar.add("(H) " + ele.text());
									hiddenNext = false;
								}
							} else if(form.equals("Hisui")) {
								if(ele.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityListHisui.add(ele.text());
								} else if(hiddenNext) {
									abilityListHisui.add("(H) " + ele.text());
									hiddenNext = false;
								}
							} else if(form.equals("Paldea")) {
								if(ele.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityListPaldea.add(ele.text());
								} else if(hiddenNext) {
									abilityListPaldea.add("(H) " + ele.text());
									hiddenNext = false;
								}
							} else if(form.equals("Other")) {
								if(ele.text().equals("Hidden Ability")) {
									hiddenNext = true;
									continue;
								}
								else if(!hiddenNext) {
									abilityListOther1.add(ele.text());
								} else if(hiddenNext) {
									abilityListOther1.add("(H) " + ele.text());
									hiddenNext = false;
								}
							}
							}
							String abilities = abilityList.toString();
							abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
							localPoke.setAbilities(abilities);
							
							if(abilityListAlola.size() > 0) {
								abilities = abilityListAlola.toString();
								abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
								localPokeAlola.setAbilities(abilities);
							}
							if(abilityListGalar.size() > 0) {
								abilities = abilityListGalar.toString();
								abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
								localPokeGalar.setAbilities(abilities);
							}
							if(abilityListHisui.size() > 0) {
								abilities = abilityListHisui.toString();
								abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
								localPokeHisui.setAbilities(abilities);
							}
							if(abilityListPaldea.size() > 0) {
								abilities = abilityListPaldea.toString();
								abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
								localPokePaldea.setAbilities(abilities);
							}
							if(abilityListOther1.size() > 0 && !Objects.isNull(localPokeOther1)) {
								abilities = abilityListOther1.toString();
								abilities = abilities.substring(1, abilities.length() - 1); //Remove the brackets.
								localPokeOther1.setAbilities(abilities);
							}
							if(abilityListOther1.size() > 0 && Objects.isNull(localPokeOther1)) {
								localPokeOther1 = new Pokemon(localPoke);
								localPokeOther1.setName(localPokeOther1.getName() + " - Ability Variant");
								abilities = abilityListOther1.toString();
								abilities = abilities.substring(1, abilities.length() - 1);
								localPokeOther1.setAbilities(abilities);
							}
							if(abilityListOther1.size() == 0 && !Objects.isNull(localPokeOther1)){
								if(!Objects.isNull(localPokeOther1)) {
									localPokeOther1.setAbilities(localPoke.getAbilities());
								}
								if(!Objects.isNull(localPokeOther2)) {
									localPokeOther2.setAbilities(localPoke.getAbilities());
								}
								if(!Objects.isNull(localPokeOther3)) {
									localPokeOther3.setAbilities(localPoke.getAbilities());
								}
								if(!Objects.isNull(localPokeOther4)) {
									localPokeOther4.setAbilities(localPoke.getAbilities());
								}
								if(!Objects.isNull(localPokeOther5)) {
									localPokeOther5.setAbilities(localPoke.getAbilities());
								}
							}
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
									localPokeOther1.setEvRewardAmt(2);
									localPoke.setEvRewardAttr("Sp. Attack [M]");
									localPokeOther1.setEvRewardAttr("Sp. Defense [F]");
									continue;
								}
								if(evString.contains("Alolan") || evString.contains("Galarian") || evString.contains("Hisuian") || evString.contains("Paldean")) {
									localPoke.setEvRewardAmt(Integer.parseInt(Character.toString(evString.charAt(0))));
									localPoke.setEvRewardAttr(evString.substring(2, evString.indexOf(" ", 2)));
									if(evString.indexOf("Alola") != -1) {
										System.out.println((evString.charAt(evString.indexOf("Alola") + 11)));
										debug3.add("Alola - " + localPoke.getName() + " - " + evString.substring(evString.indexOf("Alola") + 11));
										String substring = evString.substring(evString.indexOf("Alola") + 11);
										localPokeAlola.setEvRewardAmt(Integer.parseInt(Character.toString((substring.charAt(0)))));
										localPokeAlola.setEvRewardAttr(substring.substring(2, substring.indexOf(" ", 2)));
										if(substring.substring(2, substring.indexOf(" ", 2)).equals("Sp.")) {
											String evRewAttr = "Sp. " + substring.substring(6, substring.indexOf(" ", 6));
										}
										//localPokeAlola.setEvRewardAttr(evString);
									}
									if(evString.indexOf("Galarian") != -1) {
										System.out.println((evString.charAt(evString.indexOf("Galarian") + 14)));
										debug3.add("Galar - " + localPoke.getName() + " - " + (evString.substring(evString.indexOf("Galarian") + 14)));
										String substring = evString.substring(evString.indexOf("Galarian") + 14);
										localPokeGalar.setEvRewardAmt(Integer.parseInt(Character.toString((substring.charAt(0)))));
										localPokeGalar.setEvRewardAttr(substring.substring(2, substring.indexOf(" ", 2)));
										if(substring.substring(2, substring.indexOf(" ", 2)).equals("Sp.")) {
											String evRewAttr = "Sp. " + substring.substring(6, substring.indexOf(" ", 6));
											debug3.add(localPoke.getName() + " - " + evRewAttr);
										}
									}
									//debug2.add(localPoke.getName()); Meowth, Slowpoke, Slowbro, Articuno, Zapdos, Moltres, Slowking
									//System.out.println("SPECIAL CASE");
									continue;
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
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().contains("Damage Taken")) {
					System.out.println("ALTERNATE WEAKNESS TABLE");
					debug2.add(localPoke.getName());
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
					debug1.add(localPoke.getName());
					System.out.println("Weaknesses: " + weaknesses);
					System.out.println("Resistances: " + resistances);
					System.out.println("Immunities: " + immunities);
					System.out.println("Neutral Types: " + neutrals);
					String subspecies = titleCol.text();
					subspecies = subspecies.substring(subspecies.indexOf("Damage Taken ") + 13);
					debug2.add(subspecies);
					//localPoke.setWeaknesses(weaknesses);
					//localPoke.setResistances(resistances);
					//localPoke.setImmunities(immunities);
					//localPoke.setNeutrals(neutrals);
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
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Alola Form Level Up")) {
					if(Objects.isNull(localPokeAlola)) {
						localPokeAlola = new Pokemon(localPoke);
					}
					System.out.println("Level Moves Table - Alola");
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
					localPokeAlola.setLevelMoves(levelMoves);
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Galarian Form Level Up")) {
					if(Objects.isNull(localPokeGalar)) {
						localPokeGalar = new Pokemon(localPoke);
					}
					System.out.println("Level Moves Table - Galar");
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
					localPokeGalar.setLevelMoves(levelMoves);
				}
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Hisuian Form Level Up")) {
					if(Objects.isNull(localPokeHisui)) {
						localPokeHisui = new Pokemon(localPoke);
					}
					System.out.println("Level Moves Table - Hisui");
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
					localPokeHisui.setLevelMoves(levelMoves);
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().equals("Paldean Form Level Up")) {
					if(Objects.isNull(localPokePaldea)) {
						localPokePaldea = new Pokemon(localPoke);
					}
					System.out.println("Level Moves Table - Paldea");
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
					localPokePaldea.setLevelMoves(levelMoves);
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
					
					if(localPoke.getName().contains("Oricorio")) {
						localPokeOther1.setBaseHP(baseHP);
						localPokeOther1.setBaseAtk(baseAtk);
						localPokeOther1.setBaseDef(baseDef);
						localPokeOther1.setBaseSpAtk(baseSpAtk);
						localPokeOther1.setBaseSpDef(baseSpDef);
						localPokeOther1.setBaseSpd(baseSpd);
						localPokeOther1.setBst(bst);
						
						localPokeOther2.setBaseHP(baseHP);
						localPokeOther2.setBaseAtk(baseAtk);
						localPokeOther2.setBaseDef(baseDef);
						localPokeOther2.setBaseSpAtk(baseSpAtk);
						localPokeOther2.setBaseSpDef(baseSpDef);
						localPokeOther2.setBaseSpd(baseSpd);
						localPokeOther2.setBst(bst);
						
						localPokeOther3.setBaseHP(baseHP);
						localPokeOther3.setBaseAtk(baseAtk);
						localPokeOther3.setBaseDef(baseDef);
						localPokeOther3.setBaseSpAtk(baseSpAtk);
						localPokeOther3.setBaseSpDef(baseSpDef);
						localPokeOther3.setBaseSpd(baseSpd);
						localPokeOther3.setBst(bst);
					}
					if(localPoke.getName().contains("Greninja")) {
						localPokeOther1.setBaseHP(baseHP);
						localPokeOther1.setBaseAtk(baseAtk);
						localPokeOther1.setBaseDef(baseDef);
						localPokeOther1.setBaseSpAtk(baseSpAtk);
						localPokeOther1.setBaseSpDef(baseSpDef);
						localPokeOther1.setBaseSpd(baseSpd);
						localPokeOther1.setBst(bst);
					}
				
				}
				
				if(!(Objects.isNull(titleCol.text())) && titleCol.text().contains("Stats") && !titleCol.text().equals("Stats")) {
					
					row = dexTable.select("tr").get(2);
					baseHP = Integer.parseInt(row.select("td").get(1).text());
					baseAtk = Integer.parseInt(row.select("td").get(2).text());
					baseDef = Integer.parseInt(row.select("td").get(3).text());
					baseSpAtk = Integer.parseInt(row.select("td").get(4).text());
					baseSpDef = Integer.parseInt(row.select("td").get(5).text());
					baseSpd = Integer.parseInt(row.select("td").get(6).text());
					bst = baseHP + baseAtk + baseDef + baseSpAtk + baseSpDef + baseSpd;
					if(titleCol.text().indexOf("Alolan") != -1) {
						localPokeAlola.setBaseHP(baseHP);
						localPokeAlola.setBaseAtk(baseAtk);
						localPokeAlola.setBaseDef(baseDef);
						localPokeAlola.setBaseSpAtk(baseSpAtk);
						localPokeAlola.setBaseSpDef(baseSpDef);
						localPokeAlola.setBaseSpd(baseSpd);
						localPokeAlola.setBst(bst);
					} else if(titleCol.text().indexOf("Galarian") != -1) {
						localPokeGalar.setBaseHP(baseHP);
						localPokeGalar.setBaseAtk(baseAtk);
						localPokeGalar.setBaseDef(baseDef);
						localPokeGalar.setBaseSpAtk(baseSpAtk);
						localPokeGalar.setBaseSpDef(baseSpDef);
						localPokeGalar.setBaseSpd(baseSpd);
						localPokeGalar.setBst(bst);
					} else if(titleCol.text().indexOf("Hisuian") != -1) {
						localPokeHisui.setBaseHP(baseHP);
						localPokeHisui.setBaseAtk(baseAtk);
						localPokeHisui.setBaseDef(baseDef);
						localPokeHisui.setBaseSpAtk(baseSpAtk);
						localPokeHisui.setBaseSpDef(baseSpDef);
						localPokeHisui.setBaseSpd(baseSpd);
						localPokeHisui.setBst(bst);
					} else if(titleCol.text().indexOf("Paldean") != -1) {
						localPokePaldea.setBaseHP(baseHP);
						localPokePaldea.setBaseAtk(baseAtk);
						localPokePaldea.setBaseDef(baseDef);
						localPokePaldea.setBaseSpAtk(baseSpAtk);
						localPokePaldea.setBaseSpDef(baseSpDef);
						localPokePaldea.setBaseSpd(baseSpd);
						localPokePaldea.setBst(bst);
						
						if(!Objects.isNull(localPokeOther1)) {
							localPokeOther1.setBaseHP(baseHP);
							localPokeOther1.setBaseAtk(baseAtk);
							localPokeOther1.setBaseDef(baseDef);
							localPokeOther1.setBaseSpAtk(baseSpAtk);
							localPokeOther1.setBaseSpDef(baseSpDef);
							localPokeOther1.setBaseSpd(baseSpd);
							localPokeOther1.setBst(bst);
					}
						if(!Objects.isNull(localPokeOther2)) {
							localPokeOther2.setBaseHP(baseHP);
							localPokeOther2.setBaseAtk(baseAtk);
							localPokeOther2.setBaseDef(baseDef);
							localPokeOther2.setBaseSpAtk(baseSpAtk);
							localPokeOther2.setBaseSpDef(baseSpDef);
							localPokeOther2.setBaseSpd(baseSpd);
							localPokeOther2.setBst(bst);
					}
					} else if(titleCol.text().indexOf("Alternate Forms") != -1) {
						localPokeOther1.setBaseHP(baseHP);
						localPokeOther1.setBaseAtk(baseAtk);
						localPokeOther1.setBaseDef(baseDef);
						localPokeOther1.setBaseSpAtk(baseSpAtk);
						localPokeOther1.setBaseSpDef(baseSpDef);
						localPokeOther1.setBaseSpd(baseSpd);
						localPokeOther1.setBst(bst);
						
						localPokeOther2.setBaseHP(baseHP);
						localPokeOther2.setBaseAtk(baseAtk);
						localPokeOther2.setBaseDef(baseDef);
						localPokeOther2.setBaseSpAtk(baseSpAtk);
						localPokeOther2.setBaseSpDef(baseSpDef);
						localPokeOther2.setBaseSpd(baseSpd);
						localPokeOther2.setBst(bst);
						
						localPokeOther3.setBaseHP(baseHP);
						localPokeOther3.setBaseAtk(baseAtk);
						localPokeOther3.setBaseDef(baseDef);
						localPokeOther3.setBaseSpAtk(baseSpAtk);
						localPokeOther3.setBaseSpDef(baseSpDef);
						localPokeOther3.setBaseSpd(baseSpd);
						localPokeOther3.setBst(bst);
						
						localPokeOther4.setBaseHP(baseHP);
						localPokeOther4.setBaseAtk(baseAtk);
						localPokeOther4.setBaseDef(baseDef);
						localPokeOther4.setBaseSpAtk(baseSpAtk);
						localPokeOther4.setBaseSpDef(baseSpDef);
						localPokeOther4.setBaseSpd(baseSpd);
						localPokeOther4.setBst(bst);
						
						localPokeOther5.setBaseHP(baseHP);
						localPokeOther5.setBaseAtk(baseAtk);
						localPokeOther5.setBaseDef(baseDef);
						localPokeOther5.setBaseSpAtk(baseSpAtk);
						localPokeOther5.setBaseSpDef(baseSpDef);
						localPokeOther5.setBaseSpd(baseSpd);
						localPokeOther5.setBst(bst);
					} else if(titleCol.text().indexOf("Origin Forme") != -1) {
						if(Objects.isNull(localPokeOther1)) {
							localPokeOther1 = new Pokemon(localPoke);
					}
						localPokeOther1.setBaseHP(baseHP);
						localPokeOther1.setBaseAtk(baseAtk);
						localPokeOther1.setBaseDef(baseDef);
						localPokeOther1.setBaseSpAtk(baseSpAtk);
						localPokeOther1.setBaseSpDef(baseSpDef);
						localPokeOther1.setBaseSpd(baseSpd);
						localPokeOther1.setBst(bst);
					}
					else if(titleCol.text().indexOf("Therian Forme") != -1) {
						if(Objects.isNull(localPokeOther1)) {
							localPokeOther1 = new Pokemon(localPoke);
					}
						localPokeOther1.setBaseHP(baseHP);
						localPokeOther1.setBaseAtk(baseAtk);
						localPokeOther1.setBaseDef(baseDef);
						localPokeOther1.setBaseSpAtk(baseSpAtk);
						localPokeOther1.setBaseSpDef(baseSpDef);
						localPokeOther1.setBaseSpd(baseSpd);
						localPokeOther1.setBst(bst);
					}
					else {
					debug1.add(localPoke.getName() + " - " + titleCol.text());
					}
				
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
	
				
				
			localPokeList.add(localPoke);
			if(!Objects.isNull(localPokeAlola)) {
				if(Objects.isNull(localPokeAlola.getEggGroups())) {
					localPokeAlola.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeAlola.getEggSteps() == 0) {
					localPokeAlola.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeAlola.getCapRate() != localPoke.getCapRate()) {
					localPokeAlola.setCapRate(localPoke.getCapRate());
				}
				if(localPokeAlola.getEvRewardAmt() == 0) {
					localPokeAlola.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeAlola.getEvRewardAttr())) {
					localPokeAlola.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(Objects.isNull(localPokeAlola.getHeight())) {
					localPokeAlola.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeAlola.getWeight())) {
					localPokeAlola.setWeight(localPoke.getWeight());
				}
				
			localPokeList.add(localPokeAlola);
			variantPokeList.add(localPokeAlola);
			}
			if(!Objects.isNull(localPokeGalar)) {
				if(Objects.isNull(localPokeGalar.getEggGroups())){
					localPokeGalar.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeGalar.getEggSteps() == 0){
					localPokeGalar.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeGalar.getCapRate() != localPoke.getCapRate()) {
					localPokeGalar.setCapRate(localPoke.getCapRate());
				}
				if(localPokeGalar.getEvRewardAmt()  == 0) {
					localPokeGalar.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeGalar.getEvRewardAttr())) {
					localPokeGalar.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(Objects.isNull(localPokeGalar.getHeight())) {
					localPokeGalar.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeGalar.getWeight())) {
					localPokeGalar.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokeGalar);
				variantPokeList.add(localPokeGalar);
				}
			if(!Objects.isNull(localPokeHisui)) {
				if(Objects.isNull(localPokeHisui.getEggGroups())) {
					localPokeHisui.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeHisui.getEggSteps() == 0){
					localPokeHisui.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeHisui.getCapRate() != localPoke.getCapRate()) {
					localPokeHisui.setCapRate(localPoke.getCapRate());
				}
				if(localPokeHisui.getEvRewardAmt()  == 0) {
					localPokeHisui.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeHisui.getEvRewardAttr())) {
					localPokeHisui.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(Objects.isNull(localPokeHisui.getHeight())) {
					localPokeHisui.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeHisui.getWeight())) {
					localPokeHisui.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokeHisui);
				variantPokeList.add(localPokeHisui);
				}
			if(!Objects.isNull(localPokePaldea)) {
				if(Objects.isNull(localPokePaldea.getEggGroups())) {
					localPokePaldea.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokePaldea.getEggSteps() == 0){
					localPokePaldea.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokePaldea.getCapRate() != localPoke.getCapRate()) {
					localPokePaldea.setCapRate(localPoke.getCapRate());
				}
				if(localPokePaldea.getEvRewardAmt()  == 0) {
					localPokePaldea.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokePaldea.getEvRewardAttr())) {
					localPokePaldea.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(Objects.isNull(localPokePaldea.getHeight())) {
					localPokePaldea.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokePaldea.getWeight())) {
					localPokePaldea.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokePaldea);
				variantPokeList.add(localPokePaldea);
				}
			if(!Objects.isNull(localPokeOther1)) {
				if(Objects.isNull(localPokeOther1.getEggGroups())) {
					localPokeOther1.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeOther1.getEggSteps() == 0){
					localPokeOther1.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeOther1.getCapRate() != localPoke.getCapRate()) {
					localPokeOther1.setCapRate(localPoke.getCapRate());
				}
				if(localPokeOther1.getEvRewardAmt() == 0) {
					localPokeOther1.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeOther1.getEvRewardAttr())) {
					localPokeOther1.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(localPokeOther1.getPokeTypes().equals(localPoke.getPokeTypes())) {
					localPokeOther1.setWeaknesses(localPoke.getWeaknesses());
					localPokeOther1.setImmunities(localPoke.getImmunities());
					localPokeOther1.setNeutrals(localPoke.getNeutrals());
					localPokeOther1.setResistances(localPoke.getResistances());
				}
				if(Objects.isNull(localPokeOther1.getHeight())) {
					localPokeOther1.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeOther1.getWeight())) {
					localPokeOther1.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokeOther1);
				variantPokeList.add(localPokeOther1);
				}
			if(!Objects.isNull(localPokeOther2)) {
				if(Objects.isNull(localPokeOther2.getEggGroups())) {
					localPokeOther2.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeOther2.getEggSteps() == 0){
					localPokeOther2.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeOther2.getCapRate() != localPoke.getCapRate()) {
					localPokeOther2.setCapRate(localPoke.getCapRate());
				}
				if(localPokeOther2.getEvRewardAmt() == 0) {
					localPokeOther2.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeOther2.getEvRewardAttr())) {
					localPokeOther2.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(localPokeOther2.getPokeTypes().equals(localPoke.getPokeTypes())) {
					localPokeOther2.setWeaknesses(localPoke.getWeaknesses());
					localPokeOther2.setImmunities(localPoke.getImmunities());
					localPokeOther2.setNeutrals(localPoke.getNeutrals());
					localPokeOther2.setResistances(localPoke.getResistances());
				}
				if(Objects.isNull(localPokeOther2.getHeight())) {
					localPokeOther2.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeOther2.getWeight())) {
					localPokeOther2.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokeOther2);
				variantPokeList.add(localPokeOther2);
				}
			if(!Objects.isNull(localPokeOther3)) {
				if(Objects.isNull(localPokeOther3.getEggGroups())) {
					localPokeOther3.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeOther3.getEggSteps() == 0){
					localPokeOther3.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeOther3.getCapRate() != localPoke.getCapRate()) {
					localPokeOther3.setCapRate(localPoke.getCapRate());
				}
				if(localPokeOther3.getEvRewardAmt() != localPoke.getEvRewardAmt()) {
					localPokeOther3.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeOther3.getEvRewardAttr())) {
					localPokeOther3.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(localPokeOther3.getPokeTypes().equals(localPoke.getPokeTypes())) {
					localPokeOther3.setWeaknesses(localPoke.getWeaknesses());
					localPokeOther3.setImmunities(localPoke.getImmunities());
					localPokeOther3.setNeutrals(localPoke.getNeutrals());
					localPokeOther3.setResistances(localPoke.getResistances());
				}
				if(Objects.isNull(localPokeOther3.getHeight())) {
					localPokeOther3.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeOther3.getWeight())) {
					localPokeOther3.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokeOther3);
				variantPokeList.add(localPokeOther3);
				}
			if(!Objects.isNull(localPokeOther4)) {
				if(Objects.isNull(localPokeOther4.getEggGroups())) {
					localPokeOther4.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeOther4.getEggSteps() == 0){
					localPokeOther4.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeOther4.getCapRate() != localPoke.getCapRate()) {
					localPokeOther4.setCapRate(localPoke.getCapRate());
				}
				if(localPokeOther4.getEvRewardAmt() == 0) {
					localPokeOther4.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeOther4.getEvRewardAttr())) {
					localPokeOther4.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(localPokeOther4.getPokeTypes().equals(localPoke.getPokeTypes())) {
					localPokeOther4.setWeaknesses(localPoke.getWeaknesses());
					localPokeOther4.setImmunities(localPoke.getImmunities());
					localPokeOther4.setNeutrals(localPoke.getNeutrals());
					localPokeOther4.setResistances(localPoke.getResistances());
				}
				if(Objects.isNull(localPokeOther4.getHeight())) {
					localPokeOther4.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeOther4.getWeight())) {
					localPokeOther4.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokeOther4);
				variantPokeList.add(localPokeOther4);
				}
			if(!Objects.isNull(localPokeOther5)) {
				if(Objects.isNull(localPokeOther5.getEggGroups())) {
					localPokeOther5.setEggGroups(localPoke.getEggGroups());
				}
				if(localPokeOther5.getEggSteps() == 0){
					localPokeOther5.setEggSteps(localPoke.getEggSteps());
				}
				if(localPokeOther5.getCapRate() != localPoke.getCapRate()) {
					localPokeOther5.setCapRate(localPoke.getCapRate());
				}
				if(localPokeOther5.getEvRewardAmt() == 0) {
					localPokeOther5.setEvRewardAmt(localPoke.getEvRewardAmt());
				}
				if(Objects.isNull(localPokeOther5.getEvRewardAttr())) {
					localPokeOther5.setEvRewardAttr(localPoke.getEvRewardAttr());
				}
				if(localPokeOther5.getPokeTypes().equals(localPoke.getPokeTypes())) {
					localPokeOther5.setWeaknesses(localPoke.getWeaknesses());
					localPokeOther5.setImmunities(localPoke.getImmunities());
					localPokeOther5.setNeutrals(localPoke.getNeutrals());
					localPokeOther5.setResistances(localPoke.getResistances());
				}
				if(Objects.isNull(localPokeOther5.getHeight())) {
					localPokeOther5.setHeight(localPoke.getHeight());
				}
				if(Objects.isNull(localPokeOther5.getWeight())) {
					localPokeOther5.setWeight(localPoke.getWeight());
				}
				localPokeList.add(localPokeOther5);
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
			} else if (dbSample.toString().equals(poke.toString())){
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
		
		System.out.println(debug1.toString());
		System.out.println(debug2.toString());
		System.out.println(debug3.toString());
			
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
			learnset = learnsetTree.toString().replace("[", "").replace("]", "");
			if(learnset.isEmpty() || learnset.equals("[]")) {
				learnset = "None";
			}
			System.out.println("Pokemon that can currently learn this move in Gen 9: " + learnset);
			inputMove.setLearnset(learnset);
			System.out.println(inputMove.toString());
				
			MoveHelpers.dbPersist(inputMove, session);
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
