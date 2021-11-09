package hh.swd20.discgolfbag.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Bag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String color;
	
	@OneToOne(mappedBy = "bag")
	@JsonIgnoreProperties("bag")
	private User user;
	
	@ManyToMany(targetEntity = Disc.class, mappedBy="bags", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("bags")
	private List<Disc> discs;
	
	public Bag() {
		super();
		this.name = null;
		this.color = null;
	}
	
	public Bag(User user) {
		super();
		this.name = user.getUsername() + "'s bag";
		this.color = "default";
		this.user = user;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Disc> getDiscs() {
		return discs;
	}
	
	public int getBagSize() {
		return this.discs.size();
	}

	public void setDiscs(List<Disc> discs) {
		this.discs = discs;
	}
	
	public void addDisc(Disc disc) {
		this.discs.add(disc);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "DGBag [id=" + id + ", name=" + name + ", color=" + color + "]";
	}
	
}
