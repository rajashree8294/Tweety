package com.me.tweety.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reply")
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int replyid;
	String reply;
	int tweetid;
	String userid;
	
	public int getReplyid() {
		return replyid;
	}
	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public int getTweetid() {
		return tweetid;
	}
	public void setTweetid(int tweetid) {
		this.tweetid = tweetid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
