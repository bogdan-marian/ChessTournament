/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package eu.chesstournament.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import javax.inject.Named;

import eu.chesstournament.backend.models.MyBean;
import eu.chesstournament.backend.tools.Constants;
import eu.chesstournament.backend.tools.MyOauthFlowHelpers;

/**
 * An endpoint class we are exposing
 */
@Api(   name = "myApi",
		version = "v1",
		namespace = @ApiNamespace(
				ownerDomain = "backend.chesstournament.eu",
				ownerName = "backend.chesstournament.eu",
				packagePath = ""),
		scopes = {Constants.EMAIL_SCOPE},
		clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
				Constants.API_EXPLORER_CLIENT_ID},
		audiences = {Constants.ANDROID_AUDIENCE})
public class MyEndpoint {

	/**
	 * A simple endpoint method that takes a name and says Hi back
	 */
	@ApiMethod(name = "sayHi")
	public MyBean sayHi(@Named("name") String name) {
		MyBean response = new MyBean();
		response.setData("Hi, " + name);
		return response;
	}

	@ApiMethod(
			name = "exampleOauth",
			httpMethod = ApiMethod.HttpMethod.POST)
	public MyBean exampleOauth(MyBean myBeanToken){

		MyBean myBean = new MyBean();
		myBean.setData("For the moment there is no answer");

		MyOauthFlowHelpers helper = new MyOauthFlowHelpers();
		helper.exchangeCodeForTokens("some token");
		return myBean;
	}

	@ApiMethod(
			name="exampleInjectedUser",
			httpMethod = ApiMethod.HttpMethod.POST
	)
	public MyBean exampleInjectedUser(MyBean myBean, final User user)
			throws UnauthorizedException {
		if (user == null){
			throw new UnauthorizedException("Authorization required. No injected user was found");
		}
		myBean.setData("user id=" + user.getUserId()+" user email=" + user.getEmail());
		return myBean;
	}
}
