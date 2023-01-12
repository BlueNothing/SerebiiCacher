package hibernate.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hibernate.Pokemon;
import hibernate.Repository.PokemonRepository;

@RestController
public class PokemonController {
	private final PokemonRepository repository;
	
	PokemonController(PokemonRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/pokemon")
	List<Pokemon> all(){
		return repository.findAll();
	}
	
	@GetMapping("/pokemon/{name}")
	Pokemon one(@PathVariable String name){
		return repository.findById(name)
				.orElse(new Pokemon("MissingNo")); //Fill this with a blank entry or error entry.
	}
	

}
