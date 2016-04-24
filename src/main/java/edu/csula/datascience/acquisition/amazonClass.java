package edu.csula.datascience.acquisition;

public class amazonClass {
	String UserID = "";
	String helpfulness = "";
	String time = "";
	String reviewtxt = "";
	String productID = "";
	String profileName = "";
	String score = "";
	String summary = "";
	String moviename = "";
	
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getHelpfulness() {
		return helpfulness;
	}
	public void setHelpfulness(String helpfulness) {
		this.helpfulness = helpfulness;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getReviewtxt() {
		return reviewtxt;
	}
	public void setReviewtxt(String reviewtxt) {
		this.reviewtxt = reviewtxt;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getMoviename() {
		return moviename;
	}
	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}
	public amazonClass(String userID, String helpfulness, String time,
			String reviewtxt, String productID, String profileName,
			String score, String summary, String moviename) {
		super();
		this.UserID = userID;
		this.helpfulness = helpfulness;
		this.time = time;
		this.reviewtxt = reviewtxt;
		this.productID = productID;
		this.profileName = profileName;
		this.score = score;
		this.summary = summary;
		this.moviename = moviename;
	}
}
