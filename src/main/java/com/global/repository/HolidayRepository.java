package com.global.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.global.model.Holiday;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Integer>{
		

}
