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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Length(max = 10, min = 10)
    private String username;

    @NotNull
    private String novelTitle;

    @NotNull
    private String novelChapter;

    @NotNull
    private String chapterId;

    @NotNull
    private String time;
}
