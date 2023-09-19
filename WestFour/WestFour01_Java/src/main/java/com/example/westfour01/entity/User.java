package com.example.westfour01.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author CXQ
 * @date 2022/1/26
 */

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @NotNull
    @Length(max = 10, min = 10)
    private String username;

    @NotNull
    @Length(max = 10, min = 10)
    private String password;
}
