package com.project.unibibliotek;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.project.unibibliotek.logic.ImageCommonInterface;
import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.utils.NetworkUtils;

public class ResultsListAdapter extends BaseAdapter implements ImageCommonInterface
{
	private List<Book> booksList;
	Context context;
    private LayoutInflater inflater = null;
    private NetworkUtils networkUtils;
    
    public ResultsListAdapter(Context con ,List<Book> books)
	{
    	context = con;
    	inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		booksList = books;
		networkUtils = new NetworkUtils();
	}
	
	public void setBooksList(List<Book> books)
	{
		booksList = books;
	}

	@Override
	public int getCount() {
		return booksList.size();
	}

	@Override
	public Book getItem(int position) {
		return booksList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if(convertView==null)
        {
            //LayoutInflater inflater = (LayoutInflater) ResultsListAdapter.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.result_list_item, parent,false);
			convertView = inflater.inflate(R.layout.result_list_item, null);
        }

        TextView bookNameTV = (TextView)convertView.findViewById(R.id.bookNameTextView);
        TextView authorNameTV = (TextView)convertView.findViewById(R.id.authorTextView);
        TextView availabilityTV = (TextView)convertView.findViewById(R.id.availabilityTextView);
        ImageView imageTV = (ImageView)convertView.findViewById(R.id.resultsBookImageView);
        
        Book book = booksList.get(position);
        if (book.getTitle() != null && book.getTitle() != ""){
        	bookNameTV.setText(book.getTitle());
        }
        else {
        	bookNameTV.setText("");
        }
        String author = "";
        if (book.getAuthor().getName() != null){
        	author += book.getAuthor().getName();
        }
        if (book.getAuthor().getLastName() != null) {
        	author += " " + book.getAuthor().getLastName();
        }
        String year = "";
        if (book.getYear() != null && book.getYear() != "") {
        	year = book.getYear();
        }
        authorNameTV.setText(author + ", " + year);
        String availability = "";
        if (book.getAvailability() !=null) {
        	availability = book.getAvailability().toString();
        }
        availabilityTV.setText(availability);
        if (book.getImages() != null && book.getImages().getSmallImage() != null) {
        	imageLoader.displayImage(book.getImages().getSmallImage(), imageTV, options, animationListener);
        }
        return convertView;
	}
	

}
