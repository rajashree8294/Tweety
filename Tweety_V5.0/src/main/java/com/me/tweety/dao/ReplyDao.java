package com.me.tweety.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.me.tweety.pojo.Reply;
import com.me.tweety.pojo.Tweet;
import com.me.tweety.pojo.User;

public class ReplyDao extends DAO {
	public User addReply(Reply reply) {
		try {
			begin();
			Query query = getSession().createQuery("from User u where u.userid=:id");
			query.setParameter("id", reply.getUserid());
			User user = (User) query.uniqueResult();

			query = getSession().createQuery("from Tweet t where t.tweetid=:tid");
			query.setParameter("tid", reply.getTweetid());
			Tweet tweet = (Tweet) query.uniqueResult();

			if (null != user && null != tweet) {
				Reply r = new Reply();
				r.setUserid(reply.getUserid());
				r.setTweetid(reply.getTweetid());
				r.setReply(reply.getReply());
				user.getReplies().add(r);
				getSession().save(user);
				commit();
				return user;
			} else {
				rollback();
				System.out.println("User/Tweet does not exist");
			}
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new reply into the database");
		}
		return new User();
	}

	public boolean removeReply(int rid) {
		try {
			begin();
			Reply reply = (Reply) getSession().load(Reply.class, rid);
			if (null != reply) {
				getSession().delete(reply);
				commit();
				return true;
			} else {
				rollback();
				return false;
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while deleting reply from the database");
		}
		return false;
	}

	public List<Reply> getReplies() {
		try {
			begin();
			@SuppressWarnings("unchecked")
			List<Reply> replies = getSession().createQuery("from Reply").list();
			commit();
			return replies;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list Replies");
		}
		return new ArrayList<Reply>();
	}
}
