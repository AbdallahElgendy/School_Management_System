package com.global.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Course extends BaseEntity{
	
	private String courseName ; 
	private String fees ; 
	
	@ManyToMany(mappedBy = "courses" , fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
	private Set<Person> persons ; 
}
