package eu.chesstournament;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import eu.chesstournament.netServices.my.GetGeneralResume;
import eu.chesstournament.netServices.my.GetTokenFromGoogle;
import eu.chesstournament.tools.Constants;


public class AuthenticationActivity extends ActionBarActivity implements
		ConnectionCallbacks, OnConnectionFailedListener{

	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/* A flag indicating that a PendingIntent is in progress and prevents
	   * us from starting further intents.
	   */
	private boolean mIntentInProgress;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN)
				.build();

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_authentication, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//-----------------------------------------------------
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	public void onConnectionFailed(ConnectionResult result) {
		if (!mIntentInProgress && result.hasResolution()) {
			try {
				mIntentInProgress = true;
				startIntentSenderForResult(result.getResolution().getIntentSender(),
						RC_SIGN_IN, null, 0, 0, 0);
			} catch (IntentSender.SendIntentException e) {
				// The intent was canceled before it was sent.  Return to the default
				// state and attempt to connect to get an updated ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	public void onConnected(Bundle connectionHint) {
		// We've resolved any connection errors.  mGoogleApiClient can be used to
		// access Google APIs on behalf of the user.
		Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		String message = String.format("Signed in as %s ", person.getDisplayName());

		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

		//get user email
		Constants.accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);

		//build credentials
		Constants.googleAccountCredential =
				GoogleAccountCredential.usingAudience(this, "server:client_id:"
						+ Constants.WEB_CLIENT_ID);
		Constants.googleAccountCredential.setSelectedAccountName(Constants.accountName);

		//connect to landingEndpoint
		//GetGeneralResume getGeneralResume = new GetGeneralResume();
		//getGeneralResume.execute();
		Intent intent = new Intent(this, ClubActivity.class);
		startActivity(intent);
	}

	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

}
