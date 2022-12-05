package serebiiCacher.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Move {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name="moveName")
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
	
	@Column(name="battleEffect")
	private String battleEffect;
	
	@Column(name="secondaryEffect")
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
	
	@Column(name="isReflectable");
	private boolean isReflectable;
	
	@Column(name="isBlockable")
	private int isBlockable;
	
	@Column(name="isCopyable")
	private                     
	
	@Column(name =) //Revise this one more closely.
	/*isReflectable, isBlockable, isCopyablets
	 */

}
