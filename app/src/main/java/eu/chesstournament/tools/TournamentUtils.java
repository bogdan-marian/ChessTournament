package eu.chesstournament.tools;

import android.content.ContentValues;

import eu.chesstournament.data.TournamentContract;

/**
 * Created by bogdan on 26/04/2015.
 */
public class TournamentUtils {
	public static String getProfileId(ContentValues contentValues){
		String profileId;
		profileId = contentValues.getAsString(TournamentContract.ProfileEntry.COLUMN_ID);
		if(profileId !=null){
			return profileId;
		}
		throw new IllegalStateException("Illegal content values set");
	}
}
