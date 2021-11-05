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
import hh.swd20.discgolfbag.domain.DGBag;
import hh.swd20.discgolfbag.domain.DGBagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.Plastic;
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
	public CommandLineRunner discgolfbagApp(DiscRepository dr,PlasticRepository pr, CategoryRepository catr, CompanyRepository comr, UserRepository ur, DGBagRepository DGBr) {
		
		return (args) -> {
			log.info("insert example data to database");
			
			comr.save(new Company("Innova"));
			comr.save(new Company("Discmania"));
			comr.save(new Company("Westside"));
			comr.save(new Company("Latitude 64"));
			
			log.info("fetch all companies");
			for(Company company: comr.findAll()) {
				log.info(company.toString());
			}
			
			pr.save(new Plastic("Star", comr.findByName("Innova").get()));
			pr.save(new Plastic("D-Line", comr.findByName("Discmania").get()));
			pr.save(new Plastic("BT Soft", comr.findByName("Westside").get()));
			pr.save(new Plastic("Opto", comr.findByName("Latitude 64").get()));
			
			log.info("fetch all types of plastic");
			for(Category category: catr.findAll()) {
				log.info(category.toString());
			}
			
			catr.save(new Category("Driver"));
			catr.save(new Category("Fairway Driver"));
			catr.save(new Category("Midrange"));
			catr.save(new Category("Putter"));
			
			log.info("fetch all categories");
			for(Category category: catr.findAll()) {
				log.info(category.toString());
			}
			
			dr.save(new Disc("Tern", 12, 6, -2, 2, pr.findByName("Star").get(), catr.findByName("Driver").get(), comr.findByName("Innova").get()));
			dr.save(new Disc("River", 7, 7, -1, 1, pr.findByName("Opto").get(), catr.findByName("Fairway Driver").get(), comr.findByName("Latitude 64").get()));
			dr.save(new Disc("Method", 5, 5, 0, 3, pr.findByName("D-Line").get(), catr.findByName("Midrange").get(), comr.findByName("Discmania").get()));
			dr.save(new Disc("Faith", 2, 3, 0, 1, pr.findByName("Opto").get(), catr.findByName("Putter").get(), comr.findByName("Latitude 64").get()));
			
			log.info("fetch all discs");
			for(Disc disc: dr.findAll()) {
				log.info(disc.toString());
			}
			
			// create users: admin/admin user/user
			User user = new User("user", "user@user.com", "$2a$10$7fDUJhaHcW..lozviLnwbuOw6G8VK/tHq4ew/Vo5J47LEX/SUv/KS", "USER");
			User admin = new User("admin", "admin@admin.com", "$2a$10$FfqOrHQA9ACQQCVIM7wSNeQqfMN/Seix1zVjWgQWPSbiI4qL7dFo6", "ADMIN");
			
			ur.save(user);
			ur.save(admin);
			
			DGBr.save(new DGBag("user's bag", "Blue", user));
			DGBr.save(new DGBag("admin's bag", "Red", admin));
			
			log.info("fetch all DGBags");
			for(DGBag bag: DGBr.findAll()) {
				log.info(bag.toString());
			}
			
			
			
		};
	}
}
