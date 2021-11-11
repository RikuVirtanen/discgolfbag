package hh.swd20.discgolfbag;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.swd20.discgolfbag.domain.Bag;
import hh.swd20.discgolfbag.domain.BagRepository;
import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.PlasticRepository;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppRepositoryTest {

	@Autowired private BagRepository bagRepo;
	@Autowired private CategoryRepository categoryRepo;
	@Autowired private CompanyRepository companyRepo;
	@Autowired private DiscRepository discRepo;
	@Autowired private UserRepository userRepo;
	@Autowired private PlasticRepository plasticRepo;
	
	// Test to test userRepository by finding admin user by username
	@Test
	public void findByUsernameShouldReturnUser() {
		User user = userRepo.findByUsername("admin");
		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo("admin@admin.com");
	}
	
	// UserRepository and BagRepository test testing user and bag creation
	@Test
	public void createUserAndBag() {
		User user = new User("user", "user@user.com", "$2a$10$1wJWuTUH5ILp7yiQS2wXKu3ce0eNZG7SvQCTVFUoCVrVYGrBjQogq", "USER");
		user.setBag(new Bag(user));
		userRepo.save(user);
		assertThat(user.getId()).isNotNull();
		assertThat(user.getBag()).isEqualTo(bagRepo.findBagByUserId(user.getId()).get());
	}
	
	// Testing CompanyRepository creating new Company
	@Test
	public void createCompany() {
		Company company = new Company("Discmania");
		companyRepo.save(company);
		assertThat(company.getId()).isNotNull();
	}
	
	// Testing CompanyRepository by finding company that is in database
	@Test
	public void findByNameShouldReturnCompany() {
		Company company = companyRepo.findByName("innova").get();
		assertThat(company).isNotNull();
		assertThat(company.getId()).isNotNull();
	}
	
	// Testing CategoryRepository by creating new category
	@Test
	public void createCategory() {
		Category category = new Category("midrange putter");
		categoryRepo.save(category);
		assertThat(category.getId()).isNotNull();
	}
	
	// Testing DiscRepository and other repositories by creating new disc with company, category and plastic 
	@Test
	public void createDisc() {
		Disc disc = new Disc("shryke", "13", "6", "-2", "2", plasticRepo.findByName("champion").get(), categoryRepo.findByName("driver").get(), companyRepo.findByName("innova").get());
		discRepo.save(disc);
		assertThat(disc.getId()).isNotNull();
		assertThat(disc.getCapitalizedName()).isEqualTo("Shryke");
		assertThat(disc.getCategory()).isEqualTo(categoryRepo.findByName("driver"));
		assertThat(disc.getCompany()).isEqualTo(companyRepo.findByName("innova"));
		assertThat(disc.getPlastic()).isEqualTo(plasticRepo.findByName("champion"));
	}
	
	
}
