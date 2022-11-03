package com.accenture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.bindings.ActivateAccount;
import com.accenture.bindings.CreateAccount;
import com.accenture.bindings.SignIn;
import com.accenture.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registeration(@RequestBody CreateAccount createAccount)
	{
		boolean register = userService.register(createAccount);
		
		if(register)
		{
			return new ResponseEntity<String>("Registration Success",HttpStatus.CREATED);
		}else
		{
			return new ResponseEntity<String>("Registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/activate")
	public ResponseEntity<String> activate(@RequestBody ActivateAccount activateAccount)
	{
		boolean activate = userService.activate(activateAccount);
		
		if(activate)
		{
			return new ResponseEntity<String>("Account Activated",HttpStatus.OK);
		}else
		{
			return new ResponseEntity<String>("Invalid Temporary Password",HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/users")
	public ResponseEntity<List<CreateAccount>> getAllUsers()
	{
		List<CreateAccount> all = userService.getAll();
		return new ResponseEntity<>(all,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<CreateAccount> getUserById(@PathVariable("id") Integer id)
	{
		CreateAccount userById = userService.getUserById(id);
		return new ResponseEntity<>(userById,HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer id)
	{
		 boolean delete = userService.delete(id);
		 if(delete)
			{
				return new ResponseEntity<String>("Account Deleted Successfully",HttpStatus.OK);
			}else
			{
				return new ResponseEntity<String>("Unable to delete Account",HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("/user/{userId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable("id") Integer id,@PathVariable("status") String status)
	{
		  boolean statusChange = userService.statusChange(id, status);
		 if(statusChange)
			{
				return new ResponseEntity<String>("Status Changed",HttpStatus.OK);
			}else
			{
				return new ResponseEntity<String>("Unable to Change the Status",HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody SignIn singIn)
	{
		 String login = userService.login(singIn);
		return new ResponseEntity<>(login,HttpStatus.OK);
	}
	
	@GetMapping("/forgotpwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable("email") String email)
	{
		String recover = userService.recover(email);
		return new ResponseEntity<>(recover,HttpStatus.OK);
	}
	
}
