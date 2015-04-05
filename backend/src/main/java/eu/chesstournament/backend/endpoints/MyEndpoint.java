/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package eu.chesstournament.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import eu.chesstournament.backend.models.MyBean;

/**
 * An endpoint class we are exposing
 */
@Api(   name = "myApi",
		version = "v1",
		namespace = @ApiNamespace(
				ownerDomain = "backend.chesstournament.eu",
				ownerName = "backend.chesstournament.eu",
				packagePath = ""))
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
			name = "authenticate",
			httpMethod = ApiMethod.HttpMethod.POST)
	public MyBean authenticate(MyBean myBeanToken){
		MyBean myBean = new MyBean();
		myBean.setData("For the moment there is no answer");
		return myBean;
	}

}
