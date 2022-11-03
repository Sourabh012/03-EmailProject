package com.accenture.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


@Data
@Entity
@Table(name="User_Master")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String fullName;
	
	private String emailId;
	
	private long mobileNumber;
	
	private LocalDate dob;
	
	private String gender;
	
	private long ssn;
	
	private String pwd;
	
	private String status;
	
	private String createdby;
	
	private String updatedby;
	
	@Column(name="Create_Date",updatable = false)
	@CreationTimestamp
	private LocalDate createDate;
	
	@Column(name="Updated_Data",insertable = false)
	@UpdateTimestamp
	private LocalDate updateDate;
	

}
