package com.global.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.global.model.Holiday;
import com.global.repository.HolidayRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HolidayController {
	@Autowired
	private HolidayRepository holidayRepository;

	@GetMapping("/holidays/{display}")
	public String displayHoliday(@PathVariable String display, Model model) {
		if (display != null && display.equals("all")) {
			model.addAttribute("festival", true);
			model.addAttribute("federal", true);
		} else if (display.equals("festival")) {
			model.addAttribute("festival", true);
		} else {
			model.addAttribute("federal", true);
		}

		Iterable<Holiday> holidays = holidayRepository.findAll();
		List<Holiday> holidaysList = StreamSupport.stream(holidays.spliterator(), false).collect(Collectors.toList()) ;

		Holiday.Type[] types = Holiday.Type.values();
		for (Holiday.Type type : types) {
			model.addAttribute(type.toString(),
					(holidaysList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
		}
		return "holidays.html";
	}
}
