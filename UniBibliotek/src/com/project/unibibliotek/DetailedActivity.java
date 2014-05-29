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

import com.project.unibibliotek.logic.ImageCommonInterface;
import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.Location;
import com.project.unibibliotek.utils.NetworkUtils;

public class DetailedActivity extends Activity implements ImageCommonInterface 
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
		String title = "";
		if (book.getTitle() != null && book.getTitle() != "") {
			String isbn = "";
			if (book.getIsbn() != null && book.getIsbn().size() > 0) {
				isbn = book.getIsbn().get(0);
			}
			title = book.getTitle() + "\n ISBN:" + isbn;
		}
		titleTV.setText(title);
		ImageView coverIV = (ImageView) findViewById(R.id.DescriptionBookImageView);
		NetworkUtils networkUtils = new NetworkUtils();
		if (book.getImages() != null && book.getImages().getLargeImage() != null) {
			imageLoader.displayImage(book.getImages().getSmallImage(), coverIV);
		}
		String description = "";
		TextView descriptionTV = (TextView) findViewById(R.id.DescriptionDescriptionTextView);
		if (book.getDescription() != null && book.getDescription() != "") {
			description = book.getDescription();
		}
		descriptionTV.setText(description);
		TextView authorTV = (TextView) findViewById(R.id.DescriptionAuthorTextView);
		String rating = "";
		if (book.getRating() != null) {
			rating = "\nRating: " + book.getRating().getAverageRating() + "/5 -- " + book.getRating().getTotalRatings() + " votes\n";
		}
		String location = "Location: ";
		if (book.getLocations()!=null) {
			if (book.getLocations().size()>0) {
				for (Location l:book.getLocations()) {
					if (l.getSection() != null) {
						location += l.getSection(); 
					}
					if (l.getSite() != null) {
						location += " | " + l.getSite();
					}
				}
				location = "\n" + location;
			}
		}
		authorTV.setText("Author:" + book.getAuthor().getAuthorName()+", "+book.getYear()+"\n" + "Publisher: " + book.getPublisher() + "\n Status:" + book.getAvailability().toString() + rating + location);
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
