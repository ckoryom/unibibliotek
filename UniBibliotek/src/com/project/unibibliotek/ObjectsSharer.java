package com.project.unibibliotek;
import com.project.unibibliotek.model.Book;

public class ObjectsSharer 
{
	private static Book book;
	public static Book getBook()
	{
		return book;
	}
	public static void setBook(Book b)
	{
		book = b;
	}
}
