package com.accenture.service;

import java.util.List;

import com.accenture.bindings.ActivateAccount;
import com.accenture.bindings.CreateAccount;
import com.accenture.bindings.SignIn;
import com.accenture.entity.User;


public interface UserService {
	
	public boolean register(CreateAccount createAccount);
	
	public boolean activate(ActivateAccount activateAccount);
	
	public List<CreateAccount> getAll();
	
	public CreateAccount getUserById(Integer id);
	
	public boolean delete(Integer Id);
	
	public boolean statusChange(Integer id,String status);
	
	public String login(SignIn signIn);
	
	public String recover(String emailId);

}
