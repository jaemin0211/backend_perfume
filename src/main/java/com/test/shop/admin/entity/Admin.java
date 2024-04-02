package com.test.shop.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Admin {

	@Id
	private String id;
	private String password;
	private String name;
	private String email;

	public boolean isMatchPassword(String password) {
		return this.password.equals(password);
	}

}
