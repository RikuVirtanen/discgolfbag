package hh.swd20.discgolfbag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.swd20.discgolfbag.domain.BagRepository;
import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.CompanyRepository;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.PlasticRepository;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;

@SpringBootApplication
public class DiscgolfbagApplication {
	private static final Logger log = LoggerFactory.getLogger(DiscgolfbagApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DiscgolfbagApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner discgolfbagApp(DiscRepository dr,PlasticRepository pr, 
			CategoryRepository catr, CompanyRepository comr, UserRepository ur, BagRepository DGBr) {
		return (args) -> {
			
			log.info("insert example data");
			catr.save(new Category("Driver"));
			catr.save(new Category("Fairway Driver"));
			catr.save(new Category("Midrange"));
			catr.save(new Category("Putter"));
			
			log.info("save example users");
			// Create users: admin/admin user/user
			User user = new User("user", "user@user.com", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User admin = new User("admin", "admin@admin.com", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			
			ur.save(user);
			ur.save(admin);
			
			log.info("fetch all users"); 
			for (User u: ur.findAll()) {
				log.info(u.toString());
			}
		};
	}
}
