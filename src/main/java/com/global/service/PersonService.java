package com.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.global.constants.EasySchoolConstants;
import com.global.model.Person;
import com.global.model.Role;
import com.global.repository.PersonRepository;
import com.global.repository.RoleRepository;
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;

        Person existingPerson = personRepository.readByEmail(person.getEmail());
        if (existingPerson != null) {
            return false; 
        }

        Role role = roleRepository.findByRoleName(EasySchoolConstants.STUDENT_ROLE);
        person.setRole(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        person = personRepository.save(person);

        if (null != person && person.getId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }
}