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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.chesstournament.tools.Constants;


public class HomeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_home, menu);
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
		ArrayAdapter<String> mOptionAdapter;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
			String[] optionsAray = {
					"User connected: " + Constants.accountName,
					"Create club",
					"Join club"
			};
			List<String> options = new ArrayList<>(Arrays.asList(optionsAray));
			mOptionAdapter = new ArrayAdapter<String>(
					getActivity(),
					R.layout.list_item_home,
					R.id.list_item_home_textView,
					options
			);
			View rootView = inflater.inflate(R.layout.fragment_home, container, false);
			ListView listView = (ListView) rootView.findViewById(R.id.listview_home);
			listView.setAdapter(mOptionAdapter);
			listView.setOnItemClickListener(getClickListener());
			return rootView;
		}

		private AdapterView.OnItemClickListener getClickListener(){
			AdapterView.OnItemClickListener onItemClickListener =
					new AdapterView.OnItemClickListener(){
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							reactOnClick(position);
						}
					};
			return onItemClickListener;
		}

		private void reactOnClick(int position){
			String listText = mOptionAdapter.getItem(position);
			//
			switch (listText){
				case "Create club":
					Toast.makeText(getActivity(),"Bingo " +listText,Toast.LENGTH_SHORT).show();
					break;
				case "Join club":
					Toast.makeText(getActivity(),"Bingo " +listText,Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}
}
