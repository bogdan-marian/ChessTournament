package eu.chesstournament.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import eu.chesstournament.tools.TournamentUtils;

/**
 * Created by bogdan on 26/04/2015.
 */
public class TournamentProvider extends ContentProvider {
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	private TournamentDbHelper mOpenHelper;

	static final int PROFILE = 100;

	@Override
	public boolean onCreate() {
		mOpenHelper = new TournamentDbHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		final int match = sUriMatcher.match(uri);
		Cursor retCursor;
		switch (match){
			case PROFILE:
				retCursor = mOpenHelper.getReadableDatabase().query(
						TournamentContract.ProfileEntry.TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder
				);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		retCursor.setNotificationUri(getContext().getContentResolver(),uri);
		return retCursor;
	}

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);

		switch (match){
			case PROFILE:
				return TournamentContract.ProfileEntry.CONTENT_TYPE;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		String idProfile = TournamentUtils.getProfileId(values);
		Uri returnUri;

		switch (match){
			case PROFILE: {
				long id = db.insert(TournamentContract.ProfileEntry.TABLE_NAME,null,values);
				if (id > 0){
					returnUri = TournamentContract.ProfileEntry.buildProfileUri(idProfile);
				}else {
					throw new android.database.SQLException("Failed to insert row into " + uri);
				}
				break;
			}
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri,null);
		return returnUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int rowsDeleted;

		//this makes delete all rows return the number of rows deleted
		if (selection == null) selection = "1";
		switch (match){
			case PROFILE:
				rowsDeleted = db.delete(
						TournamentContract.ProfileEntry.TABLE_NAME,selection,selectionArgs);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		if (rowsDeleted != 0){
			getContext().getContentResolver().notifyChange(uri,null);
		}
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int rowsUpdated;

		switch (match){
			case PROFILE:
				rowsUpdated = db.update(
						TournamentContract.ProfileEntry.TABLE_NAME, values,selection,selectionArgs);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		if (rowsUpdated!= 0){
			getContext().getContentResolver().notifyChange(uri,null);
		}
		return rowsUpdated;
	}

	static UriMatcher buildUriMatcher(){
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = TournamentContract.CONTENT_AUTHORITY;

		//For each type of URI you want to add, create a corresponding code
		matcher.addURI(authority, TournamentContract.PATH_PROFILE, PROFILE);

		return matcher;
	}
}
