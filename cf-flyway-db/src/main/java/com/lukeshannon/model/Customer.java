package com.lukeshannon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer workOutId;
	
	@Column(name = "name")
	private String name = new String();

	public Integer getWorkOutId() {
		return workOutId;
	}

	public void setWorkOutId(Integer workOutId) {
		this.workOutId = workOutId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
