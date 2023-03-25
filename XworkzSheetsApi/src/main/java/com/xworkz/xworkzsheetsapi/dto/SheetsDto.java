package com.xworkz.xworkzsheetsapi.dto;

public class SheetsDto {
	
	private String name;
	private String email;
	private String phoneNumber;
	
	public SheetsDto() {
		super();
	}
	
	

    public SheetsDto(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "SheetsDto [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}
	
	
	
	

}
