package com.me.tweety.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.me.tweety.pojo.Connection;
import com.me.tweety.pojo.User;

public class ConnectionDao extends DAO {
	public User addConnection(Connection con) {
		try {
			begin();
			Query query = getSession().createQuery("from User u where u.userid=:id");
			query.setParameter("id", con.getFrom_userid());
			User user = (User) query.uniqueResult();

			if (null != user) {
				Connection c = new Connection();
				c.setDate(new Date());
				c.setFrom_userid(con.getFrom_userid());
				c.setTo_userid(con.getTo_userid());
				c.setStatus(0);
				user.getConnections().add(c);
				getSession().update(con.getFrom_userid(), user);
				commit();
				return user;
			} else {
				rollback();
				System.out.println("User does not exist");
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while adding new connection request into the database");
		}
		return new User();
	}

	public int isConnectionExists(Connection con) {
		// 0: request pending 1: already friends -1: good to go

		Query q = getSession()
				.createQuery("select c.status from Connection c where c.from_userid=:fid AND c.to_userid=:tid");
		q.setParameter("fid", con.getTo_userid());
		q.setParameter("tid", con.getFrom_userid());
		
		Query q1 = getSession()
				.createQuery("select c.status from Connection c where c.from_userid=:fid AND c.to_userid=:tid");
		q1.setParameter("fid", con.getFrom_userid());
		q1.setParameter("tid", con.getTo_userid());
		
		if(null == q.uniqueResult() && null == q1.uniqueResult())
			return -1;
		
		return null == q.uniqueResult() ? (Integer) q1.uniqueResult() : (Integer) q.uniqueResult();
	}

	public List<Connection> getAllConnections() {
		try {
			begin();
			@SuppressWarnings("unchecked")
			List<Connection> cons = getSession().createQuery("from Connection").list();
			commit();
			return cons;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list Connections");
		}
		return null;
	}

	public boolean removeConnection(int cid) {
		try {
			begin();
			Connection con = (Connection) getSession().load(Connection.class, cid);
			if (null != con) {
				getSession().delete(con);
				commit();
				return true;
			} else {
				rollback();
				return false;
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while deleting connection from the database");
		}
		return false;
	}

	public boolean acceptConnectionRequest(String from_userid, String to_userid) {
		try {
			begin();
			Query query = getSession().createQuery(
					"select c.connectionid from Connection c where c.from_userid=:fid AND c.to_userid=:tid");
			query.setParameter("fid", from_userid);
			query.setParameter("tid", to_userid);
			int conId = (Integer) query.uniqueResult();

			if (conId > 0) {
				query = getSession().createQuery("update Connection c set c.status=1 where c.connectionid=:cid");
				query.setParameter("cid", conId);
				int res = query.executeUpdate();
				commit();
				System.out.println(res + " Records updated");
				return true;
			} else {
				rollback();
				return false;
			}

		} catch (Exception e) {
			rollback();
			System.out.println("Error while accepting connection for the user");
		}
		return false;
	}

	public List<Connection> getMyConnections(String id) {
		try {
			begin();
			@SuppressWarnings("unchecked")
			Query q = getSession().createQuery("from Connection where (from_userid=:id OR to_userid=:id) AND status = 1");
			q.setParameter("id", id);
			List<Connection> cons = q.list();
			commit();
			return cons;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Could not list my Connections");
		}
		return null;
	}
}
