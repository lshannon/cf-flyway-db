package com.lukeshannon.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 566377239990360714L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="customers_id_seq")
	@SequenceGenerator(name = "customers_id_seq", sequenceName = "customers_id_seq", allocationSize=1)
	@Column(name = "id")
	private Long workOutId;
	
	@Column(name = "name")
	private String name = new String();

	public Long getWorkOutId() {
		return workOutId;
	}

	public void setWorkOutId(Long workOutId) {
		this.workOutId = workOutId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
