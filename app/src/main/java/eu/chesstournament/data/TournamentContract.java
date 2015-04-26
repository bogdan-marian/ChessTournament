package eu.chesstournament.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the local tournament database
 * Created by bogdan on 4/19/2015.
 */
public class TournamentContract {
	//content authority and uri
	public static final String CONTENT_AUTHORITY ="eu.chesstournament";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

	//paths
	public static final String PATH_PROFILE = "profile";
	public static final String PATH_CLUB = "club";
	public static final String PATH_CLUB_MANAGER = "clubManager";
	public static final String PATH_CLUB_MEMBER = "clubMember";

	/**
	 * Inner class that defines the contents of the profile table
	 */
	public static final class ProfileEntry implements BaseColumns{
		//content stuff
		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFILE).build();

		public static final String CONTENT_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_PROFILE;
		public static final String CONTENT_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_PROFILE;

		//build uri
		public static Uri buildProfileUri(String id){
			return CONTENT_URI.buildUpon().appendPath(id).build();
		}

		//table columns
		public static final String TABLE_NAME = "profile";
		public static final String COLUMN_ID = "id";
		public static final String COLUMN_EMAIL = "email";
		public static final String COLUMN_NAME ="name";
		public static final String COLUMN_DATE_OF_BIRTH="dateOfBirth";
		public static final String COLUMN_ELO_OFFICIAL = "eloOfficial";
		public static final String COLUMN_ELO_CLUB = "eloClub";
	}

	public static final class ClubEntry implements BaseColumns{
		public static final String TABLE_NAME = "club";
		public static final String COLUMN_ID = "id";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_SHORT_NAME = "shortName";
		public static final String COLUMN_EMAIL = "email";
		public static final String COLUMN_COUNTRY = "country";
		public static final String COLUMN_CITY = "city";
		public static final String COLUMN_DESCRIPTION = "description";
	}

	public static final class ClubManagerEntry implements BaseColumns{
		public static final String TABLE_NAME = "clubManager";
		public static final String COLUMN_ID = "id";
		public static final String COLUMN_PROFILE_ID = "profileId";
		public static final String COLUMN_CLUB_ID = "clubId";
		public static final String COLUMN_DATE = "dateCreated";
	}

	public static final class ClubMemberEntry implements BaseColumns{
		public static final String TABLE_NAME = "clubMember";
		public static final String COLUMN_ID = "id";
		public static final String COLUMN_PROFILE_ID = "profileId";
		public static final String COLUMN_CLUB_ID = "clubId";
		public static final String COLUMN_DATE = "date";
	}
}
