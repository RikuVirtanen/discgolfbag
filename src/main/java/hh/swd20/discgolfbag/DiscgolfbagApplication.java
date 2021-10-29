package hh.swd20.discgolfbag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;

@SpringBootApplication
public class DiscgolfbagApplication {
	private static final Logger log = LoggerFactory.getLogger(DiscgolfbagApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DiscgolfbagApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner discgolfbagApp(DiscRepository dr, CategoryRepository catr, CompanyRepository comr, UserRepository ur) {
		return (args) -> {
			log.info("insert example data to database");
			
			catr.save(new Category("Driver"));
			catr.save(new Category("Fairway Driver"));
			catr.save(new Category("Midrange"));
			catr.save(new Category("Putter"));
			
			log.info("fetch all categories");
			for(Category category: catr.findAll()) {
				log.info(category.toString());
			}
			
			comr.save(new Company("Innova"));
			comr.save(new Company("Discmania Evolution"));
			comr.save(new Company("Westside"));
			comr.save(new Company("Latitude 64"));
			
			log.info("fetch all companies");
			for(Company company: comr.findAll()) {
				log.info(company.toString());
			}
			
			dr.save(new Disc("Tern", 12, 6, -2, 2, "Champion", catr.findByName("Driver").get(), comr.findByName("Innova").get()));
			dr.save(new Disc("River", 7, 7, -1, 1, "Opto", catr.findByName("Fairway Driver").get(), comr.findByName("Latitude 64").get()));
			dr.save(new Disc("Method", 5, 5, 0, 3, "Hard Exo", catr.findByName("Midrange").get(), comr.findByName("Discmania Evolution").get()));
			dr.save(new Disc("Faith", 2, 3, 0, 1, "Sense", catr.findByName("Putter").get(), comr.findByName("Latitude 64").get()));
			
			log.info("fetch all discs");
			for(Disc disc: dr.findAll()) {
				log.info(disc.toString());
			}
			
			// create users: admin/admin user/user
			ur.save(new User("user", "user@user.com", "$2a$10$7fDUJhaHcW..lozviLnwbuOw6G8VK/tHq4ew/Vo5J47LEX/SUv/KS", "USER"));
			ur.save(new User("admin", "admin@admin.com", "$2a$10$FfqOrHQA9ACQQCVIM7wSNeQqfMN/Seix1zVjWgQWPSbiI4qL7dFo6", "ADMIN"));
			
			log.info("fetch all users");
			for(User user: ur.findAll()) {
				log.info(user.toString());
			}
		};
	}

}
