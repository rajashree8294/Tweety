package com.me.tweety.pojo;

public class ConRequest {
	private User user;
	private String requestConfirmation;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRequestConfirmation() {
		return requestConfirmation;
	}
	public void setRequestConfirmation(String requestConfirmation) {
		this.requestConfirmation = requestConfirmation;
	}
}
