package com.project.unibibliotek;
import java.util.ArrayList;

import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.SearchFilter;

public class ObjectsSharer 
{
	private static Book book;
	private static ArrayList<SearchFilter> searchFilters;
	public static Book getBook()
	{
		return book;
	}
	public static void setBook(Book b)
	{
		book = b;
	}
	public static ArrayList<SearchFilter> getSearchFilters()
	{
		return searchFilters;
	}
	public static void setSearchFilters(ArrayList<SearchFilter> filters)
	{
		searchFilters = filters;
	}
}
