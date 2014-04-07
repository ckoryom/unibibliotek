package com.project.unibibliotek;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends ActionBarActivity {
	public final static String SEARCH_TO_RESULT_QUERY_MESSAGE = "com.project.unibibliotek.SEARCH_TO_RESULT_QUERY_MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E1A22E")));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public void initiateBookSearch(View view) 
    {
		Intent intent = new Intent(this, ResultsActivity.class);
	    EditText editText = (EditText) findViewById(R.id.searchEditText);
	    String message = editText.getText().toString();
	    intent.putExtra(SEARCH_TO_RESULT_QUERY_MESSAGE, message);
	    startActivity(intent);
    }
}
