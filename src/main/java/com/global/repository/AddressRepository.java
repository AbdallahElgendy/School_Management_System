package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
