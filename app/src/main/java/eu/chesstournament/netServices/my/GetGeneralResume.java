package eu.chesstournament.netServices.my;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import eu.chesstournament.backend.landingApi.LandingApi;
import eu.chesstournament.backend.landingApi.model.GeneralResume;
import eu.chesstournament.tools.Constants;

/**
 * Created by bogdan on 4/9/2015.
 */
public class GetGeneralResume extends AsyncTask<Void, Void, GeneralResume> {
	private LandingApi landingApi;

	@Override
	protected GeneralResume doInBackground(Void... params) {
		if (landingApi == null){
			LandingApi.Builder builder =
					new LandingApi.Builder(AndroidHttp.newCompatibleTransport(),
							new AndroidJsonFactory(), Constants.googleAccountCredential)
					.setRootUrl(Constants.ROOT_URL);
			landingApi = builder.build();
		}
		try{
			return landingApi.getGeneralResume().execute();
		} catch (IOException e) {
			Log.d(Constants.LOG_TAG, "not able to authenticate on the backend: " + e);
			return null;
		}

	}

	@Override
	protected void onPostExecute(GeneralResume generalResume) {
		if(generalResume != null){
			try {
				String details = (new AndroidJsonFactory()).toString(generalResume);
				Log.d(Constants.LOG_TAG,"details: "+ details);
			} catch (IOException e) {
				Log.d(Constants.LOG_TAG,"json not working " + e.getStackTrace());
				e.printStackTrace();
			}
		}
	}
}
