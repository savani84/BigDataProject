package edu.csula.datascience.acquisition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterCollector {
	private static final String CONSUMER_KEY		= System.getenv("TWITTER_CONSUMER_KEY");
	private static final String CONSUMER_SECRET 	= System.getenv("TWITTER_CONSUMER_SECRET");

	private static final int TWEETS_PER_QUERY = 100; 
	private static final int MAX_QUERIES = 100000; 
	
	public static String cleanText(String text) 
	{ 
		text = text.replace("\n", "\\n"); 
		text = text.replace("\t", "\\t"); 

		return text; 
	}  

	public static OAuth2Token getOAuth2Token() 
	{ 
		OAuth2Token token = null; 
		ConfigurationBuilder cb; 
		cb = new ConfigurationBuilder(); 
		cb.setApplicationOnlyAuthEnabled(true); 
		cb.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET); 

		try { 
			token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token(); 
		} catch (Exception e) { 
			System.out.println("Could not get OAuth2 token"); e.printStackTrace(); System.exit(0); 
		} 
		return token; 
	} 
	
	public static Twitter getTwitter() 
	{ 
		OAuth2Token token; 

		token = getOAuth2Token(); 

		ConfigurationBuilder cb = new ConfigurationBuilder(); 

		cb.setApplicationOnlyAuthEnabled(true); 

		cb.setOAuthConsumerKey(CONSUMER_KEY); 
		cb.setOAuthConsumerSecret(CONSUMER_SECRET); 

		cb.setOAuth2TokenType(token.getTokenType()); 
		cb.setOAuth2AccessToken(token.getAccessToken()); 

		return new TwitterFactory(cb.build()).getInstance(); 
	} 

	public Collection<Status> mungee(String SEARCH_TERM) {
		// TODO Auto-generated method stub
		List<Status> list = Lists.newArrayList();
	    
		int totalTweets = 0;

		long maxID = -1; 
		Twitter twitter = getTwitter(); 

		try 
		{ 
			Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search"); 

			RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets"); 

			System.out.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n", 
					searchTweetsRateLimit.getRemaining(), 
					searchTweetsRateLimit.getLimit(), 
					searchTweetsRateLimit.getSecondsUntilReset()); 
			
			for (int queryNumber=0;queryNumber < MAX_QUERIES; queryNumber++) 
			{ 
				System.out.printf("\n\n!!! Starting loop %d and Total tweets %d\n\n", queryNumber,totalTweets); 

				if(searchTweetsRateLimit != null){
					if (searchTweetsRateLimit.getRemaining() == 0) 
					{ 
						System.out.printf("!!! Sleeping for %d seconds due to rate limits\n", searchTweetsRateLimit.getSecondsUntilReset()); 
	
						Thread.sleep((searchTweetsRateLimit.getSecondsUntilReset()+2) * 1000l); 
					}
				}

				Query q = new Query(SEARCH_TERM); // Search for tweets that contains this term 
				q.setCount(TWEETS_PER_QUERY); // How many tweets, max, to retrieve 
				q.resultType(Query.RECENT); // Get all tweets 
				q.setLang("en"); // English language tweets, please 

				if (maxID != -1) 
				{ 
					q.setMaxId(maxID - 1); 
				} 

				QueryResult r = twitter.search(q); 

				if (r.getTweets().size() == 0) 
				{ 
					break; // Nothing? We must be done 
				} 

				for (Status s: r.getTweets()) 
				{ 
					totalTweets++; 

					if (maxID == -1 || s.getId() < maxID) 
					{ 
						maxID = s.getId(); 
					}
					list.add(s);
				}  
				searchTweetsRateLimit = r.getRateLimitStatus(); 
			} 
			//bw.close();

			System.out.println("Done");
			
		} 
		catch (Exception e) 
		{ 
			System.out.println("That didn't work well...wonder why?"); 
			e.printStackTrace(); 
		} 
		System.out.printf("\n\nA total of %d tweets retrieved\n", totalTweets);  
		return list;
	}

	public void save(Collection<Status> allTweets,String moviename) throws IOException {
		// TODO Auto-generated method stub
		//Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
	    //mongoLogger.setLevel(Level.SEVERE); 
	    
		MongoClient mongoClient;
	    MongoDatabase database;
	    MongoCollection<Document> collection;
		try {
	        mongoClient = new MongoClient();
	        database = mongoClient.getDatabase("twitter_demo");
	        collection = database.getCollection("twitter_data");
		    
		    File file = new File(moviename + ".txt");
	
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(Status s : allTweets){
				Document doc = new Document("moviename", moviename).
					  append("datasource", "TWITTER").
					  append("location", s.getUser().getLocation()).
					  append("username", s.getUser().getScreenName()).
					  append("usercreation", s.getUser().getCreatedAt()).
					  append("userdesc", s.getUser().getDescription()).
					  append("userfav", s.getUser().getFavouritesCount()).
					  append("userfollow", s.getUser().getFollowersCount()).
					  append("userfrnd", s.getUser().getFriendsCount()).
					  append("userfrnd", s.getUser().getFriendsCount()).
					  append("userlang", s.getUser().getLang()).
					  append("usertimezone", s.getUser().getTimeZone()).
					  append("userstatus", s.getUser().getStatus()).
					  append("userstatuscount", s.getUser().getStatusesCount()).
					  append("userstatus", s.getUser().getStatus()).
					  append("createAt", s.getCreatedAt()).
	                  append("UserRetweet", s.getCurrentUserRetweetId()).
	                  append("favcount", s.getFavoriteCount()).
	                  append("lang", s.getLang()).
	                  append("retweetcount", s.getRetweetCount()).
	                  append("tweetsource", s.getSource()).
	                  append("tweettext", cleanText(s.getText()));
				
				bw.write(doc.toString());
				collection.insertOne(doc);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
}
