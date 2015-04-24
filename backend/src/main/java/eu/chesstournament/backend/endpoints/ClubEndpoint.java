package eu.chesstournament.backend.endpoints;


//import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.cmd.Query;

import java.util.List;
import java.util.logging.Logger;

import eu.chesstournament.backend.entities.Club;
import eu.chesstournament.backend.entities.Profile;
import eu.chesstournament.backend.models.DataPackage1;
import eu.chesstournament.backend.services.ClubService;
import eu.chesstournament.backend.services.ProfileService;
import eu.chesstournament.backend.tools.Constants;

import static eu.chesstournament.backend.services.OfyService.ofy;

@Api(   name = "clubApi",
		version = "v1",
		namespace = @ApiNamespace(
				ownerDomain = "backend.chesstournament.eu",
				ownerName = "backend.chesstournament.eu",
				packagePath = ""),
		scopes = {Constants.EMAIL_SCOPE},
		clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
				Constants.API_EXPLORER_CLIENT_ID},
		audiences = {Constants.ANDROID_AUDIENCE})
public class ClubEndpoint {


	@ApiMethod(
			name="getDataPackage1",
			httpMethod = ApiMethod.HttpMethod.POST
	)
	public DataPackage1 getDataPackage1(final User user) throws UnauthorizedException{
		if (user == null){
			throw new UnauthorizedException("Authorization required. No valid user identified");
		}
		DataPackage1 dataPackage1= new DataPackage1();
		Profile profile = ProfileService.getProfile(user.getEmail());
		List<Club> memberClubs = ClubService.getMemberClubs(profile.getId());
		List<Club> managerClubs = ClubService.getManagerClubs(profile.getId());

		dataPackage1.setProfile(profile);
		dataPackage1.setClubsManager(managerClubs);
		dataPackage1.setClubsMember(memberClubs);

		return dataPackage1;
	}

	@ApiMethod(
			name="insert",
			httpMethod = ApiMethod.HttpMethod.POST
	)
	public Club insert(Club club, final User user) throws  UnauthorizedException{
		if (user == null){
			throw new UnauthorizedException("Authorization required. No valid user identified");
		}
		return ClubService.insert(club, user.getEmail());
	}

	@ApiMethod(
			name="listAll",
			httpMethod = ApiMethod.HttpMethod.GET
	)
	public List<Club> listAll(){
		List<Club> clubs =  ofy().load().type(Club.class).list();
		return clubs;
	}
}

