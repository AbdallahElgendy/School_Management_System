package com.global.model;


import java.util.Set;

import com.global.annotations.FieldsValueMatch;
import com.global.annotations.PasswordValidator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@FieldsValueMatch.list({
	@FieldsValueMatch(
			field = "pwd"  ,
			fieldMatch = "confirmPwd",
			message = "Please enter identical passwords"
			) ,
	@FieldsValueMatch(
			field = "email" , 
			fieldMatch = "confirmEmail" , 
			message = "Please enter identical emails" 
			)
})
@Entity
public class Person extends BaseEntity{
	
    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient
    private String confirmEmail;

    @NotBlank(message="Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Confirm Password must be at least 5 characters long")
    @Transient
    private String confirmPwd;
    
    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId" , referencedColumnName = "id" , nullable = true)
    private Address address ; 
    
    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    @JoinColumn(name = "roleId" , referencedColumnName = "id" , nullable = false)
    private Role role ; 
    
    @ManyToOne(fetch = FetchType.EAGER , optional = true)
    @JoinColumn(name = "classId" , referencedColumnName = "id" , nullable = true)
    private SchoolClass schoolClass ; 
    
    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    @JoinTable(name = "student_courses" , joinColumns = {@JoinColumn(name = "personId" , referencedColumnName = "id")}
    			, inverseJoinColumns = {@JoinColumn(name = "courseId" , referencedColumnName = "id")})
    private Set<Course> courses ; 
    
    
    
}
