package edu.csula.datascience.acquisition;

/**
 * A simple model for testing
 */
public class SimpleModel {
	private final String moviename;
    private final String datasource;
    private final String location;
    private final String username;
    private final String usercreation;
    private final String userdesc;
    private final String userfav;
    private final String userfollow;
    private final String userfrnd;
    private final String userlang;
    private final String usertimezone;
    private final String userstatus;
    private final String createAt;
    private final String userRetweet;
    private final String favcount;
    private final String lang;
    private final String retweetcount;
    private final String tweetsource;
    private final String tweettext;

    

    public SimpleModel(String moviename, String datasource, String location,
			String username, String usercreation, String userdesc,
			String userfav, String userfollow, String userfrnd,
			String userlang, String usertimezone, String userstatus,
			String createAt, String userRetweet, String favcount, String lang,
			String retweetcount, String tweetsource, String tweettext) {
		super();
		this.moviename = moviename;
		this.datasource = datasource;
		this.location = location;
		this.username = username;
		this.usercreation = usercreation;
		this.userdesc = userdesc;
		this.userfav = userfav;
		this.userfollow = userfollow;
		this.userfrnd = userfrnd;
		this.userlang = userlang;
		this.usertimezone = usertimezone;
		this.userstatus = userstatus;
		this.createAt = createAt;
		this.userRetweet = userRetweet;
		this.favcount = favcount;
		this.lang = lang;
		this.retweetcount = retweetcount;
		this.tweetsource = tweetsource;
		this.tweettext = tweettext;
	}



	public String getMoviename() {
		return moviename;
	}



	public String getDatasource() {
		return datasource;
	}



	public String getLocation() {
		return location;
	}



	public String getUsername() {
		return username;
	}



	public String getUsercreation() {
		return usercreation;
	}



	public String getUserdesc() {
		return userdesc;
	}



	public String getUserfav() {
		return userfav;
	}



	public String getUserfollow() {
		return userfollow;
	}



	public String getUserfrnd() {
		return userfrnd;
	}



	public String getUserlang() {
		return userlang;
	}



	public String getUsertimezone() {
		return usertimezone;
	}



	public String getUserstatus() {
		return userstatus;
	}



	public String getCreateAt() {
		return createAt;
	}



	public String getUserRetweet() {
		return userRetweet;
	}



	public String getFavcount() {
		return favcount;
	}



	public String getLang() {
		return lang;
	}



	public String getRetweetcount() {
		return retweetcount;
	}



	public String getTweetsource() {
		return tweetsource;
	}



	public String getTweettext() {
		return tweettext;
	}



	public static SimpleModel build(MockData data) {
        return new SimpleModel(data.getMoviename(), data.getDatasource(), data.getLocation(), 
        		data.getUsername(), data.getUsercreation(), data.getUserdesc(), data.getUserfav(),
        		data.getUserfollow(), data.getUserfrnd(), data.getUserlang(),
        		data.getUsertimezone(), data.getUserstatus(), data.getCreateAt(), data.getUserRetweet(),
        		data.getFavcount(), data.getLang(), data.getRetweetcount(), data.getTweetsource(), data.getTweettext());
    }
}
