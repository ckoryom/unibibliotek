package com.project.unibibliotek;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.unibibliotek.logic.WebService;
import com.project.unibibliotek.model.Book;

public class ResultsActivity extends Activity {
	
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
        try {
			librarian.setUrl(new URL("http://ubz-primo-test.hosted.exlibrisgroup.com/PrimoWebServices/services/tags"));
		} catch (MalformedURLException e) { e.printStackTrace(); }
        librarian.connect();
        booksList = librarian.search("39UBZ",bookTitleToSearch);
        String[] booksArray = new String[booksList.size()];
        for (int i=0; i<booksList.size(); i++)
        {
        	Book book = booksList.get(i);
        	booksArray[i] = book.getTitle();
        }
        //booksList.toArray(booksArray);
        
        ListView resultLV = (ListView) findViewById(R.id.resultListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, booksArray);
        resultLV.setAdapter(arrayAdapter);
        
        resultLV.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				
			}
        });
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
