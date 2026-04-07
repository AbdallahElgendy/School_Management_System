package com.global.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.global.model.Course;
import com.global.model.Person;
import com.global.model.SchoolClass;
import com.global.repository.ClassRepository;
import com.global.repository.CourseRepository;
import com.global.repository.PersonRepository;

import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ClassRepository classRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private CourseRepository courseRepository  ;

	@GetMapping("/displayClasses")
	public ModelAndView displayClasses() {
	    List<SchoolClass> eazyClasses = classRepository.findAll();	    
	    ModelAndView modelAndView = new ModelAndView("classes.html"); 
	    modelAndView.addObject("eazyClasses", eazyClasses);
	    modelAndView.addObject("eazyClass", new SchoolClass());
	    return modelAndView;
	}

	@PostMapping("/addNewClass")
	public ModelAndView addNewClass( @ModelAttribute("eazyClass") SchoolClass eazyClass) {
		classRepository.save(eazyClass);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
		return modelAndView;
	}

	@RequestMapping("/deleteClass")
	public ModelAndView deleteClass( @RequestParam int id) {
		Optional<SchoolClass> eazyClass = classRepository.findById(id);
		for (Person person : eazyClass.get().getPersons()) {
			person.setSchoolClass(null);
			personRepository.save(person);
		}
		classRepository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
		return modelAndView;
	}

	@GetMapping("/displayStudents")
	public ModelAndView displayStudents(@RequestParam int id, HttpSession session,
			@RequestParam(value = "error", required = false) String error) {
		String errorMessage = null;
		ModelAndView modelAndView = new ModelAndView("students.html");
		Optional<SchoolClass> eazyClass = classRepository.findById(id) ;
		if (eazyClass.get().getPersons() == null) {
			eazyClass.get().setPersons(new HashSet<Person>());
		}
		if (null != eazyClass) {
			modelAndView.addObject("eazyClass", eazyClass.get());
			modelAndView.addObject("person", new Person());
			session.setAttribute("eazyClass", eazyClass.get());
		}
		if (error != null) {
			errorMessage = "Invalid Email entered!!";
			modelAndView.addObject("errorMessage", errorMessage);
		}
		return modelAndView;
	}

	@PostMapping("/addStudent")
	public ModelAndView addStudent(@ModelAttribute("person") Person person, HttpSession session) {
	    ModelAndView modelAndView = new ModelAndView();

	    SchoolClass eazyClass = (SchoolClass) session.getAttribute("eazyClass");
	    eazyClass = classRepository.findById(eazyClass.getId()).orElseThrow();

	    // Find person by email
	    Person personEntity = personRepository.readByEmail(person.getEmail());
	    if (personEntity == null) {
	        modelAndView.setViewName("redirect:/admin/displayStudents?id=" + eazyClass.getId() + "&error=true");
	        return modelAndView;
	    }

	    // Set bidirectional relationship
	    personEntity.setSchoolClass(eazyClass);
	    personRepository.save(personEntity);

	    if (!eazyClass.getPersons().contains(personEntity)) {
	        eazyClass.getPersons().add(personEntity);
	    }

	    classRepository.save(eazyClass); // merge
	    session.setAttribute("eazyClass", eazyClass); // update session

	    modelAndView.setViewName("redirect:/admin/displayStudents?id=" + eazyClass.getId());
	    return modelAndView;
	}

	@GetMapping("/deleteStudent")
	public ModelAndView deleteStudent(@RequestParam int id, HttpSession session) {
		SchoolClass eazyClass = (SchoolClass) session.getAttribute("eazyClass");
		Optional<Person> person = personRepository.findById(id);
		person.get().setSchoolClass(null);
		eazyClass.getPersons().remove(person.get());
		SchoolClass eazyClassSaved = classRepository.save(eazyClass);
		session.setAttribute("eazyClass", eazyClassSaved);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?id=" + eazyClass.getId());
		return modelAndView;
	}
	
	@GetMapping("/displayCourses")
	public ModelAndView displayCourses() {
	//	List<Course> courses = courseRepository.findByOrderByNameDesc() ; 
	    List<Course> courses = courseRepository.findAll(Sort.by("courseName").descending());	    
	    ModelAndView modelAndView = new ModelAndView("courses_secure.html"); 
	    modelAndView.addObject("courses", courses);
	    modelAndView.addObject("course", new Course());
	    return modelAndView;
	}
	
	@PostMapping("/addNewCourse")
	public ModelAndView addNewClass(@ModelAttribute("course") Course course) {
		courseRepository.save(course);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCourses");
		return modelAndView;
	}
	
	@RequestMapping("/deleteCourse")
	public ModelAndView deleteCourse(@RequestParam int id) {
		Optional<Course> course = courseRepository.findById(id);
		for (Person person : course.get().getPersons()) {
			person.setCourses(null);
			personRepository.save(person);
		}
		courseRepository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCourses");
		return modelAndView;
	}
	
	@GetMapping("/viewStudents")
	public ModelAndView displayCourseStudents(@RequestParam int id, HttpSession session,
			@RequestParam(value = "error", required = false) String error) {
		String errorMessage = null;
		ModelAndView modelAndView = new ModelAndView("course_students.html");
		Optional<Course> course = courseRepository.findById(id) ;
		if (null != course) {
			modelAndView.addObject("course", course.get());
			modelAndView.addObject("person", new Person());
			session.setAttribute("course", course.get());
		}
		if (error != null) {
			errorMessage = "Invalid Email entered!!";
			modelAndView.addObject("errorMessage", errorMessage);
		}
		return modelAndView;
	}
	
	@PostMapping("/addStudentToCourse")
	public ModelAndView addStudentToCourse(@ModelAttribute("person") Person person, HttpSession session) {
	    ModelAndView modelAndView = new ModelAndView();

	    Course course = (Course) session.getAttribute("course");
	    course = courseRepository.findById(course.getId()).orElseThrow();

	    // Find person by email
	    Person personEntity = personRepository.readByEmail(person.getEmail());
	    if (personEntity == null) {
	        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getId() + "&error=true");
	        return modelAndView;
	    }

	    // Set bidirectional relationship
	    personEntity.getCourses().add(course);
	    personRepository.save(personEntity);

	    if (!course.getPersons().contains(personEntity)) {
	        course.getPersons().add(personEntity);
	    }

	    courseRepository.save(course); // merge
	    session.setAttribute("course", course); // update session

	    modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getId());
	    return modelAndView;
	}

	@GetMapping("/deleteStudentFromCourse")
	public ModelAndView deleteStudentFromCourse(@RequestParam int id, HttpSession session) {
		Course course = (Course) session.getAttribute("course");
		Optional<Person> person = personRepository.findById(id);
		person.get().getCourses().remove(course);
		course.getPersons().remove(person.get());
		Course courseSaved = courseRepository.save(course);
		session.setAttribute("course", courseSaved);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?id=" + course.getId());
		return modelAndView;
	}
	


}
