package com.patilluxuries.controller;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patilluxuries.config.JwtProvider;
import com.patilluxuries.exception.UserException;
import com.patilluxuries.model.Cart;
import com.patilluxuries.model.User;
import com.patilluxuries.repository.UserRepository;
import com.patilluxuries.request.LoginRequest;
import com.patilluxuries.responce.AuthResponce;
import com.patilluxuries.service.CartService;
import com.patilluxuries.service.CustomUserServiceImplimentation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserServiceImplimentation customUserServiceImplimentation;
	
	@Autowired
	private CartService cartService;
   
	@PostMapping("/signup")
	public ResponseEntity<AuthResponce> createuserHandler(@RequestBody User user )throws UserException{
		String email =user.getEmail();
		String password=user.getPassword();
		String fisrtname=user.getFirstName();
		String lastName=user.getLastName();
		
		User isEmailExist=userRepository.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new UserException("email is Already Used With Another Account");
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(fisrtname);
		createdUser.setLastName(lastName);
		
		User saveduser=userRepository.save(createdUser);
		
		Cart cart =cartService.createCart(saveduser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(saveduser.getEmail(), saveduser.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		
		
		AuthResponce authResponce=new AuthResponce();
		
		authResponce.setJwt(token);
		authResponce.setMessage("Signup Success");
		
		
		
		return new ResponseEntity<AuthResponce>(authResponce,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponce> loginUserHandler(@RequestBody LoginRequest loginRequest){
		
		String username=loginRequest.getEmail();
		
		String password=loginRequest.getPassword();
		
		Authentication authentication=authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
			
        String token=jwtProvider.generateToken(authentication);
		
		
		AuthResponce authResponce=new AuthResponce();
	
		
		authResponce.setJwt(token);
		authResponce.setMessage("Signin Success");
		
		
		
		return new ResponseEntity<AuthResponce>(authResponce,HttpStatus.ACCEPTED);
		 
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails=customUserServiceImplimentation.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
		    throw new BadCredentialsException("Invalid Password...");	
		}
		
		
		
		
		return new UsernamePasswordAuthenticationToken( userDetails,null,userDetails.getAuthorities());
	}
	
	
	
	
}
