package com.patilluxuries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patilluxuries.exception.UserException;
import com.patilluxuries.model.User;
import com.patilluxuries.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getMethodName(@RequestHeader("Authorization")String jwt)throws UserException {
		
		User user=userService.finduserProfileByJwt(jwt);
		
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	

}
