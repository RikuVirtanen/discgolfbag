package hh.swd20.discgolfbag.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Disc {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	private String name;
	private String capitalizedName;
	private String speed;
	private String glide;
	private String turn;
	private String fade;
	
	
	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties("discs")
	@JoinColumn(name = "plasticid", referencedColumnName = "id")
	private Plastic plastic;
	
	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties("discs")
	@JoinColumn(name = "categoryid", referencedColumnName = "id")
	private Category category;
	
	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties("discs")
	@JoinColumn(name = "companyid", referencedColumnName = "id")
	private Company company;
	
	@ManyToMany(targetEntity = Bag.class, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Bag> bags;

	public Disc() {
		super();
		this.name = null;
		this.capitalizedName = null;
		this.speed = null;
		this.glide = null;
		this.turn = null;
		this.fade = null;
		this.plastic = null;
		this.category = null;
		this.company = null;
	}
	
	public Disc(String name, String speed, String glide, String turn, String fade, Plastic plastic,
			Category category, Company company) {
		super();
		this.name = name.toLowerCase();
		this.capitalizedName = capitalize(name);
		this.speed = speed;
		this.glide = glide;
		this.turn = turn;
		this.fade = fade;
		this.plastic = plastic;
		this.category = category;
		this.company = company;
		this.bags = null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
		this.capitalizedName = capitalize(name);
	}
	
	public String getCapitalizedName() {
		return capitalizedName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getGlide() {
		return glide;
	}

	public void setGlide(String glide) {
		this.glide = glide;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getFade() {
		return fade;
	}

	public void setFade(String fade) {
		this.fade = fade;
	}

	public Plastic getPlastic() {
		return plastic;
	}

	public void setPlastic(Plastic plastic) {
		this.plastic = plastic;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Bag> getBags() {
		return bags;
	}

	public void setBags(List<Bag> bags) {
		this.bags = bags;
	}

	public void addToBag(Bag bag) {
		this.bags.add(bag);
	}
	
	public void removeFromBag(Bag bag) {
		this.bags.remove(bag);
	}

	public String capitalize(String word) {
		String words[] = word.split("\\s");
		String outcome = "";
		for (String w: words) {
			String first = w.substring(0, 1);
			String rest = w.substring(1);
			outcome += first.toUpperCase() + rest.toLowerCase() + " ";
		}
		
		return outcome.trim();
	}

	@Override
	public String toString() {
		return "Disc [id=" + id + ", speed=" + speed + ", glide=" + glide + ", turn=" + turn + ", fade=" + fade
				+ ", name=" + capitalize(name) + ", plastic=" + plastic + ", category=" + category + ", company=" + company
				+ "]";
	}

	
}
