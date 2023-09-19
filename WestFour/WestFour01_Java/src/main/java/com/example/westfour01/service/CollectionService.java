package com.example.westfour01.service;

import com.example.westfour01.dao.CollectionRepository;
import com.example.westfour01.entity.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author CXQ
 * @date 2022/2/1
 */

@Transactional(rollbackOn = Exception.class)
@Service
public class CollectionService {


    @Autowired
    private final CollectionRepository repository = null;


    public void insert(Collection collection) {
        repository.save(collection);
    }

    public boolean exist(Collection collection) {
        return repository.existsCollectionByUsernameAndNovelTitle(collection.getUsername(), collection.getNovelTitle());
    }

    public List<String> findAll(String username) {
        return repository.findAllTitleByUsername(username);
    }

    public Integer clear(String username) {
        return repository.deleteAllByUsername(username);
    }

    public void delete(Collection collection) {
        repository.deleteCollectionByUsernameAndNovelTitle(collection.getUsername(), collection.getNovelTitle());
    }


}
