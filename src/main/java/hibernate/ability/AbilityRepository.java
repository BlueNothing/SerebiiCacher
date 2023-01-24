package hibernate.ability;

import java.util.List;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import hibernate.HibernateUtil;
import hibernate.pokemon.Pokemon;

public interface AbilityRepository extends JpaRepository<Ability, String>{
	
	@Override
	default
	List<Ability> findAll(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String HQL = "FROM Ability";
		List<Ability> abilities = session.createQuery(HQL).list();
		session.close();
		return abilities;
	}

}
