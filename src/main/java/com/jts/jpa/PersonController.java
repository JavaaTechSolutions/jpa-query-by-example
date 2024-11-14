package com.jts.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;

	@GetMapping("/search")
	public List<Person> search(@RequestParam String firstName, @RequestParam int age) {
		Person examplePerson = new Person();
		examplePerson.setFirstName(firstName);
		examplePerson.setAge(age);

		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues() // Ignore null fields
				.withIgnoreCase() // Ignore case for all string properties
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Match string properties containing the
																				// given value

		Pageable pageable = PageRequest.of(0, 10, Sort.by("lastName").ascending());
		Example<Person> example = Example.of(examplePerson, matcher);
		
		return personRepository.findAll(example, pageable).getContent();
	}
}
