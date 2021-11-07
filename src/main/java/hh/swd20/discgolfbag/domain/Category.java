package hh.swd20.discgolfbag.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category" )
	@JsonIgnoreProperties({"plastic", "category", "company", "bags"})
	private List<Disc> discs;
	
	public Category() {
		super();
		this.name = null;
	}
	
	public Category(String name) {
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
		return "Category [id=" + id + ", name=" + capitalize(this.name) + "]";
	}
}
