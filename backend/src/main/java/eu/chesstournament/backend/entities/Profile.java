package eu.chesstournament.backend.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;


@Entity
public class Profile {
	@Id
	private String id;
	private String email;
	private String name;
	private Date dateOfBirth;
	private int eloOfficial;
	private int eloClub;

	public int getEloOfficial() {
		return eloOfficial;
	}

	public void setEloOfficial(int eloOfficial) {
		this.eloOfficial = eloOfficial;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}



	public int getEloClub() {
		return eloClub;
	}

	public void setEloClub(int eloClub) {
		this.eloClub = eloClub;
	}
}
