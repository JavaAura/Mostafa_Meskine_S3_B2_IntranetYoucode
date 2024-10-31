package com.example.IntranetYoucode.Entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {

    private Long id;
    private String name;
    private Integer roomNumber;
}
