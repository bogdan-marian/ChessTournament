package eu.chesstournament.netServices.my;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.Scopes;

import java.io.IOException;

import eu.chesstournament.tools.Constants;

/**get token
 * extra details available at:
 * http://developer.android.com/reference/com/google/android/gms/common/Scopes.html
 *
 * https://developer.android.com/google/auth/http-auth.html#ExtendAsyncTask
 * https://developer.android.com/google/auth/http-auth.html#SpecifyingScopes
 */
public class GetTokenFromGoogle extends AsyncTask<Context, Void, String>{
	String accountName;
	@Override
	protected String doInBackground(Context... params) {
		accountName = Constants.accountName;

		String plusLogin = Scopes.PLUS_LOGIN;
		String mScopes = "oauth2:" + plusLogin;

		Context context = params[0];
		try {
			String token = GoogleAuthUtil.getToken(context, Constants.accountName, mScopes);

			return token;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GoogleAuthException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String token) {
		Log.d("eu.chesstournament", "Received token = " + token);
		(new SendTokenToServer()).execute(token);
	}
}
