package com.iv1201.project.recruitment.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplicationDTO {

    private String firstName;
    private String lastName;
 //   private Competence competence;
}
