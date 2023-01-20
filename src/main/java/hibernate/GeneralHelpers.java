package hibernate;

import java.util.Objects;

import hibernate.ability.Ability;
import org.hibernate.Session;

public class GeneralHelpers {
	
	public static <T> void dbPersist(T dbData, Session session){
		
		System.out.println(dbData.getClass().toString());
		/*
		Ability dbSample = session.get(dbData.getClass(), dbData.getClass().getName());
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
	*/
	
	}

}
