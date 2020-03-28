package com.me.tweety.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.me.tweety.pojo.Follow;
import com.me.tweety.pojo.User;

public class FollowDao extends DAO {

	public User followUser(Follow follow) {
		try {
			begin();
			Query query = getSession().createQuery("from User u where u.userid=:id");
			query.setParameter("id", follow.getFollower_userid());
			User user = (User) query.uniqueResult();

			if (null != user) {
				Follow f = new Follow();
				f.setFollower_userid(follow.getFollower_userid());
				f.setFollowing_userid(follow.getFollowing_userid());
				user.getFollowings().add(f);
				getSession().update(follow.getFollower_userid(), user);
				commit();
				return user;
			} else {
				rollback();
				System.out.println("User does not exist");
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while following the user");
		}
		return null;
	}

	public boolean unfollow(int followId) {
		try {
			begin();
			Follow follow = (Follow) getSession().load(Follow.class, followId);
			if (null != follow) {
				getSession().delete(follow);
				commit();
				return true;
			} else {
				rollback();
				System.out.println("User does not exist");
				return false;
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while deleting connection from the database");
		}
		return false;
	}

	public List<Follow> getAllFollowRecords() {
		try {
			begin();
			@SuppressWarnings("unchecked")
			List<Follow> followings = getSession().createQuery("from Follow").list();
			commit();
			return followings;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list Follow records");
		}
		return new ArrayList<Follow>();
	}
	
	public List<String> getFollwers(String userid){
		try {
			begin();
			Query q = getSession().createQuery("select follower_userid from Follow where following_userid=:id");
			q.setParameter("id", userid);
			if(null == q.list()) {
				rollback();
				System.out.println("No user exists with this id");
				return null;
			}
			commit();
			return q.list();
		}catch (Exception e) {
			rollback();
			System.out.println("Error while retrieving followers");
		}
		return null;
	}
	
	public List<String> getFollwing(String userid){
		try {
			begin();
			Query q = getSession().createQuery("select following_userid from Follow where follower_userid=:id");
			q.setParameter("id", userid);
			if(null == q.list()) {
				rollback();
				System.out.println("No user exists with this id");
				return null;
			}
			commit();
			return q.list();
		}catch (Exception e) {
			rollback();
			System.out.println("Error while retrieving following");
		}
		return null;
	}

	public Integer getFollowId(String follower, String following) {
		try {
			begin();
			Query q = getSession().createQuery("select followid from Follow where (follower_userid=:follower AND following_userid=:following)");
			q.setParameter("follower", follower);
			q.setParameter("following", following);
			if(null == q.uniqueResult()) {
				rollback();
				System.out.println("No user exists with this id");
			}
			commit();
			return (Integer)q.uniqueResult();
		}catch (Exception e) {
			rollback();
			System.out.println("Error while retrieving following");
		}
		return -1;
	}
}
