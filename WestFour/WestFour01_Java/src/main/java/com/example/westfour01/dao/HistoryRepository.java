package com.example.westfour01.dao;

import com.example.westfour01.entity.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author CXQ
 * @date 2022/2/1
 */
@Repository
public interface HistoryRepository extends CrudRepository<History, LocalDate> {

    List<History> findAllByUsername(String username);

    Integer deleteAllByUsername(String username);

}
