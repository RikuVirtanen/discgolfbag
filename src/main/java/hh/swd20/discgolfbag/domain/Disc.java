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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Disc {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "attributesid", referencedColumnName = "id")
	private Attributes attributes;
	
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
		this.attributes = new Attributes(0, 0, 0, 0, this);
		this.plastic = null;
		this.category = null;
		this.company = null;
	}
	
	public Disc(String name, Plastic plastic, Category category, 
			Company company) {
		super();
		this.name = name;
		this.attributes = new Attributes(0, 0, 0, 0, this);
		this.plastic = plastic;
		this.category = category;
		this.company = company;
	}
	
	public Disc(String name, Attributes attributes, Plastic plastic, Category category, 
			Company company) {
		super();
		this.name = name;
		this.attributes = attributes;
		this.plastic = plastic;
		this.category = category;
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
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
		return "Disc [id=" + id + ", name=" + name + ", attributes=" + attributes + ", plastic=" + plastic
				+ ", category=" + category + ", company=" + company + ", bags=" + bags + "]";
	}
}
