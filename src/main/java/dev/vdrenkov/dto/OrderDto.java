package dev.vdrenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderDto {

  private final int id;
  private final ClientDto clientDto;
  private final List<String> booksNames;
  private final LocalDate issueDate;
  private final LocalDate dueDate;
}

