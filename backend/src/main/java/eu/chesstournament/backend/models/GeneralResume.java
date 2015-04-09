package eu.chesstournament.backend.models;

/**
 * Created by bogdan on 4/9/2015.
 */
public class GeneralResume {
	private String userEmail;
	private String domainUserId = "null for the moment - first milestone";
	private String userDomain = "on todo: example: google.com";
	private String localUserId = "on todo: wee should create a local user bound to this user email or id" +
			" but it is not required for this demo";

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getDomainUserId() {
		return domainUserId;
	}

	public void setDomainUserId(String domainUserId) {
		this.domainUserId = domainUserId;
	}

	public String getLocalUserId() {
		return localUserId;
	}

	public void setLocalUserId(String localUserId) {
		this.localUserId = localUserId;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}
}
