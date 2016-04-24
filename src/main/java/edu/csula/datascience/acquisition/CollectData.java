package edu.csula.datascience.acquisition;

import java.io.IOException;
import java.util.Collection;

import twitter4j.Status;

public class CollectData {

	public static void main(String[] args) {
		TwitterCollector collector = new TwitterCollector();
		// TODO Auto-generated method stub
		//TwitterSource source = new TwitterSource(Long.MAX_VALUE, "#bigdata");
		String[] movienames = {"#10CloverfieldLane", "#barbershop", "#BatmanvSuperman",
				"#CivilWar","#CompadresMovie","#CriminalMovie","#Deadpool","#EyeintheSky",
				"#fan","#GoTSeason6","#hardcorehenry","#junglebook","#Keanu'","#KungFuPanda3",
				"#MiraclesFromHeaven","#PurpleRain","#TaleofTales","#theboss","#TheHuntsman",
				"#TheMeddler","#TheRevenant","#Zootopia"};
		
		for(int i = 0;i < movienames.length; i++){    
	        Collection<Status> allTweets = collector.mungee(movienames[i]);
	        try {
					collector.save(allTweets,movienames[i]);
	        	collector.save(allTweets,"sabhya");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		amazonCollector amazoncollector = new amazonCollector();
			try {
				Collection<amazonClass> amazonData = amazoncollector.mungee();
				amazoncollector.save(amazonData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		

	}

}
