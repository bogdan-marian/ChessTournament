package eu.chesstournament.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import eu.chesstournament.data.TournamentContract.ProfileEntry;
import eu.chesstournament.data.TournamentContract.ClubEntry;
import eu.chesstournament.data.TournamentContract.ClubManagerEntry;
import eu.chesstournament.data.TournamentContract.ClubMemberEntry;
import eu.chesstournament.tools.Constants;


/**
 * Created by bogdan on 4/19/2015.
 */
public class TournamentDbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "ChessTournament.db";
	private static final String LOG_TAG = Constants.LOG_TAG;

	public TournamentDbHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String SQL_CREATE_PROFILE_TABLE = "CREATE TABLE " + ProfileEntry.TABLE_NAME+" ("+
				ProfileEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
				ProfileEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
				ProfileEntry.COLUMN_NAME + " TEXT NOT NULL, " +
				ProfileEntry.COLUMN_DATE_OF_BIRTH + " DATE NOT NULL, "+
				ProfileEntry.COLUMN_ELO_OFFICIAL + " INTEGER, "+
				ProfileEntry.COLUMN_ELO_CLUB + " INTEGER " +
				" );";

		final String SQL_CREATE_CLUB_TABLE = "CREATE TABLE " + ClubEntry.TABLE_NAME+" ("+
				ClubEntry.COLUMN_ID +" INTEGER PRIMARY KEY, "+
				ClubEntry.COLUMN_NAME+" TEXT NOT NULL, " +
				ClubEntry.COLUMN_SHORT_NAME+" TEXT NOT NULL, "+
				ClubEntry.COLUMN_EMAIL+" TEXT NOT NULL, "+
				ClubEntry.COLUMN_COUNTRY+" TEXT NOT NULL, "+
				ClubEntry.COLUMN_CITY+" TEXT NOT NULL, "+
				ClubEntry.COLUMN_DESCRIPTION+" TEXT NOT NULL"+
				" );";

		final String SQL_CREATE_CLUB_MANAGER_TABLE = "CREATE TABLE " + ClubManagerEntry.TABLE_NAME+" ("+
				ClubManagerEntry.COLUMN_ID +" INTEGER PRIMARY KEY, "+
				ClubManagerEntry.COLUMN_PROFILE_ID+" TEXT NOT NULL, "+
				ClubManagerEntry.COLUMN_CLUB_ID+" INTEGER NOT NULL, "+
				ClubManagerEntry.COLUMN_DATE+" DATE NOT NULL, "+

				" FOREIGN KEY ("+ ClubManagerEntry.COLUMN_PROFILE_ID +") REFERENCES " +
				ProfileEntry.TABLE_NAME+ " ("+ ProfileEntry.COLUMN_ID+") "+
				" FOREIGN KEY ("+ ClubManagerEntry.COLUMN_CLUB_ID +") REFERENCES " +
				ClubEntry.TABLE_NAME+ " ("+ ClubEntry.COLUMN_ID+") "+

				" );";
		Log.d(LOG_TAG, SQL_CREATE_PROFILE_TABLE);
		Log.d(LOG_TAG, SQL_CREATE_CLUB_TABLE);

		sqLiteDatabase.execSQL(SQL_CREATE_PROFILE_TABLE);
		sqLiteDatabase.execSQL(SQL_CREATE_CLUB_TABLE);
		sqLiteDatabase.execSQL(SQL_CREATE_CLUB_MANAGER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProfileEntry.TABLE_NAME);
	}
}
