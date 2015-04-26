package eu.chesstournament.data;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import eu.chesstournament.tools.Constants;
import eu.chesstournament.tools.PollingCheck;


public class TestUtilities extends AndroidTestCase {
	private static final String LOG_TAG = Constants.LOG_TAG;

	static ContentValues createProfile1Values(){
		ContentValues contentValues = new ContentValues();
		contentValues.put(TournamentContract.ProfileEntry.COLUMN_ID, "profile1@gmail.com");
		contentValues.put(TournamentContract.ProfileEntry.COLUMN_EMAIL, "profile1@gmail.com");
		contentValues.put(TournamentContract.ProfileEntry.COLUMN_NAME, "Bogdan Marian Test1");
		/*@SuppressWarnings("deprecation")
		Date date = new Date("14/august/1978");*/
		contentValues.put(TournamentContract.ProfileEntry.COLUMN_DATE_OF_BIRTH, "14/august/1978");
		contentValues.put(TournamentContract.ProfileEntry.COLUMN_ELO_OFFICIAL, 1001);
		contentValues.put(TournamentContract.ProfileEntry.COLUMN_ELO_CLUB, 1002);

		return contentValues;
	}
	static ContentValues createClub1Values(){
		ContentValues contentValues = new ContentValues();
		contentValues.put(TournamentContract.ClubEntry.COLUMN_ID, 101);
		contentValues.put(TournamentContract.ClubEntry.COLUMN_NAME, "CRELEL junior debug");
		contentValues.put(TournamentContract.ClubEntry.COLUMN_SHORT_NAME, "CRELEL");
		contentValues.put(TournamentContract.ClubEntry.COLUMN_EMAIL, "crelel@gmail.com");
		contentValues.put(TournamentContract.ClubEntry.COLUMN_COUNTRY, "Belgium");
		contentValues.put(TournamentContract.ClubEntry.COLUMN_CITY, "Liege");
		contentValues.put(TournamentContract.ClubEntry.COLUMN_DESCRIPTION, "just debug description");
		return contentValues;
	}
	static ContentValues createManager1Values(){
		ContentValues contentValues = new ContentValues();
		contentValues.put(TournamentContract.ClubManagerEntry.COLUMN_ID, 201);
		contentValues.put(TournamentContract.ClubManagerEntry.COLUMN_PROFILE_ID, "profile1@gmail.com");
		contentValues.put(TournamentContract.ClubManagerEntry.COLUMN_CLUB_ID, 101);
		contentValues.put(TournamentContract.ClubManagerEntry.COLUMN_DATE, "26/04/2015");
		return contentValues;
	}

	static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
		assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
		validateCurrentRecord(error, valueCursor, expectedValues);
		valueCursor.close();
	}

	static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
		Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
		for (Map.Entry<String, Object> entry : valueSet) {
			String columnName = entry.getKey();
			int idx = valueCursor.getColumnIndex(columnName);
			assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
			String expectedValue = entry.getValue().toString();
			String containedValue = valueCursor.getString(idx);
			assertEquals("Value '" + entry.getValue().toString() +
					"' did not match the expected value '" +
					expectedValue + "'. " + error, expectedValue, containedValue);
		}
	}

	public static String createItem(SQLiteDatabase db,String tableName, ContentValues testValues){

		Long insertResult = db.insert(tableName,null, testValues);
		Log.d(LOG_TAG, "InsertResult for " +tableName + " = "+ insertResult);

		Cursor cursor = db.query(
				tableName, // Table to Query
				null, // all columns
				null, // Columns for the "where" clause
				null, // Values for the "where" clause
				null, // columns to group by
				null, // columns to filter by row groups
				null // sort order
		);

		//test wee have data
		assertTrue("Error: No Records returned from query", cursor.moveToFirst());

		//test data is the same
		TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
				cursor, testValues);
		return testValues.getAsString(TournamentContract.ProfileEntry.COLUMN_ID);
	}

	/*
        Students: The functions we provide inside of TestProvider use this utility class to test
        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
        CTS tests.

        Note that this only tests that the onChange function is called; it does not test that the
        correct Uri is returned.
     */
	static class TestContentObserver extends ContentObserver {
		final HandlerThread mHT;
		boolean mContentChanged;

		static TestContentObserver getTestContentObserver() {
			HandlerThread ht = new HandlerThread("ContentObserverThread");
			ht.start();
			return new TestContentObserver(ht);
		}

		private TestContentObserver(HandlerThread ht) {
			super(new Handler(ht.getLooper()));
			mHT = ht;
		}

		// On earlier versions of Android, this onChange method is called
		@Override
		public void onChange(boolean selfChange) {
			onChange(selfChange, null);
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			mContentChanged = true;
		}

		public void waitForNotificationOrFail() {
			// Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
			// It's useful to look at the Android CTS source for ideas on how to test your Android
			// applications.  The reason that PollingCheck works is that, by default, the JUnit
			// testing framework is not running on the main Android application thread.
			new PollingCheck(5000) {
				@Override
				protected boolean check() {
					return mContentChanged;
				}
			}.run();
			mHT.quit();
		}
	}

	static TestContentObserver getTestContentObserver() {
		return TestContentObserver.getTestContentObserver();
	}
}
