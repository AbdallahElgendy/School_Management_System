package com.global.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.global.constants.EasySchoolConstants;
import com.global.model.Contact;
import com.global.repository.ContactRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepository ; 
	
	public boolean saveMessageDetails(Contact contact) {
		boolean isSaved = false ; 
		contact.setStatus(EasySchoolConstants.OPEN);
		Contact savedContact = contactRepository.save(contact) ; 
		if (null != savedContact && savedContact.getId() > 0) {
			isSaved = true ; 
		}
		return isSaved;
	}
	
	public Page<Contact> findMsgWithOpenStatus(int pageNum ,String sortField, String sortDir){
		int pageSize = 5 ; 
		Pageable pageable = PageRequest.of(pageNum-1, pageSize, sortDir
				.equals("asc") ? Sort.by(sortField).ascending():Sort.by(sortField).descending()) ; 
		Page<Contact> contactPage = contactRepository.findByStatus(EasySchoolConstants.OPEN , pageable) ;  
		return contactPage ;
	}
	
	public boolean updateMsgStatus(int contactId) {
		boolean isUpdated =  false ; 
		int rows = contactRepository.updateStatusById(EasySchoolConstants.CLOSE, contactId) ; 
		if (rows > 0) {
			isUpdated = true ; 
		}
		return isUpdated;
	}
}













