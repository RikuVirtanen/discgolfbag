package hh.swd20.discgolfbag.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company" )
	//@JsonIgnoreProperties({"company", "discs"})
	private List<Plastic> plastics;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company" )
	//@JsonIgnoreProperties({"company", "discs"})
	private List<Disc> discs;
	
	public Company() {
		super();
		this.name = null;
	}
	
	public Company(String name) {
		super();
		this.name = name.toLowerCase();
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
	
	public String getCapitalizedName() {
		return capitalize(name);
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public List<Plastic> getPlastics() {
		return plastics;
	}

	public void setPlastics(List<Plastic> plastics) {
		this.plastics = plastics;
	}

	public List<Disc> getDiscs() {
		return discs;
	}

	public void setDiscs(List<Disc> discs) {
		this.discs = discs;
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
		return "Company [id=" + id + ", name=" + capitalize(this.name) + "]";
	}
	
}
