package com.me.tweety.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tweet")
public class Tweet {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int tweetid;
	String tweet;
	String userid;
	Date date;
	public int getTweetid() {
		return tweetid;
	}
	public void setTweetid(int tweetid) {
		this.tweetid = tweetid;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
