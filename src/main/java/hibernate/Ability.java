package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "abilities")
public class Ability {
	/*
	 * NAME, GAME TEXT, IN-DEPTH EFFECT;
	 */

	@Id
    @Column(name = "name")
    private String abilityName;

    @Column(name = "gameText")
    private String abilityGameText;
    
    @Column(name = "inDepthAbilityEffect")
    private String inDepthAbilityEffect;
}
