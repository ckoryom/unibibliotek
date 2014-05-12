package com.project.unibibliotek;

import java.util.ArrayList;

import com.project.unibibliotek.model.Filter;
import com.project.unibibliotek.model.SearchFilter;

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


public class SearchActivity extends ActionBarActivity 
{
	
	public final static String SEARCH_TO_RESULT_QUERY_MESSAGE = "com.project.unibibliotek.SEARCH_TO_RESULT_QUERY_MESSAGE";
	
	private enum SearchType 
	{
	    ALL, ISBN, DETAILED 
	}
	private SearchType searchType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E1A22E")));
        setSearchAll(null);
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

	//Button actions
	
	public void setSearchAll(View view)
	{
		searchType = SearchType.ALL;
		EditText editText1 = (EditText) findViewById(R.id.searchEditText1);
		EditText editText2 = (EditText) findViewById(R.id.searchEditText2);
		EditText editText3 = (EditText) findViewById(R.id.searchEditText3);
		editText1.setHint("query");
		editText2.setVisibility(View.GONE);
		editText3.setVisibility(View.GONE);
	}
	
	public void setSearchIsbn(View view)
	{
		searchType = SearchType.ISBN;
		EditText editText1 = (EditText) findViewById(R.id.searchEditText1);
		EditText editText2 = (EditText) findViewById(R.id.searchEditText2);
		EditText editText3 = (EditText) findViewById(R.id.searchEditText3);
		editText1.setHint("isbn");
		editText2.setVisibility(View.GONE);
		editText3.setVisibility(View.GONE);
	}
	
	public void setSearchDetailed(View view)
	{
		searchType = SearchType.DETAILED;
		EditText editText1 = (EditText) findViewById(R.id.searchEditText1);
		EditText editText2 = (EditText) findViewById(R.id.searchEditText2);
		EditText editText3 = (EditText) findViewById(R.id.searchEditText3);
		editText1.setHint("title");
		editText2.setVisibility(View.VISIBLE);
		editText3.setVisibility(View.VISIBLE);
	}
	
	public void initiateBookSearch(View view) 
    {
		Intent intent = new Intent(this, ResultsActivity.class);
	    EditText editText1 = (EditText) findViewById(R.id.searchEditText1);
	    EditText editText2 = (EditText) findViewById(R.id.searchEditText2);
	    EditText editText3 = (EditText) findViewById(R.id.searchEditText3);
	    
	    ArrayList<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
	    if (searchType == SearchType.ALL)
	    {
	    	SearchFilter subjectFilter = new SearchFilter(Filter.ANY, editText1.getText().toString());
    		searchFilters.add(subjectFilter);
	    }
	    else if (searchType == SearchType.ISBN)
	    {
	    	SearchFilter subjectFilter = new SearchFilter(Filter.ISBN, editText1.getText().toString());
    		searchFilters.add(subjectFilter);
	    }
	    else if (searchType == SearchType.DETAILED)
	    {
	    	if (editText1.getText().toString().length() > 0)
	    	{
	    		SearchFilter subjectFilter = new SearchFilter(Filter.TITLE, editText1.getText().toString());
	    		searchFilters.add(subjectFilter);
	    	}
	    	if (editText2.getText().toString().length() > 0)
	    	{
	    		SearchFilter subjectFilter = new SearchFilter(Filter.AUTHOR, editText2.getText().toString());
	    		searchFilters.add(subjectFilter);
	    	}
	    	if (editText3.getText().toString().length() > 0)
	    	{
	    		SearchFilter subjectFilter = new SearchFilter(Filter.SUBJECT, editText3.getText().toString());
	    		searchFilters.add(subjectFilter);
	    	}		
	    }
	    intent.putExtra(SEARCH_TO_RESULT_QUERY_MESSAGE, searchFilters);
	    startActivity(intent);
    }
	
}
