package eu.chesstournament.data;

import android.provider.BaseColumns;

/**
 * Defines table and column names for the local tournament database
 * Created by bogdan on 4/19/2015.
 */
public class TournamentContract {
	/**
	 * Inner class that defines the contents of the profile table
	 */
	public static final class ProfileEntry implements BaseColumns{
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
