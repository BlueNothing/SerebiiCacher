package hibernate.move;

import java.util.List;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import hibernate.HibernateUtil;
import hibernate.pokemon.Pokemon;

public interface MoveRepository extends JpaRepository<Move, String>{

	@Override
	default
	List<Move> findAll(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String HQL = "FROM Move";
		List<Move> moves = session.createQuery(HQL).list();
		session.close();
		return moves;
	}
}
