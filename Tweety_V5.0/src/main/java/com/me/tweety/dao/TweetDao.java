package com.me.tweety.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.query.Query;

import com.me.tweety.pojo.Tweet;
import com.me.tweety.pojo.User;

public class TweetDao extends DAO {
	public User addTweet(Tweet tweet, String userid) {
		try {
			begin();
			Query query = getSession().createQuery("from User u where u.userid=:id");
			query.setParameter("id", userid);
			User user = (User) query.uniqueResult();

			if (null != user) {
				Tweet t = new Tweet();
				t.setDate(new Date());
				t.setTweet(tweet.getTweet());
				t.setUserid(userid);
				user.getTweets().add(t);
				getSession().update(userid, user);
				commit();
				return user;
			} else {
				rollback();
				System.out.println("User does not exist");
			}
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new tweet into the database");
		}
		return null;
	}

	public List<Tweet> getAllTweet() {
		try {
			begin();
			@SuppressWarnings("unchecked")
			List<Tweet> tweets = getSession().createQuery("from Tweet order by date desc").list();
			commit();
			return tweets;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list Tweets");
		}
		return null;
	}
	
	public boolean removeTweet(int tid) {
		try {
			begin();
			Tweet tweet = (Tweet) getSession().load(Tweet.class, tid);
			if (null != tweet) {
				getSession().delete(tweet);
				commit();
				return true;
			} else {
				rollback();
				return false;
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while deleting tweet from the database");
		}
		return false;
	}

	public List<Integer> getFollowerTweets(String id) {
		try {
			begin();
			@SuppressWarnings("unchecked")
			Query q = getSession().createQuery("select t.tweetid from Tweet t where t.userid in (select f.following_userid from Follow f left join User u on u.userid = f.follower_userid where u.userid=:id)");
			q.setParameter("id", id);
			List<Integer> tweetids = q.list();
			commit();
			return tweetids;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list Tweets");
		}
		return null;
	}
	
	public List<Integer> getConnectionTweets(String id) {
		try {
			begin();
			@SuppressWarnings("unchecked")
			SQLQuery q = getSession().createSQLQuery("select t.tweetid from Tweet t where t.userid in (SELECT c.to_userid FROM Connection c where (c.from_userid=:id AND c.status = 1) union \r\n" + 
					"SELECT c1.from_userid FROM Connection c1 where (c1.to_userid=:id AND c1.status = 1))");
			q.setParameter("id", id);
			List<Integer> tweetids = q.list();
			commit();
			return tweetids;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list Tweets");
		}
		return null;
	}
}
