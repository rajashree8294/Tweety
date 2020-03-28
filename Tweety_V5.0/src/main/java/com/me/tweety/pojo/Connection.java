package com.me.tweety.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="connection")
public class Connection {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int connectionid;
	
	String from_userid;
	String to_userid;
	int status;
	Date date;
	public int getConnectionid() {
		return connectionid;
	}
	public void setConnectionid(int connectionid) {
		this.connectionid = connectionid;
	}
	public String getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(String from_userid) {
		this.from_userid = from_userid;
	}
	public String getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(String to_userid) {
		this.to_userid = to_userid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
