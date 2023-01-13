package hibernate.Controller;

import java.io.IOException;
import java.util.List;
import hibernate.DatabasePrep;
import hibernate.HTMLCrawler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hibernate.DatabasePrep;

@RestController
public class HomepageController {
	
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
	void initDB(@PathVariable("id") String id) throws IOException {
		DatabasePrep.databaseInitializer(id);
	}
	
	@GetMapping(value="collide")
	void collide() throws IOException {
		HTMLCrawler.collisionChecker();
	}
	

}