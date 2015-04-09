package eu.chesstournament.backend.endpoints;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import eu.chesstournament.backend.models.GeneralResume;
import eu.chesstournament.backend.tools.Constants;

@Api(   name = "landingApi",
		version = "v1",
		namespace = @ApiNamespace(
				ownerDomain = "backend.chesstournament.eu",
				ownerName = "backend.chesstournament.eu",
				packagePath = ""),
		scopes = {Constants.EMAIL_SCOPE},
		clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
				Constants.API_EXPLORER_CLIENT_ID},
		audiences = {Constants.ANDROID_AUDIENCE})
public class LandingEndpoint {

	@ApiMethod(
			name = "getGeneralResume",
			httpMethod = ApiMethod.HttpMethod.POST
	)
	public GeneralResume getGeneralResume(final User user) throws UnauthorizedException{
		if (user == null){
			throw new UnauthorizedException("Authorization required. No valid user identified");
		}
		GeneralResume generalResume = new GeneralResume();
		generalResume.setUserEmail(user.getEmail());
		if (user.getUserId()!= null){
			generalResume.setDomainUserId("Bingo. found user:"+ user.getUserId());
		}
		return generalResume;
	}
}
