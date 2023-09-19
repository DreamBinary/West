package com.example.westfour01.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * @author CXQ
 * @date 2022/2/1
 */

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Length(max = 10, min = 10)
    private String username;

    @NotNull
    private String novelTitle;
}
