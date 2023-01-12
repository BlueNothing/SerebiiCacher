package hibernate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hibernate.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, String>{

}
