package eu.chesstournament.tools;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

/**
 * Created by bogdan on 4/6/2015.
 */
public class Constants {
	//non static
	public static String accountName = "";
	public static GoogleAccountCredential googleAccountCredential;

	//static set by me
	public static final String LOG_TAG = "eu.chesstournament";
	public static final String ROOT_URL = "https://chess-tournaments.appspot.com/_ah/api/";

	//static by google
	public static final String WEB_CLIENT_ID = "1069292997068-4hb8famlmhfld52pgdlu6s12gugjaeqk.apps.googleusercontent.com";
}
