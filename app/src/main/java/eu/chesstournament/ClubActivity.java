package eu.chesstournament;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClubActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_club);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_club, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		ArrayAdapter<String> mClubAdapter;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {


			String[] fakeClubs = {
					"Crerel junior Liege",
					"Politehnica Iasi",
					"Juniorul Iasi",
					"Amatorul Liege"
			};
			List<String> clubList = new ArrayList<>(Arrays.asList(fakeClubs));
			clubList.add("Manual added club");
			mClubAdapter =
					new ArrayAdapter<String>(
							getActivity(),
							R.layout.list_item_club,
							R.id.list_item_club_textView,
							clubList
					);
			View rootView = inflater.inflate(R.layout.fragment_club, container, false);

			ListView listView = (ListView) rootView.findViewById(R.id.listview_club);
			listView.setAdapter(mClubAdapter);
			return rootView;
		}

	}
}
