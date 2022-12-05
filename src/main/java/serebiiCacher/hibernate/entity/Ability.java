package serebiiCacher.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Ability {
	/*
	 * NAME, GAME TEXT, IN-DEPTH EFFECT;
	 */

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String abilityName;

    @Column(name = "gameText")
    private String abilityGameText;
    
    @Column(name = "inDepthAbilityEffect")
    private String inDepthAbilityEffect;
}
