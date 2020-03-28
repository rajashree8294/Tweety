package com.me.tweety.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.me.tweety.pojo.Search;
import com.me.tweety.pojo.User;

public class UserDao extends DAO {
	public User addNewUser(User u) {
		try {
			begin();
			User user = new User();
			user.setUserid(u.getUserid());
			user.setEmailid(u.getEmailid());
			user.setPassword(u.getPassword());
			user.setFname(u.getFname());
			user.setLname(u.getLname());
			user.setRole(u.getRole());
			getSession().save(u);
			commit();
			return user;
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return null;
	}

	public boolean removeUser(String userid) {
		try {
			begin();
			User user = (User) getSession().load(User.class, userid);
			if (null != user) {
				getSession().delete(user);
				commit();
				return true;
			} else {
				rollback();
				return false;
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while deleting user from the database");
		}
		return false;
	}

	public List<User> getAllUsers() {
		try {
			begin();
			@SuppressWarnings("unchecked")
			List<User> users = getSession().createQuery("from User").list();
			commit();
			return users;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list User");
		}
		return new ArrayList<User>();
	}

	public boolean update(String userid, User user) {
		try {
			begin();
			User u = (User) getSession().load(User.class, userid);
			if (null != u) {
				getSession().update(userid, user);
				commit();
				return true;
			} else {
				rollback();
				return false;
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while updating user into the database");
		}
		return false;
	}

	public User getUser(String searchkey, String type) {
		try {
			Query query;
			begin();
			if(type.equalsIgnoreCase("email"))
				query = getSession().createQuery("from User u where u.emailid=:id");
			else
				query = getSession().createQuery("from User u where u.userid=:id");
			query.setParameter("id", searchkey);
			User user = (User) query.uniqueResult();

			if (null != user) {
				commit();
				return user;
			} else {
				rollback();
				System.out.println("User does not exist");
			}
		} catch (Exception e) {
			rollback();
			System.out.println("Error while finding the user using email and password");
		}
		return null;
	}
	
	public List<Search> searchUser(String key) {
		try {
			begin();
			SQLQuery q = getSession().createSQLQuery("SELECT userid, fname, lname FROM User u where (u.userid like :key) OR (u.fname like :key)");
			q.setParameter("key", key+"%");
			List<Search> searches = q.list();
			System.out.println(searches);
			commit();
			return searches;
		} catch (Exception e) {
			rollback();
			System.out.println("Error while searching the user");
		}
		return null;
	}
}
