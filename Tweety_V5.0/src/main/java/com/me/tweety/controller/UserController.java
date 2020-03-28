package com.me.tweety.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.tweety.pojo.Search;
import com.me.tweety.pojo.User;
import com.me.tweety.service.TweetyDBService;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	TweetyDBService dbService;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<User> getUsers(Model model) {
		ArrayList<User> list = (ArrayList<User>)dbService.getUsers();
		model.addAttribute("usersList", list);
		return list;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public User addUser(@RequestBody User user) {
		System.out.println("User: "+ user.getEmailid());
		return dbService.addNewUser(user);
	}
	
	@RequestMapping(value="/remove/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> removeUser(@PathVariable String id) {
		System.out.println("User id to be removed: "+ id);
		if(dbService.removeUser(id))
			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<String>("invalid input or no such user exist", HttpStatus.OK);
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User user){
		System.out.println("User id to be updated: "+ id);
		if(dbService.updateUser(id, user))
			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<String>("invalid input or no such user exist", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{searchkey}/{type}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<User> getUser(@PathVariable String searchkey, @PathVariable String type) {
		System.out.println("Search Key: "+ searchkey + " Type: "+ type);
		User user = (User)dbService.getUser(searchkey, type);
		if(user == null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/{searchkey}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<List<Search>> searchUser(@PathVariable String searchkey) {
		System.out.println("Search Key: "+ searchkey);
		List<Search> user = dbService.searchUser(searchkey);
		if(user == null) {
			return new ResponseEntity<List<Search>>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Search>>(user, HttpStatus.OK);
	}
}
