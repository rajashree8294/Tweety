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

import com.me.tweety.pojo.ConRequest;
import com.me.tweety.pojo.Connection;
import com.me.tweety.pojo.User;
import com.me.tweety.service.TweetyDBService;

@Controller
public class ConnectionController {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionController.class);

	@Autowired
	TweetyDBService dbService;

	@RequestMapping(value = "/addConnection", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<ConRequest> addConnection(@RequestBody Connection con) {
		int flag = dbService.isConnectionExists(con);
		if (flag == 1) {
			ConRequest cr = new ConRequest();
			cr.setRequestConfirmation("You are already connected!");
			cr.setUser(new User());
			return new ResponseEntity<ConRequest>(cr, HttpStatus.CONFLICT);
		} else if (flag == 0) {
			ConRequest cr = new ConRequest();
			cr.setRequestConfirmation("Connection Request is pending!");
			cr.setUser(new User());
			return new ResponseEntity<ConRequest>(cr, HttpStatus.CONFLICT);
		} else {
			User user = dbService.addConnection(con);
			if(null == user.getUserid()) {
				ConRequest cr = new ConRequest();
				cr.setRequestConfirmation("Error while adding connection into the databse!");
				cr.setUser(new User());
				return new ResponseEntity<ConRequest>(cr, HttpStatus.BAD_REQUEST);
			}
			ConRequest cr = new ConRequest();
			cr.setRequestConfirmation("Connection Request Sent Successfully!");
			cr.setUser(user);
			return new ResponseEntity<ConRequest>(cr, HttpStatus.ACCEPTED);
		}
	}

	@RequestMapping(value = "/connections", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<Connection> getAllConnections() {
		ArrayList<Connection> list = (ArrayList<Connection>) dbService.getConnections();
		return list;
	}

	@RequestMapping(value = "/connections/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<Connection> myConnections(@PathVariable String id) {
		ArrayList<Connection> list = (ArrayList<Connection>) dbService.getMyConnections(id);
		return list;
	}
	
	@RequestMapping(value = "/removeConnection/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody ResponseEntity<String> removeConnection(@PathVariable String id) {
		if (dbService.removeConnection(Integer.parseInt(id)))
			return new ResponseEntity<String>("Connection Removed Successfully", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error while removing Connection", HttpStatus.BAD_GATEWAY);
	}

	@RequestMapping(value = "/acceptReq/{fid}/{tid}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody ResponseEntity<String> acceptConnectionRequest(@PathVariable String fid,
			@PathVariable String tid) {
		if (dbService.acceptRequest(fid, tid))
			return new ResponseEntity<String>("Connection Request Accepted Successfully", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error while accepting the Connection Request", HttpStatus.BAD_GATEWAY);
	}
}
