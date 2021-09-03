package com.wiley.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class UserBean {
	
	private String name;
	@Pattern(regexp="^[a-zA-Z0-9-_]+@[a-zA-Z]+.[a-zA-Z]+$",message="Invalid email")
	@Id
	private String email;
	@Size(min=4,max=10,message="Password must be atleast 4 character")
	private String password;
	private String address;
}

