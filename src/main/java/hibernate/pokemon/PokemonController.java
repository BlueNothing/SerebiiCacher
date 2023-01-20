package hibernate.pokemon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/pokemon")
public class PokemonController {
	@Autowired
	private final PokemonRepository repository;
	
	PokemonController(PokemonRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("")
	public List<Pokemon> all(){
		return repository.findAll();
	}
	
	@GetMapping("/{name}")
	public Pokemon one(@PathVariable String name){
		return repository.findById(name)
				.orElse(new Pokemon("MissingNo")); //Fill this with a blank entry or error entry.
	}
	

}
