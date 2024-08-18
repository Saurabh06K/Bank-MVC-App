package com.Aurionpro.Model;

public class Customer {
	private int customerid;
	private String firstname;
	private String lasttname;
	private String email;
	private String password;
	
	public Customer() {
		
	}
	
	public Customer(int customerid, String firstname, String lasttname, String email) {
		super();
		this.customerid = customerid;
		this.firstname = firstname;
		this.lasttname = lasttname;
		this.email = email;
	}
	
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLasttname() {
		return lasttname;
	}
	public void setLasttname(String lasttname) {
		this.lasttname = lasttname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
