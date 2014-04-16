package com.project.unibibliotek;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.unibibliotek.model.Book;

public class ResultsListAdapter extends BaseAdapter 
{
	private List<Book> booksList;
	Context context;
    private LayoutInflater inflater = null;
    
    public ResultsListAdapter(Context con ,List<Book> books)
	{
    	context = con;
    	inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		booksList = books;
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

        Book book = booksList.get(position);

        bookNameTV.setText(book.getTitle());
        authorNameTV.setText(book.getAuthor().getName()+" "+book.getAuthor().getLastName()+", "+book.getYear());
        availabilityTV.setText(book.getAvailability().toString());

        return convertView;
	}
	

}
