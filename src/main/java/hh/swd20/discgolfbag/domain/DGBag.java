package hh.swd20.discgolfbag.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class DGBag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String color;
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToMany
	@JoinColumn(name = "discId")
	private List<Disc> discs;
	
	public DGBag() {
		super();
		this.name = null;
		this.color = null;
		this.discs = new ArrayList<>();
	}
	
	public DGBag(String name, String color, User user) {
		super();
		this.name = name;
		this.color = color;
		this.user = user;
		this.discs = new ArrayList<>();
	}
	
	public DGBag(String name, String color, User user, List<Disc> discs) {
		super();
		this.name = name;
		this.color = color;
		this.user = user;
		this.discs = discs;
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
			outcome += first.toUpperCase() + rest + " ";
		}
		
		return outcome.trim();
	}

	@Override
	public String toString() {
		return "DGBag [id=" + id + ", name=" + name + ", color=" + color + ", user=" + user.getUsername() + "]";
	}
	
}
