package com.accenture.serviceImp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.accenture.bindings.ActivateAccount;
import com.accenture.bindings.CreateAccount;
import com.accenture.bindings.SignIn;
import com.accenture.entity.User;
import com.accenture.repo.UserRepo;
import com.accenture.service.UserService;
import com.accenture.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public boolean register(CreateAccount createAccount) {
		// TODO Auto-generated method stub
		User user = new User();
		BeanUtils.copyProperties(createAccount, user);
		user.setPwd(generateRandomPwd());
		user.setStatus("In-Active");
		
		 userRepo.save(user);
		
		String subject ="Your Registration Success";
		
		String fileName="REG-EMAIL-BODY.txt";
		
		String body=readMailBody(user.getFullName(), user.getPwd(),fileName);
		
		if(emailUtils.sendEmail(user.getEmailId(), subject, body))
		{
			return true;
		}else
		{
		return false;
		}
	}

	@Override
	public boolean activate(ActivateAccount activateAccount) {
		// TODO Auto-generated method stub
		
		User user =new User();
		user.setEmailId(activateAccount.getEmailId());
		user.setPwd(activateAccount.getTempPwd());
		
		Example<User> example= Example.of(user);
		
		List<User> findAll = userRepo.findAll(example);
		
		if(findAll.isEmpty())
		{
			return false;
		}
		else {
			User user1 = findAll.get(0);
			user1.setPwd(activateAccount.getNewPwd());
			user1.setStatus("Active");
			
			userRepo.save(user1);
			return true;
		}
	}

	@Override
	public List<CreateAccount> getAll() {
		// TODO Auto-generated method stub
		List<User> findAll = userRepo.findAll();
		List<CreateAccount> createdAccount = new ArrayList();
		CreateAccount create=new CreateAccount();
		
		findAll.forEach(account ->{
			BeanUtils.copyProperties(account, create);
			createdAccount.add(create);
		});
		
		return createdAccount;
	}

	@Override
	public CreateAccount getUserById(Integer id) {
		// TODO Auto-generated method stub
		Optional<User> findById = userRepo.findById(id);
		if(findById.isPresent())
		{
			CreateAccount createAccount = new CreateAccount();
			User user=findById.get();
			BeanUtils.copyProperties(user, createAccount);
			return createAccount;
		}
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		try {
			userRepo.deleteById(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean statusChange(Integer id, String status) {
		// TODO Auto-generated method stub
		Optional<User> findById = userRepo.findById(id);
		if(findById.isPresent())
		{
			User user = findById.get();
			user.setStatus(status);
			userRepo.save(user);
			return true;
		}
		return false;
	}

	@Override
	public String login(SignIn signIn) {
		// TODO Auto-generated method stub
		User user= new User();
		user.setEmailId(signIn.getEmailId());
		user.setPwd(signIn.getPwd());
		
		Example<User> example = Example.of(user);
		List<User> findAll = userRepo.findAll(example);
		if(findAll.isEmpty())
		{
			return "Invaild Credential";
		}else {
			User user1=findAll.get(0);
			if(user1.getStatus().equals("Active"))
			{
				return "Success";
			}else {
				return "Account not Activated";
			}
		}
	}

	@Override
	public String recover(String emailId) {
		// TODO Auto-generated method stub
		User user=userRepo.findByEmailId(emailId);
		if(user == null)
		{
			return "Invaild Email";
			
		}
		String subject="Forgot Password";
		String fileName="RECOVER-PWD-BODY.txt";
		String body=readMailBody(user.getFullName(), user.getPwd(), fileName);
		boolean sendEmail = emailUtils.sendEmail(user.getEmailId(), subject, body);
		
		if(sendEmail)
		{
			return "Password sent to your registered email";
		}
		
		
		return null;
	}
	
	private String generateRandomPwd()
	{
		   
		    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		    StringBuilder sb = new StringBuilder();

		    Random random = new Random();

		    int length = 6;

		    for(int i = 0; i < length; i++) {

		      int index = random.nextInt(alphabet.length());
		
		      char randomChar = alphabet.charAt(index);
		      
		      sb.append(randomChar);
		    }

		    return sb.toString();

		  }
	
	private String readMailBody(String fullName,String pwd,String fileName) {
		
		String url="";
		String mailBody=null;
		
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
			StringBuilder builder = new StringBuilder();
			String line = null;
			
			while((line=br.readLine())!=null)	builder.append(line);
				
			
			br.close();
			mailBody=builder.toString();
			mailBody=mailBody.replace("{FULLNAME}", fullName);
			mailBody=mailBody.replace("{TEMP-PWD}", pwd);
			mailBody=mailBody.replace("{URL}", url);
			mailBody=mailBody.replace("{PWD}", pwd);
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mailBody;
	}
		
}


