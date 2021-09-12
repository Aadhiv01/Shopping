package com.wiley.bean;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name="Movies")
@Entity
@ToString
public class MovieBean {
	
	@Id
	private int id;
	private String name;
	private Time duration;
}
