package eu.chesstournament.backend.models;

import java.util.List;

/**
 * Created by bogdan on 01/05/2015.
 */
public class Country {
	private String name;
	private List<String> towns;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTowns() {
		return towns;
	}

	public void setTowns(List<String> towns) {
		this.towns = towns;
	}
}
