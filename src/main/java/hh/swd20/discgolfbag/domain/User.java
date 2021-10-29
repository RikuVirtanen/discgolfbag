package hh.swd20.discgolfbag.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String passwordHash;
	
	@Column(name = "role", nullable = false)
	private String role;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Disc> bag;

	public User() {
		super();
		this.username = null;
		this.email = null;
		this.passwordHash = null;
		this.role = null;
		this.bag = new ArrayList<Disc>();
	}

	public User(String username, String email, String passwordHash, String role) {
		super();
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
		this.bag = new ArrayList<Disc>();
	}
	
	public User(String username, String email, String passwordHash, String role, List<Disc> bag) {
		super();
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
		this.bag = bag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public int getBagSize() {
		return bag.size();
	}
	
	public void addDisc(Disc disc) {
		this.bag.add(disc);
	}

	public List<Disc> getBag() {
		return bag;
	}

	public void setBag(List<Disc> bag) {
		this.bag = bag;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", passwordHash=" + passwordHash
				+ ", role=" + role + "]";
	}

}
