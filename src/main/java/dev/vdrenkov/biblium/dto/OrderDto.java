package dev.vdrenkov.biblium.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * OrderDto record.
 *
 * @param id
 *     Order identifier.
 * @param clientDto
 *     Client payload.
 * @param booksNames
 *     Ordered book titles.
 * @param issueDate
 *     Order issue date.
 * @param dueDate
 *     Order due date.
 */
public record OrderDto(int id, ClientDto clientDto, List<String> booksNames, LocalDate issueDate, LocalDate dueDate) {
}
