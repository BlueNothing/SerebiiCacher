package hibernate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hibernate.HibernateUtil;
import hibernate.Pokemon;
import org.hibernate.Session;

public interface PokemonRepository extends JpaRepository<Pokemon, String>{

	@Override
	default
	List<Pokemon> findAll(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String HQL = "FROM pokemon";
		List<Pokemon> pokemon = session.createQuery(HQL).list();
		session.close();
		return pokemon;
	}
}
