package eu.chesstournament.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;


public class TestDb  extends AndroidTestCase{


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
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	//TODO Bogdan: Create ClubMember table and you should insert some test data in tables also;

	private void deleteTheDatabase(){
		 mContext.deleteDatabase(TournamentDbHelper.DATABASE_NAME);
	}
}
