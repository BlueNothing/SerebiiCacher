package hibernate;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HomepageController {
	
	@GetMapping(value="")
	String home() {
		return "Application working!";
	}
	
	@GetMapping(value="error")
	String errorHandle(){
		return "There was an error handling your request";
	
	}
	
	@GetMapping(value="loadDB")
	String initDB() throws IOException {
		DatabasePrep.databaseInitializer("");
		return "Loading databases";
	}
	
	@GetMapping(value="loadDB/{id}")
	String initDB(@PathVariable("id") String id) throws IOException {
		DatabasePrep.databaseInitializer(id);
		return "Loading database for: " + id;
	}
	
	@GetMapping(value="collide")
	void collide() throws IOException {
		HTMLCrawler.collisionChecker();
	}
	

}