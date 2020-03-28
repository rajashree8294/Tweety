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

import com.me.tweety.pojo.Reply;
import com.me.tweety.pojo.Tweet;
import com.me.tweety.pojo.User;
import com.me.tweety.service.TweetyDBService;

@Controller
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	TweetyDBService dbService;
	
	@RequestMapping(value="/addReply", method=RequestMethod.POST)
	@ResponseBody
	public User addUser(@RequestBody Reply reply) {
		return dbService.addReply(reply);
	}
	
	@RequestMapping(value="/removeReply/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> removeReply(@PathVariable String id ) {
		if(dbService.removeReply(Integer.parseInt(id)))
			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<String>("invalid input or no such reply exist", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/replies", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<Reply> getUsers() {
		ArrayList<Reply> list = (ArrayList<Reply>)dbService.getReplies();
		return list;
	}
}
