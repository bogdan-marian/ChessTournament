package eu.chesstournament.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import eu.chesstournament.backend.entities.Club;
import eu.chesstournament.backend.entities.ClubManager;
import eu.chesstournament.backend.entities.ClubMember;

import static eu.chesstournament.backend.services.OfyService.ofy;

/**
 * Created by bogdan on 4/18/2015.
 */
public class ClubService {

	private static final Logger logger = Logger.getLogger(ClubService.class.getName());

	/**
	 * return list with the clubs for witch the profileId is member of
	 * @param profileId
	 * @return
	 */
	public static List<Club> getMemberClubs(String profileId){
		List<ClubMember> memberClubs =
				ofy().load().type(ClubMember.class).filter("profileId", profileId).list();
		if (memberClubs.size()==0){
			return new ArrayList<>();
		}
		List<Long> idList = new ArrayList<>();
		for (ClubMember clubMember:memberClubs){
			idList.add(clubMember.getClubId());
		}

		return getByIdList(idList);
	}
	public static List<Club> getManagerClubs(String profileId){
		List<ClubManager> managerClubs =
				ofy().load().type(ClubManager.class).filter("profileId", profileId).list();
		if (managerClubs.size()==0){
			return new ArrayList<>();
		}
		List<Long> idList = new ArrayList<>();
		for (ClubManager clubMember:managerClubs){
			idList.add(clubMember.getClubId());
		}

		return getByIdList(idList);
	}

	private static List<Club> getByIdList(List<Long> idList){
		List<Club> clubs = ofy().load().type(Club.class).filter("id in", idList).list();
		return clubs;
	}

	/**
	 * create a club and also make the user manager and member of the created club
	 */
	public static Club insert(Club club, String profileId){
		club.setId(null);
		if (clubAlreadyExists(club.getName())){
			throw new IllegalStateException("A club with this name already exists. ");
		}
		ofy().save().entity(club).now();
		logger.info("Created Club with id: " + club.getId());
		return ofy().load().entity(club).now();
	}
	private static boolean clubAlreadyExists(String name){
		List<Club> clubs = findByName(name);
		if (clubs.size()>0){
			return true;
		}else{
			return false;
		}
	}
	private static List<Club> findByName(String name){
		List<Club> clubs = ofy().load().type(Club.class).filter("name", name).list();
		System.out.println("clubs.size() = " + clubs.size());
		return clubs;
	}
}
