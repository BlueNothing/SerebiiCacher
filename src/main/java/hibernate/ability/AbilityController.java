package hibernate.ability;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = {"/abilities"})
public class AbilityController {
	@Autowired
	private final AbilityRepository repository;
	
	AbilityController(AbilityRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("")
	public List<Ability> all(){
		System.out.println(repository.findAll().toString());
		return repository.findAll();
	}
	
	@GetMapping("/{name}")
	public Ability one(@PathVariable("name") String name){
		return repository.findById(name)
				.orElse(new Ability()); //Fill this with a blank entry or error entry.
	}
	

}
