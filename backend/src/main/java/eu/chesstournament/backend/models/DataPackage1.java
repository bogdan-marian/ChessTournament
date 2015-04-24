package eu.chesstournament.backend.models;

import java.util.List;

import eu.chesstournament.backend.entities.Club;
import eu.chesstournament.backend.entities.Profile;

/**
 * Created by bogdan on 4/18/2015.
 */
public class DataPackage1 {
	private Profile profile;
	private List<Club> clubsManager;
	private List<Club> clubsMember;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Club> getClubsManager() {
		return clubsManager;
	}

	public void setClubsManager(List<Club> clubsManager) {
		this.clubsManager = clubsManager;
	}

	public List<Club> getClubsMember() {
		return clubsMember;
	}

	public void setClubsMember(List<Club> clubsMember) {
		this.clubsMember = clubsMember;
	}
}
