package com.accenture.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateAccount {
	
	private String fullName;
	
	private String emailId;
	
	private long mobileNumber;
	
	private String gender;
	
	private LocalDate dob;
	
	private long ssn;

}
