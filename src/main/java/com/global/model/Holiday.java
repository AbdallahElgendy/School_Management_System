package com.global.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Holiday extends BaseEntity{

	private String day ; 
	private String reason ; 
	@Enumerated(EnumType.STRING)
	private Type type ; 
	
	public enum Type{
		FESTIVAL , FEDERAL
	}

	
}
