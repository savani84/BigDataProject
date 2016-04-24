package edu.csula.datascience.acquisition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class amazonCollector {
	public List<amazonClass> mungee() throws IOException {
		List<amazonClass> list = Lists.newArrayList();
		
		File file = new File("movies.txt");
		

			FileReader fw = new FileReader(file);
			BufferedReader bw = new BufferedReader(fw);
			
			String UserID = "";
			String helpfulness = "";
			String time = "";
			String reviewtxt = "";
			String productID = "";
			String profileName = "";
			String score = "";
			String summary = "";
			String moviename = "";
			while(bw.readLine() != null){
				
				UserID = bw.readLine();
				System.out.println(UserID);
				if(UserID.equals("")){
					UserID = "";
					helpfulness = "";
					time = "";
					reviewtxt = "";
					productID = "";
					score = "";
					summary = "";
					moviename = "";
					continue;
				}
				if(UserID.contains("review/userId:"))
				{
					UserID = UserID.substring(UserID.indexOf(":")+1, UserID.length());
				}
				if(UserID.contains("review/helpfulness:"))
				{
					helpfulness = UserID.substring(UserID.indexOf(":")+1, UserID.length());
				}
				if(UserID.contains("review/time:"))
				{
					time = UserID.substring(UserID.indexOf(":")+1, UserID.length());
				}
				if(UserID.contains("review/text:"))
				{
					reviewtxt = UserID.substring(UserID.indexOf(":")+1, UserID.length());
				}
				if(UserID.contains("product/productId:"))
				{
					productID = UserID.substring(UserID.indexOf(":")+1, UserID.length());
					try
					{
						System.out.println(productID.trim());
					
						moviename = ListLinks.format(productID.trim());
						System.out.println("movie ttt name====="+moviename);
					}catch(Exception ae)
					{
						ae.printStackTrace();
						System.out.println(ae.getMessage());
						
						
					}
				}
				if(UserID.contains("review/profileName:"))
				{
					profileName = UserID.substring(UserID.indexOf(":")+1, UserID.length());
				}
				if(UserID.contains("review/score:"))
				{
					score = UserID.substring(UserID.indexOf(":")+1, UserID.length());
				}
				if(UserID.contains("review/summary:"))
				{
					summary = UserID.substring(UserID.indexOf(":")+1, UserID.length());
				}
				
				if(!moviename.equals("") && !reviewtxt.equals("") ){
					amazonClass amazonobj = new amazonClass(UserID,helpfulness,time,reviewtxt,
							productID,profileName,score,summary,moviename);
					list.add(amazonobj);
				}
			
			}
			bw.close();
		return list;
	}

	public void save(Collection<amazonClass> amazonData) {
		MongoClient mongoClient;
	    MongoDatabase database;
	    MongoCollection<Document> collection;
		try {
	        mongoClient = new MongoClient();
	        database = mongoClient.getDatabase("twitter_demo");
	        collection = database.getCollection("amazon_data");
		    
		    File file = new File("amazondata.txt");
	
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(amazonClass s : amazonData){
				Document doc = new Document("moviename", s.getMoviename()).
						  append("datasource", "AMAZONDATA").
						  append("UserID", s.getUserID()).
		                  append("helpfulness", s.getHelpfulness()).
		                  append("time", s.getTime()).
		                  append("reviewtxt", s.getReviewtxt()).
		                  append("productID", s.getProductID()).
		                  append("score", s.getScore()).
		                  append("summary",s.getSummary());
				
				bw.write(doc.toString());
				collection.insertOne(doc);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
