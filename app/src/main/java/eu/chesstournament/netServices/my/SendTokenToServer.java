package eu.chesstournament.netServices.my;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import eu.chesstournament.backend.myApi.MyApi;
import eu.chesstournament.backend.myApi.model.MyBean;
import eu.chesstournament.tools.Constants;

/**
 * Created by bogdan on 4/5/2015.
 */
public class SendTokenToServer extends AsyncTask<String, Void, String> {
	private MyApi myApi = null;

	@Override
	protected String doInBackground(String... params) {
		Log.d(Constants.LOG_TAG, "Start of send");
		if (myApi == null) {
			MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
					new AndroidJsonFactory(), null)
					.setRootUrl(Constants.ROOT_URL);
			myApi = builder.build();
		}
		try{
			MyBean myBean = new MyBean();
			myBean.setData(params[0]);
			MyBean returnBean = myApi.authenticate(myBean).execute();
			Log.d(Constants.LOG_TAG, "End of send");
			return returnBean.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String response) {
		Log.d(Constants.LOG_TAG, "Response : "+response);
	}
}
