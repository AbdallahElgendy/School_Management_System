package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByRoleName(String roleName) ; 
}
