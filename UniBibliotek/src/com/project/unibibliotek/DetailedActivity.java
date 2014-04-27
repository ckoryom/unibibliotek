package com.project.unibibliotek;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.utils.NetworkUtils;

public class DetailedActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed);
		
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E1A22E")));
		
		Book book = ObjectsSharer.getBook();
		TextView titleTV = (TextView) findViewById(R.id.DescriptionTitleTextView);
		titleTV.setText(book.getTitle());
		ImageView coverIV = (ImageView) findViewById(R.id.DescriptionBookImageView);
		NetworkUtils networkUtils = new NetworkUtils();
		Bitmap largeBitmap = networkUtils.getBitmapFromUrl(book.getImages().getLargeImage());
		coverIV.setImageBitmap(largeBitmap);
		Log.d(getClass().getName(),String.format("Image height: %d",largeBitmap.getHeight()));
		System.out.printf("Image height: %d\n",largeBitmap.getHeight());
		TextView descriptionTV = (TextView) findViewById(R.id.DescriptionDescriptionTextView);
		descriptionTV.setText(book.getDescription());
		Log.d(getClass().getName(),"Book description: "+book.getDescription());
		TextView authorTV = (TextView) findViewById(R.id.DescriptionAuthorTextView);
		authorTV.setText(book.getAuthor().getAuthorName()+", "+book.getYear()+"\n"+book.getAvailability().toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed, menu);
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
