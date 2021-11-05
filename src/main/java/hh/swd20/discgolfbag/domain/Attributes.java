package hh.swd20.discgolfbag.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Attributes {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int speed;
	private int glide;
	private int turn;
	private int fade;
	
	@OneToOne(mappedBy = "attributes")
	@JsonIgnoreProperties("attributes")
	private Disc disc;
	
	public Attributes() {
		super();
		this.speed = 0;
		this.glide = 0;
		this.turn = 0;
		this.fade = 0;
	}
	
	public Attributes(int speed, int glide, int turn, int fade, Disc disc) {
		this.speed = speed;
		this.glide = glide;
		this.turn = turn;
		this.fade = fade;
		this.disc = disc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Disc getDisc() {
		return disc;
	}

	public void setDisc(Disc disc) {
		this.disc = disc;
	}

	@Override
	public String toString() {
		return "Attributes [id=" + id + ", speed=" + speed + ", glide=" + glide + ", turn=" + turn + ", fade=" + fade
				+ ", disc=" + disc + "]";
	}
}
