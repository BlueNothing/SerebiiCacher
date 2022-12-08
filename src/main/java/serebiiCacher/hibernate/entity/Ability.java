package serebiiCacher.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
