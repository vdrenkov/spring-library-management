package dev.vdrenkov.slm.dto;

import java.time.LocalDate;

/**
 * BookDto record.
 *
 * @param id
 *     Book identifier.
 * @param name
 *     Book title.
 * @param publishDate
 *     Publishing date.
 * @param authorDto
 *     Author payload.
 * @param quantity
 *     Available quantity.
 */
public record BookDto(int id, String name, LocalDate publishDate, AuthorDto authorDto, int quantity) {

}
