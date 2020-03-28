package com.me.tweety.pojo;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
	@Id
	private String userid;
	
	@Column(nullable=false, unique = true)
	private String emailid;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String fname;
	
	@Column(nullable=false)
	private String lname;
	
	private String role;
	
	@OneToMany(targetEntity = Tweet.class, cascade=CascadeType.ALL)
	@JoinColumn(name="userid", referencedColumnName = "userid")
	private List<Tweet> tweets;
	
	@OneToMany(targetEntity = Reply.class, cascade = CascadeType.ALL)
	@JoinColumn(name="userid", referencedColumnName="userid")
	private List<Reply> replies;
	
	@OneToMany(targetEntity = Connection.class, cascade = CascadeType.ALL)
	@JoinColumn(name="from_userid", referencedColumnName = "userid")
	private Set<Connection> connections;
	
	@OneToMany(targetEntity = Follow.class, cascade = CascadeType.ALL)
	@JoinColumn(name="follower_userid", referencedColumnName = "userid")
	private Set<Follow> followings;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public List<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	public Set<Connection> getConnections() {
		return connections;
	}
	public void setConnections(Set<Connection> connections) {
		this.connections = connections;
	}
	public Set<Follow> getFollowings() {
		return followings;
	}
	public void setFollowings(Set<Follow> followings) {
		this.followings = followings;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
