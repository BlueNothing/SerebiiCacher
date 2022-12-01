package com.Sophie.SerebiiCacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SerebiiCacherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerebiiCacherApplication.class, args);
	}

	/*
	 * Hmmm... since we're pulling for the Paldea Pokedex, we'll need to establish the information we need to pull.
	 * Number, Name, Type(s), Abilities, Standard Learnset, TM Learnset, Egg Learnset
	 * 
	 * Moves should probably be defined as objects in their own right, and have the following properties - 
	 * TM # (May be null), Level (May be null), Egg move (Boolean), Name, Type, Category (Phys/Special/Status), Base Power, Accuracy, PP, Eff %, and Description.
	 */
}
