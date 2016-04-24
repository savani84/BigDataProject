package edu.csula.datascience.acquisition;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListLinks  {
	
	
	public static String format(String s) throws InterruptedException 
	{String moviename="";
		try {
		String url ="http://www.amazon.com/dp/"+s;
		System.out.println("url=="+url);
		
        Document doc;
		
			doc = Jsoup.connect(url).get();
		
			  String title = doc.title();
		      moviename=title;
		      int i=title.indexOf(":");
		      
			  if(title.contains("("))
			  {	  
				  moviename= title.substring(++i,title.indexOf("("));
			  }else
			  {
				  moviename= title.substring(++i,title.length());
			  }
			  System.out.println("manish : " +moviename);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moviename;	
	}
}