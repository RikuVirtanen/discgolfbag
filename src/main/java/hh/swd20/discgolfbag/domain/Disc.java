package hh.swd20.discgolfbag.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Disc {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	private String name;
	private int speed;
	private int glide;
	private int turn;
	private int fade;
	private String plastic;
	
	@ManyToOne
	@JsonIgnoreProperties("discs")
	@JoinColumn(name = "id")
	private Category category;
	
	@ManyToOne
	@JsonIgnoreProperties("discs")
	@JoinColumn(name = "id")
	private Company company;

	public Disc() {
		super();
		this.name = null;
		this.speed = 0;
		this.glide = 0;
		this.turn = 0;
		this.fade = 0;
		this.plastic = null;
		this.category = null;
		this.company = null;
	}

	public Disc(String name, int speed, int glide, int turn, int fade, String plastic, Category category,
			Company company) {
		super();
		this.name = name;
		this.speed = speed;
		this.glide = glide;
		this.turn = turn;
		this.fade = fade;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getGlide() {
		return glide;
	}

	public void setGlide(int glide) {
		this.glide = glide;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getFade() {
		return fade;
	}

	public void setFade(int fade) {
		this.fade = fade;
	}

	public String getPlastic() {
		return plastic;
	}

	public void setPlastic(String plastic) {
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

	@Override
	public String toString() {
		return "Disc [id=" + id + ", name=" + name + ", speed=" + speed + ", glide=" + glide + ", turn=" + turn
				+ ", fade=" + fade + ", plastic=" + plastic + ", category=" + this.getCategory().getName() + ", company=" + this.company.getName() + "]";
	}
	
	
}
