package com.accenture.bindings;

import lombok.Data;

@Data
public class ActivateAccount {
	
	private String emailId;
	
	private String tempPwd;
	
	private String newPwd;
	
	private String confirmPwd;

}
