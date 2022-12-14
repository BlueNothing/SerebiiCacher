package hibernate.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hibernate.Move;
import hibernate.Repository.MoveRepository;

@RestController
public class MoveController {
	private final MoveRepository repository;
	
	MoveController(MoveRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/moves")
	List<Move> all(){
		return repository.findAll();
	}
	
	@GetMapping("/moves/{name}")
	Move one(@PathVariable String name){
		return repository.findById(name)
				.orElse(new Move()); //Fill this with a blank entry or error entry.
	}
	

}
