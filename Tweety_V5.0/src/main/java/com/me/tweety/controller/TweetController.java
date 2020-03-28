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

import com.me.tweety.pojo.Tweet;
import com.me.tweety.pojo.User;
import com.me.tweety.service.TweetyDBService;

@Controller
public class TweetController {
private static final Logger logger = LoggerFactory.getLogger(TweetController.class);
	
	@Autowired
	TweetyDBService dbService;

	@RequestMapping(value = "/addTweet/{id}", method = RequestMethod.PUT, produces="application/json")
	public @ResponseBody User getUsers(@PathVariable String id, @RequestBody Tweet tweet) {
		return dbService.addTweet(tweet, id);
	}
	
	@RequestMapping(value = "/removeTweet/{id}", method = RequestMethod.DELETE, produces="application/json")
	public @ResponseBody ResponseEntity<String> removeTweet(@PathVariable String id) {
		if(dbService.removeTweet(Integer.parseInt(id)))
			return new ResponseEntity<String>("Tweet Removed Successfully",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error while removing Tweet",HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/tweets", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<Tweet> getAllTweets() {
		ArrayList<Tweet> list = (ArrayList<Tweet>)dbService.getTweets();
		return list;
	}
	
	@RequestMapping(value = "/followingTweets/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<Integer> getFollowersTweet(@PathVariable String id) {
		ArrayList<Integer> list = (ArrayList<Integer>)dbService.getFollowerTweets(id);
		return list;
	}
	
	@RequestMapping(value = "/connectionTweets/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<Integer> getConnectionsTweet(@PathVariable String id) {
		ArrayList<Integer> list = (ArrayList<Integer>)dbService.getConnectionTweets(id);
		return list;
	}
}
