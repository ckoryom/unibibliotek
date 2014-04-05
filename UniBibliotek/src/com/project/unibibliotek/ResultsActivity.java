package com.project.unibibliotek;

import java.util.List;

import com.project.unibibliotek.logic.WebService;
import com.project.unibibliotek.model.Book;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ResultsActivity extends ActionBarActivity {
	
	public List<Book> booksList;
	public WebService librarian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E1A22E")));
        
        Intent intent = getIntent();
        String bookTitleToSearch = intent.getStringExtra(SearchActivity.SEARCH_TO_RESULT_QUERY_MESSAGE);
        
        //TODO: request results and display them in a list
        librarian = new WebService();
        booksList = librarian.search(bookTitleToSearch);
        //In progress..
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
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

}
