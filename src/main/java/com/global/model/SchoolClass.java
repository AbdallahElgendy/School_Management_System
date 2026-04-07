package com.global.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "class")
public class SchoolClass extends BaseEntity{
	
	@NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
	private String className ;
	
	@OneToMany(mappedBy = "schoolClass" , cascade = CascadeType.PERSIST 
			, fetch = FetchType.LAZY , targetEntity = Person.class)
	private Set<Person> persons  ; 
	
}
