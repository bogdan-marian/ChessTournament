package eu.chesstournament.backend.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Connection {
	@Id
	private Long id;
	private String initialToken;
	private String activeToken;
}
