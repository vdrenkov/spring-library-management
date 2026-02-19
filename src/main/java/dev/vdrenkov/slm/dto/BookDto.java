package dev.vdrenkov.slm.dto;

import java.time.LocalDate;

public record BookDto(int id, String name, LocalDate publishDate, AuthorDto authorDto, int quantity) {

}

