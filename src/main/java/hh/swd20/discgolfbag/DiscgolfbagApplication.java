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
import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;
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
	public CommandLineRunner discgolfbagApp(DiscRepository dr,PlasticRepository pr, 
			CategoryRepository catr, CompanyRepository comr, UserRepository ur, BagRepository DGBr) {
		return (args) -> {
			
			log.info("insert example data of disc types");
			catr.save(new Category("Driver"));
			catr.save(new Category("Fairway Driver"));
			catr.save(new Category("Midrange"));
			catr.save(new Category("Putter"));
			
			comr.save(new Company("Innova"));
			comr.save(new Company("Westside"));
			comr.save(new Company("Discmania"));
			comr.save(new Company("Latitude 64"));
			
			pr.save(new Plastic("Star", comr.findByName("innova").get()));
			pr.save(new Plastic("Champion", comr.findByName("innova").get()));
			pr.save(new Plastic("Opto", comr.findByName("latitude 64").get()));
			pr.save(new Plastic("Gold", comr.findByName("latitude 64").get()));
			pr.save(new Plastic("BT Soft", comr.findByName("westside").get()));
			pr.save(new Plastic("BT Hard", comr.findByName("westside").get()));
			
			dr.save(new Disc("Tern", "12", "6", "-3", "2", pr.findByName("star").get(), catr.findByName("driver").get(), comr.findByName("innova").get()));
			dr.save(new Disc("Roc3", "5", "4", "0", "3", pr.findByName("champion").get(), catr.findByName("midrange").get(), comr.findByName("innova").get()));
			dr.save(new Disc("Aviar", "12", "6", "-3", "2", pr.findByName("star").get(), catr.findByName("putter").get(), comr.findByName("innova").get()));
			
			dr.save(new Disc("Gladiator", "13", "5", "0", "3.5", pr.findByName("opto").get(), catr.findByName("driver").get(), comr.findByName("latitude 64").get()));
			dr.save(new Disc("Explorer", "7", "5", "0", "2", pr.findByName("opto").get(), catr.findByName("fairway driver").get(), comr.findByName("latitude 64").get()));
			dr.save(new Disc("Claymore", "5", "5", "-1", "1", pr.findByName("gold").get(), catr.findByName("midrange").get(), comr.findByName("latitude 64").get()));
			
			dr.save(new Disc("Hatchet", "9", "6", "-2", "1", pr.findByName("bt soft").get(), catr.findByName("fairway driver").get(), comr.findByName("westside").get()));
			dr.save(new Disc("Warship", "5", "6", "0", "1", pr.findByName("bt hard").get(), catr.findByName("midrange").get(), comr.findByName("westside").get()));
			dr.save(new Disc("Swan 2", "3", "3", "-1", "0", pr.findByName("bt soft").get(), catr.findByName("putter").get(), comr.findByName("westside").get()));
			
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
