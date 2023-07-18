package bg.vdrenkov.dto;

import lombok.Getter;

@Getter
public class ClientDto extends PersonDto {

  private final String phoneNumber;
  private final String email;

  public ClientDto(int id, String name, String surname, String phoneNumber, String email) {
    super(id, name, surname);
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
}