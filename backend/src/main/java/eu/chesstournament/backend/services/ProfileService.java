package eu.chesstournament.backend.services;

import eu.chesstournament.backend.entities.Profile;
import static eu.chesstournament.backend.services.OfyService.ofy;

public class ProfileService {
	/**
	 * if the profile exists then return the profile, if nod then create the profile and
	 * return the new created one
	 * @param id
	 * @return
	 */
	public static Profile getProfile(String id){
		Profile profile = ofy().load().type(Profile.class).id(id).now();
		if (profile == null){
			return createProfile(id);
		}
		return profile;
	}

	private static Profile createProfile(String id){
		Profile profile = new Profile();
		profile.setId(id);
		profile.setEmail(id);
		profile.setName("-no name?-");

		ofy().save().entity(profile).now();

		return profile;
	}
}
