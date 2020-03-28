package com.me.tweety.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="follow")
public class Follow {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int followid;
	
	String follower_userid;
	String following_userid;
	public int getFollowid() {
		return followid;
	}
	public void setFollowid(int followid) {
		this.followid = followid;
	}
	public String getFollower_userid() {
		return follower_userid;
	}
	public void setFollower_userid(String follower_userid) {
		this.follower_userid = follower_userid;
	}
	public String getFollowing_userid() {
		return following_userid;
	}
	public void setFollowing_userid(String following_userid) {
		this.following_userid = following_userid;
	}
}
