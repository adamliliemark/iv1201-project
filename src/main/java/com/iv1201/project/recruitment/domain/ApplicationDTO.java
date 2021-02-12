package com.iv1201.project.recruitment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A Data Transfer Object class used for transferring the first name and the
 * last name of users matching a searched for application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplicationDTO {
    private String firstName;
    private String lastName;
}
