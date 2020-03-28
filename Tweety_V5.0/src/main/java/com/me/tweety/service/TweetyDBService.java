package com.me.tweety.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.me.tweety.dao.ConnectionDao;
import com.me.tweety.dao.FollowDao;
import com.me.tweety.dao.ReplyDao;
import com.me.tweety.dao.TweetDao;
import com.me.tweety.dao.UserDao;
import com.me.tweety.pojo.Connection;
import com.me.tweety.pojo.Follow;
import com.me.tweety.pojo.Reply;
import com.me.tweety.pojo.Search;
import com.me.tweety.pojo.Tweet;
import com.me.tweety.pojo.User;

@Service
public class TweetyDBService {

	UserDao userDao = new UserDao();
	TweetDao tweetDao = new TweetDao();
	ReplyDao replyDao = new ReplyDao();
	ConnectionDao conDao = new ConnectionDao();
	FollowDao followDao = new FollowDao();
	
	public List<User> getUsers() {
		return userDao.getAllUsers();
	}
	
	public User addNewUser(User u) {
		return userDao.addNewUser(u);
	}
	
	public boolean removeUser(String userid) {
		return userDao.removeUser(userid);
	}
	
	public boolean updateUser(String userid, User user) {
		return userDao.update(userid, user);
	}
	
	//returns null f user does not exist
	public User getUser(String searchkey, String type) {
		return userDao.getUser(searchkey, type);
	}
	
	public List<Search> searchUser(String key) {
		return userDao.searchUser(key);
	}
	
	public List<Tweet> getTweets(){
		return tweetDao.getAllTweet();
	}
	
	//returns null for any error
	public User addTweet(Tweet tweet, String userid) {
		return tweetDao.addTweet(tweet, userid);
	}
	
	public boolean removeTweet(int tid) {
		return tweetDao.removeTweet(tid);
	}
	
	//can return null
	public List<Integer> getFollowerTweets(String id) {
		return tweetDao.getFollowerTweets(id);
	}
	
	//can return null
	public List<Integer> getConnectionTweets(String id) {
		return tweetDao.getConnectionTweets(id);
	}

	public User addReply(Reply reply) {
		return replyDao.addReply(reply);
	}
	
	public boolean removeReply(int rid) {
		return replyDao.removeReply(rid);
	}

	public List<Reply> getReplies() {
		return replyDao.getReplies();
	}
	
	public int isConnectionExists(Connection con) {
		return conDao.isConnectionExists(con);
	}
	public User addConnection(Connection con) {
		return conDao.addConnection(con);
	}
	
	public List<Connection> getConnections(){
		return conDao.getAllConnections();
	}
	
	public List<Connection> getMyConnections(String id) {
		return conDao.getMyConnections(id);
	}
	
	public boolean removeConnection(int cid) {
		return conDao.removeConnection(cid);
	}
	
	public boolean acceptRequest(String from_userid, String to_userid) {
		return conDao.acceptConnectionRequest(from_userid, to_userid);
	}
	
	public List<Follow> getAllFollowRecords(){
		return followDao.getAllFollowRecords();
	}
	
	//can return null
	public User followUser(Follow follow) {
		return followDao.followUser(follow);
	}
	
	public boolean unfollow(int fid) {
		return followDao.unfollow(fid);
	}
	
	//can return null
	public List<String> getFollowers(String userid){
		return followDao.getFollwers(userid);
	}
	
	//can return null
	public List<String> getFollowing(String userid){
		return followDao.getFollwing(userid);
	}
	
	//can return -1
	public Integer getFollowId(String follower, String following) {
		return followDao.getFollowId(follower, following);
	}
}
