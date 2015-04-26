package eu.chesstournament.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import eu.chesstournament.data.TournamentContract.ProfileEntry;
import eu.chesstournament.tools.Constants;
import eu.chesstournament.tools.TournamentUtils;


public class TestProvider extends AndroidTestCase{
	private static String LOG_TAG = Constants.LOG_TAG;

	public void testInsertReadProvider(){
		ContentValues profileValues = TestUtilities.createProfile1Values();

		//register a content observer
		TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
		mContext.getContentResolver().registerContentObserver(ProfileEntry.CONTENT_URI,true,tco);
		Uri profileUri = mContext.getContentResolver().insert(ProfileEntry.CONTENT_URI, profileValues);

		//did our content observer got called?
		tco.waitForNotificationOrFail();
		mContext.getContentResolver().unregisterContentObserver(tco);

		String profileId = profileUri.getLastPathSegment();
		Log.d(LOG_TAG, "inserted profile id: " + profileId);

		String originalId = TournamentUtils.getProfileId(profileValues);
		assertTrue("Error: the profile was not inserted correctly ", profileId.equals(originalId));

		//a cursor is the primary interface to query results
		Cursor cursor = mContext.getContentResolver().query(
				ProfileEntry.CONTENT_URI,
				null, // leaving "columns" null just returns all the columns.
				null, // cols for "where" clause
				null, // values for "where" clause
				null  // sort order
		);

		TestUtilities.validateCursor("testInsertReadProvider. Error validating ProfileEntry. ",
				cursor, profileValues);
	}

	@Override
	protected void setUp() throws Exception {
		deleteTheDatabase();
	}
	private void deleteTheDatabase(){
		mContext.deleteDatabase(TournamentDbHelper.DATABASE_NAME);
		Log.d(LOG_TAG,"Database deleted");
	}
}
