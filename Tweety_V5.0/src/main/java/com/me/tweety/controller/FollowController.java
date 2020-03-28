package com.me.tweety.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.tweety.pojo.Follow;
import com.me.tweety.pojo.User;
import com.me.tweety.service.TweetyDBService;

@Controller
public class FollowController {
private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
	
	@Autowired
	TweetyDBService dbService;
	
	@RequestMapping(value = "/allFollowRecord", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<Follow> getAllFollowRecords() {
		ArrayList<Follow> list = (ArrayList<Follow>) dbService.getAllFollowRecords();
		return list;
	}
	
	@RequestMapping(value = "/followers/{userid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<String> getFollowers(@PathVariable String userid) {
		if(null == dbService.getFollowers(userid)) {
			return null;
		}
		ArrayList<String> list = (ArrayList<String>) dbService.getFollowers(userid);
		return list;
	}  
	
	@RequestMapping(value = "/following/{userid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<String> getFollowing(@PathVariable String userid) {
		if(null == dbService.getFollowing(userid)) {
			return null;
		}
		ArrayList<String> list = (ArrayList<String>) dbService.getFollowing(userid);
		return list;
	}

	@RequestMapping(value = "/unfollow/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody String unfollow(@PathVariable String id) {
		if (dbService.unfollow((Integer.parseInt(id))))
			return "Unfollowed Successfully";
		else
			return null;
	}
	
	@RequestMapping(value = "/follow", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody User follow(@RequestBody Follow follow) {
		return dbService.followUser(follow);
	}
	
	@RequestMapping(value = "/followId/{follower}/{following}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Integer getFollowId(@PathVariable String follower, @PathVariable String following) {
		return dbService.getFollowId(follower, following);
	}
}
