package eu.chesstournament.backend.tools;

import java.util.logging.Level;
import java.util.logging.Logger;

import eu.chesstournament.backend.endpoints.MyEndpoint;

/**
 * Created by bogdan on 4/7/2015.
 */
public class MyLogger {
	public static final Logger logger = buildLogger();

	private static Logger buildLogger(){
		Logger newLogger  = Logger.getLogger(MyEndpoint.class.getName());
		newLogger.setLevel(Level.INFO);
		return newLogger;
	}
}
