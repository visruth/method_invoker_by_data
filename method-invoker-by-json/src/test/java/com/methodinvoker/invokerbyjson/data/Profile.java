package com.methodinvoker.invokerbyjson.data;

public class Profile {
	
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Profile [address=" + address + "]";
	}
	
	
	
}
