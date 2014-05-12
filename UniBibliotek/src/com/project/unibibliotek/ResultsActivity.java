package com.project.unibibliotek;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.project.unibibliotek.logic.WebService;
import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.SearchFilter;
import com.project.unibibliotek.ObjectsSharer;

public class ResultsActivity extends ActionBarActivity 
{
	public final static String RESULT_TO_DETAILED_QUERY_MESSAGE = "com.project.unibibliotek.RESULT_TO_DETAILED_QUERY_MESSAGE";
	
	private List<Book> booksList;
	private WebService librarian;
	private ProgressBar progressBar;
	
	private class SearchBookTask extends AsyncTask<ArrayList<SearchFilter>, Void, List<Book>> {
		
		@Override
		protected List<Book> doInBackground(ArrayList<SearchFilter>... params) {
			librarian = new WebService();
	        librarian.connect();
	        return librarian.search(params[0]);
			
		}
		
		@Override
		protected void onPreExecute () {
			progressBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onPostExecute(List<Book> books) {
			booksList = books;
			paintBookList();
			progressBar.setVisibility(View.INVISIBLE);
		}
		
	}
	
	private void paintBookList () {
		//Check if list is empty. Not tested yet.
        for (Book book: booksList)
        {
        	if (book.getTitle() == null)
        		book.setTitle("No title.");
        }
        if (booksList.size() == 0)
        {
        	Book book = new Book();
        	book.setTitle("No results found");
        	booksList.add(book);
        }
        
        ListView resultLV = (ListView) findViewById(R.id.resultListView);
        resultLV.setAdapter(new ResultsListAdapter(this, booksList));
        
        resultLV.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				pushDetailedScreen(position);
			}
        });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E1A22E")));
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
        	ArrayList<SearchFilter> filters = (ArrayList<SearchFilter>)extras.getSerializable(SearchActivity.SEARCH_TO_RESULT_QUERY_MESSAGE);
            new SearchBookTask().execute(filters);
        }
       
             
	}

	private void pushDetailedScreen(int pos)
	{
		try {
			Intent intent = new Intent(this, com.project.unibibliotek.DetailedActivity.class);
			Book book = booksList.get(pos);
			ObjectsSharer.setBook(book);
	        startActivity(intent);
		}
		catch (Exception e) {
			Log.e("DetailedActivity", e.getMessage());
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	    switch (item.getItemId()) 
	    {
	        case android.R.id.home:
	        	this.finish();
	            return true;
	        case R.id.action_settings:
	        return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
