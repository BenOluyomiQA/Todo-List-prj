package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class LibraryDto {

	public Long id;
	public String name;

	private List<BookDto> books = new ArrayList<>();
}
