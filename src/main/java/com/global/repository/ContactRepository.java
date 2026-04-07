package com.global.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.model.Contact;

import jakarta.transaction.Transactional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{
	

	@Query("Select c from Contact c where c.status = :status")
	List<Contact> findByStatusWithQuery(String status) ; 
	//@Query("Select c from Contact c where c.status = :status")	
	//@Query(value = "select * from CONTACT c WHERE c.STATUS=:status"  , nativeQuery = true)
	//The function is working correctly with or without any of these queries
	Page<Contact> findByStatus(String status , Pageable pageable) ; 
	
	
	@Transactional
	@Modifying
	@Query("Update Contact c SET c.status=?1 WHERE c.id=?2")
	int updateStatusById(String status , int id) ; 
}

















