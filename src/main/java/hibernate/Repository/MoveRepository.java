package hibernate.Repository;

import java.util.List;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import hibernate.HibernateUtil;
import hibernate.Move;
import hibernate.Pokemon;

public interface MoveRepository extends JpaRepository<Move, String>{

	@Override
	default
	List<Move> findAll(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String HQL = "FROM moves";
		List<Move> moves = session.createQuery(HQL).list();
		session.close();
		return moves;
	}
}
