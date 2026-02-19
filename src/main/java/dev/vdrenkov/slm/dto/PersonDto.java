package dev.vdrenkov.slm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonDto {

  private final int id;
  private final String name;
  private final String surname;
}

