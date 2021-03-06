package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LibraryDto;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.persistence.domain.Library;
import com.example.demo.persistence.repo.LibraryRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class LibraryService {

	// this is where our business logic will happen

//	this is also where CRUD logic will take place.

	// implements are crud functionality
	private LibraryRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// we create our mapToDto

	private LibraryDto mapToDTO(Library library) {
		return this.mapper.map(library, LibraryDto.class);
	}

	@Autowired
	public LibraryService(LibraryRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public LibraryDto create(Library library) {
		return this.mapToDTO(this.repo.save(library));
	}

	// read all method
	public List<LibraryDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		// stream - returns a sequential stream considering collection as its source
		// map - used to map each element to its corresponding result
		// :: - for each e.g. Random random = new Random();
		// random.ints().limit(10).forEach(System.out::println);
		// Collectors - used to return a list or string
	}

	// read one method
	public LibraryDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(BookNotFoundException::new));
	}

	// update
	public LibraryDto update(LibraryDto libraryDto, Long id) {
		// check if record exists
		Library toUpdate = this.repo.findById(id).orElseThrow(BookNotFoundException::new);
		// set the record to update
		toUpdate.setName(libraryDto.getName());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(libraryDto, toUpdate);
		// retun the method from repo
		return this.mapToDTO(this.repo.save(toUpdate));

	}

	// what happenes when you try to merge null stuff?

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);// true
		return !this.repo.existsById(id);// true
	}

}
