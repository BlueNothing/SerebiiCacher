package hibernate.move;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/moves")
public class MoveController {
	@Autowired
	private final MoveRepository repository;
	
	MoveController(MoveRepository repository){
		this.repository = repository;
	}
	
	@GetMapping(value="")
	List<Move> all(){
		return repository.findAll();
	}
	
	@GetMapping(value="/{name}")
	Move one(@PathVariable String name){
		return repository.findById(name)
				.orElse(new Move()); //Fill this with a blank entry or error entry.
	}
	

}
