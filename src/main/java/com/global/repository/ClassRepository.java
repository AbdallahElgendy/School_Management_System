package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.model.SchoolClass;

@Repository
public interface ClassRepository extends JpaRepository<SchoolClass, Integer>{
	
}
