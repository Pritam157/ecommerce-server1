package com.patilluxuries.service;

import org.springframework.stereotype.Service;

import com.patilluxuries.exception.UserException;
import com.patilluxuries.model.User;

@Service
public interface UserService {

	
	public User findUserById(Long userid)throws UserException;
	
	public User finduserProfileByJwt(String jwt)throws UserException;
	
	
}
