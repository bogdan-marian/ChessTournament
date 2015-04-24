package eu.chesstournament.backend.services;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import eu.chesstournament.backend.entities.Club;
import eu.chesstournament.backend.entities.ClubManager;
import eu.chesstournament.backend.entities.ClubMember;
import eu.chesstournament.backend.entities.Profile;

/**
 * Created by bogdan on 4/18/2015.
 */
public class OfyService {
	static{
		factory().register(Profile.class);
		factory().register(ClubMember.class);
		factory().register(ClubManager.class);
		factory().register(Club.class);
	}

	public static Objectify ofy(){
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory(){
		return ObjectifyService.factory();
	}
}
