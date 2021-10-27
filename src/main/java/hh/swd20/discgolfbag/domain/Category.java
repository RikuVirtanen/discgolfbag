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
	@JsonIgnoreProperties("category")
	private List<Disc> discs;
	
	public Category() {
		super();
		this.name = null;
	}

	public Category(String name, List<Disc> discs) {
		super();
		this.name = name;
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

	public List<Disc> getDiscs() {
		return discs;
	}

	public void setDiscs(List<Disc> discs) {
		this.discs = discs;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
}
