package eu.chesstournament.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.Test;

import java.util.HashSet;

import eu.chesstournament.tools.Constants;


public class TestDb  extends AndroidTestCase{
	private static String LOG_TAG = Constants.LOG_TAG;


	@Override
	protected void setUp() throws Exception {
		deleteTheDatabase();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1AndroidTestCaseSetupProperly() {
		assertNotNull("Context is null. setContext should be called before tests are run",
				mContext);
	}
	public void test2CreateDbAndComponents(){

		TournamentDbHelper tournamentDbHelper = new TournamentDbHelper(mContext);
		SQLiteDatabase db = tournamentDbHelper.getWritableDatabase();
		assertTrue("Not able to create a database",db.isOpen());

		//have wee created the tables we want?
		final HashSet<String> tableNameHasSet = new HashSet<>();
		tableNameHasSet.add(TournamentContract.ProfileEntry.TABLE_NAME);
		tableNameHasSet.add(TournamentContract.ClubEntry.TABLE_NAME);
		tableNameHasSet.add(TournamentContract.ClubMemberEntry.TABLE_NAME);
		tableNameHasSet.add(TournamentContract.ClubManagerEntry.TABLE_NAME);
		Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
		assertTrue("Error: The database has not been created correctly", c.moveToFirst());

		do{
			tableNameHasSet.remove(c.getString(0));
		}while (c.moveToNext());
		db.close();

		assertTrue("Error: Database does not contain the correct tables", tableNameHasSet.isEmpty());
	}
	public void test3DbOperations(){
		/*String profileId = createProfile1();
		assertTrue("Not the expected profile", profileId.equals("profile1@gmail.com"));*/

		TournamentDbHelper dbHelper = new TournamentDbHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("PRAGMA foreign_keys=ON;");

		String profileTable = TournamentContract.ProfileEntry.TABLE_NAME;
		ContentValues profileValues = TestUtilities.createProfile1Values();
		TestUtilities.createItem(db, profileTable, profileValues);


		String clubTable = TournamentContract.ClubEntry.TABLE_NAME;
		ContentValues clubValues = TestUtilities.createClub1Values();
		TestUtilities.createItem(db, clubTable, clubValues);

		String managerTable = TournamentContract.ClubManagerEntry.TABLE_NAME;
		ContentValues managerValues = TestUtilities.createManager1Values();
		TestUtilities.createItem(db, managerTable, managerValues);
	}
	private String createProfile1(){
		TournamentDbHelper dbHelper = new TournamentDbHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues testValues = TestUtilities.createProfile1Values();
		db.insert(TournamentContract.ProfileEntry.TABLE_NAME,null, testValues);

		Cursor cursor = db.query(
				TournamentContract.ProfileEntry.TABLE_NAME, // Table to Query
				null, // all columns
				null, // Columns for the "where" clause
				null, // Values for the "where" clause
				null, // columns to group by
				null, // columns to filter by row groups
				null // sort order
		);

		//test wee have data
		assertTrue("Error: No Records returned from location query", cursor.moveToFirst());
		Log.d(LOG_TAG, "Some text from bogdan");

		//test data is the same
		TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
				cursor, testValues);
		return testValues.getAsString(TournamentContract.ProfileEntry.COLUMN_ID);
	}

	private void deleteTheDatabase(){
		mContext.deleteDatabase(TournamentDbHelper.DATABASE_NAME);
		Log.d(LOG_TAG,"Database deleted");
	}
}
