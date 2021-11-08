package hh.swd20.discgolfbag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscgolfbagApplication {
	//private static final Logger log = LoggerFactory.getLogger(DiscgolfbagApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DiscgolfbagApplication.class, args);
	}
	
	/*@Bean
	public CommandLineRunner discgolfbagApp(DiscRepository dr,
			PlasticRepository pr, 
			CategoryRepository catr, 
			CompanyRepository comr, 
			UserRepository ur, 
			BagRepository DGBr) {
		return (args) -> {
			
			log.info("insert example data of disc types");
			catr.save(new Category("driver"));
			catr.save(new Category("fairway Driver"));
			catr.save(new Category("midrange"));
			catr.save(new Category("putter"));
			
			comr.save(new Company("innova"));
			comr.save(new Company("westside"));
			comr.save(new Company("discmania"));
			comr.save(new Company("latitude 64"));
			
			pr.save(new Plastic("Star", comr.findByName("innova").get()));
			pr.save(new Plastic("Champion", comr.findByName("innova").get()));
			pr.save(new Plastic("Opto", comr.findByName("latitude 64").get()));
			pr.save(new Plastic("Gold", comr.findByName("latitude 64").get()));
			pr.save(new Plastic("BT Soft", comr.findByName("westside").get()));
			pr.save(new Plastic("BT Hard", comr.findByName("westside").get()));
			
			dr.save(new Disc("tern", "12", "6", "-3", "2", pr.findByName("star").get(), catr.findByName("driver").get(), comr.findByName("innova").get()));
			dr.save(new Disc("roc3", "5", "4", "0", "3", pr.findByName("champion").get(), catr.findByName("midrange").get(), comr.findByName("innova").get()));
			dr.save(new Disc("aviar", "12", "6", "-3", "2", pr.findByName("star").get(), catr.findByName("putter").get(), comr.findByName("innova").get()));
			
			dr.save(new Disc("gladiator", "13", "5", "0", "3.5", pr.findByName("opto").get(), catr.findByName("driver").get(), comr.findByName("latitude 64").get()));
			dr.save(new Disc("explorer", "7", "5", "0", "2", pr.findByName("opto").get(), catr.findByName("fairway driver").get(), comr.findByName("latitude 64").get()));
			dr.save(new Disc("claymore", "5", "5", "-1", "1", pr.findByName("gold").get(), catr.findByName("midrange").get(), comr.findByName("latitude 64").get()));
			
			dr.save(new Disc("hatchet", "9", "6", "-2", "1", pr.findByName("bt soft").get(), catr.findByName("fairway driver").get(), comr.findByName("westside").get()));
			dr.save(new Disc("warship", "5", "6", "0", "1", pr.findByName("bt hard").get(), catr.findByName("midrange").get(), comr.findByName("westside").get()));
			dr.save(new Disc("swan 2", "3", "3", "-1", "0", pr.findByName("bt soft").get(), catr.findByName("putter").get(), comr.findByName("westside").get()));
			
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
	}*/
}
