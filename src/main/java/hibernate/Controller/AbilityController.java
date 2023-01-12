package hibernate.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hibernate.Ability;
import hibernate.Repository.AbilityRepository;

@RestController
public class AbilityController {
	private final AbilityRepository repository;
	
	AbilityController(AbilityRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/abilities")
	List<Ability> all(){
		return repository.findAll();
	}
	
	@GetMapping("/abilities/{name}")
	Ability one(@PathVariable String name){
		return repository.findById(name)
				.orElse(new Ability()); //Fill this with a blank entry or error entry.
	}
	

}
