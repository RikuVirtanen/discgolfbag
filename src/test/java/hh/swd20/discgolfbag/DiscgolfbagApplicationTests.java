package hh.swd20.discgolfbag;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.swd20.discgolfbag.web.AppController;
import hh.swd20.discgolfbag.web.BagController;
import hh.swd20.discgolfbag.web.CategoryController;
import hh.swd20.discgolfbag.web.CompanyController;
import hh.swd20.discgolfbag.web.DiscController;
import hh.swd20.discgolfbag.web.UserController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DiscgolfbagApplicationTests {
	
	@Autowired private AppController appController;
	@Autowired private DiscController discController;
	@Autowired private CompanyController compController;
	@Autowired private BagController bagController;
	@Autowired private CategoryController catController;
	@Autowired private UserController userController;
	

	@Test
	public void contextLoads() throws Exception {
		assertThat(appController).isNotNull();
		assertThat(discController).isNotNull();
		assertThat(compController).isNotNull();
		assertThat(bagController).isNotNull();
		assertThat(catController).isNotNull();
		assertThat(userController).isNotNull();
	}

}
