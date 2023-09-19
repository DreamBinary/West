package com.example.westfour01.service;

import com.example.westfour01.entity.History;
import com.example.westfour01.dao.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author CXQ
 * @date 2022/2/1
 */


@Service
@Transactional(rollbackOn = Exception.class)
public class HistoryService {

    @Autowired
    private final HistoryRepository repository = null;

    public List<History> findAll(String username) {
        return repository.findAllByUsername(username);
    }

    public void insert(History history) {
        repository.save(history);
    }

    public Integer clear(String username) {
        return repository.deleteAllByUsername(username);
    }

}
