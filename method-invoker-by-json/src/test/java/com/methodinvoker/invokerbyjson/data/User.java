package com.methodinvoker.invokerbyjson.data;

public class User {

	private String firstName;
	private Profile profile;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", profile=" + profile + "]";
	}

}
