package eu.chesstournament.backend.tools;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.chesstournament.backend.endpoints.MyEndpoint;

/**
 * Place holder class for code to implement oauth authentication flow
 */
public class MyOauthFlowHelpers {
	private static final Logger logger = Logger.getLogger(MyEndpoint.class.getName());

	public void exchangeCodeForTokens(String code){

		logger.setLevel(Level.INFO);

		logger.info("message from bogdan");
	}
}
