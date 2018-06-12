package de.topobyte.xml4jah.dom;

public class Book
{

	private String year;
	private String title;
	private String wikidata;

	public Book(String year, String title, String wikidata)
	{
		this.year = year;
		this.title = title;
		this.wikidata = wikidata;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getWikidata()
	{
		return wikidata;
	}

	public void setWikidata(String wikidata)
	{
		this.wikidata = wikidata;
	}

}
